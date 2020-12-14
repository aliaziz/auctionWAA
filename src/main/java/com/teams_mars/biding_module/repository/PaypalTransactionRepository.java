package com.teams_mars.biding_module.repository;

import com.teams_mars.biding_module.domain.PaypalTransaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaypalTransactionRepository extends CrudRepository<PaypalTransaction, Integer> {
    PaypalTransaction findByCustomer_UserIdAndProduct_ProductIdAndPaymentEnumType(int customerId, int productId, String type);
    List<PaypalTransaction> findAllByCustomer_UserIdAndProduct_ProductId(int customerId, int productId);
}
