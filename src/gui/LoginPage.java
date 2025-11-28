package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import dao.users_dao;
import model.Customer;
import model.User;

//This page is shown at the very beginning of the application to login

public class LoginPage extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginBtn;
    private JButton registerBtn;

    private users_dao userDao = new users_dao();

    public LoginPage() {
        setTitle("Flight Booking System");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        getContentPane().setBackground(new Color(90, 200, 200));
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Flight Booking System", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 26));
        title.setForeground(Color.WHITE);

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.add(title, BorderLayout.CENTER);

        titlePanel.setBorder(BorderFactory.createEmptyBorder(60, 0, 0, 0));

        titlePanel.setOpaque(false);

        add(titlePanel, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        
        mainPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;

        //for username
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel userLabel = new JLabel("Username:");
        userLabel.setForeground(Color.WHITE);
        mainPanel.add(userLabel, gbc);

        gbc.gridx = 1;
        usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(150, 25));
        mainPanel.add(usernameField, gbc);

        //for password
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel passLabel = new JLabel("Password:");
        passLabel.setForeground(Color.WHITE);
        mainPanel.add(passLabel, gbc);

        gbc.gridx = 1;
        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(150, 25));
        mainPanel.add(passwordField, gbc);

        //for login button
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;

        loginBtn = new JButton("Login");
        mainPanel.add(loginBtn, gbc);

        //register button
        gbc.gridy = 3;
        registerBtn = new JButton("Register");
        mainPanel.add(registerBtn, gbc);

        add(mainPanel, BorderLayout.CENTER);

        loginBtn.addActionListener(e -> {
            String email = usernameField.getText();
            String password = new String(passwordField.getPassword());

            boolean validLogin = userDao.validateLogin(email, password);

            if (!validLogin) {
                JOptionPane.showMessageDialog(this, "Incorrect email or password", "Login Failed", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String role = userDao.getRoleByEmail(email);

            switch (role) {
                case "Customer":
                    new HomePage();
                    break;
                case "Agent":
                    new CustomerManagementGUI();
                    break;
                case "Admin":
                    new FlightManagementGUI();
                    break;
            }
            dispose();
        });

        registerBtn.addActionListener(e -> {
            new RegisterPage().setVisible(true);
            dispose();
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginPage().setVisible(true));
    }
}
