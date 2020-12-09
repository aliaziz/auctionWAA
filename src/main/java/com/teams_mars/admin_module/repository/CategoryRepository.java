package com.teams_mars.admin_module.repository;


import com.teams_mars.admin_module.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    @Override
    //@Query(value = "select c from Category c")//手动输入的数据里有信息
    public List<Category> findAll();
    //@Query(value = "select c from #{#entityName} c where c.name = :name ")
    //Category findCategoryByName(String name); //this is interface





}
