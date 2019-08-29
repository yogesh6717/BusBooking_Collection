package com.capg.bbs.service;

import java.sql.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.capg.bbs.beans.Admin;
import com.capg.bbs.beans.Available;
import com.capg.bbs.beans.Bus;
import com.capg.bbs.beans.Suggestion;
import com.capg.bbs.beans.Ticket;
import com.capg.bbs.beans.User;
import com.capg.bbs.dao.BusBookingDAOImpl;
import com.capg.bbs.dao.BusBookingDAO;

public class BusBookingServiceImpl implements BusBookingService {

	BusBookingDAO busbookingdao = new BusBookingDAOImpl();

	@Override
	public Boolean createUser(User user) {
		return busbookingdao.createUser(user);
	}

	@Override
	public Boolean updateUser(User user) {
		return busbookingdao.updateUser(user);
	}

	@Override
	public Boolean deleteUser(int user_id, String password) {
		return busbookingdao.deleteUser(user_id, password);
	}

	@Override
	public User loginUser(int user_id, String password) {
		return busbookingdao.loginUser(user_id, password);
	}

	@Override
	public Admin adminLogin(int admin_id, String password) {
		return busbookingdao.adminLogin(admin_id, password);
	}

	@Override
	public User searchUser(int user_id) {
		return busbookingdao.searchUser(user_id);
	}

	@Override
	public Boolean createBus(Bus bus) {
		return busbookingdao.createBus(bus);
	}

	@Override
	public Boolean updateBus(Bus bus) {
		return busbookingdao.updateBus(bus);
	}

	@Override
	public Bus searchBus(int bus_id) {
		return busbookingdao.searchBus(bus_id);
	}

	@Override
	public Boolean deletebus(int bus_id) {
		return busbookingdao.deletebus(bus_id);
	}

	@Override
	public Ticket bookTicket(Ticket ticket) {
		return busbookingdao.bookTicket(ticket);
	}

	@Override
	public Boolean cancelTicket(int booking_id) {
		return busbookingdao.cancelTicket(booking_id);
	}

	@Override
	public Ticket getTicket(int booking_id) {
		return busbookingdao.getTicket(booking_id);
	}

	@Override
	public List<Available> checkAvailability(String source, String destination, Date date) {
		return busbookingdao.checkAvailability(source, destination, date);
	}

	@Override
	public Integer checkAvailability(int bus_id, Date date) {
		return busbookingdao.checkAvailability(bus_id, date);
	}

	@Override
	public Boolean setAvailability(Available available) {
		return busbookingdao.setBusAvailability(available);
	}

	@Override
	public Boolean giveFeedback(int userId, String feedback) {
		return busbookingdao.giveFeedback(userId, feedback);
	}

	@Override
	public List<Suggestion> getAllSuggestions(Suggestion sugg) {
		return busbookingdao.getAllSuggestions(sugg);
	}

	@Override
	public Integer validationId(String id) {
		Pattern pat = Pattern.compile("\\d+");
		Matcher mat = pat.matcher(id);
		if (mat.matches()) {
			return Integer.parseInt(id);
		} else {
			return null;
		}

	}

	@Override
	public String validationEmail(String email) {
		Pattern pat = Pattern.compile("\\w+\\@\\w+\\.\\w+");
		Matcher mat = pat.matcher(email);
		if (mat.matches()) {
			return email;
		} else {
			return null;
		}

	}

	@Override
	public Long validationContact(String contact) {
		Pattern pat = Pattern.compile("\\d{10}");
		Matcher mat = pat.matcher(contact);
		if (mat.matches()) {
			return Long.parseLong(contact);
		} else {
			return null;
		}

	}

}
