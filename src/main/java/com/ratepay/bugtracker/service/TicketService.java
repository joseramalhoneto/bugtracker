package com.ratepay.bugtracker.service;

import com.ratepay.bugtracker.exception.ResourceNotFoundException;
import com.ratepay.bugtracker.exception.TicketException;
import com.ratepay.bugtracker.model.Ticket;
import com.ratepay.bugtracker.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;

    @Autowired
    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public Ticket saveTicket(Ticket ticket){
        Integer ticketByTicketNumber = ticketRepository.findTicketByTicketNumber(ticket.getTicketNumber());

        if(ticketByTicketNumber == null){
            return ticketRepository.save(ticket);
        }else{
            throw new TicketException("The ticket already exists. Try another number.");
        }
    }

    public List<Ticket> findAllTicket(){
        return ticketRepository.findAll();
    }

    public Ticket findTicketById(Long id){
        return ticketRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found with id: " + id));
    }

    public Ticket updateTicket(Ticket Ticket){
        return ticketRepository.save(Ticket);
    }

    public void deleteTicketById(Long id){
        ticketRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found with id: " + id));

        ticketRepository.deleteById(id);
    }

}
