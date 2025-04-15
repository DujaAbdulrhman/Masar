package com.example.event.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Artist {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "enter your name")
    private String name;
    @NotEmpty
    private String field;
    @NotEmpty
    private String originCountry;

    private boolean featured;
    @NotNull(message = "age is required")
    private Integer age;

    @NotEmpty(message = "gender is required")
    @Pattern(regexp = "^(male|female)$", message = "gender must be either 'male' or 'female'")
    private String gender;

}
