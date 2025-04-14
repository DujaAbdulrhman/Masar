package com.example.event.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CurrentTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @CurrentTimestamp
    private LocalDate date;

    @JsonProperty("pId")
    @NotNull(message = "product id is required")
    private Integer pId;

    @NotNull(message = "Quantity is required")
    private Integer quantity;

    @Column(columnDefinition = "vId")
    @JsonProperty("vId")
    @NotNull (message = "Purchase id is required")
    private Integer vId;
}
