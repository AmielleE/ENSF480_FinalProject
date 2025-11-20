import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class LoginGUI extends JFrame {

    private List<User> users; // all registered users
    private ManageFlightController flightController;

    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel statusLabel;

    public LoginGUI(List<User> users, ManageFlightController fc) {
        this.users = users;
        this.flightController = fc;

        setTitle("Flight System Login");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 1, 5, 5));
        setLocationRelativeTo(null); // center on screen

        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        add(new JLabel("Email:"));
        emailField = new JTextField();
        add(emailField);

        add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        add(passwordField);

        loginButton = new JButton("Login");
        add(loginButton);

        statusLabel = new JLabel("", SwingConstants.CENTER);
        add(statusLabel);

        loginButton.addActionListener(e -> attemptLogin());
    }

    private void attemptLogin() {
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());

        for (User u : users) {
            if (u.login(email, password)) {
                statusLabel.setText("Login successful!");

                // open main screen based on user type
                if (u instanceof SystemAdmin) {
                    SwingUtilities.invokeLater(() -> new FlightManagementGUI((SystemAdmin) u));
                } else if (u instanceof FlightAgent) {
                    // you can create FlightAgent GUI here
                    JOptionPane.showMessageDialog(this, "Flight Agent logged in! (GUI not implemented yet)");
                } else if (u instanceof Customer) {
                    // you can create Customer GUI here
                    JOptionPane.showMessageDialog(this, "Customer logged in! (GUI not implemented yet)");
                }

                dispose(); // close login window
                return;
            }
        }
        statusLabel.setText("Invalid email or password.");
    }

    public static void main(String[] args) {
        // Example users
        ManageFlightController fc = new ManageFlightController();
        List<User> users = new ArrayList<>();
        users.add(new SystemAdmin(1, "Admin", "User", "admin@example.com", "admin123", fc));
        // add FlightAgent and Customer if needed
        SwingUtilities.invokeLater(() -> new LoginGUI(users, fc));
    }
}
