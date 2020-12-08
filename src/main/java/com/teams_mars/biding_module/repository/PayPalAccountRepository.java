package com.teams_mars.biding_module.repository;

import com.teams_mars.biding_module.domain.PaypalAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PayPalAccountRepository extends CrudRepository<PaypalAccount, Integer> {
    PaypalAccount findByCustomer_UserId(int customerId);
}
