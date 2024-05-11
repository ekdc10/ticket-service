package com.ticket.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ticket.model.Category;
import com.ticket.model.Event;
import com.ticket.model.Ticket;
import com.ticket.model.TicketBooking;
import com.ticket.model.TicketSellingTime;
import com.ticket.model.dto.BaseResponse;
import com.ticket.model.dto.CreateTicketResponse;
import com.ticket.model.dto.ListTicketResponse;
import com.ticket.model.dto.TicketCategory;
import com.ticket.repository.CategoryRepository;
import com.ticket.repository.EventRepository;
import com.ticket.repository.TicketBookingRepository;
import com.ticket.repository.TicketRepository;
import com.ticket.repository.TicketSellingTimeRepository;
import com.ticket.repository.UserTicketRepository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Service
public class TicketService {
    private static final Logger logger = LoggerFactory.getLogger(TicketService.class);
    // private final JdbcTemplate jdbcTemplate;

    @Autowired
    JdbcTemplate jdbcTemplate;
    
    @Autowired
    EntityManager entityManager;

    @Autowired
    UserTicketRepository userTicketRepository;

    @Autowired
    EventRepository eventRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    TicketSellingTimeRepository ticketSellingTimeRepository;

    @Autowired
    TicketBookingRepository ticketBookingRepository;

    ObjectMapper om = new ObjectMapper();

    Random random = new Random();

    @Transactional
    public BaseResponse<Event> createEvent(String userLogin, JsonNode request) throws Exception {
        JsonNode userLoginNode = om.readTree(userLogin);
        String userName = userLoginNode.findPath("userName").asText();
        
        Event event = new Event();
        event.setCreatedBy(userName);
        event.setEventName(request.findPath("eventName").asText());
        event.setEventLocation(request.findPath("eventLocation").asText());
        event.setTotalTicket(request.findPath("totalTicket").asInt());
        event.setEventImg(request.findPath("eventImg").asText());
        event.setEventSeatImg(request.findPath("eventSeatImg").asText());

        try {
            String sEventDatetime = request.findPath("eventDatetime").asText();
            LocalDateTime eventDatetime = LocalDateTime.parse(sEventDatetime);
            event.setEventDate(eventDatetime);
        } catch (Exception e) {
            logger.error("error parsing date {}", e.getMessage());
        }

        eventRepository.save(event);

        BaseResponse<Event> response = new BaseResponse<>();
        response.setData(event);
        return response;    
    }

    @Transactional
    public BaseResponse<Event> updateEvent(String userLogin, JsonNode request) throws Exception {
        BaseResponse<Event> response = new BaseResponse<>();
        JsonNode userLoginNode = om.readTree(userLogin);
        String userName = userLoginNode.findPath("userName").asText();

        Event event = eventRepository.findByEventId(request.findPath("eventId").asLong());
        if (event != null) {
            event.setUpdatedBy(userName);
            event.setEventName(request.findPath("eventName").asText());
            event.setEventLocation(request.findPath("eventLocation").asText());
            event.setTotalTicket(request.findPath("totalTicket").asInt());
            event.setEventImg(request.findPath("eventImg").asText());
            event.setEventSeatImg(request.findPath("eventSeatImg").asText());
    
            try {
                String sEventDatetime = request.findPath("eventDatetime").asText();
                LocalDateTime eventDatetime = LocalDateTime.parse(sEventDatetime);
                event.setEventDate(eventDatetime);
            } catch (Exception e) {
                logger.error("error parsing date {}", e.getMessage());
            }
    
            eventRepository.save(event);
        } else {
            response.setCode(500);
            response.setMessage("Data Not Found");
            return response;
        }

        response.setData(event);
        return response;    
    }

    @Transactional
    public BaseResponse<List<Category>> createCategory(String userLogin, JsonNode request) throws Exception {
        BaseResponse<List<Category>> response = new BaseResponse<>();
        List<JsonNode> categories = om.convertValue(request.findPath("categories"), new TypeReference<>() {});
        JsonNode userLoginNode = om.readTree(userLogin);
        String userName = userLoginNode.findPath("userName").asText();
        Event event = eventRepository.findByEventId(request.findPath("eventId").asLong());

        if (event == null) {
            response.setCode(500);
            response.setMessage("Event Not Found");
            return response;
        }

        List<Category> listCategory = new ArrayList<>();
        for (JsonNode node : categories) {
            Category category = new Category();
            category.setEventId(event.getEventId());
            category.setCategoryName(node.findPath("categoryName").asText());
            category.setCategoryPrice(node.findPath("categoryPrice").asDouble());
            category.setCategoryCurrency(node.findPath("categoryCurrency").asText());
            category.setCreatedBy(userName);

            listCategory.add(category);
        }

        categoryRepository.saveAll(listCategory);
        response.setData(listCategory);
        return response;    
    }

    @Transactional
    public BaseResponse<CreateTicketResponse> createTicket(String userLogin, JsonNode request) throws Exception{
        BaseResponse<CreateTicketResponse> response = new BaseResponse<>();
        List<JsonNode> ticketCategories = om.convertValue(request.findPath("ticketCategories"), new TypeReference<>() {});
        JsonNode userLoginNode = om.readTree(userLogin);
        String userName = userLoginNode.findPath("userName").asText();
        Event event = eventRepository.findByEventId(request.findPath("eventId").asLong());
        
        if (event == null) {
            response.setCode(500);
            response.setMessage("Event Not Found");
            return response;
        }
        List<TicketCategory> listTicketCategories = new ArrayList<>();
        for (JsonNode ticketCategory : ticketCategories) {
            Long categoryId = ticketCategory.findPath("categoryId").asLong();
            Integer totalTicket = ticketCategory.findPath("totalTicket").asInt(0);
            Category category = categoryRepository.findByCategoryId(categoryId);
            if (category == null) {
                response.setCode(500);
                response.setMessage("Category Not Found");
                return response;
            }
        
            List<Ticket> tickes = new ArrayList<>();
            for (int i=0; i < totalTicket; i++){
                int randomNumber = 10000 + random.nextInt(90000);

                Ticket ticket = new Ticket();
                ticket.setCreatedBy(userName);
                ticket.setCategoryId(category.getCategoryId());
                ticket.setEventId(event.getEventId());
                ticket.setStatus(Ticket.StatusType.AVAILABLE);
                ticket.setBarcodeNumber(randomNumber);
                tickes.add(ticket);
            }
            TicketCategory ticketCategoryDto = new TicketCategory();
            ticketCategoryDto.setCategoryId(category.getCategoryId());
            ticketCategoryDto.setCategoryName(category.getCategoryName());
            ticketCategoryDto.setPrice(category.getCategoryPrice());
            ticketCategoryDto.setTotalAvailableTicket(tickes.size());
            listTicketCategories.add(ticketCategoryDto);
            ticketRepository.saveAll(tickes);
        }

        CreateTicketResponse createTicketResponse = new CreateTicketResponse();
        createTicketResponse.setTicketCategories(listTicketCategories);
        response.setData(createTicketResponse);
        return response;    
    }


    public BaseResponse<ListTicketResponse> listTicket(JsonNode request) {
        String status = request.findPath("status").asText();
        
        ListTicketResponse results = getTicketSummary(status);
        BaseResponse<ListTicketResponse> response = new BaseResponse<>();
        response.setData(results);
        return response;
    }

    public ListTicketResponse getTicketSummary(String status) {
        StringBuilder sqlQuery = new StringBuilder();
            sqlQuery.append("SELECT ");
            sqlQuery.append("SUM(totalTicket) AS total, ");
            sqlQuery.append("jsonb_agg(data_ticket) AS dataticket ");
            sqlQuery.append("FROM ( ");
            sqlQuery.append("SELECT ");
            sqlQuery.append("c.category_id, ");
            sqlQuery.append("c.category_name, ");
            sqlQuery.append("c.category_price, ");
            sqlQuery.append("c.category_currency, ");
            sqlQuery.append("COUNT(t.ticket_id) AS totalTicket, ");
            sqlQuery.append("jsonb_build_object( ");
            sqlQuery.append("'categoryName', c.category_name, ");
            sqlQuery.append("'categoryId', c.category_id, ");
            sqlQuery.append("'categoryPrice', c.category_price, ");
            sqlQuery.append("'categoryCurrency', c.category_currency, ");
            sqlQuery.append("'totalTicket', COUNT(t.ticket_id), ");
            sqlQuery.append("'totalSellingNow',  COUNT(t.ticket_id) FILTER ( ");
            sqlQuery.append("WHERE t.ticket_selling_time_id IN ( ");
            sqlQuery.append("SELECT ticket_selling_time_id ");
            sqlQuery.append("FROM ticket_schema.ticket_selling_time tst ");
            sqlQuery.append("WHERE tst.start_datetime <= CURRENT_TIMESTAMP ");
            sqlQuery.append("AND tst.end_datetime >= CURRENT_TIMESTAMP ");
            sqlQuery.append(") ");
            sqlQuery.append(") ");
            sqlQuery.append(") AS data_ticket ");
            sqlQuery.append("FROM ");
            sqlQuery.append("ticket_schema.category c ");
            sqlQuery.append("LEFT JOIN ticket_schema.ticket t ON t.category_id = c.category_id ");
            sqlQuery.append("WHERE t.status = ? ");
            sqlQuery.append("GROUP BY ");
            sqlQuery.append("c.category_id, c.category_name, c.category_price, c.category_currency ");
            sqlQuery.append(") AS t;");

        return jdbcTemplate.queryForObject(sqlQuery.toString(), (rs, rowNum) -> {
            ListTicketResponse response = new ListTicketResponse();
            String dataTicket = rs.getString("dataticket");
            response.setTotalTicket(rs.getInt("total"));
            try {
                response.setTicketCategories(om.readTree(dataTicket));
            } catch (Exception e) {
                logger.error("No Data Found");
            } 
                        
            return response;
        }, status);    
    }

    @Transactional
    public BaseResponse<List<TicketSellingTime>> setSellingTime(String userLogin, JsonNode request) throws Exception {
        BaseResponse<List<TicketSellingTime>> response = new BaseResponse<>();
        List<JsonNode> sellingTimes = om.convertValue(request.findPath("sellingTimes"), new TypeReference<>() {});
        JsonNode userLoginNode = om.readTree(userLogin);
        String userName = userLoginNode.findPath("userName").asText();
        Event event = eventRepository.findByEventId(request.findPath("eventId").asLong());

        if (event == null) {
            response.setCode(500);
            response.setMessage("Event Not Found");
            return response;
        }

        List<TicketSellingTime> listSellingTime = new ArrayList<>();
        for (JsonNode node : sellingTimes) {
            TicketSellingTime ticketSellingTime = new TicketSellingTime();
            ticketSellingTime.setEventId(event.getEventId());
            ticketSellingTime.setCreatedBy(userName);

            try {
                String sStartDateTime = node.findPath("startDateTime").asText();
                String sEndDateTime = node.findPath("endDateTime").asText();
                LocalDateTime startDateTime = LocalDateTime.parse(sStartDateTime);
                LocalDateTime endDateTime = LocalDateTime.parse(sEndDateTime);

                ticketSellingTime.setStartDateTime(startDateTime);
                ticketSellingTime.setEndDateTime(endDateTime);
            } catch (Exception e) {
                response.setCode(500);
                response.setMessage("Invalid Date");
                return response;
            }

            ticketSellingTime.setMaxBuying(node.findPath("maxBuying").asInt());
            ticketSellingTime.setTotalTicket(node.findPath("totalTicket").asInt());
            ticketSellingTime.setStatus(TicketSellingTime.StatusType.AVAILABLE);
            ticketSellingTimeRepository.save(ticketSellingTime);
            listSellingTime.add(ticketSellingTime);

            String categoryId = node.findPath("categoryId").asText();
            ticketRepository.updateTicketSellingTimeId(ticketSellingTime.getTicketSellingTimeId(), ticketSellingTime.getTotalTicket(), categoryId);            
        }

        response.setData(listSellingTime);
        return response;    
    }


    @Transactional
    public BaseResponse<List<Ticket>> reserveTicket(String userLogin, JsonNode request) throws Exception {
        BaseResponse<List<Ticket>> response = new BaseResponse<>();
        List<JsonNode> reserveTicketCategories = om.convertValue(request.findPath("reserveTicketCategories"), new TypeReference<>() {});
        Ticket.StatusType oldStatus = Ticket.StatusType.valueOf(request.findPath("oldStatus").asText());
        Ticket.StatusType newStatus = Ticket.StatusType.valueOf(request.findPath("newStatus").asText());
        JsonNode userLoginNode = om.readTree(userLogin);
        String userName = userLoginNode.findPath("userName").asText();
        Event event = eventRepository.findByEventId(request.findPath("eventId").asLong());

        if (event == null) {
            response.setCode(500);
            response.setMessage("Event Not Found");
            return response;
        }

        TicketBooking ticketBooking = new TicketBooking();
        ticketBooking.setCreatedBy(userName);
        ticketBooking.setEventId(event.getEventId());
        ticketBooking.setBookedDateTime(LocalDateTime.now());
        ticketBooking.setStatus(TicketBooking.StatusType.OPEN);
        ticketBookingRepository.save(ticketBooking);

        for (JsonNode node : reserveTicketCategories) {
            Integer totalTicket = node.findPath("totalTicket").asInt();
            Long categoryId = node.findPath("categoryId").asLong();
            ticketRepository.updateTicketStatusByStatusAndCategory(oldStatus, newStatus, categoryId, ticketBooking.getTicketBookingId(), totalTicket);
        }
        
        response.setData(ticketRepository.findByTicketBookingId(ticketBooking.getTicketBookingId()));
        return response;    
    }
}
