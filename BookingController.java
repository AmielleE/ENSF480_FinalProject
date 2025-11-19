import java.util.HashMap;

public class BookingController {

    private HashMap<Integer, Booking> bookings = new HashMap<>();
    private int nextConfirmation = 1000;

    public int makeBooking(Flight flight, Customer customer, int seats) {
        if (!flight.bookSeats(seats)) return -1;

        int conf = nextConfirmation++;
        Booking b = new Booking(conf, customer, flight, seats);
        bookings.put(conf, b);
        return conf;
    }

    public void cancelBooking(int bookingID) {
        if (bookings.containsKey(bookingID)) {
            bookings.get(bookingID).cancel();
            bookings.remove(bookingID);
        }
    }

    public Booking viewBookingDetails(int bookingID) {
        return bookings.get(bookingID);
    }
}
