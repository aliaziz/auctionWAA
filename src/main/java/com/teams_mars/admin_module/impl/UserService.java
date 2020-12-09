package com.teams_mars.admin_module.impl;



import com.teams_mars.admin_module.domain.User_temp;

import java.util.List;

public interface UserService {
    public User_temp findUserById(Long id);
    public List<User_temp> findAllUser();
}
