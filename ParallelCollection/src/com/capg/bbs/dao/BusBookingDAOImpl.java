package com.capg.bbs.dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.capg.bbs.beans.Admin;
import com.capg.bbs.beans.Available;
import com.capg.bbs.beans.Bus;
import com.capg.bbs.beans.Suggestion;
import com.capg.bbs.beans.Ticket;
import com.capg.bbs.beans.User;
import com.capg.bbs.repository.Repository;

public class BusBookingDAOImpl implements BusBookingDAO {
	Repository repo = new Repository();
	private HashMap<Integer, User> userMap = repo.userMap;
	private HashMap<Integer, Admin> adminMap = repo.adminMap;
	private HashMap<Integer, Bus> busMap = repo.busMap;
	private HashMap<Integer, Ticket> bookMap = repo.bookMap;
	private HashMap<Integer, Suggestion> suggestionMap = repo.suggestionMap;
	private HashMap<Integer, Available> availMap = repo.availMap;

	// create user

	@Override
	public Boolean createUser(User user) {
		if (user != null) {
			userMap.put(user.getUserId(), user);
			return true;
		} else {
			return false;
		}
	}

	// update user

	@Override
	public Boolean updateUser(User user) {
		if (userMap.containsKey(user.getUserId()) != false) {
			userMap.put(user.getUserId(), user);
			return true;
		} else {
			return false;
		}
	}

	// deleteUser

	@Override
	public Boolean deleteUser(int user_id, String password) {
		if (userMap.containsKey(user_id)) {
			User user = userMap.get(user_id);
			if (user.getUserPassword().equals(password)) {

				userMap.remove(user_id);
				return true;
			}
		}
		return false;
	}

	// loginUser

	@Override
	public User loginUser(int user_id, String password) {
		if (userMap.containsKey(user_id)) {
			User user = userMap.get(user_id);
			if (user.getUserPassword().equals(password)) {
				return user;
			}
		}
		return null;
	}

	// searchUser

	@Override
	public User searchUser(int user_id) {
		if (userMap.containsKey(user_id)) {
			return userMap.get(user_id);
		}
		return null;
	}

	// createBus

	@Override
	public Boolean createBus(Bus bus) {
		if (busMap.containsKey(bus.getBusId()) != false) {
			busMap.put(bus.getBusId(), bus);
			return true;
		}
		return false;
	}

	// updateBus

	@Override
	public Boolean updateBus(Bus bus) {
		if (busMap.containsKey(bus.getBusId())) {
			busMap.put(bus.getBusId(), bus);
			return true;
		}
		return false;
	}

	// searchBus

	@Override
	public Bus searchBus(int bus_id) {
		if (busMap.containsKey(bus_id)) {
			return busMap.get(bus_id);
		}
		return null;
	}

	// deletebus

	@Override
	public Boolean deletebus(int bus_id) {
		if (busMap.containsKey(bus_id)) {
			busMap.remove(bus_id);
			return true;
		}
		return false;
	}

	// adminLogin

	@Override
	public Admin adminLogin(int admin_id, String password) {
		if (adminMap.containsKey(admin_id)) {
			Admin admin = adminMap.get(admin_id);
			if (admin.getAdminPassword().equals(password)) {
				return admin;
			}
		}
		return null;
	}

	// bookTicket

	@Override
	public Ticket bookTicket(Ticket ticket) {

		ticket.setDateTime(new java.sql.Timestamp(new java.util.Date().getTime()));

		Integer availableseats = checkAvailability(ticket.getBusId(), ticket.getJourneyDate());
		if (availableseats >= ticket.getNoofSeats()) {
			bookMap.put(ticket.getBookingId(), ticket);
			return ticket;
		} else {

			return null;
		}
	}

	// cancelTicket

	@Override
	public Boolean cancelTicket(int booking_id) {
		if (bookMap.containsKey(booking_id)) {
			bookMap.remove(booking_id);
			return true;
		}
		return false;
	}

	// getticket

	@Override
	public Ticket getTicket(int booking_id) {
		Ticket ticket = null;
		if (bookMap.containsKey(booking_id)) {
			return bookMap.get(booking_id);
		} else {
			return ticket;
		}
	}

	// check availability

	@Override
	public List<Available> checkAvailability(String source, String destination, Date date) {
		List<Available> list = new ArrayList<Available>();
		Available available = null;
		Bus bus = null;
		for (Integer availId : availMap.keySet()) {
			available = availMap.get(availId);
			bus = searchBus(available.getBusId());
			if (bus.getSource().equals(source) && bus.getDestination().equals(destination)
					&& available.getAvailableDate().equals(date)) {
				list.add(available);
			}

		}
		return list;
	}

	@Override
	public Integer checkAvailability(int bus_id, Date date) {
		int availSeats = 0;
		for (Integer availId : availMap.keySet()) {
			Available available = availMap.get(availId);
			if (bus_id == available.getBusId() && date.equals(available.getAvailableDate())) {
				availSeats = available.getAvailableSeats();
			}
		}
		return availSeats;

	}

	// give feeedback

	@Override
	public Boolean giveFeedback(int userId, String feedback) {
		Suggestion suggest = new Suggestion();
		suggest.setSuggId(1002);
		suggestionMap.put(suggest.getSuggId(), suggest);
		if (suggest != null) {
			return true;
		} else {
			return false;
		}
	}

	// get all sugestions

	@Override
	public List<Suggestion> getAllSuggestions(Suggestion sugg) {
		List<Suggestion> list = new ArrayList<>();
		for (Integer sugId : suggestionMap.keySet()) {
			sugg = suggestionMap.get(sugId);
			list.add(sugg);
		}

		return list;
	}

	// set bus availability
	@Override
	public Boolean setBusAvailability(Available available) {
		if (availMap.containsKey(available.getAvailableId())) {
			availMap.put(available.getAvailableId(), available);
			return true;
		} else {

			return false;
		}
	}
}
