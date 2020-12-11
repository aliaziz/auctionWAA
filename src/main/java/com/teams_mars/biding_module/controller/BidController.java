package com.teams_mars.biding_module.controller;

import com.paypal.api.payments.*;
import com.paypal.base.rest.PayPalRESTException;
import com.teams_mars.biding_module.domain.Bid;
import com.teams_mars.biding_module.domain.BidWon;
import com.teams_mars.biding_module.domain.PaymentEnumType;
import com.teams_mars.biding_module.repository.BidWonRepository;
import com.teams_mars.biding_module.service.BidService;
import com.teams_mars.biding_module.service.PayPalService;
import com.teams_mars.customer_module.service.CustomerService;
import com.teams_mars.seller_module.domain.Product;
import com.teams_mars.seller_module.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/bid")
public class BidController {

    @Autowired
    BidService bidService;

    @Autowired
    PayPalService payPalService;

    @Autowired
    BidWonRepository bidWonRepository;

    @Autowired
    ProductService productService;

    @Autowired
    CustomerService customerService;

    @RequestMapping("/place/{productId}/{price}")
    @ResponseBody
    public String placeBid(@PathVariable int productId, @PathVariable int price) {
        int customerId = 1; //from Session
        bidService.placeBid(2, 3, 30000);
        bidService.placeBid(2, 3, 30001);
        bidService.placeBid(2, 3, 30002);
        bidService.placeBid(2, 3, 30003);
        String error3Message = bidService.placeBid(2, 4, 600);


        bidService.placeBid(1, 3, 30004);
        bidService.placeBid(1, 3, 30005);

        bidService.placeBid(1, 3, 30006);
        bidService.placeBid(1, 3, 30007);

        return "index";
    }

    @RequestMapping("/deposit/{productId}")
    public String makeDeposit(@PathVariable int productId) throws PayPalRESTException {
        double amount = bidService.getDepositAmount(productId);
        Payment payment = payPalService.createPayment(amount, productId, PaymentEnumType.DEPOSIT);
        for (Links link : payment.getLinks()) {
            if (link.getRel().equals("approval_url")) return "redirect:" + link.getHref();
        }

        return "index";
    }

    @RequestMapping("/fullPayment")
    public String makeFullPayment(@ModelAttribute BidWon bidWon) throws PayPalRESTException {
        double amount = bidService.getBidBalanceAmount(bidWon.getBidWonId());
        int productId = bidWon.getProduct().getProductId();
        Payment payment = payPalService.createPayment(amount, productId, PaymentEnumType.FULL_PAYMENT);
        for (Links link : payment.getLinks()) {
            if (link.getRel().equals("approval_url")) return "redirect:" + link.getHref();
        }

        return "index";
    }

    @ResponseBody
    @GetMapping(PayPalService.SUCCESS_URL + "/{paymentType}/{productId}")
    public boolean handleSuccess(@PathVariable int productId,
                                 @PathVariable String paymentType,
                                 @RequestParam String paymentId,
                                 @RequestParam("PayerID") String payerId,
                                 Model model) throws PayPalRESTException {

        model.addAttribute("userId", 2);
        Integer userId = (Integer) model.getAttribute("userId");

        Payment payment = payPalService.executePayment(paymentId, payerId);
        System.out.println(payment.toJSON());
        if (payment.getState().equals("approved")) {
            PaymentEnumType enumType = PaymentEnumType.valueOf(paymentType);

            Transaction transaction = payment.getTransactions().get(0);
            RelatedResources relatedResources = transaction.getRelatedResources().get(0);
            Sale sale = relatedResources.getSale();
            String saleId = sale.getId();
            double amount = Double.parseDouble(sale.getAmount().getTotal());
            payPalService.savePaypalTransaction(saleId, productId, userId,
                    paymentId, payerId, enumType, amount);

            switch (enumType) {
                case DEPOSIT: {
                    Product product = productService.getProduct(productId).orElseThrow();
                    return bidService.makeDeposit(product.getDeposit(), userId, productId);
                }
                case FULL_PAYMENT: {
                    BidWon bidWon = bidWonRepository.findBidWonByProduct_ProductId(productId);
                    return bidService.makeFullPayment(bidWon);
                }
                default:
                    break;
            }
        }

        return false;
    }

    @GetMapping("/refund")
    public String handleRefund() {
        return "success";
    }

    @ResponseBody
    @RequestMapping(value = "/get/{productId}", method = RequestMethod.GET)
    public List<Bid> getBids(@PathVariable int productId) {
        return bidService.getProductBidHistory(productId);
    }

    @ResponseBody
    @GetMapping(PayPalService.CANCEL_URL)
    public boolean handleCancel() {
        return false;
    }

    @InitBinder
    public void Bind(WebDataBinder binder) {
        binder.setDisallowedFields("productImage");
    }
}
