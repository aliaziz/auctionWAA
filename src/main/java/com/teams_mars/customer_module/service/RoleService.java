package com.teams_mars.customer_module.service;



import com.teams_mars._global_domain.Role;

import java.util.List;

public interface RoleService {

    List<Role> findAll();

    Role get(Long roleId);
}
