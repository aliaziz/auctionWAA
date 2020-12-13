package com.teams_mars.customer_module.repository;


import com.teams_mars._global_domain.User;
import com.teams_mars.customer_module.domain.Verification;
import org.springframework.data.repository.CrudRepository;

public interface VerificationRepository extends CrudRepository<Verification,Long> {

    Verification findVerificationByUser(User user);


}
