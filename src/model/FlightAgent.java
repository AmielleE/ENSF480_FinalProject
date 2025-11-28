package model;

import java.util.List;
import controller.BookingController;
import controller.ManageFlightController;

public class FlightAgent extends User { //Flight Agent is a kind of User

    private BookingController bookingController;
    private ManageFlightController flightController;
    private CustomerManager customerManager;

    public FlightAgent(
            int id, String fn, String ln, String email, String pw,
            BookingController bc,
            ManageFlightController fc,
            CustomerManager cm
    ) {
        super(id, fn, ln, email, pw);
        this.bookingController = bc;
        this.flightController = fc;
        this.customerManager = cm;
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

    public void addCustomer(Customer c) {
        customerManager.addCustomer(c);
    }

    public boolean removeCustomer(int id) {
        return customerManager.removeCustomer(id);
    }

    public Customer findCustomer(int id) {
        return customerManager.findCustomer(id);
    }

    public boolean updateCustomer(int id, String fn, String ln, String email, String pw) {
        return customerManager.updateCustomer(id, fn, ln, email, pw);
    }

    public List<Customer> getAllCustomers() {
        return customerManager.getAllCustomers();
    }

}
