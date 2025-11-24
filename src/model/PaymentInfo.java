package model;

public class PaymentInfo {
    private String cardNumber;
    private String name;
    private int securityCode;
    private String expiryDate;

    public PaymentInfo(String cardNumber, String name, int code, String expiry) {
        this.cardNumber = cardNumber;
        this.name = name;
        this.securityCode = code;
        this.expiryDate = expiry;
    }
}