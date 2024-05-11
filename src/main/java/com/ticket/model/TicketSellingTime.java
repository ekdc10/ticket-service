package com.ticket.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ticket_selling_time")
public class TicketSellingTime extends BaseModel{
   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_selling_time_id")
    private Long ticketSellingTimeId;

    @Column(name = "event_id", nullable = false)
    private Long eventId;

    @Column(name = "start_datetime", nullable = false)
    private LocalDateTime startDateTime;

    @Column(name = "end_datetime", nullable = false)
    private LocalDateTime endDateTime;

    @Column(name = "max_buying")
    private Integer maxBuying;

    @Column(name = "total_ticket", nullable = false)
    private Integer totalTicket;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusType status;

    public Long getTicketSellingTimeId() {
        return ticketSellingTimeId;
    }

    public void setTicketSellingTimeId(Long ticketSellingTimeId) {
        this.ticketSellingTimeId = ticketSellingTimeId;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public Integer getMaxBuying() {
        return maxBuying;
    }

    public void setMaxBuying(Integer maxBuying) {
        this.maxBuying = maxBuying;
    }

    public Integer getTotalTicket() {
        return totalTicket;
    }

    public void setTotalTicket(Integer totalTicket) {
        this.totalTicket = totalTicket;
    }

    public StatusType getStatus() {
        return status;
    }

    public void setStatus(StatusType status) {
        this.status = status;
    }

    public enum StatusType {
        AVAILABLE, SOLD_OUT, CLOSED
    }
}
