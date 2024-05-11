package com.ticket.repository;

import org.springframework.data.repository.CrudRepository;

import com.ticket.model.Event;

public interface EventRepository extends CrudRepository<Event, Long> {
    Event findByEventId(Long eventId);
}
