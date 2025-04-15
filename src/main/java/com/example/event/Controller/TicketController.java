package com.example.event.Controller;

import com.example.event.ApiResponse.Api;
import com.example.event.Model.Ticket;
import com.example.event.Service.TicketService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ticket")
public class TicketController {

    final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }


    @PostMapping("/add")
    public ResponseEntity add(@Valid @RequestBody Ticket ticket,Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        ticketService.add(ticket);
        return ResponseEntity.status(200).body(new Api("Added successfully"));
    }

    @GetMapping("/getall")
    public ResponseEntity getall(){
        return ResponseEntity.status(200).body(ticketService.getall());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity update(@Valid @RequestBody Ticket ticket, Errors errors, @PathVariable int id){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        ticketService.update(ticket,id);
        return ResponseEntity.status(200).body(new Api("ticket updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(Errors errors,@PathVariable int id){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        ticketService.delete(id);
        return ResponseEntity.status(200).body(new Api("ticket deleted successfully"));
    }

    //-----------------------------------------------


     //5 ages avareg
    @GetMapping("/average-age")
    public ResponseEntity<Double> getAverageVisitorAge() {
        Double averageAge = ticketService.getAverageAgeOfVisitorsWhoBooked();
        return ResponseEntity.status(200).body(averageAge);
    }



    //6 the canceling
    @PostMapping("/cancel")
    public ResponseEntity<String> cancelReservation(@RequestBody Ticket ticket) {
        String result = ticketService.cancelReservation(ticket.getId(), ticket.getVId(), ticket.getWId());

        return switch (result) {
            case "Ticket not found.", "Ticket does not match the provided visitor or workshop.",
                 "Workshop not found.", "You can't cancel. The workshop starts today." ->
                    ResponseEntity.status(400).body(result);
            default -> ResponseEntity.ok(result); // "Cancelled successfully."
        };
    }

    //Count the sales amount by the artist id
    @GetMapping("/sales/artist/{artistId}")
    public ResponseEntity<Double> getTotalSalesByArtist(@PathVariable Integer artistId) {
        Double totalSales = ticketService.getTotalSalesByArtist(artistId);
        return ResponseEntity.status(200).body(totalSales);
    }







}
