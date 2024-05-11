package com.ticket.model.dto;

import java.util.List;

public class CreateTicketResponse extends BaseResponse {
    private List<TicketCategory> TicketCategories;

    public List<TicketCategory> getTicketCategories() {
        return TicketCategories;
    }


    public void setTicketCategories(List<TicketCategory> ticketCategories) {
        TicketCategories = ticketCategories;
    }

}
