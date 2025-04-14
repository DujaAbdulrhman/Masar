package com.example.event.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @JsonProperty ("name")
    @NotEmpty(message = "the name is required")
    private String name;
    @JsonProperty ("price")
    @NotNull(message = "the price is required")
    @Min(value = 1, message = "price should be hiegher than 0")
    private Double price;

    @JsonProperty("quantity")
    @NotNull(message = "quantity is required")
    private Integer quantity;
    @NotNull(message = "quantity is required")

    @JsonProperty ("aId")
    // ID الفنان اللي صنع المنتج
   // @NotNull(message = "aId is required")
    private Integer aId;

}
