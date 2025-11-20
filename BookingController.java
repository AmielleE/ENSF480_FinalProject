import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class BookingController {
    private final List<Booking> bookings = new ArrayList<>();
    private final AtomicInteger idGenerator = new AtomicInteger(1000);

    public int makeBooking(Flight flight, Customer customer, int seats) {
        if (flight == null || customer == null || seats <= 0) return -1;

        synchronized (flight) {
            if (!flight.bookSeats(seats)) {
                System.out.println("Booking failed: not enough seats on flight " + (flight != null ? flight.getFlightID() : ""));
                return -1;
            }
        }

        int id = idGenerator.getAndIncrement();
        Booking b = new Booking(id, customer, flight, seats);
        synchronized (bookings) {
            bookings.add(b);
        }
        System.out.println("Booking created: " + b);
        return id;
    }

    public boolean cancelBooking(int bookingID) {
        synchronized (bookings) {
            for (int i = 0; i < bookings.size(); i++) {
                Booking b = bookings.get(i);
                if (b.getBookingId() == bookingID) {
                    Flight f = b.getFlight();
                    if (f != null) {
                        f.restoreSeats(b.getSeats());
                    }
                    bookings.remove(i);
                    System.out.println("Booking cancelled: " + bookingID);
                    return true;
                }
            }
        }
        System.out.println("Booking not found: " + bookingID);
        return false;
    }

    public Booking viewBookingDetails(int bookingID) {
        synchronized (bookings) {
            for (Booking b : bookings) {
                if (b.getBookingId() == bookingID) return b;
            }
        }
        return null;
    }

    public List<Booking> getAllBookings() {
        synchronized (bookings) {
            return new ArrayList<>(bookings);
        }
    }
}
