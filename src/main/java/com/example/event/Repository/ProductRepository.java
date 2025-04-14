package com.example.event.Repository;

import com.example.event.Model.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {

    @Query("SELECT p FROM Product p JOIN Purchase pur ON pur.pId = p.id GROUP BY p.id ORDER BY SUM(pur.quantity) DESC")
    List<Product> findBestSellingProducts();

    @Modifying
    @Transactional
    @Query("UPDATE Product p SET p.quantity = p.quantity - :quantity WHERE p.id = :productId")
    void updateProductQuantity(Integer productId, Integer quantity);


    List<Product> findAll(Sort sort);
}
