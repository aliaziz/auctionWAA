package com.teams_mars.biding_module.repository;

import com.teams_mars.biding_module.domain.WithHeldAmount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WithHeldRepository extends CrudRepository<WithHeldAmount, Integer> { }
