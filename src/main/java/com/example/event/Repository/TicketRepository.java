package com.example.event.Repository;

import com.example.event.Model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket,Integer> {


    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN TRUE ELSE FALSE END FROM Ticket t WHERE t.vId = :visitorId AND t.wId = :workShopId")
    boolean existsByVIdAndWId(@Param("visitorId") Integer visitorId, @Param("workShopId") Integer workShopId);

    Integer id(Integer id);


    @Query("SELECT SUM(t.price) FROM Ticket t WHERE t.wId IN (SELECT w.id FROM WorkShop w WHERE w.artistId = :artistId)")
    Double getTotalSalesByArtist(@Param("artistId") Double artistId);

}
