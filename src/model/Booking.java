package model;

import java.util.List;

public class Booking {

    private int confirmationNumber;
    private Customer customer;
    private Flight flight;
    private List<String> seatNumbers;

    // Constructor used by bookings_dao (no Customer passed)
    public Booking(int confirmationNumber, Flight flight, List<String> seatNumbers) {
        this.confirmationNumber = confirmationNumber;
        this.flight = flight;
        this.seatNumbers = seatNumbers;
        this.customer = null;
    }

    // Constructor used by BookingController (Customer included)
    public Booking(int confirmationNumber, Customer customer, Flight flight, List<String> seatNumbers) {
        this.confirmationNumber = confirmationNumber;
        this.customer = customer;
        this.flight = flight;
        this.seatNumbers = seatNumbers;
    }

    // Getters
    public int getConfirmationNumber() { return confirmationNumber; }
    public Customer getCustomer() { return customer; }
    public Flight getFlight() { return flight; }
    public List<String> getSeatNumbers() { return seatNumbers; }

    // Cancel booking
    public void cancel() {
        for (String seat : seatNumbers) {
            flight.getSeatMap().cancelSeat(seat);
        }
        System.out.println("Booking " + confirmationNumber + " canceled.");
    }

    // Modify booking
    public boolean modifyBooking(Flight newFlight, List<String> newSeats) {

        // Free old seats
        for (String seat : seatNumbers) {
            flight.getSeatMap().cancelSeat(seat);
        }

        boolean allBooked = true;
        for (String seat : newSeats) {
            if (!newFlight.getSeatMap().bookSeat(seat)) {
                allBooked = false;
                break;
            }
        }

        if (allBooked) {
            this.flight = newFlight;
            this.seatNumbers = newSeats;
            System.out.println("Booking " + confirmationNumber + " modified.");
            return true;
        }

        // Rollback old seats if modification failed
        for (String seat : seatNumbers) {
            flight.getSeatMap().bookSeat(seat);
        }

        System.out.println("Modification failed: Some seats are not available.");
        return false;
    }

    public String getBookingSummary() {
        return "Booking #" + confirmationNumber +
                "\nCustomer: " + 
                (customer != null ? customer.firstName + " " + customer.lastName : "Unknown") +
                "\nFlight: " + flight.getFlightID() +
                " (" + flight.getOrigin() + " → " + flight.getDestination() + ")" +
                "\nDate: " + flight.getDate() +
                "\nDeparture: " + flight.getDepartureTime() +
                "\nSeats booked: " + seatNumbers;
    }
}
