package com.teams_mars.customer_module.service;

import com.teams_mars.customer_module.domain.User;
import com.teams_mars.customer_module.repository.CustomerRepository;
import com.teams_mars.seller_module.domain.Product;
import com.teams_mars.seller_module.repository.ProductRepository;
import com.teams_mars.seller_module.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ProductService productService;

    @Override
    public boolean isBidEligible(int customerId) {
        return customerRepository.isBidEligible(customerId);
    }

    @Override
    public boolean isSeller(int customerId, int productId) {
        return customerRepository.isSellerOfProduct(productId) == customerId;
    }

    @Override
    public Optional<User> getCustomer(int customerId) {
        return customerRepository.findById(customerId);
    }

    @Override
    public List<Product> viewPagedProductList(int pageNum, String attr, boolean isDesc) {
        return productService.getAllProductsByPage(pageNum, attr, isDesc);
    }
}
