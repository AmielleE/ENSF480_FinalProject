package gui;

import javax.swing.*;
import java.awt.*;
import dao.users_dao;
import model.Customer;

public class RegisterPage extends JFrame {

    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField emailField;
    private JPasswordField passwordField;

    public RegisterPage() {

        setTitle("Register");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 350);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        firstNameField = new JTextField(15);
        lastNameField = new JTextField(15);
        emailField = new JTextField(15);
        passwordField = new JPasswordField(15);

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("First Name:"), gbc);
        gbc.gridx = 1;
        formPanel.add(firstNameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Last Name:"), gbc);
        gbc.gridx = 1;
        formPanel.add(lastNameField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        formPanel.add(emailField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        formPanel.add(passwordField, gbc);

        JButton registerBtn = new JButton("Register");
        JButton cancelBtn = new JButton("Cancel");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(registerBtn);
        buttonPanel.add(cancelBtn);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // REGISTER button logic
        registerBtn.addActionListener(e -> {
            String fn = firstNameField.getText().trim();
            String ln = lastNameField.getText().trim();
            String email = emailField.getText().trim();
            String pw = new String(passwordField.getPassword());

            if (fn.isEmpty() || ln.isEmpty() || email.isEmpty() || pw.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields must be filled.");
                return;
            }

            users_dao usersDao = new users_dao();

            int newID = (int)(Math.random() * 9000) + 1000;

            Customer newCustomer = new Customer(newID, fn, ln, email, pw);

            boolean ok = usersDao.insertCustomer(newCustomer);

            if (ok) {
                JOptionPane.showMessageDialog(this, "Account created successfully!");
                dispose();
                new LoginPage().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Account creation failed.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // CANCEL button logic
        cancelBtn.addActionListener(e -> {
            dispose();
            new LoginPage().setVisible(true);
        });
    }
}
