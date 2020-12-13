package com.teams_mars.customer_module.service;


import com.teams_mars._global_domain.User;

import java.util.List;

public interface UserService {

    User save(User user);

    User getUserById(int id);

    User findUserByEmail(String email);

    void changePassword(User user,String password);

    List<User> findAll();



}
