package test.java.com.ratepay.bugtracker.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.ratepay.bugtracker.controller.TicketController;
import com.ratepay.bugtracker.model.Ticket;
import com.ratepay.bugtracker.service.TicketService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TicketController.class)
class TicketControllerTest {

    private Ticket ticket1, ticket2;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TicketService ticketService;

    @BeforeEach
    void setUp() {
        ticket1  = new Ticket(100L, 100, "projectTest1", "issueTypeTest1" , "summaryTest1", "descriptionTest1", 1, "reporterTest1");
        ticket2  = new Ticket(200L, 200, "projectTest2", "issueTypeTest2" , "summaryTest2", "descriptionTest2", 2, "reporterTest2");
    }

    @Test
    void saveTicket() throws Exception {
        when(ticketService.saveTicket(any()))
                .thenReturn(ticket1);

        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/ticket/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(new ObjectMapper().writeValueAsString(ticket1))))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.ticketId").value(100L))
                .andExpect(jsonPath("$.ticketNumber").value(100))
                .andExpect(jsonPath("$.project").value("projectTest1"));
    }

    @Test
    void findAllTicket() throws Exception {
        when(ticketService
                .findAllTicket())
                .thenReturn(List.of(ticket1, ticket2));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/ticket/find/all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].ticketId").value(100L))
                .andExpect(jsonPath("$[1].ticketId").value(200L));
    }

    @Test
    void findTicketById() throws Exception {
        when(ticketService
                .findTicketById(100L))
                .thenReturn(ticket1);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/ticket/find/{id}", 100L))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ticketId").value(100L))
                .andExpect(jsonPath("$.ticketNumber").value(100));
    }

    @Test
    void updateTicket() throws Exception {
        when(ticketService.updateTicket(any()))
                .thenReturn(ticket1);

        this.mockMvc
                .perform(MockMvcRequestBuilders.put("/ticket/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(new ObjectMapper().writeValueAsString(ticket1))))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ticketId").value(100L))
                .andExpect(jsonPath("$.ticketNumber").value(100))
                .andExpect(jsonPath("$.project").value("projectTest1"));
    }

    @Test
    void deleteTicketById() throws Exception {
        when(ticketService.saveTicket(any()))
                .thenReturn(ticket1);

        this.mockMvc.perform(delete("/ticket/delete/{id}", 100L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}