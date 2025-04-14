package com.example.event.Repository;

import com.example.event.Model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase,Integer> {

    @Query("SELECT COUNT(p) FROM Purchase p WHERE p.pId = :aId")
    long countPurchaseByAId(Integer aId);


    @Query("SELECT v.name FROM Purchase p " +
            "JOIN Visitor v ON p.vId = v.id " +
            "WHERE p.pId = :productId " +
            "GROUP BY v.id HAVING COUNT(p) > 1")
    List<String> BoughtMoreThanOnce(Integer productId);

}
