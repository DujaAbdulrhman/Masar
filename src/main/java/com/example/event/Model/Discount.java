package com.example.event.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Discount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonProperty("review")
    @NotEmpty
    @Column(columnDefinition = "review")
    private String review; // نص التقييم

    @JsonProperty("stars")
    @Column(columnDefinition = "stars")
    @Min(0)
    @Max(5)
    @NotNull(message = "enter the stars rating")
    private Double stars; // التقييم

    @JsonProperty("wId")
    @Column(columnDefinition = "wId")
    @NotNull(message = "you should enter the wId")
    private Integer wId; // معرف الورشة
}
