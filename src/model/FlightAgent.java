package model;

import java.util.List;

import controller.BookingController;
import controller.ManageFlightController;

public class FlightAgent extends User {

    private BookingController bookingController;
    private ManageFlightController flightController;

    public FlightAgent(int id, String fn, String ln, String email, String pw, BookingController bc, ManageFlightController fc) {
        super(id, fn, ln, email, pw);
        this.bookingController = bc;
        this.flightController = fc;
        this.role = "Agent";
    }

    public List<Flight> searchFlights(String origin, String destination, String date) {
        return flightController.viewFlights(origin, destination, date);
    }

    public int createCustomerBooking(Customer customer, Flight flight, List<String> seatNumbers) {
        return bookingController.makeBooking(flight, customer, seatNumbers);
    }

    public boolean modifyCustomerBooking(int bookingID, Flight newFlight, List<String> newSeats) {
        return bookingController.modifyBooking(bookingID, newFlight, newSeats);
    }

    public void cancelCustomerBooking(int bookingID) {
        bookingController.cancelBooking(bookingID);
    }

    public void viewCustomerDetails(Customer c) {
        System.out.println("Customer: " + c.firstName + " " + c.lastName);
        System.out.println("Email: " + c.email);
    }
}
