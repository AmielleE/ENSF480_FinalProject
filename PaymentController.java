public class PaymentController {
    public boolean pay(PaymentInfo info) {
        if (info == null) return false;
        if (info.getCardNumber() != null && info.getCardNumber().trim().length() >= 12) {
            System.out.println("Payment processed for: " + info.getName());
            return true;
        } else {
            System.out.println("Payment failed (invalid card).");
            return false;
        }
    }
}
