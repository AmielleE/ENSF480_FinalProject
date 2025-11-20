public class Booking {
    private int bookingId;
    private Customer customer;
    private Flight flight;
    private int seats;
    private double totalPrice;

    public Booking(int bookingId, Customer customer, Flight flight, int seats) {
        this.bookingId = bookingId;
        this.customer = customer;
        this.flight = flight;
        this.seats = seats;
        this.totalPrice = seats * (flight != null ? flight.getPrice() : 0.0);
    }

    public int getBookingId() { return bookingId; }
    public Customer getCustomer() { return customer; }
    public Flight getFlight() { return flight; }
    public int getSeats() { return seats; }
    public double getTotalPrice() { return totalPrice; }

    @Override
    public String toString() {
        return "Booking{" +
                "bookingId=" + bookingId +
                ", customer=" + customer.getFirstName() + " " + customer.getLastName() +
                ", flight=" + (flight != null ? flight.getFlightID() : "null") +
                ", seats=" + seats +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
