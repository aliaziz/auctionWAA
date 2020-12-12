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
    public String placeBid(@PathVariable int productId, @PathVariable double price) {
        int customerId = 1; //from Session
        return bidService.placeBid(customerId, productId, price);
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

    @GetMapping(PayPalService.SUCCESS_URL + "/{paymentType}/{productId}")
    public String handleSuccess(@PathVariable int productId,
                                 @PathVariable String paymentType,
                                 @RequestParam String paymentId,
                                 @RequestParam("PayerID") String payerId,
                                 Model model) throws PayPalRESTException {

        model.addAttribute("userId", 1);
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
                    bidService.makeDeposit(product.getDeposit(), userId, productId);
                    break;
                }
                case FULL_PAYMENT: {
                    BidWon bidWon = bidWonRepository.findBidWonByProduct_ProductId(productId);
                    bidService.makeFullPayment(bidWon);
                    break;
                }
                default:
                    break;
            }
        }

        return "redirect:/products/"+productId;
    }

    @RequestMapping(value = "/bidHistory/{productId}", method = RequestMethod.GET)
    public String getBids(@PathVariable int productId, Model model) {
        model.addAttribute("bidHistory", bidService.getProductBidHistory(productId));
        return "product/bid_history";
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
