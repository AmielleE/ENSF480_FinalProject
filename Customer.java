public class Customer extends User {
    private PaymentInfo paymentInfo;

    public Customer(int id, String first, String last, String email, String pass) {
        super(id, first, last, email, pass);
    }

    public void setPaymentInfo(PaymentInfo paymentInfo) {
        this.paymentInfo = paymentInfo;
    }

    public void viewHistory() {
        //NOT DONE YET, WE HAVE TO MAKE THE DATABASE
        System.out.println("Viewing booking history...");
    }

    public void receivePromotions() {
        System.out.println("Received monthly promotion.");
    }
}
