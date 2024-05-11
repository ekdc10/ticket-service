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
@Table(name = "ticket")
public class Ticket extends BaseModel{
   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id")
    private Long ticketId;

    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    @Column(name = "event_id", nullable = false)
    private Long eventId;

    @Column(name = "ticket_selling_time_id")
    private Long ticketSellingTimeId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusType status;

    @Column(name = "ticket_booking_id")
    private Long ticketBookingId;

    @Column(name = "barcode_number", nullable = false)
    private Integer barcodeNumber;

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public Long getTicketSellingTimeId() {
        return ticketSellingTimeId;
    }

    public void setTicketSellingTimeId(Long ticketSellingTimeId) {
        this.ticketSellingTimeId = ticketSellingTimeId;
    }

    public Long getTicketBookingId() {
        return ticketBookingId;
    }

    public void setTicketBookingId(Long ticketBookingId) {
        this.ticketBookingId = ticketBookingId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public StatusType getStatus() {
        return status;
    }

    public void setStatus(StatusType status) {
        this.status = status;
    }

    public Integer getBarcodeNumber() {
        return barcodeNumber;
    }

    public void setBarcodeNumber(Integer barcodeNumber) {
        this.barcodeNumber = barcodeNumber;
    }

    public enum StatusType {
        AVAILABLE, SOLD_OUT, RESERVED
    }
}

