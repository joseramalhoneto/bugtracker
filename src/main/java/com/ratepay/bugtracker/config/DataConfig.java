package com.ratepay.bugtracker.config;

import com.ratepay.bugtracker.model.Ticket;
import com.ratepay.bugtracker.repository.TicketRepository;
import org.springframework.context.annotation.Configuration;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Configuration
public class DataConfig {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final TicketRepository ticketRepository;

    @Autowired
    public DataConfig(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Bean
    CommandLineRunner runner() {
        return args -> {
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<List<Ticket>> typeReferenceCustomer  = new TypeReference<List<Ticket>>() {};
            InputStream inputStreamCustomer = TypeReference.class.getResourceAsStream("/static/json/ticket_structure.json");
            try {
                ticketRepository.deleteAll();
                List<Ticket> customers = mapper.readValue(inputStreamCustomer, typeReferenceCustomer);
                ticketRepository.saveAll(customers);
            } catch (IOException e){
                logger.error(e.getMessage());
            }
        };
    };
}
