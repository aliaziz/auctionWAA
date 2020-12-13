package com.teams_mars.customer_module.service;

import com.teams_mars._global_domain.Role;
import com.teams_mars.customer_module.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImp implements RoleService {
    @Autowired
    RoleRepository roleRepository;

    @Override
    public List<Role> findAll() {
        return (List<Role>) roleRepository.findAll();
    }

    @Override
    public Role get(Long roleId) {
        return roleRepository.findById(roleId).get();
    }
}
