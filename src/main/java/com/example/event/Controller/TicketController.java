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


    //5 the workshop booking
    @PostMapping("/reserve/{vId}/{wId}")
    public ResponseEntity<String> reserveWorkshop(
            @PathVariable Integer vId,
            @PathVariable Integer wId,
            @RequestParam String ticketDate) {

        if (vId == null || wId == null || ticketDate == null) {
            return ResponseEntity.badRequest().body("All parameters are required");
        }

        String result = ticketService.reserveTicket(vId, wId, ticketDate);

        return switch (result) {
            case "Visitor not found", "Workshop not found",
                 "No available spots in the workshop",
                 "Invalid ticket date format" ->
                    ResponseEntity.badRequest().body(result);
            default -> ResponseEntity.ok(result);
        };
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
