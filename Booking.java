public class Booking {
    private int confirmationNumber;
    private Customer customer;
    private Flight flight;
    private int seatsBooked;

    public Booking(int confirmationNumber, Customer customer, Flight flight, int seatsBooked) {
        this.confirmationNumber = confirmationNumber;
        this.customer = customer;
        this.flight = flight;
        this.seatsBooked = seatsBooked;
    }

    //Getters
    public int getConfirmationNumber() { return confirmationNumber; }
    public Customer getCustomer() { return customer; }
    public Flight getFlight() { return flight; }
    public int getSeatsBooked() { return seatsBooked; }

    public void cancel() {
        flight.restoreSeats(seatsBooked);
        System.out.println("Booking " + confirmationNumber + " canceled.");
    }

    public boolean modifyBooking(Flight newFlight, int newSeats) {
        flight.restoreSeats(seatsBooked);

        if (newFlight.bookSeats(newSeats)) {
            this.flight = newFlight;
            this.seatsBooked = newSeats;
            System.out.println("Booking " + confirmationNumber + " modified.");
            return true;
        } else {
            flight.bookSeats(seatsBooked);
            System.out.println("Modification failed: Not enough seats.");
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
                "\nSeats booked: " + seatsBooked;
    }
}
