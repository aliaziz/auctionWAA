package com.teams_mars.biding_module.repository;

import com.teams_mars.biding_module.domain.WithHeldAmount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WithHeldRepository extends CrudRepository<WithHeldAmount, Integer> {
    Optional<WithHeldAmount> findByCustomer_UserIdAndProductHeld_ProductId(int customerId, int productId);
}
