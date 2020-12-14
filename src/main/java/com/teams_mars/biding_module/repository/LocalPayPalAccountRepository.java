package com.teams_mars.biding_module.repository;

import com.teams_mars.biding_module.domain.LocalPaypalAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocalPayPalAccountRepository extends CrudRepository<LocalPaypalAccount, Integer> {
    LocalPaypalAccount findByCustomer_UserId(int customerId);
}
