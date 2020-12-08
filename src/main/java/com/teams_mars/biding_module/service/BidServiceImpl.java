package com.teams_mars.biding_module.service;

import com.teams_mars.biding_module.domain.Bid;
import com.teams_mars.biding_module.repository.BidRepository;
import com.teams_mars.customer_module.domain.User;
import com.teams_mars.customer_module.service.CustomerService;
import com.teams_mars.seller_module.domain.Product;
import com.teams_mars.seller_module.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BidServiceImpl implements BidService {

    @Autowired
    BidRepository bidRepository;

    @Autowired
    CustomerService customerService;

    @Autowired
    ProductService productService;

    @Override
    public String placeBid(int customerId, int productId, double price) {
        boolean isEligible = customerService.isBidEligible(customerId);
        boolean isSeller = customerService.isSeller(customerId, productId);

        if (!isEligible) return "Not eligible, please get account verified.";
        if (isSeller) return "Seller can't bid on own product";

        User user = customerService.getCustomer(customerId).orElseThrow();
        Product product = productService.getProduct(productId).orElseThrow();
        boolean isPriceLower = getHighestBidPrice(productId) > price;

        if (isPriceLower) return "Price must be higher than current highest";

        Bid bid = new Bid();
        bid.setProduct(product);
        bid.setCustomer(user);
        bid.setPrice(price);

        bidRepository.save(bid);
        return "Bid saved";
    }

    @Override
    public List<Bid> getCustomerBidHistory(int customerId) {
        return bidRepository.findAllByCustomer_UserIdOrderByBidDateDesc(customerId);
    }

    @Override
    public List<Bid> getProductBidHistory(int productId) {
        return bidRepository.findAllByProduct_ProductIdOrderByBidDate(productId);
    }

    @Override
    public double getHighestBidPrice(int productId) {
       return getProductBidHistory(productId)
               .stream()
               .mapToDouble(Bid::getPrice)
               .max()
               .orElse(0.0);
    }
}
