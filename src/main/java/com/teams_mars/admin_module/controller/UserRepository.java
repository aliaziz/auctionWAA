package com.teams_mars.admin_module.controller;

import com.teams_mars.customer_module.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
 /*  List<User> findUserByProfileVerifiedIsFalse();*/
}
