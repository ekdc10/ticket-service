package com.ticket.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;

@MappedSuperclass
public class BaseModel {

    @Column(name = "created_datetime", nullable = false)
    @ColumnDefault("current_timestamp")
    private LocalDateTime createdDateTime;

    @Column(name = "created_by", nullable = false)
    @ColumnDefault("''")
    private String createdBy;

    @Column(name = "updated_datetime", nullable = false)
    @ColumnDefault("current_timestamp")
    private LocalDateTime updatedDateTime;

    @Column(name = "updated_by", nullable = false)
    @ColumnDefault("''")
    private String updatedBy;

    @Column(name = "is_active", nullable = false)
    @ColumnDefault("true")
    private boolean isActive;

    @Column(name = "version", nullable = false)
    private Integer version;

    @Column(name = "inactive_datetime")
    private LocalDateTime inactiveDateTime;

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(LocalDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getUpdatedDateTime() {
        return updatedDateTime;
    }

    public void setUpdatedDateTime(LocalDateTime updatedDateTime) {
        this.updatedDateTime = updatedDateTime;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public LocalDateTime getInactiveDateTime() {
        return inactiveDateTime;
    }

    public void setInactiveDateTime(LocalDateTime inactiveDateTime) {
        this.inactiveDateTime = inactiveDateTime;
    }

    public Integer isVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
    
    @PrePersist
    protected void beforePersist() {
        createdDateTime = LocalDateTime.now();
        updatedDateTime = LocalDateTime.now();
        isActive = true;
        updatedBy = "";
        if (version == null) {
            version = 1;
        } else {
            version = version + 1;
        }
    }
}

