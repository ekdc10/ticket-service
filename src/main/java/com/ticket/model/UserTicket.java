package com.ticket.model;

import java.util.List;

import org.springframework.data.repository.Repository;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_ticket")
public class UserTicket extends BaseModel{
   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_ticket_id")
    private Long userTicketId;

    @Column(name = "ticket_id")
    private Long ticketId;

    @OneToMany(mappedBy = "userTicket")
    private List<User> user;

    @Column(name = "ticket_selling_time_id")
    private Long ticketSellingTimeId;

}

