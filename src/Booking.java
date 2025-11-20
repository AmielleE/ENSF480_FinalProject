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

    public int getConfirmationNumber() { return confirmationNumber; }
    public Customer getCustomer() { return customer; }

    public void cancel() {
        flight.restoreSeats(seatsBooked);
        System.out.println("Booking canceled.");
    }
}
