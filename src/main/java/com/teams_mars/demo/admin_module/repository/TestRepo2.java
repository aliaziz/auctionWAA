package com.teams_mars.demo.admin_module.repository;

import com.teams_mars.demo.customer_module.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepo2 extends CrudRepository<User, Integer> {
}
