package com.teams_mars.biding_module.repository;

import com.teams_mars.biding_module.domain.BidWon;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BidWonRepository extends CrudRepository<BidWon, Integer> {
    BidWon findBidWonByProduct_ProductId(int productId);
    List<BidWon> findAllByBidWinner_UserId(int userId);
}
