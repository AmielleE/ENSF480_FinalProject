import java.util.List;

public class Main {
    public static void main(String[] args) {
        ManageFlightController mfc = new ManageFlightController();
        BookingController bc = new BookingController();
        PaymentController pc = new PaymentController();

        Plane p = new Plane("Boeing737", 5);
        Flight f1 = new Flight("F100", "YYC", "YVR", "2025-12-01", "08:00", 150.0, p, "YYC");
        Flight f2 = new Flight("F101", "YYC", "YVR", "2025-12-01", "12:00", 200.0, p, "YYC");

        mfc.addFlight(f1);
        mfc.addFlight(f2);

        Customer cust = new Customer(1, "Alice", "Smith", "a@example.com", "pw", bc, mfc, pc);
        FlightAgent agent = new FlightAgent(2, "Bob", "Agent", "b@example.com", "pw", bc, mfc);

        List<Flight> matches = cust.searchFlights("YYC", "YVR", "2025-12-01");
        System.out.println("Search results: " + matches);

        int bookingId = cust.makeBooking(f1, 2);
        System.out.println("Booking id: " + bookingId);

        Booking b = cust.viewBooking(bookingId);
        System.out.println("Viewed booking: " + b);

        PaymentInfo pi = new PaymentInfo("Alice Smith", "123456789012", "12/27", "123");
        boolean paid = cust.makePayment(pi);
        System.out.println("Payment status: " + paid);

        agent.viewCustomerDetails(cust);

        cust.cancelBooking(bookingId);

        System.out.println("Seats available after cancel: " + f1.getSeatsAvailable());
    }
}
