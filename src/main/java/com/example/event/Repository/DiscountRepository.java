package com.example.event.Repository;

import com.example.event.Model.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Integer> {

    @Query("SELECT AVG(d.stars) FROM Discount d WHERE d.wId = :wId")
    Double findAverageStarsByWorkShopId(@Param("wId") Long wId);

    List<Discount> findBywId(Long wId);
}
