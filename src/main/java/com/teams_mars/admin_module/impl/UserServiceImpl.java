package com.teams_mars.admin_module.impl;

import com.teams_mars.admin_module.domain.User_temp;
import com.teams_mars.admin_module.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired//需要一个实例
    UserRepository userRepository;

    @Override
    public User_temp findUserById(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public List<User_temp> findAllUser() {
        return userRepository.findAll();
    }
}
