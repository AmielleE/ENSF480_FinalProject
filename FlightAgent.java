import java.util.List;

public class FlightAgent extends User {

    private BookingController bookingController;
    private ManageFlightController flightController;

    public FlightAgent(int id, String fn, String ln, String email, String pw, BookingController bc, ManageFlightController fc) {
        super(id, fn, ln, email, pw);
        this.bookingController = bc;
        this.flightController = fc;
    }

    public List<Flight> searchFlights(String origin, String destination, String date) {
        return flightController.viewFlights(origin, destination, date);
    }

    public int createCustomerBooking(Customer customer, Flight flight, int seats) {
        return bookingController.makeBooking(flight, customer, seats);
    }

    public void modifyCustomerBooking(int bookingID, Flight newFlight, int seats, Customer customer) {
        bookingController.cancelBooking(bookingID);
        bookingController.makeBooking(newFlight, customer, seats);
    }

    public void cancelCustomerBooking(int bookingID) {
        bookingController.cancelBooking(bookingID);
    }

    public void viewCustomerDetails(Customer c) {
        System.out.println("Customer: " + c.firstName + " " + c.lastName);
        System.out.println("Email: " + c.email);
    }
}
