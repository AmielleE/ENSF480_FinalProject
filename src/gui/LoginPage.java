package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import dao.user_dao;
import model.Customer;
import model.User;

public class LoginPage extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginBtn;
    private JButton registerBtn;

    private user_dao userDao = new user_dao();

    public LoginPage() {
        setTitle("Login Page");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Background color of ENTIRE page
        getContentPane().setBackground(new Color(90, 200, 200));  // modern blue
        setLayout(new BorderLayout());

        // ==========================
        // TOP TITLE PANEL
        // ==========================
        JLabel title = new JLabel("Flight Booking System", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 26));
        title.setForeground(Color.WHITE); // make text readable on blue

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.add(title, BorderLayout.CENTER);

        // Push title downward
        titlePanel.setBorder(BorderFactory.createEmptyBorder(60, 0, 0, 0));

        // Transparent panel so blue background shows
        titlePanel.setOpaque(false);

        add(titlePanel, BorderLayout.NORTH);

        // ==========================
        // CENTER LOGIN FORM PANEL
        // ==========================
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setOpaque(false);   // transparent

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;

        // USERNAME ROW
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel userLabel = new JLabel("Username:");
        userLabel.setForeground(Color.WHITE);
        mainPanel.add(userLabel, gbc);

        gbc.gridx = 1;
        usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(150, 25));
        mainPanel.add(usernameField, gbc);

        // PASSWORD ROW
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel passLabel = new JLabel("Password:");
        passLabel.setForeground(Color.WHITE);
        mainPanel.add(passLabel, gbc);

        gbc.gridx = 1;
        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(150, 25));
        mainPanel.add(passwordField, gbc);

        // LOGIN BUTTON (CENTERED)
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;

        loginBtn = new JButton("Login");
        mainPanel.add(loginBtn, gbc);

        // REGISTER BUTTON ()
        gbc.gridy = 3;
        registerBtn = new JButton("Register");
        mainPanel.add(registerBtn, gbc);

         add(mainPanel, BorderLayout.CENTER);

        // ==========================
        // LOGIN LOGIC
        // ==========================
        loginBtn.addActionListener(e -> {
            String email = usernameField.getText();
            String password = new String(passwordField.getPassword());

            boolean validLogin = userDao.validateLogin(email, password);

            if (!validLogin) {
                JOptionPane.showMessageDialog(this, "Incorrect email or password", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }

            String role = userDao.getRoleByEmail(email);

            switch (role) {
                case "Customer":
                    new HomePage();
                    break;
                case "Agent":
                    new AgentPage();
                    break;
                case "Admin":
                    new AdminPage();
                    break;
            }

            dispose();
        });

        // ==========================
        // REGISTER LOGIC
        // ==========================
        registerBtn.addActionListener(e -> {
            String fn = JOptionPane.showInputDialog(this, "First name:");
            if (fn == null) return;

            String ln = JOptionPane.showInputDialog(this, "Last name:");
            if (ln == null) return;

            String email = JOptionPane.showInputDialog(this, "Email:");
            if (email == null) return;

            String pw = JOptionPane.showInputDialog(this, "Password:");
            if (pw == null) return;

            Customer newCustomer = new Customer(fn, ln, email, pw);
            int id = userDao.addUser(newCustomer);

            if (id > 0) {
                JOptionPane.showMessageDialog(this, "Account created succesfully! Your ID: " + id);
            } else {
                JOptionPane.showMessageDialog(this, "Error creating account.");
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginPage().setVisible(true));
    }
}
