package com.teams_mars.biding_module.repository;

import com.teams_mars.biding_module.domain.Transaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Integer> { }
