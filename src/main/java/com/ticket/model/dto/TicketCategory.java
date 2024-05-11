package com.ticket.model.dto;

public class TicketCategory {
    private Integer totalAvailableTicket;
    private Long categoryId;
    private String categoryName;
    private Double price;
    
    public Integer getTotalAvailableTicket() {
        return totalAvailableTicket;
    }
    public void setTotalAvailableTicket(Integer totalAvailableTicket) {
        this.totalAvailableTicket = totalAvailableTicket;
    }
    public Long getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
    public String getCategoryName() {
        return categoryName;
    }
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }

}