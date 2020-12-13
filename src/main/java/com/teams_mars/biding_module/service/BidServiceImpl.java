package com.teams_mars.biding_module.service;

import com.teams_mars.biding_module.domain.*;
import com.teams_mars.biding_module.repository.*;
import com.teams_mars._global_domain.User;
import com.teams_mars.customer_module.service.CustomerService;
import com.teams_mars.seller_module.domain.Product;
import com.teams_mars.seller_module.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@EnableScheduling
public class BidServiceImpl implements BidService {

    @Autowired
    WithHeldRepository withHeldRepository;

    @Autowired
    BidRepository bidRepository;

    @Autowired
    PayPalAccountRepository payPalAccountRepository;

    @Autowired
    CustomerService customerService;

    @Autowired
    ProductService productService;

    @Autowired
    BidWonRepository bidWonRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    PayPalService payPalService;

    @Override
    public String placeBid(int customerId, int productId, double price) {
        boolean customerVerified = customerService.isCustomerVerified(customerId);
        boolean isSeller = customerService.isSeller(customerId, productId);
        boolean hasMadeDeposit = hasMadeDeposit(customerId, productId);

        if (!customerVerified) return "Not eligible, please get account verified.";
        if (isSeller) return "Seller can't bid on own product";
        if (!hasMadeDeposit) return "Customer must make deposit first";

        User user = customerService.getCustomer(customerId).orElseThrow();
        Product product = productService.getProduct(productId).orElseThrow();
        boolean isPriceLower = getHighestBidPrice(productId) >= price;
        boolean isPriceLowerThanStartPrice = product.getStartingPrice() >= price;

        if (isPriceLower && isPriceLowerThanStartPrice) return "Price must be higher than current highest";

        Bid bid = new Bid();
        bid.setProduct(product);
        bid.setCustomer(user);
        bid.setPrice(price);
        bid.setBidDate(LocalDateTime.now());

        bidRepository.save(bid);
        return "Bid saved";
    }

    @Override
    public boolean hasMadeDeposit(int customerId, int productId) {
        return withHeldRepository
                .findByCustomer_UserIdAndProductHeld_ProductId(customerId, productId)
                .isPresent();
    }

    @Override
    public List<Bid> getCustomerBidHistory(int customerId) {
        return bidRepository.findAllByCustomer_UserIdOrderByBidDateDesc(customerId);
    }

    @Override
    public List<Bid> getProductBidHistory(int productId) {
        return bidRepository.findAllByProduct_ProductIdOrderByBidDate(productId);
    }

    @Override
    public double getHighestBidPrice(int productId) {
        return getProductBidHistory(productId)
                .stream()
                .mapToDouble(Bid::getPrice)
                .max()
                .orElse(0.0);
    }

    @Override
    public boolean makeDeposit(double amount, int customerId, int productId) {
        User user = customerService.getCustomer(customerId).orElseThrow();
        Product product = productService.getProduct(productId).orElseThrow();
        PaypalAccount paypalAccount = payPalAccountRepository.findByCustomer_UserId(customerId);
        double availableBalance = paypalAccount.getAvailableBalance();

        if (product.getDeposit() == amount && availableBalance >= amount) {
            //Update the withheld table amount to the current deposit
            WithHeldAmount withHeldAmount = new WithHeldAmount();
            withHeldAmount.setAmount(amount);
            withHeldAmount.setCustomer(user);
            withHeldAmount.setProductHeld(product);
            withHeldRepository.save(withHeldAmount);

            //Update the paypal account, reduce new available balance
            double currentAvailableBalance = availableBalance - amount;
            double currentPayPalWithHeldAmount = paypalAccount.getTotalWithHeldAmount() + amount;

            paypalAccount.setAvailableBalance(currentAvailableBalance);
            paypalAccount.setTotalWithHeldAmount(currentPayPalWithHeldAmount);
            payPalAccountRepository.save(paypalAccount);
            return true;
        }
        return false;
    }

    @Override
    public boolean makeFullPayment(BidWon bidWon) {
        Product product = bidWon.getProduct();
        User user = bidWon.getBidWinner();
        PaypalAccount paypalAccount = payPalAccountRepository.findByCustomer_UserId(user.getUserId());

        double finalToBePaidAmount = bidWon.getBalanceAmount();
        double currentAvailableBalance = paypalAccount.getAvailableBalance();

        if (currentAvailableBalance < finalToBePaidAmount) return false;
        chargeDeposit(user.getUserId(), product.getProductId());

        paypalAccount.setAvailableBalance(currentAvailableBalance - finalToBePaidAmount);
        paypalAccount.setTotalBalance(paypalAccount.getTotalBalance() - finalToBePaidAmount);

        bidWon.setHasCustomerPaid(true);
        bidWon.setCustMadePaymentDate(LocalDateTime.now());
        product.setPaymentMade(true);

        payPalAccountRepository.save(paypalAccount);
        productService.saveProduct(product);
        bidWonRepository.save(bidWon);

        return true;
    }

    @Override
    public void productReceived(int productId) {
        productService.getProduct(productId)
                .ifPresent(product -> {
                    product.setReceived(true);
                    paySeller(productId);
                    productService.saveProduct(product);
                });
    }

    @Override
    public boolean paySeller(int productId) {
        BidWon bidWon = bidWonRepository.findBidWonByProduct_ProductId(productId);
        double bidFinalPayment = bidWon.getBidFinalAmount();
        User seller = bidWon.getProduct().getOwner();

        if (bidWon.isSellerPaid()) return false;

        Transaction transaction = new Transaction();
        transaction.setAmount(bidFinalPayment);
        transaction.setBuyer(bidWon.getBidWinner());
        transaction.setProduct(bidWon.getProduct());
        transaction.setSeller(seller);
        transaction.setTransactionDate(LocalDateTime.now());
        transactionRepository.save(transaction);

        PaypalAccount sellerAccount = payPalAccountRepository.findByCustomer_UserId(seller.getUserId());
        sellerAccount.setAvailableBalance(sellerAccount.getAvailableBalance() + bidFinalPayment);
        sellerAccount.setTotalBalance(sellerAccount.getTotalBalance() + bidFinalPayment);

        payPalAccountRepository.save(sellerAccount);
        bidWon.setSellerPaid(true);

        return true;
    }

    @Override
    public BidWon getInvoice(int customerId, int productId) {
        return bidWonRepository.findBidWonByProduct_ProductIdAndBidWinner_UserId(productId, customerId);
    }

    @Override
    public double getDepositAmount(int productId) {
        return productService.getProduct(productId).orElseThrow().getDeposit();
    }

    @Override
    public double getBidBalanceAmount(int bidId) {
        return bidWonRepository.findById(bidId).orElseThrow().getBalanceAmount();
    }

    private void returnFullPayment(BidWon bidWon) {
        int userId = bidWon.getBidWinner().getUserId();
        int productId = bidWon.getProduct().getProductId();
        double refundableAmount = bidWon.getBidFinalAmount();

        boolean depositRefunded = payPalService.refundPayment(userId, productId, PaymentEnumType.DEPOSIT.name());
        boolean balanceRefunded = payPalService.refundPayment(userId, productId, PaymentEnumType.FULL_PAYMENT.name());
        if (depositRefunded && balanceRefunded) {

            Product product = bidWon.getProduct();
            PaypalAccount paypalAccount = payPalAccountRepository.findByCustomer_UserId(userId);

            double currentAvailableBalance = paypalAccount.getAvailableBalance();
            double currentTotalBalance = paypalAccount.getTotalBalance();

            paypalAccount.setAvailableBalance(currentAvailableBalance + refundableAmount);
            paypalAccount.setTotalBalance(currentTotalBalance + refundableAmount);

            product.setPaymentMade(false);

            payPalAccountRepository.save(paypalAccount);
            productService.saveProduct(product);
            bidWonRepository.delete(bidWon);
        } else throw new RuntimeException("Failed to refund deposit or balance amount.");
    }

    /**
     * Charges deposit from the user's payPal account of the  customer.
     * This can happen in 2 scenarios.
     * 1. When making a full payment
     * 2. When the customer hasn't made the full payment and the payment due date has expired.
     *
     * @param userId
     * @param productId
     */
    private void chargeDeposit(int userId, int productId) {
        PaypalAccount paypalAccount = payPalAccountRepository.findByCustomer_UserId(userId);
        Product product = productService.getProduct(productId).orElseThrow();

        double productDeposit = product.getDeposit();
        double currentWithHeldAmount = paypalAccount.getTotalWithHeldAmount();
        double currentTotalBalance = paypalAccount.getTotalBalance();

        paypalAccount.setTotalWithHeldAmount(currentWithHeldAmount - productDeposit);
        paypalAccount.setTotalBalance(currentTotalBalance - productDeposit);

        payPalAccountRepository.save(paypalAccount);
    }

    /**
     * Get's the highest bidder to determine who is winning or has won the bid.
     *
     * @param productId
     * @return [Optional<User>]
     */
    private Optional<User> getHighestBidder(int productId) {
        return bidRepository
                .findAllByProduct_ProductId(productId)
                .stream()
                .max(Comparator.comparingDouble(Bid::getPrice))
                .map(Bid::getCustomer);
    }

    /**
     * Saves the winner of the bid to a bidWonRecord object with the user's
     * details, product won details and the amount pending payment.
     *
     * @param product
     * @param bidWinner
     */
    private void saveBidWonRecord(Product product, User bidWinner) {
        double highestBidPrice = getHighestBidPrice(product.getProductId());
        double remainingBalance = highestBidPrice - product.getDeposit();
        BidWon bidWon = new BidWon();
        bidWon.setProduct(product);
        bidWon.setBidWinner(bidWinner);
        bidWon.setBidFinalAmount(highestBidPrice);
        bidWon.setBalanceAmount(remainingBalance);
        bidWon.setDateWon(LocalDateTime.now());
        bidWon.setPaymentDueDate(product.getBidPaymentDueDate());
        bidWonRepository.save(bidWon);
    }

    /**
     * Returns all deposits to all user's that lost the bid 💀
     *
     * @param productId
     * @param bidWinner
     */
    private void returnAllDeposits(int productId, User bidWinner) {
        productService
                .getProduct(productId)
                .ifPresent(product -> {
                    product.setClosed(true);
                    productService.saveProduct(product);
                    saveBidWonRecord(product, bidWinner);
                });

        bidRepository
                .maximumBidByUser()
                .stream()
                .map(Bid::getCustomer)
                .filter(user -> user.getUserId() != bidWinner.getUserId())
                .forEach(user -> returnDeposit(user.getUserId(), productId));
    }

    /**
     * Returns deposit to user's paypal account from Withheld object.
     *
     * @param userId
     * @param productId
     */
    private void returnDeposit(int userId, int productId) {
        boolean payPalRefunded = payPalService.refundPayment(userId, productId, PaymentEnumType.DEPOSIT.name());
        if (payPalRefunded) {
            WithHeldAmount withHeldAmountObj = withHeldRepository
                    .findByCustomer_UserIdAndProductHeld_ProductId(userId, productId)
                    .orElseThrow();

            PaypalAccount paypalAccount = payPalAccountRepository
                    .findByCustomer_UserId(userId);

            double withHeldAmount = withHeldAmountObj.getAmount();
            double paypalWithHeldAmount = paypalAccount.getTotalWithHeldAmount();
            double currentPaypalWithHeldAmount = paypalWithHeldAmount - withHeldAmount;
            double currentPaypalAvailableBalance = paypalAccount.getAvailableBalance() + withHeldAmount;

            withHeldRepository.delete(withHeldAmountObj);

            paypalAccount.setTotalWithHeldAmount(currentPaypalWithHeldAmount);
            paypalAccount.setAvailableBalance(currentPaypalAvailableBalance);
            payPalAccountRepository.save(paypalAccount);
        } else throw new RuntimeException("Failed to refund paypal deposit.");
    }

    @Scheduled(fixedDelay = 120000, initialDelay = 60000)
    private void closeBid() {
        productService.getActiveProducts()
                .stream()
                .filter(p -> {
                    LocalDateTime currentDate = LocalDateTime.now();
                    LocalDateTime dueDate = p.getBidDueDate();
                    return currentDate.isAfter(dueDate) || currentDate.isEqual(dueDate);
                }).forEach(p -> {
                    getHighestBidder(p.getProductId())
                            .ifPresent(bidWinner -> returnAllDeposits(p.getProductId(), bidWinner));
        });
    }

    @Scheduled(fixedDelay = 300000, initialDelay = 120000)
    private void checkIfCustomerPaid() {
        List<BidWon> bidWonList = new ArrayList<>();
        bidWonRepository.findAll().forEach(bidWonList::add);

        bidWonList.stream()
                .filter(bidWon -> !bidWon.isHasCustomerPaid())
                .filter(bidWon -> LocalDateTime.now().isAfter(bidWon.getPaymentDueDate()))
                .forEach(bidWon -> {
                    chargeDeposit(bidWon.getBidWinner().getUserId(), bidWon.getProduct().getProductId());
                });
    }

    /**
     * Scheduler to check if seller has shipped the product yet.
     * Checks if customer paid, if product is shipped and today's date is higher than 3days since the
     * customer paid for the product.
     */
    @Scheduled(fixedDelay = 300000, initialDelay = 120000)
    private void checkIfProductShipped() {
        List<BidWon> bidWonList = new ArrayList<>();
        bidWonRepository.findAll().forEach(bidWonList::add);

        bidWonList.stream()
                .filter(BidWon::isHasCustomerPaid)
                .filter(bidWon -> !bidWon.getProduct().isShipped())
                .filter(bidWon -> bidWon.getCustMadePaymentDate().plusMinutes(3).isBefore(LocalDateTime.now()))
                .forEach(this::returnFullPayment);
    }
}
