package com.capg.bbs.main;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

import com.capg.bbs.beans.Available;
import com.capg.bbs.beans.Bus;
import com.capg.bbs.beans.Suggestion;
import com.capg.bbs.beans.Ticket;
import com.capg.bbs.beans.User;
import com.capg.bbs.exception.BusCreateFailException;
import com.capg.bbs.exception.BusDeleteFailException;
import com.capg.bbs.exception.BusNotFoundException;
import com.capg.bbs.exception.DeleteException;
import com.capg.bbs.exception.LoginException;
import com.capg.bbs.exception.RegisterException;
import com.capg.bbs.exception.TicketBookingException;
import com.capg.bbs.exception.UpdateException;
import com.capg.bbs.service.BusBookingService;
import com.capg.bbs.service.BusBookingServiceImpl;

public class BusBookingSystemApp {
	static int userid = 0; // global id

	static BusBookingService busbookingservice = new BusBookingServiceImpl();
	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		System.out.println("*****************************" + "\n" + "BUS BOOKING SYSTEM" + "\n"
				+ "*****************************" + "\n" + "1.Admin" + "\n" + "2.User" + "\n" + "3.Exit" + "\n"
				+ "*****************************");

		int check = Integer.parseInt(sc.next());

		switch (check) {
		case 1:
			adminApp();

			break;
		case 2:
			userApp();

			break;

		case 3:
			System.out.println("*****************************");

			System.exit(0);
			break;

		default:
			System.out.println("Incorect Option");
			break;

		}

	}

	// ---------------------------------------ADMIN-------------------------------------------------

	private static void adminApp() {

		Boolean admin = false;
		try {
			admin = adminLogin();
		} catch (LoginException exceptionLogin) {
			System.out.println(exceptionLogin.getMessage());
		}
		if (admin) {
			System.out.println("Login Succesful");
			boolean bo = true;
			while (bo) {
				System.out.println("*****************************" + "\n" + "1.Search User" + "\n" + "2.Add Bus" + "\n"
						+ "3.Update Bus" + "\n" + "4.Delete Bus" + "\n" + "5.Set Bus Availability" + "\n"
						+ "6.Search Bus" + "\n" + "7.View Feedback" + "\n" + "8.Exit" + "\n"
						+ "*****************************");

				int ad = Integer.parseInt(sc.next());

				switch (ad) {
				case 1:
					searchUser();

					break;
				case 2:
					try {
						createBus();
					} catch (BusCreateFailException exceptionBusCreate) { // custom exception
						System.out.println(exceptionBusCreate.getMessage());
					}
					break;

				case 3:
					try {
						updateBus();
					} catch (UpdateException exceptionUpdate) { // custom exception
						System.out.println(exceptionUpdate.getMessage());
					}
					break;
				case 4:
					try {
						deleteBus();
					} catch (BusDeleteFailException exceptionDeletion) { // custom exception
						System.out.println(exceptionDeletion.getMessage());
					}
					break;
				case 5:
					setAvailability();
					break;
				case 6:
					try {
						searchBus();
					} catch (BusNotFoundException exceptionSearchBus) { // custom exception
						System.out.println(exceptionSearchBus.getMessage());
					}
					break;
				case 7:
					getSuggestion();
					break;
				case 8:
					bo = false;
					System.out.println("*****************************");

					break;
				default:
					System.out.println("Incorrect Option");
					break;
				}
			}
		}

	}

	// admin login

	private static Boolean adminLogin() throws LoginException {

		boolean adminCheck = true;
		Integer adminId = 0;
		while (adminCheck) {
			System.out.println("Enter Admin id:"); // validation of id
			Integer tempId = busbookingservice.validationId(sc.next());
			if (tempId != null) {
				adminId = tempId;
				adminCheck = false;
			} else {
				System.out.println("Admin id should be number !");

			}
		}

		System.out.println("Enter Admin password:");
		String password = sc.next();
		if (busbookingservice.adminLogin(adminId, password) != null) {
			return true;
		} else {
			throw new LoginException("invalidIdException:Admin Login Fail Exception"); // throws custom exception
		}
	}

	// search user

	private static void searchUser() {
		boolean adminCheck = true;
		Integer userId = 0;
		while (adminCheck) {
			System.out.println("Enter UserID"); // validation of id
			Integer tempId = busbookingservice.validationId(sc.next());
			if (tempId != null) {
				userId = tempId;
				adminCheck = false;
			} else {
				System.out.println("User id should be number !");

			}
		}

		User user = busbookingservice.searchUser(userId);
		if (user != null) {
			System.out.println(user);
		} else {
			System.out.println("User Does Not Exist");
		}
	}

	// add bus

	private static void createBus() throws BusCreateFailException {
		Bus bus = new Bus();
		boolean busCheck = true;
		Integer busId = 0;
		while (busCheck) // validation of id
		{
			System.out.println("Enter BusId");
			Integer tempId = busbookingservice.validationId(sc.next());
			if (tempId != null) {
				busId = tempId;
				busCheck = false;
			} else {
				System.out.println("Busid id should be number !");

			}
		}
		if (busbookingservice.searchBus(busId) != null) {
			throw new BusCreateFailException("Bus ID Already Exist with " + busId);

		} else {
			bus.setBusId(busId);
		}
		System.out.println("Enter BusName");
		bus.setBusName(sc.next());
		System.out.println("Enter Bus type");
		bus.setBusType(sc.next());
		System.out.println("Enter Source");
		bus.setSource(sc.next());
		System.out.println("Enter Destination");
		bus.setDestination(sc.next());
		System.out.println("Enter Total Seats");
		bus.setTotalSeats(Integer.parseInt(sc.next()));
		System.out.println("Enter Price");
		bus.setPrice(Double.parseDouble(sc.next()));

		boolean creb = busbookingservice.createBus(bus);
		if (creb) {
			System.out.println("Bus created");
		} else {
			throw new BusCreateFailException("BusCreateException:Fail to Create Bus Exception"); // throws custom
			// exception
		}

	}

	// update the bus

	private static void updateBus() throws UpdateException {
		Bus bus = new Bus();
		boolean busCheck = true;
		Integer busId = 0;
		while (busCheck) // validation of id
		{
			System.out.println("Enter BusId");
			Integer tempId = busbookingservice.validationId(sc.next());
			if (tempId != null) {
				busId = tempId;
				busCheck = false;
			} else {
				System.out.println("Busid id should be number !");

			}
		}
		if (busbookingservice.searchBus(busId) == null) {
			throw new UpdateException("Bus ID does not Exist with " + busId);

		} else {
			bus.setBusId(busId);
		}
		System.out.println("Enter New BusName");
		bus.setBusName(sc.next());
		System.out.println("Enter New Source");
		bus.setSource(sc.next());
		System.out.println("Enter New Destination");
		bus.setDestination(sc.next());
		System.out.println("Enter New Bus type");
		bus.setBusType(sc.next());
		System.out.println("Enter New Total Seats");
		bus.setTotalSeats(Integer.parseInt(sc.next()));
		System.out.println("Enter New Price");
		bus.setPrice(Double.parseDouble(sc.next()));

		boolean upbus = busbookingservice.updateBus(bus);
		if (upbus) {
			System.out.println("Bus Successfully Updated");
		} else {
			throw new UpdateException("UpdateException:Fail to Update Bus Exception"); // throws custom exception
		}
	}

	// delete the bus

	private static void deleteBus() throws BusDeleteFailException {
		boolean busCheck = true;
		Integer busId = 0;
		while (busCheck) // validation of id
		{
			System.out.println("Enter BusId");
			Integer tempId = busbookingservice.validationId(sc.next());
			if (tempId != null) {
				busId = tempId;
				busCheck = false;
			} else {
				System.out.println("Busid id should be number !");

			}
		}
		boolean delbus = busbookingservice.deletebus(busId);
		if (delbus) {
			System.out.println("Bus Successfully Deleted");
		} else {
			throw new BusDeleteFailException("BusDeleteFailException:Bus Not Found to Delete"); // throws custom
			// exception
		}
	}

	// search the bus

	private static void searchBus() throws BusNotFoundException {

		boolean busCheck = true;
		Integer busId = 0;
		while (busCheck) // validation of id
		{
			System.out.println("Enter BusId");
			Integer tempId = busbookingservice.validationId(sc.next());
			if (tempId != null) {
				busId = tempId;
				busCheck = false;
			} else {
				System.out.println("Bus id should be number !");

			}
		}

		Bus bus = busbookingservice.searchBus(busId);
		if (bus != null) {
			System.out.println(bus);
		} else {
			throw new BusNotFoundException("Bus Not Found Exception"); // throws custom exception
		}

	}

	// set availabililty of bus

	private static void setAvailability() {
		Available available = new Available();

		boolean busCheck = true;
		Integer busId = 0;
		while (busCheck) // validation of id
		{
			System.out.println("Enter BusId");
			Integer tempId = busbookingservice.validationId(sc.next());
			if (tempId != null) {
				busId = tempId;
				busCheck = false;
			} else {
				System.out.println("Busid id should be number !");

			}
		}
		Bus bus = busbookingservice.searchBus(busId);

		if (bus == null) {
			System.out.println("Bus ID does not Exist with " + busId);

		} else {
			System.out.println(bus);

			available.setBusId(busId);
		}

		System.out.println("Enter the Available seats");
		available.setAvailableSeats(Integer.parseInt(sc.next()));
		System.out.println("Enter the date(yyyy-mm-dd)");
		String tempDate = sc.next();
		Date date = Date.valueOf(tempDate);
		available.setAvailableDate(date);

		if (busbookingservice.setAvailability(available)) {
			System.out.println("Successfully Set the availability");
		} else {
			System.out.println("Failed to Set the availability");
		}

	}

	// get all suggestions of users

	private static void getSuggestion() {
		Suggestion sugg = new Suggestion();
		List<Suggestion> li = busbookingservice.getAllSuggestions(sugg);
		for (Suggestion s : li) {
			System.out.println(s);
		}

	}

	// ---------------------------------------USER-------------------------------------------------

	private static void userApp() {
		System.out.println("1.Login" + "\n" + "2.Register");
		int option = Integer.parseInt(sc.next());
		if (option == 1) {
			Boolean login = false;
			try {
				login = loginUser();
			} catch (LoginException e) {
				System.out.println(e.getMessage());
			}
			if (login) {
				System.out.println("Login Successful");
				boolean bo = true;
				while (bo) {

					System.out.println("*****************************" + "\n" + "1.Update Profile" + "\n"
							+ "2.Delete Profile" + "\n" + "3.Search Bus" + "\n" + "4.Check Availability" + "\n"
							+ "5.Book Ticket" + "\n" + "6.Get Ticket" + "\n" + "7.Cancel Ticket" + "\n" + "8.Feedback"
							+ "\n" + "9.Exit" + "\n" + "*****************************");
					int log = sc.nextInt();
					switch (log) {

					case 1:
						try {
							updateUser();
						} catch (UpdateException exceptionUpdate) { // custom exception
							System.out.println(exceptionUpdate.getMessage());
						}
						break;
					case 2:
						try {
							deleteUser();
						} catch (DeleteException exceptionDelete) { // custom exception
							System.out.println(exceptionDelete.getMessage());
						}
						break;

					case 3:
						try {
							searchBuses();
						} catch (BusNotFoundException exceptionSearch) { // custom exception
							System.out.println(exceptionSearch.getMessage());
						}
						break;
					case 4:
						checkAvailability();
						break;
					case 5:
						try {
							bookTicket();
						} catch (TicketBookingException exceptionBookTicket) { // custom exception
							System.out.println(exceptionBookTicket.getMessage());
						}
						break;
					case 6:
						getTicket();
						break;

					case 7:
						cancelTicket();
						break;
					case 8:
						giveFeedback();
						break;
					case 9:
						bo = false;
						sc.close();
						System.out.println("*****************************");
						break;
					default:
						System.out.println("Incoorect Option");
						break;
					}

				}
			} else {
				System.out.println("Login unsucessful");
			}

		} else if (option == 2) {
			try {
				createUser();
			} catch (RegisterException exceptionRegister) { // custom exception
				System.out.println(exceptionRegister.getMessage());
			}

		}
	}

	// update user

	private static void updateUser() throws UpdateException {
		User user = new User();
		user.setUserId(userid);
		System.out.println("Enter Password");
		String password = sc.next();
		user.setUserPassword(password);
		System.out.println("Enter New Username");
		user.setUserName(sc.next());
		boolean checkEmail = true;
		while (checkEmail) { // email validation
			System.out.println("Enter Email:");
			String temp = busbookingservice.validationEmail(sc.next());
			if (temp != null) {
				user.setEmail(temp);
				checkEmail = false;
			} else {
				throw new UpdateException("UpdateException:Wrong Email Format!! e.g(example@email.com)");

			}
		}

		boolean checkContact = true;
		while (checkContact) {
			System.out.println("Enter 10 digits Contact No.:");
			Long temp = busbookingservice.validationContact(sc.next()); // contact validation
			if (temp != null) {
				user.setContact(temp);
				checkContact = false;
			} else {
				System.out.println("Contact should be of 10 digits!!");
				throw new UpdateException("UpdateException:Contact should be of 10 digits!!");

			}
		}

		boolean b = busbookingservice.updateUser(user);
		if (b) {
			System.out.println("SuccessFully Updated");
		} else {
			throw new UpdateException("Updation Fail Exception");
		}

	}

	// login user

	private static boolean loginUser() throws LoginException {

		boolean checkLogin = true;
		while (checkLogin) {
			System.out.println("Enter userid:");
			Integer tempId = busbookingservice.validationId(sc.next()); // regex for validation of id
			if (tempId != null) {
				userid = tempId;
				checkLogin = false;
			} else {
				throw new LoginException("LoginException:User id should be number !");

			}
		}
		System.out.println("Enter password:");
		String password = sc.next();
		if (busbookingservice.loginUser(userid, password) != null) {
			return true;
		} else {
			throw new LoginException("Login Failed Exception"); // throws custom exception
		}

	}

	// delete the user

	private static void deleteUser() throws DeleteException {
		System.out.println("Enter Password");
		String password = sc.next();
		if (busbookingservice.deleteUser(userid, password)) {
			System.out.println("Profile sucessfully Deleted");
		} else {
			throw new DeleteException("DeleteException:Password Incoorect"); // throws custom exception
		}
	}

	// search the bus

	private static void searchBuses() throws BusNotFoundException {
		boolean busCheck = true;
		Integer busId = 0;
		while (busCheck) {
			System.out.println("Enter BusId"); // validation of id
			Integer tempId = busbookingservice.validationId(sc.next());
			if (tempId != null) {
				busId = tempId;
				busCheck = false;
			} else {
				throw new BusNotFoundException("SearchException:User id should be number !");

			}
		}

		Bus bus = busbookingservice.searchBus(busId);
		if (bus != null) {
			System.out.println(bus);
		} else {
			throw new BusNotFoundException("Bus Not Found Exception"); // throws custom exception
		}

	}

	// check availability of bus

	private static void checkAvailability() {
		// Available available = new Available();
		System.out.println("Enter Source point");
		String source = sc.next();
		System.out.println("Enter Destination point");
		String destination = sc.next();
		System.out.println("Enter Date (YYYY-MM-DD)");
		String tempDate = sc.next();
		Date date = Date.valueOf(tempDate);
		List<Available> list = busbookingservice.checkAvailability(source, destination, date);

		for (Available bs : list) {
			System.out.println(bs);
			int avail = busbookingservice.checkAvailability(bs.getBusId(), date);
			System.out.println("Available Seats:" + avail);
		}

	}
	// book the ticket

	private static void bookTicket() throws TicketBookingException {
		Ticket ticket = new Ticket();

		System.out.println("Enter source point");
		String checksource = sc.next();
		System.out.println("Enter Destination point");
		String checkdestination = sc.next();
		System.out.println("Enter date of journey(yyyy-mm-dd)");
		String tempDate = sc.next();
		Date date = Date.valueOf(tempDate);
		ticket.setJourneyDate(date);
		List<Available> list = busbookingservice.checkAvailability(checksource, checkdestination, date);

		for (Available bs : list) {

			System.out.println(bs);
			int avail = busbookingservice.checkAvailability(bs.getBusId(), date);
			System.out.println("Available Seats:" + avail);
		}

		System.out.println("Enter the bus_id");
		int bus_id = sc.nextInt();
		ticket.setBusId(bus_id);

		ticket.setUserId(userid);

		System.out.println("Enter number of seats to book");
		int noOfSeats = sc.nextInt();
		ticket.setNoofSeats(noOfSeats);
		Bus bus = busbookingservice.searchBus(bus_id);
		ticket.setTotalPrice(bus.getPrice() * noOfSeats);

		Ticket bookTicket = busbookingservice.bookTicket(ticket);
		if (bookTicket != null) {
			System.out.println("Ticket sucessfully Booked");
			System.out.println(busbookingservice.getTicket(ticket.getBookingId()));
		} else {
			throw new TicketBookingException("Ticket Booking Fail Exception"); // throws custom exception
		}
	}

	// get the ticket

	private static void getTicket() {
		System.out.println("Enter BookingId");
		Ticket ticket = busbookingservice.getTicket(sc.nextInt());
		if (ticket != null) {
			System.out.println(ticket);
		} else {
			System.out.println("No Tickets Found");
		}
	}

	// cancel the ticket

	private static void cancelTicket() {
		System.out.println("Enter BookingId");
		Boolean cancelTicket = busbookingservice.cancelTicket(sc.nextInt());
		if (cancelTicket) {
			System.out.println("Ticket Successfully Cancelled");
		} else {
			System.out.println("No Tickets Found");
		}
	}

	// write feedback

	private static void giveFeedback() {
		System.out.println("Enter Your Feedback");
		String feedback = sc.next();
		Boolean sugg = busbookingservice.giveFeedback(userid, feedback);
		if (sugg) {
			System.out.println("Feedback Successfully Given");
		} else {
			System.out.println("Fail to give Feedback");
		}
	}

	// add user

	private static void createUser() throws RegisterException {
		User user = new User();
		boolean checkLogin = true;
		while (checkLogin) {
			System.out.println("Enter userid:"); // validation of id
			Integer tempId = busbookingservice.validationId(sc.next());
			if (tempId != null) {

				User users = busbookingservice.searchUser(tempId);
				int id = users.getUserId();
				if (tempId != id) {
					userid = tempId;
					user.setUserId(userid);
					checkLogin = false;

				} else {
					throw new RegisterException("RegistrationException:User Id already Exist !");

				}
			} else {
				throw new RegisterException("RegistrationException:User id should be number !");

			}
		}
		System.out.println("Enter Username:");
		user.setUserName(sc.next());
		boolean checkEmail = true; // email validations
		while (checkEmail) {
			System.out.println("Enter Email:");
			String temp = busbookingservice.validationEmail(sc.next());
			if (temp != null) {
				user.setEmail(temp);
				checkEmail = false;
			} else {
				throw new RegisterException("RegistrationException:Wrong Email Format!! e.g(example@email.com)");

			}
		}

		boolean checkContact = true;
		while (checkContact) {
			System.out.println("Enter 10 digits Contact No.:");
			Long temp = busbookingservice.validationContact(sc.next()); // contact validation
			if (temp != null) {
				user.setContact(temp);
				checkContact = false;
			} else {
				throw new RegisterException("RegistrationException:Contact should be of 10 digits!!");

			}
		}
		System.out.println("Enter Password:");
		user.setUserPassword(sc.next());
		sc.close();
		boolean reg = busbookingservice.createUser(user);
		if (reg) {
			System.out.println("Registration Successful");
		} else {
			throw new RegisterException("Registration Fail Exception"); // throws custom exception
		}

	}

}
