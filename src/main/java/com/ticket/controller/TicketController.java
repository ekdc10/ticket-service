package com.ticket.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.ticket.model.Category;
import com.ticket.model.Event;
import com.ticket.model.Ticket;
import com.ticket.model.TicketSellingTime;
import com.ticket.model.UserTicket;
import com.ticket.model.dto.BaseResponse;
import com.ticket.model.dto.CreateTicketResponse;
import com.ticket.model.dto.ListTicketResponse;
import com.ticket.service.TicketService;

@RestController
@RequestMapping("/ticket")
public class TicketController {

    @Autowired
    TicketService ticketService;

	@PostMapping("/createEvent")
	public BaseResponse<Event> createEvent(@RequestHeader("User-Login") String userLogin, @RequestBody JsonNode request) throws Exception {
		return ticketService.createEvent(userLogin, request);
	}

	@PostMapping("/updateEvent")
	public BaseResponse<Event> updateEvent(@RequestHeader("User-Login") String userLogin, @RequestBody JsonNode request) throws Exception {
		return ticketService.updateEvent(userLogin, request);
	}

	@PostMapping("/createCategory")
	public BaseResponse<List<Category>> createCategory(@RequestHeader("User-Login") String userLogin, @RequestBody JsonNode request) throws Exception {
		return ticketService.createCategory(userLogin, request);
	}

	@PostMapping("/createTicket")
	public BaseResponse<CreateTicketResponse> createTicket(@RequestHeader("User-Login") String userLogin, @RequestBody JsonNode request) throws Exception {
		return ticketService.createTicket(userLogin, request);
	}

	@PostMapping("/setSellingTime")
	public BaseResponse<List<TicketSellingTime>> setSellingTime(@RequestHeader("User-Login") String userLogin, @RequestBody JsonNode request) throws Exception {
		return ticketService.setSellingTime(userLogin, request);
	}

	@PostMapping("/reserveTicket")
	public BaseResponse<List<Ticket>> reserveTicket(@RequestHeader("User-Login") String userLogin, @RequestBody JsonNode request) throws Exception {
		return ticketService.reserveTicket(userLogin, request);
	}

	@GetMapping("/listByStatus")
	public BaseResponse<ListTicketResponse> listTicket(@RequestBody JsonNode request) {
		return ticketService.listTicket(request);
	}
}
