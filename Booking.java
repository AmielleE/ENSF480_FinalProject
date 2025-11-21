import java.util.List;

public class Booking {
    private int confirmationNumber;
    private Customer customer;
    private Flight flight;
    private List<String> seatNumbers; //like 3A, 23C

    public Booking(int confirmationNumber, Customer customer, Flight flight, List<String> seatNumbers) {
        this.confirmationNumber = confirmationNumber;
        this.customer = customer;
        this.flight = flight;
        this.seatNumbers = seatNumbers;

        for (String seat : seatNumbers) {
            flight.getSeatMap().bookSeat(seat);
        }

        flight.addPassenger(customer);
        flight.addReservation(this);
    }

    // Getters
    public int getConfirmationNumber() { return confirmationNumber; }
    public Customer getCustomer() { return customer; }
    public Flight getFlight() { return flight; }
    public List<String> getSeatNumbers() { return seatNumbers; }

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
        } else {
            for (String seat : seatNumbers) {
                flight.getSeatMap().bookSeat(seat);
            }
            System.out.println("Modification failed: Some seats are not available.");
            return false;
        }
    }

    public String getBookingSummary() {
        return "Booking #" + confirmationNumber +
                "\nCustomer: " + customer.firstName + " " + customer.lastName +
                "\nFlight: " + flight.getFlightID() +
                " (" + flight.getOrigin() + " → " + flight.getDestination() + ")" +
                "\nDate: " + flight.getDate() +
                "\nDeparture: " + flight.getDepartureTime() +
                "\nSeats booked: " + seatNumbers;
    }
}
