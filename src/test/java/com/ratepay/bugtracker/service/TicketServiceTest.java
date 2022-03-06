package test.java.com.ratepay.bugtracker.service;

import com.ratepay.bugtracker.exception.TicketException;
import com.ratepay.bugtracker.model.Ticket;
import com.ratepay.bugtracker.repository.TicketRepository;
import com.ratepay.bugtracker.service.TicketService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class TicketServiceTest {

    private Ticket ticket1, ticket2;

    @InjectMocks
    private TicketService ticketService;

    @Mock
    private TicketRepository ticketRepository;

    void setUp() {
        ticket1  = new Ticket(100L, 100, "projectTest1", "issueTypeTest1" , "summaryTest1", "descriptionTest1", 1, "reporterTest1");
        ticket2  = new Ticket(200L, 200, "projectTest2", "issueTypeTest2" , "summaryTest2", "descriptionTest2", 2, "reporterTest2");
    }

    @Test
    void saveTicket() {
        when(ticketRepository.save(ticket1)).thenReturn(ticket1);
        Ticket result = ticketService.saveTicket(ticket1);

        assertThat(result).isEqualTo(ticket1);
        verify(ticketRepository, times(1)).save(ticket1);
    }

    @Test
    void findAllTicket() {
        List<Ticket> list = new ArrayList<>();
        list.add(ticket1);
        list.add(ticket2);

        when(ticketRepository.findAll()).thenReturn(list);
        List<Ticket> result = ticketService.findAllTicket();

        assertEquals(2, result.size());
        verify(ticketRepository, times(1)).findAll();
    }

    @Test
    void findTicketById() {
        ticket1  = new Ticket(100L, 100, "projectTest1", "issueTypeTest1" , "summaryTest1", "descriptionTest1", 1, "reporterTest1");
        when(ticketRepository.findById(ticket1.getTicketId())).thenReturn(Optional.of(ticket1));
        Ticket contractReturned = ticketService.findTicketById(ticket1.getTicketId());

        assertThat(contractReturned).isEqualTo(ticket1);
        assertEquals(100L, contractReturned.getTicketId());
        assertEquals(100, contractReturned.getTicketNumber());
        assertEquals("projectTest1", contractReturned.getProject());
        verify(ticketRepository, times(1)).findById(contractReturned.getTicketId());
    }

    @Test
    void updateTicket() {
        ticket1  = new Ticket(100L, 100, "projectTest1", "issueTypeTest1" , "summaryTest1", "descriptionTest1", 1, "reporterTest1");
        ticket1.setTicketNumber(500);
        ticket1.setProject("projectTest9");

        when(ticketRepository.existsById(any())).thenReturn(true);
        when(ticketRepository.save(ticket1)).thenReturn(ticket1);

        Exception exception = assertThrows(TicketException.class, () -> ticketService.updateTicket(ticket1));
        String expectedMessage = "The ticket already exists. Try another number.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void deleteTicketById() {
    }
}