package com.ticket.model.dto;

public class ListTicketResponse {
    private Integer totalTicket;
    private Object ticketCategories;

    public Object getTicketCategories() {
        return ticketCategories;
    }

    public void setTicketCategories(Object ticketCategories) {
        this.ticketCategories = ticketCategories;
    }

    public Integer getTotalTicket() {
        return totalTicket;
    }

    public void setTotalTicket(Integer totalTicket) {
        this.totalTicket = totalTicket;
    }
}
