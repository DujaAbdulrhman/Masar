package com.example.event.Repository;

import com.example.event.Model.WorkShop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface WorkShopRepository extends JpaRepository<WorkShop,Integer> {


    @Query("SELECT w FROM WorkShop w WHERE w.startDate > :currentDate ORDER BY w.startDate ASC")
    List<WorkShop> findUpcomingWorkshops(@Param("currentDate") LocalDate currentDate);


    List<WorkShop> findByStartDate(LocalDate startDate);}
