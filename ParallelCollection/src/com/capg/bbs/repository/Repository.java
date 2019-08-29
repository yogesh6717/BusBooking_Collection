package com.capg.bbs.repository;

import java.sql.Date;
import java.util.HashMap;

import com.capg.bbs.beans.Admin;
import com.capg.bbs.beans.Available;
import com.capg.bbs.beans.Bus;
import com.capg.bbs.beans.Suggestion;
import com.capg.bbs.beans.Ticket;
import com.capg.bbs.beans.User;

public class Repository {

	public static HashMap<Integer, User> userMap = new HashMap<>();
	public static HashMap<Integer, Ticket> bookMap = new HashMap<>();
	public static HashMap<Integer, Bus> busMap = new HashMap<>();
	public static HashMap<Integer, Available> availMap = new HashMap<>();
	public static HashMap<Integer, Admin> adminMap = new HashMap<>();
	public static HashMap<Integer, Suggestion> suggestionMap = new HashMap<>();

	public Repository() {

		// user repository

		User user = new User();
		user.setUserId(1);
		user.setUserName("prasad");
		user.setEmail("prasad@gmail.com");
		user.setContact(951357482);
		user.setUserPassword("root");
		userMap.put(user.getUserId(), user);

		User user1 = new User();
		user1.setUserId(2);
		user1.setUserName("swapnil");
		user1.setEmail("swapnil@gmail.com");
		user1.setContact(955613282);
		user1.setUserPassword("root");
		userMap.put(user1.getUserId(), user1);

		// bus repository

		Bus bus = new Bus();
		bus.setBusId(101);
		bus.setBusName("VRL Travel");
		bus.setBusType("AC");
		bus.setSource("mumbai");
		bus.setDestination("banglore");
		bus.setTotalSeats(30);
		bus.setPrice(1800);
		busMap.put(bus.getBusId(), bus);

		Bus bus1 = new Bus();
		bus1.setBusId(102);
		bus1.setBusName("SRS Travel");
		bus1.setBusType("NonAC");
		bus1.setSource("mumbai");
		bus1.setDestination("hyderabad");
		bus1.setTotalSeats(25);
		bus1.setPrice(1700);
		busMap.put(bus1.getBusId(), bus1);

		// available repository

		Available available = new Available();
		available.setBusId(101);
		available.setAvailableId(201);
		String tempDate = "2019-12-13";
		Date date = Date.valueOf(tempDate);
		available.setAvailableDate(date);
		available.setAvailableSeats(30);
		availMap.put(available.getAvailableId(), available);

		// admin repository

		Admin admin = new Admin();
		admin.setAdminId(301);
		admin.setAdminPassword("root");
		adminMap.put(admin.getAdminId(), admin);

		Ticket ticket = new Ticket();
		ticket.setBookingId(201);
		bookMap.put(ticket.getBookingId(), ticket);

		// suggestion repository

		Suggestion suggest = new Suggestion();
		suggest.setUserid(1);
		suggest.setSuggest("good");
		suggest.setSuggId(1001);
		suggestionMap.put(suggest.getSuggId(), suggest);

	}
}
