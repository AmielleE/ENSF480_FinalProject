import java.util.List;

public class Customer extends User {

    private PaymentInfo paymentInfo;

    private BookingController bookingController;
    private ManageFlightController flightController;
    private PaymentController paymentController;

    public Customer(int id, String fn, String ln, String email, String pw, BookingController bc, ManageFlightController fc, PaymentController pc) {
        super(id, fn, ln, email, pw);
        this.bookingController = bc;
        this.flightController = fc;
        this.paymentController = pc;
    }

    public List<Flight> searchFlights(String origin, String destination, String date) {
        return flightController.viewFlights(origin, destination, date);
    }

    public int makeBooking(Flight flight, List<String> seatNumbers) {
        return bookingController.makeBooking(flight, this, seatNumbers);
    }

    public void cancelBooking(int bookingID) {
        bookingController.cancelBooking(bookingID);
    }

    public Booking viewBooking(int bookingID) {
        return bookingController.viewBookingDetails(bookingID);
    }

    public boolean modifyBooking(int bookingID, Flight newFlight, List<String> newSeats) {
        return bookingController.modifyBooking(bookingID, newFlight, newSeats);
    }

    public boolean makePayment(PaymentInfo info) {
        this.paymentInfo = info;
        return paymentController.pay(info);
    }

    public void receivePromotion() {
        System.out.println("Monthly promotion received.");
    }

    public PaymentInfo getPaymentInfo() { return paymentInfo; }
}
