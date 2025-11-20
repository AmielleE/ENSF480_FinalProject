import java.util.List;

public class Customer extends User {
    private PaymentInfo paymentInfo;

    private BookingController bookingController;
    private ManageFlightController flightController;
    private PaymentController paymentController;

    public Customer(int id, String fn, String ln, String email, String pw,
                    BookingController bc, ManageFlightController fc, PaymentController pc) {
        super(id, fn, ln, email, pw);
        this.bookingController = bc;
        this.flightController = fc;
        this.paymentController = pc;
    }

    public List<Flight> searchFlights(String origin, String destination, String date) {
        return flightController.viewFlights(origin, destination, date);
    }

    public void receivePromotions() {
        System.out.println("Promotions received.");
        if (paymentInfo != null) {
            System.out.println("Special offer for " + paymentInfo.getName() + "!");
        } else {
            System.out.println("No payment info available for personalized offers.");
        }
    }

    public int makeBooking(Flight flight, int seats) {
        return bookingController.makeBooking(flight, this, seats);
    }

    public void cancelBooking(int bookingID) {
        bookingController.cancelBooking(bookingID);
    }

    public Booking viewBooking(int bookingID) {
        return bookingController.viewBookingDetails(bookingID);
    }

    public int modifyBooking(int bookingID, Flight newFlight, int newSeats) {
        boolean cancelled = bookingController.cancelBooking(bookingID);
        if (!cancelled) return -1;
        return makeBooking(newFlight, newSeats);
    }

    public boolean makePayment(PaymentInfo info) {
        this.paymentInfo = info;
        return paymentController.pay(info);
    }

    public void receiveMonthlyPromotion() {
        System.out.println("Monthly promotion received.");
    }

    public PaymentInfo getPaymentInfo() { return paymentInfo; }
}
