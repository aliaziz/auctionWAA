package com.teams_mars.seller_module.service;

import com.teams_mars.seller_module.domain.Product;
import com.teams_mars.seller_module.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Optional<Product> getProduct(int productId) {
        return productRepository.findById(productId);
    }

    @Override
    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    @Override
    public List<Product> getAllProducts() {
         Iterable<Product> it = productRepository.findAll();
         List<Product> list = new ArrayList<>();
         it.forEach(list::add);
         return list;
    }

    @Override
    public List<Product> getAllSellerProducts(int id) {

        return productRepository.findAllByOwner_UserId(id);
    }

    @Override
    public List<Product> getActiveProducts() {
        return productRepository.getActiveProducts();
    }

    @Override
    public List<Product> getAllProductsByPage(int pageNum, String attr, boolean isDesc) {
        Sort sort = Sort.by(attr).descending();
        if (!isDesc) sort.ascending();

        Pageable pageable = PageRequest.of(pageNum, 6, sort);
        Page<Product> productList = productRepository.findAll(pageable);
        return productList.toList();
    }

    @Override
    public List<Product> searchProductsByName(int pageNum, String keyWord) {
        Pageable pageable = PageRequest.of(pageNum,3);
        Page<Product> productList = productRepository.findAllByNameIsLike(keyWord, pageable);
        return productList.toList();
    }

    @Override
    public List<String> getProductImages(int productId) {
        Product product = productRepository.findById(productId).orElseThrow();
        String imagesFolder = product.getImagePath();
        String [] imagePaths;

        File file = new File(imagesFolder);
        imagePaths = file.list();

        List<String> imagePathsList = new ArrayList<>();
        if (imagePaths != null) Collections.addAll(imagePathsList, imagePaths);

        return imagePathsList;
    }

    @Override
    public List<Product> findProductByCategory(String category) {
        List<Product> productByName = productRepository.findProductByCategory(category);
        return productByName;
    }


}
