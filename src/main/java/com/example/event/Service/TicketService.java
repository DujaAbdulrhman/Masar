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


    public Double getAverageAgeOfVisitorsWhoBooked() {
        List<Integer> visitorIds = ticketRepository.findDistinctVisitorIdsWhoBooked();

        if (visitorIds.isEmpty()) {
            return 0.0;
        }

        List<Visitor> visitors = visitorRepository.findAllById(visitorIds);

        double average = visitors.stream()
                .filter(v -> v.getAge() != null)
                .mapToInt(Visitor::getAge)
                .average()
                .orElse(0.0);

        return average;
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

