public class PaymentInfo {
    private String nameOnCard;
    private String cardNumber;
    private String expiry; 
    private String cvv;

    public PaymentInfo(String nameOnCard, String cardNumber, String expiry, String cvv) {
        this.nameOnCard = nameOnCard;
        this.cardNumber = cardNumber;
        this.expiry = expiry;
        this.cvv = cvv;
    }

    public String getName() { return nameOnCard; }
    public String getCardNumber() { return cardNumber; }
    public String getExpiry() { return expiry; }
    public String getCvv() { return cvv; }
}
