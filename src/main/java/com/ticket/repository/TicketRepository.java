package com.ticket.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.ticket.model.Ticket;

import jakarta.transaction.Transactional;

public interface TicketRepository extends CrudRepository<Ticket, Long> {
    Ticket findByTicketId(Long ticketId);
    List<Ticket> findTopByStatusAndTicketSellingTimeIdIsNull(Ticket.StatusType status, Pageable pageable);
    List<Ticket> findByTicketBookingId(Long ticketBookingId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Ticket t "
                   + "SET ticketSellingTimeId = :sellingTimeId "
                   + "WHERE t.ticketSellingTimeId IS NULL "
                   + "AND t.status = 'AVAILABLE' "
                   + "AND t.categoryId = :categoryId "
                   + "AND t.ticketId IN ( "
                   + "    SELECT tt.ticketId "
                   + "    FROM Ticket tt "
                   + "    WHERE tt.ticketSellingTimeId IS NULL "
                   + "    AND tt.status = 'AVAILABLE' "
                   + "    AND tt.categoryId = :categoryId"
                   + "    ORDER BY tt.ticketId "
                   + "    LIMIT :limit "
                   + ")")
    void updateTicketSellingTimeId(Long sellingTimeId, int limit, String categoryId);

    @Modifying
    @Transactional
    @Query("UPDATE Ticket t "
            + "SET status = :newStatus, "
            + "ticketBookingId = :ticketBookingId "
            + "WHERE t.ticketId IN ( "
            + "    SELECT distinct tc.ticketId "
            + "    FROM TicketSellingTime ts "
            + "    JOIN Ticket tc "
            + "    WHERE ts.startDateTime <= CURRENT_TIMESTAMP "
            + "    AND ts.endDateTime >= CURRENT_TIMESTAMP "
            + "    AND tc.status = :oldStatus "
            + "    AND tc.categoryId = :categoryId "
            + "    AND tc.status = 'AVAILABLE' "
            + "    ORDER BY tc.ticketId "
            + "    LIMIT :limit "
            + ") ")
    void updateTicketStatusByStatusAndCategory(Ticket.StatusType oldStatus, Ticket.StatusType newStatus, Long categoryId, Long ticketBookingId, int limit);

}