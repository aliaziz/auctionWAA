package com.teams_mars.biding_module.repository;

import com.teams_mars.admin_module.domain.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BidRepository extends CrudRepository<Category, Integer> { }
