package com.teams_mars.biding_module.service;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import com.teams_mars.biding_module.domain.PaymentEnumType;
import com.teams_mars.biding_module.domain.PaypalTransaction;
import com.teams_mars.biding_module.repository.PaypalTransactionRepository;
import com.teams_mars.customer_module.service.CustomerService;
import com.teams_mars.seller_module.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class PayPalService {

    @Autowired
    private APIContext context;

    @Autowired
    CustomerService customerService;

    @Autowired
    ProductService productService;

    @Autowired
    PaypalTransactionRepository paypalTransactionRepository;

    public static final String SUCCESS_URL = "/handlePayment";
    public static final String CANCEL_URL = "/error";
    private final String LOCAL_URL = "http://localhost:8888/bid";

    public Payment createPayment(Double total,
                                 int productId,
                                 PaymentEnumType paymentEnumType) throws PayPalRESTException {

        Amount amount = new Amount();
        amount.setCurrency("USD");
        total = new BigDecimal(total).setScale(2, RoundingMode.HALF_UP).doubleValue();
        amount.setTotal(String.format("%.2f", total));

        Transaction transaction = new Transaction();
        transaction.setDescription("Deposit or full payment");
        transaction.setAmount(amount);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");

        Payment payment = new Payment();
        payment.setIntent("sale");
        payment.setPayer(payer);
        payment.setTransactions(transactions);
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(CANCEL_URL);
        redirectUrls.setReturnUrl(LOCAL_URL+SUCCESS_URL+"/"+paymentEnumType.name()+"/"+productId);
        payment.setRedirectUrls(redirectUrls);

        return payment.create(context);
    }

    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException{
        Payment payment = new Payment();
        payment.setId(paymentId);
        PaymentExecution paymentExecute = new PaymentExecution();
        paymentExecute.setPayerId(payerId);
        return payment.execute(context, paymentExecute);
    }

    public boolean refundPayment(int customerId, int productId, String paymentType) {
        boolean refunded = false;
        PaypalTransaction paypalTransaction = paypalTransactionRepository
                .findByCustomer_UserIdAndProduct_ProductIdAndPaymentEnumType(customerId, productId, paymentType);

        double refundAmount = paypalTransaction.getAmount();
        String saleId = paypalTransaction.getSaleId();
        RefundRequest refundRequest = new RefundRequest();

        Amount amount = new Amount();
        amount.setCurrency("USD");
        amount.setTotal(String.valueOf(refundAmount));
        refundRequest.setAmount(amount);

        Sale sale = new Sale();
        sale.setId(saleId);

        try {
            sale.refund(context, refundRequest);
            refunded = true;
        } catch (PayPalRESTException e) {
            System.out.println(e.getDetails());
        }

        return refunded;
    }

    public void transferToSeller() {
    }


    public void savePaypalTransaction(String saleId, int productId,
                                      Integer userId, String paymentId,
                                      String payerId, PaymentEnumType enumType, double amount) {
        PaypalTransaction paypalTransaction = new PaypalTransaction();
        paypalTransaction.setPayerId(payerId);
        paypalTransaction.setPaymentId(paymentId);
        paypalTransaction.setPaymentEnumType(enumType.name());
        paypalTransaction.setCustomer(customerService.getCustomer(userId).orElseThrow());
        paypalTransaction.setProduct(productService.getProduct(productId).orElseThrow());
        paypalTransaction.setSaleId(saleId);
        paypalTransaction.setAmount(amount);

        paypalTransactionRepository.save(paypalTransaction);
    }
}
