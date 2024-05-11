package com.ticket.repository;

import org.springframework.data.repository.CrudRepository;

import com.ticket.model.TicketSellingTime;

public interface TicketSellingTimeRepository extends CrudRepository<TicketSellingTime, Long> {
    TicketSellingTime findByTicketSellingTimeId(Long ticketSellingTimeId);
}
