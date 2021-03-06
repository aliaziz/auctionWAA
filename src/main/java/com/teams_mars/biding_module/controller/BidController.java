package com.teams_mars.biding_module.controller;

import com.paypal.api.payments.*;
import com.paypal.base.rest.PayPalRESTException;
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
    public String placeBid(@PathVariable int productId, @PathVariable double price, Model model) {
        return bidService.placeBid((Integer) model.getAttribute("userId"), productId, price);
    }

    @RequestMapping("/deposit/{productId}")
    public String makeDeposit(@PathVariable int productId) throws PayPalRESTException {
        double amount = bidService.getDepositAmount(productId);
        Payment payment = payPalService.createPayment(amount, productId, PaymentEnumType.DEPOSIT);
        for (Links link : payment.getLinks()) {
            if (link.getRel().equals("approval_url")) return "redirect:" + link.getHref();
        }

        return "error";
    }

    @RequestMapping("/fullPayment/{bidWonId}")
    public String customerMakesPayment(@PathVariable int bidWonId) throws PayPalRESTException {
        BidWon bidWon = bidWonRepository.findById(bidWonId).orElseThrow();
        double amount = bidService.getBidFinalAmount(bidWon.getBidWonId());
        int productId = bidWon.getProduct().getProductId();

        //Create payment
        Payment payment = payPalService.createPayment(amount, productId, PaymentEnumType.FULL_PAYMENT);
        for (Links link : payment.getLinks()) {
            if (link.getRel().equals("approval_url")) return "redirect:" + link.getHref();
        }

        return "error";
    }

    @GetMapping(PayPalService.SUCCESS_URL + "/{paymentType}/{productId}")
    public String handleSuccess(@PathVariable int productId,
                                 @PathVariable String paymentType,
                                 @RequestParam String paymentId,
                                 @RequestParam("PayerID") String payerId,
                                 Model model) throws PayPalRESTException {

        int userId = (int) model.getAttribute("userId");

        //Authorise payment
        Payment payment = payPalService.authorizePayment(paymentId, payerId);
        PaymentEnumType enumType;

        if (payment.getState().equals("approved")) {
            enumType = PaymentEnumType.valueOf(paymentType);

            Transaction transaction = payment.getTransactions().get(0);
            RelatedResources relatedResources = transaction.getRelatedResources().get(0);
            Authorization authorization = relatedResources.getAuthorization();
            String authId = authorization.getId();

            double amount = Double.parseDouble(authorization.getAmount().getTotal());
            payPalService.savePaypalTransaction(authId, productId, userId, paymentId, payerId, enumType, amount);

            switch (enumType) {
                case DEPOSIT: {
                    Product product = productService.getProduct(productId).orElseThrow();
                    //Keep local records about making deposit
                    bidService.makeDeposit(product.getDeposit(), userId, productId);
                    return "redirect:/product/"+productId;
                }
                case FULL_PAYMENT: {
                    BidWon bidWon = bidWonRepository.findBidWonByProduct_ProductId(productId);
                    //Keep local records about making full payment
                    bidService.makeFullPayment(bidWon);
                    return "redirect:/customer/bidsWon";
                }
                default:
                    break;
            }
        }
        return "error";
    }

    @RequestMapping(value = "/bidHistory/{productId}", method = RequestMethod.GET)
    public String getBids(@PathVariable int productId, Model model) {
        model.addAttribute("bidHistory", bidService.getProductBidHistory(productId));
        return "product/bid_history";
    }

    @RequestMapping("/printInvoice/{bidWonId}")
    public String printInvoice(@PathVariable int bidWonId, Model model) {
        BidWon bidWon = bidService.getInvoice(bidWonId);
        model.addAttribute("bidWon", bidWon);
        return "/product/invoice";
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
