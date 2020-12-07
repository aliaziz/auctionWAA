package com.teams_mars.demo.admin_module.repository;

import com.teams_mars.demo._global_domain.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepo extends CrudRepository<Role, Integer> {
}

