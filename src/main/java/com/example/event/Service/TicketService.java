package com.example.event.Service;


import com.example.event.Model.Ticket;
import com.example.event.Model.Visitor;
import com.example.event.Model.WorkShop;
import com.example.event.Repository.TicketRepository;
import com.example.event.Repository.VisitorRepository;
import com.example.event.Repository.WorkShopRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public class TicketService {


    final WorkShopRepository workShopRepository;
    final VisitorRepository visitorRepository;
    final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository, WorkShopRepository workShopRepository, VisitorRepository visitorRepository) {
        this.ticketRepository = ticketRepository;
        this.workShopRepository = workShopRepository;
        this.visitorRepository = visitorRepository;
    }

    public Boolean add(Ticket ticket){
        ticketRepository.save(ticket);
        return true;
    }

    public List getall(){
        return ticketRepository.findAll();
    }

    public Boolean update(Ticket ticket,int id){
        Ticket oldT=ticketRepository.getReferenceById(id);
        if (oldT==null){
            return false;
        }
       // oldT.setAId(ticket.getAId());
        oldT.setVId(ticket.getVId());
        //oldT.setDate(ticket.getDate());

        ticketRepository.save(oldT);
        return true;
    }

    public Boolean delete(int id){
        Ticket oldeT=ticketRepository.getReferenceById(id);
        if (oldeT==null){
            return false;
        }
        ticketRepository.delete(oldeT);
        return true;
    }

    //-----------------------------------------------


    public String reserveTicket(Integer visitorId, Integer workShopId, String ticketDate) {
        if (visitorId == null || workShopId == null || ticketDate == null) {
            return "Required parameters are missing";
        }

        WorkShop workShop = workShopRepository.findById(workShopId)
                .orElseThrow(() -> new IllegalArgumentException("Workshop not found with id: " + workShopId));


        Optional<Visitor> visitorOpt = visitorRepository.findById(visitorId);
        if (visitorOpt.isEmpty()) {
            return "Visitor not found";
        }

        if (workShop.getCapacity() <= 0) {
            return "No available spots in the workshop";
        }

        if (ticketRepository.existsByVIdAndWId(visitorId, workShopId)) {
            return "You have already reserved this workshop";
        }

        LocalDate parsedDate;
        try {
            parsedDate = LocalDate.parse(ticketDate);
        } catch (DateTimeParseException e) {
            return "Invalid ticket date format. Please use YYYY-MM-DD";
        }


        Ticket ticket = new Ticket();
        ticket.setVId(visitorId);
        ticket.setWId(workShopId);
        ticket.setTicketDate(parsedDate);
        ticket.setPrice(workShop.getPrice());

        ticketRepository.save(ticket);

        workShop.setCapacity(workShop.getCapacity() - 1);
        workShopRepository.save(workShop);

        return "Ticket reserved successfully!";
    }

    public String cancelReservation(Integer ticketId, Integer visitorId, Integer workshopId) {
        Ticket ticket = ticketRepository.findById(ticketId).orElse(null);

        if (ticket == null) {
            return "Ticket not found.";
        }

        if (!ticket.getVId().equals(visitorId) || !ticket.getWId().equals(workshopId)) {
            return "Ticket does not match the provided visitor or workshop.";
        }

        LocalDate today = LocalDate.now();
        LocalDate workshopStartDate = workShopRepository.findById(workshopId).map(workshop -> workshop.getStartDate()).orElse(null);

        if (workshopStartDate == null) {
            return "Workshop not found.";
        }

        if (workshopStartDate.equals(today)) {
            return "You can't cancel. The workshop starts today.";
        }

        ticketRepository.delete(ticket);
        return "Cancelled successfully.";
    }


    public Double getTotalSalesByArtist(Integer artistId) {
        Double total = ticketRepository.getTotalSalesByArtist(Double.valueOf(artistId));
        return total != null ? total : 0.0;
    }



}

