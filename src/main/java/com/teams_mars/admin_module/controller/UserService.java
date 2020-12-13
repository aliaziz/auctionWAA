package com.teams_mars.admin_module.controller;

import com.teams_mars.customer_module.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public List<User> findAll(){
        return userRepository.findAll();
    }
    public User findUserById(Integer id){
        return userRepository.findById(id).get();
    }

    public void save(User user){
        userRepository.save(user);
    }
  /*  public List<User> findUserByProfileVerifiedIsFalse(){
        return userRepository.findUserByProfileVerifiedIsFalse();
    }*/

}
