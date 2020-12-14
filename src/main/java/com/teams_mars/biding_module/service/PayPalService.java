package com.teams_mars.biding_module.service;


import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import com.teams_mars.biding_module.domain.LocalPaypalAccount;
import com.teams_mars.biding_module.domain.PaymentEnumType;
import com.teams_mars.biding_module.domain.PaypalTransaction;
import com.teams_mars.biding_module.repository.LocalPayPalAccountRepository;
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

    @Autowired
    LocalPayPalAccountRepository localPayPalAccountRepository;


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
        payment.setIntent("authorize");
        payment.setPayer(payer);
        payment.setTransactions(transactions);
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(CANCEL_URL);
        redirectUrls.setReturnUrl(LOCAL_URL+SUCCESS_URL+"/"+paymentEnumType.name()+"/"+productId);
        payment.setRedirectUrls(redirectUrls);

        return payment.create(context);
    }

    public Payment authorizePayment(String paymentId, String payerId) throws PayPalRESTException {
        Payment payment = new Payment();
        payment.setId(paymentId);
        PaymentExecution paymentExecute = new PaymentExecution();
        paymentExecute.setPayerId(payerId);
        return payment.execute(context, paymentExecute);
    }

    public boolean refundPayment(PaypalTransaction paypalTransaction) throws PayPalRESTException {
        Authorization authorization = new Authorization();
        authorization.setId(paypalTransaction.getAuthId());
        System.out.println(authorization.doVoid(context));
        return true;
    }

    public void transferToSeller(PaypalTransaction paypalTransaction) throws PayPalRESTException {
        Authorization authorization = new Authorization();
        authorization.setId(paypalTransaction.getAuthId());

        Amount amount = new Amount();
        amount.setCurrency("USD");
        amount.setTotal(String.valueOf(paypalTransaction.getAmount()));

        Capture capture = new Capture();
        capture.setAmount(amount);

        Capture responseCapture = authorization.capture(context, capture);
        System.out.println(responseCapture.toJSON());
    }

    public void savePaypalTransaction(String authId, int productId,
                                      Integer userId, String paymentId,
                                      String payerId, PaymentEnumType enumType, double amount) {
        PaypalTransaction paypalTransaction = new PaypalTransaction();
        paypalTransaction.setPayerId(payerId);
        paypalTransaction.setPaymentId(paymentId);
        paypalTransaction.setPaymentEnumType(enumType.name());
        paypalTransaction.setCustomer(customerService.getCustomer(userId).orElseThrow());
        paypalTransaction.setProduct(productService.getProduct(productId).orElseThrow());
        paypalTransaction.setAuthId(authId);
        paypalTransaction.setAmount(amount);

        paypalTransactionRepository.save(paypalTransaction);
    }

    public void createPaypalAccount(int userId) {
        LocalPaypalAccount paypalAccount = new LocalPaypalAccount();
        paypalAccount.setCustomer(customerService.getCustomer(userId).orElseThrow());
        paypalAccount.setAvailableBalance(5000.0);
        paypalAccount.setTotalBalance(5000.0);
        paypalAccount.setTotalWithHeldAmount(0.0);
        localPayPalAccountRepository.save(paypalAccount);
    }
}
