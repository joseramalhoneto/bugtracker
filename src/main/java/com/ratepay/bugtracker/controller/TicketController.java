package com.ratepay.bugtracker.controller;

import com.ratepay.bugtracker.model.Ticket;
import com.ratepay.bugtracker.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/ticket")
public class TicketController {

    private final TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping("/save")
    public ResponseEntity<Ticket> saveTicket(@RequestBody Ticket ticket){
        Ticket ticketReturn = ticketService.saveTicket(ticket);
        return new ResponseEntity<>(ticketReturn, HttpStatus.CREATED);
    }

    @GetMapping("/find/all")
    public ResponseEntity<List<Ticket>> findAllTicket(){
        List<Ticket> allTickets = ticketService.findAllTicket();
        return new ResponseEntity<>(allTickets, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Ticket> findTicketById(@PathVariable("id") Long id){
        Ticket ticketById = ticketService.findTicketById(id);
        return new ResponseEntity<>(ticketById, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<Ticket> updateTicket(@RequestBody Ticket ticket){
        Ticket ticketReturn = ticketService.updateTicket(ticket);
        return new ResponseEntity<>(ticketReturn, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteTicketById(@PathVariable("id") Long id){
        ticketService.deleteTicketById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
