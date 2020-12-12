package com.teams_mars.customer_module.service;

import com.teams_mars.customer_module.domain.User;
import com.teams_mars.seller_module.domain.Product;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CustomerService {
    boolean isCustomerVerified(int customerId);
    boolean isSeller(int customerId, int productId);
    Optional<User> getCustomer(int customerId);
    List<Product> viewPagedProductList(int pageNum, String attr, boolean isDesc);
    List<Product> searchPagedProducts(int pageNum, String keyword);
}
