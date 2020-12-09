package com.teams_mars.biding_module.repository;

import com.teams_mars.biding_module.domain.Bid;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BidRepository extends CrudRepository<Bid, Integer> {
    List<Bid> findAllByCustomer_UserIdOrderByBidDateDesc(int customerId);
    List<Bid> findAllByProduct_ProductIdOrderByBidDate(int productId);
    List<Bid> findAllByProduct_ProductId(int productId);
    @Query("select new Bid(max(b.price), b.product, b.customer) from Bid b group by b.customer.userId, b.product.productId")
    List<Bid> maximumBidByUser();
}
