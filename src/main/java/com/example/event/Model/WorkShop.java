package com.example.event.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;


@Data @AllArgsConstructor @NoArgsConstructor @Entity
public class WorkShop {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "name cant be empty")
    //ممكن يطلعلي ايرور بعدين
    private String name;

    @NotEmpty @Size(min = 20,max = 250,message = "the length of the discreption should be at lest 20 character and not more than 250 letters ")
    private  String discreption;
    @DateTimeFormat

    @NotNull(message = "price is required")
    private Double price;

    private LocalDate startDate;


    @NotNull(message = "aid required")
    private Integer artistId;

    private Integer capacity;
  /*  private Integer maxParticipants = 15;  // الحد الأقصى للمشاركين
    private Integer currentBookings = 0;*/



}
