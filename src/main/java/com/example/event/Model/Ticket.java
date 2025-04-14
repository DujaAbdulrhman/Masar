/*
package com.example.event.Model;




import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CurrentTimestamp;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @JsonProperty("price")
    @NotNull(message = "price is required")
    private Double price;

    @Column(name = "v_id")
    @NotNull(message = "vId is required")
    private Integer vId;



    @Column(columnDefinition ="wid Int not null")
    @NotNull(message = "wId is required")
    private Integer wId;

    @Column(name = "ticket_date")
    @NotNull(message = "ticket date is required")
    private LocalDateTime date;
}


*/

package com.example.event.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "ticket")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "price")
    private Double price;

    @Column(name = "v_id")
    private Integer vId;

    @Column(name = "w_id")
    private Integer wId;


    @Column(name = "ticket_date")
    private LocalDate ticketDate;


}