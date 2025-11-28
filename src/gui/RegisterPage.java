package gui;

import javax.swing.*;
import java.awt.*;
import dao.users_dao;
import model.Customer;

//This page is used for creating new customer users

public class RegisterPage extends JFrame {
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton registerBtn;
    private JButton cancelBtn;

    private users_dao usersDao = new users_dao();

    public RegisterPage(){
        setTitle("Flight Booking System");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        getContentPane().setBackground(new Color(90,200,200));
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Create Account", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 26));
        title.setForeground(Color.WHITE);

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.add(title, BorderLayout.CENTER);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(60, 0, 0, 0));
        titlePanel.setOpaque(false);

        add(titlePanel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;

        //First Name
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel fnLabel = new JLabel("First name:");
        fnLabel.setForeground(Color.WHITE);
        formPanel.add(fnLabel, gbc);

        gbc.gridx = 1;
        firstNameField = new JTextField();
        firstNameField.setPreferredSize(new Dimension(150, 25));
        formPanel.add(firstNameField, gbc);

        //Last Name
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel lnLabel = new JLabel("Last name:");
        lnLabel.setForeground(Color.WHITE);
        formPanel.add(lnLabel, gbc);

        gbc.gridx = 1;
        lastNameField = new JTextField();
        lastNameField.setPreferredSize(new Dimension(150, 25));
        formPanel.add(lastNameField, gbc);

        //Email
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setForeground(Color.WHITE);
        formPanel.add(emailLabel, gbc);

        gbc.gridx = 1;
        emailField = new JTextField();
        emailField.setPreferredSize(new Dimension(150, 25));
        formPanel.add(emailField, gbc);

        //Password
        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel pwLabel = new JLabel("Password:");
        pwLabel.setForeground(Color.WHITE);
        formPanel.add(pwLabel, gbc);

        gbc.gridx = 1;
        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(150, 25));
        formPanel.add(passwordField, gbc);

        //Buttons row
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;

        JPanel buttonPanel = new JPanel();
        registerBtn = new JButton("Register");
        cancelBtn = new JButton("Cancel");
        buttonPanel.add(registerBtn);
        buttonPanel.add(cancelBtn);
        formPanel.add(buttonPanel, gbc);

        add(formPanel, BorderLayout.CENTER);

        //Register Button Logic
        registerBtn.addActionListener(e -> {
            String fn = firstNameField.getText().trim();
            String ln = lastNameField.getText().trim();
            String email = emailField.getText().trim();
            String pw = new String (passwordField.getPassword());

            if (fn.isEmpty() || ln.isEmpty() || email.isEmpty() || pw.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Customer newCustomer = new Customer(fn, ln, email, pw);
            int id = usersDao.addUser(newCustomer);

            if (id > 0) {
                JOptionPane.showMessageDialog(this, "Account created successfully! You can now log in.");
                dispose();
                new LoginPage().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "An account using this email already exsists.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelBtn.addActionListener(e -> {
            dispose();
            new LoginPage().setVisible(true);
        });
    }
}
