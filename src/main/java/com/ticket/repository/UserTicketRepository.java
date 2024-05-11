package com.ticket.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ticket.model.UserTicket;

public interface UserTicketRepository extends CrudRepository<UserTicket, Long> {
    List<UserTicket> findByTicketId(Long ticketId);
}
