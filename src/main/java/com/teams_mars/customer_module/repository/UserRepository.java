package com.teams_mars.customer_module.repository;

import com.teams_mars._global_domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User,Integer> {

    Optional<User> findByEmail(String email);
}
