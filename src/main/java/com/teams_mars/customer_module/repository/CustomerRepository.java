package com.teams_mars.customer_module.repository;

import com.teams_mars._global_domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<User, Integer> {

    @Query("select u.isProfileVerified from User u where u.userId =:customerId")
    boolean isBidEligible(Integer customerId);

    @Query("select p.owner.userId from Product p where p.productId = :productId")
    Integer isSellerOfProduct(Integer productId);

}
