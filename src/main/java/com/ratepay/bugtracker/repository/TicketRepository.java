package com.ratepay.bugtracker.repository;

import com.ratepay.bugtracker.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    @Query(value = "SELECT ticket_number FROM Ticket WHERE ticket_number = :ticketNumber", nativeQuery = true)
    public Integer findTicketByTicketNumber(@Param("ticketNumber") int ticketNumber);

}
