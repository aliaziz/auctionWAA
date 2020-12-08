package com.teams_mars.biding_module.service;

import com.teams_mars.biding_module.domain.Bid;
import com.teams_mars.biding_module.domain.WithHeldAmount;
import com.teams_mars.biding_module.repository.BidRepository;
import com.teams_mars.biding_module.repository.PayPalAccountRepository;
import com.teams_mars.biding_module.repository.WithHeldRepository;
import com.teams_mars.customer_module.domain.User;
import com.teams_mars.customer_module.service.CustomerService;
import com.teams_mars.seller_module.domain.Product;
import com.teams_mars.seller_module.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
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

    @Override
    public String placeBid(int customerId, int productId, double price) {
        boolean isEligible = customerService.isBidEligible(customerId);
        boolean isSeller = customerService.isSeller(customerId, productId);

        if (!isEligible) return "Not eligible, please get account verified.";
        if (isSeller) return "Seller can't bid on own product";

        User user = customerService.getCustomer(customerId).orElseThrow();
        Product product = productService.getProduct(productId).orElseThrow();
        boolean isPriceLower = getHighestBidPrice(productId) > price;

        if (isPriceLower) return "Price must be higher than current highest";

        Bid bid = new Bid();
        bid.setProduct(product);
        bid.setCustomer(user);
        bid.setPrice(price);

        bidRepository.save(bid);
        return "Bid saved";
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
    public boolean makeDeposit(int amount, int customerId, int productId) {
        User user = customerService.getCustomer(customerId).orElseThrow();
        Product product = productService.getProduct(productId).orElseThrow();
        WithHeldAmount withHeldAmount = new WithHeldAmount();
        withHeldAmount.setAmount(amount);
        withHeldAmount.setCustomer(user);
        withHeldAmount.setProductHeld(product);
        withHeldRepository.save(withHeldAmount);
        return true;
    }

    private Optional<User> getHighestBidder(int productId) {
        return bidRepository
                .findAllByProduct_ProductId(productId)
                .stream()
                .max(Comparator.comparingDouble(Bid::getPrice))
                .map(Bid::getCustomer);
    }

    @Override
    public void test(int productId) {
        getHighestBidder(productId).ifPresent(user1 -> {
            System.out.println(user1.getFullName());

        });
    }
}
