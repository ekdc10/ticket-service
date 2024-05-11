package com.ticket.scheduler;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ticket.model.Ticket;
import com.ticket.repository.TicketBookingRepository;
import com.ticket.repository.TicketRepository;

import jakarta.transaction.Transactional;

@Component
public class TicketScheduler {

    @Autowired
    TicketBookingRepository ticketBookingRepository;

    @Scheduled(cron = "0 */1  * * * ?")
    @Transactional
    public void scheduleTaskUsingCronExpression() {

    }

}
