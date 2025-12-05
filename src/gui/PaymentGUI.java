package gui;

import javax.swing.*;
import java.awt.*;

import controller.*;
import model.*;

//This is the payment page after making a booking

public class PaymentGUI extends JFrame {

    private JTextField nameField;
    private JTextField cardNumberField;
    private JTextField securityCodeField;
    private JTextField expiryField;
    private JTextField emailField;
    private Customer customer;

    private PaymentController controller;

    public PaymentGUI(Customer customer) {
        super("Flight Booking System");
        this.customer = customer;

        controller = new PaymentController();

        setLayout(new BorderLayout());
        JPanel form = new JPanel(new GridLayout(6, 2, 10, 10));

        form.add(new JLabel("Cardholder Name:"));
        nameField = new JTextField();
        form.add(nameField);

        form.add(new JLabel("Card Number:"));
        cardNumberField = new JTextField();
        form.add(cardNumberField);

        form.add(new JLabel("Security Code (CVV):"));
        securityCodeField = new JTextField();
        form.add(securityCodeField);

        form.add(new JLabel("Expiry Date (MM/YY):"));
        expiryField = new JTextField();
        form.add(expiryField);

        form.add(new JLabel("Email for Confirmation:"));
        emailField = new JTextField();
        form.add(emailField);

        JButton payBtn = new JButton("Submit Payment");
        payBtn.addActionListener(e -> handlePayment());

        add(form, BorderLayout.CENTER);
        add(payBtn, BorderLayout.SOUTH);

        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void handlePayment() {
        try {
            String name = nameField.getText();
            String cardNum = cardNumberField.getText();
            int cvv = Integer.parseInt(securityCodeField.getText());
            String expiry = expiryField.getText();
            String email = emailField.getText();

            PaymentInfo info = new PaymentInfo(cardNum, name, cvv, expiry);

            boolean success = controller.pay(info);

            if (success) {
                JOptionPane.showMessageDialog(
                        this,
                        "Payment Successful!\nConfirmation sent to: " + email,
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );

                dispose();
                new HomePage(customer).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Payment Failed. Try again.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Security Code must be a number.",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}