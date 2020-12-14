package com.teams_mars.seller_module.service;

import com.teams_mars.seller_module.domain.Product;
import com.teams_mars.seller_module.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        List<Product> list = productRepository.findAllByOwner_UserId(id);
        list.forEach(p -> p.setImagePath(getOneProductImage(p.getProductId())));
        return list;
    }

    @Override
    public List<Product> getActiveProducts() {
        List<Product> list = productRepository.getActiveProducts();
        list.forEach(p -> p.setImagePath(getOneProductImage(p.getProductId())));
        return list;
    }

    @Override
    public List<Product> getAllProductsByPage(int pageNum, String attr, boolean isDesc) {
        Sort sort = Sort.by(attr).descending();
        if (!isDesc) sort.ascending();

        Pageable pageable = PageRequest.of(pageNum, 6, sort);
        List<Product> productList = productRepository.findAll(pageable).toList();
        productList.forEach(p -> p.setImagePath(getOneProductImage(p.getProductId())));

        return productList
                .stream()
                .filter(product -> !product.isClosed())
                .filter(product -> LocalDateTime.now().isBefore(product.getBidDueDate()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Product> searchProductsByName(int pageNum, String keyWord) {
        Pageable pageable = PageRequest.of(pageNum,3);
        List<Product> productList = productRepository.findAllByNameIsLike(keyWord, pageable).toList();
        productList.forEach(p -> p.setImagePath(getOneProductImage(p.getProductId())));
        return productList;
    }

    @Override
    public List<String> getProductImages(int productId) {
        Product product = productRepository.findById(productId).orElseThrow();
        String [] imagePaths = getFolderImages(productId);
        String imagesFolder = product.getImagePath();

        List<String> imagePathsList = new ArrayList<>();
        if (imagePaths != null) {
            for (String imageName : imagePaths) {
                String shortPath = imagesFolder.substring(imagesFolder.indexOf("/uploads/"));
                imagePathsList.add(shortPath+"/"+imageName);
            }
        }

        return imagePathsList;
    }

    @Override
    public String getOneProductImage(int productId) {
        List<String> productImages = getProductImages(productId);
        if (productImages.size() > 0) return productImages.get(0);
        return "";
    }

    @Override
    public List<Product> findProductByCategory(String category) {
        List<Product> productByName = productRepository.findProductByCategory(category);
        return productByName;
    }

    private String [] getFolderImages(int productId) {
        Product product = productRepository.findById(productId).orElseThrow();
        String imagesFolder = product.getImagePath();

        File file = new File(imagesFolder);
        return file.list();
    }

}
