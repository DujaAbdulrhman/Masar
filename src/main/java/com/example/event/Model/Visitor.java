package com.example.event.Model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
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
public class Visitor {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "name is required")
    private String name;
    @NotNull (message = "balance is required")
    private Double balance;
    @Email
    private String email;
    @NotNull (message = "phone number required")
    private Integer phone;

    @NotNull(message = "age is required")
    private Integer age;

    @NotEmpty(message = "gender is required")
    @Pattern(regexp = "^(male|female)$", message = "gender must be either 'male' or 'female'")
    private String gender;


}
