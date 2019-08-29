package com.capg.bbs.service;

import java.util.List;

import com.capg.bbs.beans.Admin;
import com.capg.bbs.beans.Available;
import com.capg.bbs.beans.Bus;
import com.capg.bbs.beans.Suggestion;
import com.capg.bbs.beans.Ticket;
import com.capg.bbs.beans.User;

public interface BusBookingService {

	// user manipulation
	public Boolean createUser(User user);

	public Boolean updateUser(User user);

	public Boolean deleteUser(int user_id, String password);

	public User loginUser(int user_id, String password);

	public User searchUser(int user_id);

	// bus manipulations
	public Boolean createBus(Bus bus);

	public Boolean updateBus(Bus bus);

	public Bus searchBus(int bus_id);

	public Boolean deletebus(int bus_id);

	// admin
	public Admin adminLogin(int admin_id, String password);

	// ticket booking
	public Ticket bookTicket(Ticket ticket);

	public Boolean cancelTicket(int booking_id);

	public Ticket getTicket(int booking_id);

	public List<Available> checkAvailability(String source, String destination, java.sql.Date date);

	public Integer checkAvailability(int bus_id, java.sql.Date date);

	public Boolean setAvailability(Available available);

	// suggestions
	public Boolean giveFeedback(int userId, String feedback);

	public List<Suggestion> getAllSuggestions(Suggestion sugg);

	public Integer validationId(String id);

	public String validationEmail(String email);

	public Long validationContact(String contact);

}
