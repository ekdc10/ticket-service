package com.ticket.repository;

import org.springframework.data.repository.CrudRepository;

import com.ticket.model.TicketBooking;

public interface TicketBookingRepository extends CrudRepository<TicketBooking, Long> {

}