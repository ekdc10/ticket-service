package com.ticket.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "category")
public class Category extends BaseModel {
   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "event_id", nullable = false)
    private Long eventId;

    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "category_price")
    private Double categoryPrice;

    @Column(name = "category_currency")
    private String categoryCurrency;

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

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Double getCategoryPrice() {
        return categoryPrice;
    }

    public void setCategoryPrice(Double categoryPrice) {
        this.categoryPrice = categoryPrice;
    }

    public String getCategoryCurrency() {
        return categoryCurrency;
    }

    public void setCategoryCurrency(String categoryCurrency) {
        this.categoryCurrency = categoryCurrency;
    }

    
}
