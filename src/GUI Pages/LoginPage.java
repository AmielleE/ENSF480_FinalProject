import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginPage extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginBtn;

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

        add(mainPanel, BorderLayout.CENTER);

        // ==========================
        // LOGIN LOGIC
        // ==========================
        loginBtn.addActionListener(e -> {
            String user = usernameField.getText();
            String pass = new String(passwordField.getPassword());

            if(user.equals("user") && pass.equals("user")) {
                new HomePage();
                dispose();
            }
            else if(user.equals("admin") && pass.equals("admin")) {
                new AdminPage();
                dispose();
            }
            else if(user.equals("agent") && pass.equals("agent")) {
                new AgentPage();
                dispose();
            }
            else {
                JOptionPane.showMessageDialog(null, "Incorrect username or password");
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginPage().setVisible(true));
    }
}
