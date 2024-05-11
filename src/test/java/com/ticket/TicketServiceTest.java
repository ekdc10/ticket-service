package com.ticket;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ticket.model.Event;
import com.ticket.model.Ticket;
import com.ticket.model.TicketBooking;
import com.ticket.model.dto.BaseResponse;
import com.ticket.model.dto.ListTicketResponse;
import com.ticket.repository.EventRepository;
import com.ticket.repository.TicketBookingRepository;
import com.ticket.repository.TicketRepository;
import com.ticket.service.TicketService;

@SpringBootTest
public class TicketServiceTest {
    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private TicketService ticketService;

    @Mock
    private JsonNode request;

    @Mock
    private ListTicketResponse mockListTicketResponse;

    @Mock
    JdbcTemplate jdbcTemplate;

    @Mock
    private TicketBookingRepository ticketBookingRepository;

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private BaseResponse<List<Ticket>> baseResponse;

    private ObjectMapper om;

    @Before
    public void setup() {
        om = new ObjectMapper();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateEvent() throws Exception {
        String userLogin = "{\"userName\": \"testUser\"}";

        String requestJson = "{\"eventName\": \"Test Event\", \"eventLocation\": \"Test Location\", "
                + "\"totalTicket\": 100, \"eventImg\": \"event_image.jpg\", \"eventSeatImg\": \"seat_image.jpg\", "
                + "\"eventDatetime\": \"2024-05-20T10:00:00\"}";
        JsonNode request = om.readTree(requestJson);

        Event event = new Event();
        event.setCreatedBy("testUser");
        event.setEventName("Test Event");
        event.setEventLocation("Test Location");
        event.setTotalTicket(100);
        event.setEventImg("event_image.jpg");
        event.setEventSeatImg("seat_image.jpg");
        event.setEventDate(LocalDateTime.parse("2024-05-20T10:00:00"));

        Mockito.when(eventRepository.save(Mockito.any(Event.class))).thenReturn(event);

        BaseResponse<Event> response = ticketService.createEvent(userLogin, request);

        assertEquals("testUser", response.getData().getCreatedBy());
        assertEquals("Test Event", response.getData().getEventName());
        assertEquals("Test Location", response.getData().getEventLocation());
        assertEquals("100", response.getData().getTotalTicket().toString());
        assertEquals("event_image.jpg", response.getData().getEventImg());
        assertEquals("seat_image.jpg", response.getData().getEventSeatImg());
        assertEquals(LocalDateTime.parse("2024-05-20T10:00:00"), response.getData().getEventDate());
    }

    @Test
    public void testListTicket() throws Exception {
        when(request.findPath("status")).thenReturn(mock(JsonNode.class));
        when(request.findPath("status").asText()).thenReturn("AVAILABLE");

        when(ticketService.getTicketSummary("AVAILABLE")).thenReturn(mockListTicketResponse);

        BaseResponse<ListTicketResponse> response = ticketService.listTicket(request);

        assertNotNull(response);
    }
    
    @Test
    public void testReserveTicket() throws Exception {
        String userLogin = "{\"userName\": \"testUser\"}";
        String requestData = "{\"oldStatus\":\"AVAILABLE\",\"newStatus\":\"RESERVED\",\"eventId\":1,\"reserveTicketCategories\":[{\"totalTicket\":3,\"categoryId\":1},{\"totalTicket\":4,\"categoryId\":2}]}";
        JsonNode request = om.readTree(requestData);
        Event mockEvent = new Event();
        when(eventRepository.findByEventId(anyLong())).thenReturn(mockEvent);

        TicketBooking mockTicketBooking = new TicketBooking();
        when(ticketBookingRepository.save(any(TicketBooking.class))).thenReturn(mockTicketBooking);

        BaseResponse<List<Ticket>> response = ticketService.reserveTicket(userLogin, request);

        assertNotNull(response);
        assertNotNull(response.getData());
    }
}
