package com.teams_mars.admin_module.repository;

import com.teams_mars.admin_module.domain.User_temp;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends CrudRepository <User_temp,Long> {
    @Override
    public List<User_temp> findAll();


}
