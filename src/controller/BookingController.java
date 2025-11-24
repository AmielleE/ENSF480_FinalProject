package controller;

import java.util.HashMap;
import java.util.List;

public class BookingController {

    private HashMap<Integer, Booking> bookings = new HashMap<>();
    private int nextConfirmation = 1000;

    public int makeBooking(Flight flight, Customer customer, List<String> seatNumbers) {
        for (String seat : seatNumbers) {
            if (!flight.getSeatMap().getAvailableSeats().contains(seat)) {
                System.out.println("Seat " + seat + " is not available.");
                return -1;
            }
        }

        int conf = nextConfirmation++;
        Booking b = new Booking(conf, customer, flight, seatNumbers);
        bookings.put(conf, b);
        return conf;
    }

    public void cancelBooking(int bookingID) {
        if (bookings.containsKey(bookingID)) {
            bookings.get(bookingID).cancel();
            bookings.remove(bookingID);
        } else {
            System.out.println("Booking ID " + bookingID + " not found.");
        }
    }

    public Booking viewBookingDetails(int bookingID) {
        return bookings.get(bookingID);
    }

    public boolean modifyBooking(int bookingID, Flight newFlight, List<String> newSeats) {
        Booking b = bookings.get(bookingID);
        if (b == null) return false;
        return b.modifyBooking(newFlight, newSeats);
    }
}
