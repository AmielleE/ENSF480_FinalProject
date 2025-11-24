import javax.swing.*;
import java.awt.*;

public class HomePage extends JFrame {

    public HomePage() {
        setTitle("Flight Booking System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height - 100;
        setSize(screenWidth, screenHeight);

        // ================================
        // BACKGROUND COLOR
        // ================================
        getContentPane().setBackground(new Color(90, 200, 200)); // LIGHT BLUE
        setLayout(new BorderLayout());

        // ================================
        // OUTER CENTER PANEL
        // ================================
        JPanel outer = new JPanel(new GridBagLayout());
        outer.setOpaque(false); // transparent to show background
        GridBagConstraints gbc = new GridBagConstraints();

        // ================================
        // COLUMN PANEL (VERTICAL)
        // ================================
        JPanel column = new JPanel();
        column.setOpaque(false);
        column.setLayout(new BoxLayout(column, BoxLayout.Y_AXIS));

        // ================================
        // INNER ROW (HORIZONTAL INPUT BAR)
        // ================================
        JPanel row = new JPanel();
        row.setOpaque(false);
        row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));

        // COMMON STYLE
        Color fieldBg = new Color(255, 255, 255);
        Color textBlack = Color.BLACK;

        // FROM PANEL
        JPanel fromPanel = new JPanel();
        fromPanel.setOpaque(false);
        fromPanel.setLayout(new BoxLayout(fromPanel, BoxLayout.Y_AXIS));

        JLabel fromLabel = new JLabel("From:");
        fromLabel.setForeground(Color.WHITE);
        JTextField fromField = new JTextField(12);
        fromField.setForeground(textBlack);
        fromField.setBackground(fieldBg);

        fromPanel.add(fromLabel);
        fromPanel.add(fromField);

        // TO PANEL
        JPanel toPanel = new JPanel();
        toPanel.setOpaque(false);
        toPanel.setLayout(new BoxLayout(toPanel, BoxLayout.Y_AXIS));

        JLabel toLabel = new JLabel("To:");
        toLabel.setForeground(Color.WHITE);
        JTextField toField = new JTextField(12);
        toField.setForeground(textBlack);
        toField.setBackground(fieldBg);

        toPanel.add(toLabel);
        toPanel.add(toField);

        // DATE PANEL
        JPanel datePanel = new JPanel();
        datePanel.setOpaque(false);
        datePanel.setLayout(new BoxLayout(datePanel, BoxLayout.Y_AXIS));

        JLabel dateLabel = new JLabel("Departure Date:");
        dateLabel.setForeground(Color.WHITE);

        JSpinner dateSpinner = new JSpinner(new SpinnerDateModel());
        dateSpinner.setForeground(textBlack);
        dateSpinner.setBackground(fieldBg);

        JSpinner.DateEditor editor = new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd");
        editor.getTextField().setForeground(textBlack);
        editor.getTextField().setBackground(fieldBg);
        dateSpinner.setEditor(editor);

        datePanel.add(dateLabel);
        datePanel.add(dateSpinner);

        // SEARCH BUTTON PANEL
        JPanel searchPanel = new JPanel();
        searchPanel.setOpaque(false);
        JButton searchBtn = new JButton("Search");
        searchPanel.add(searchBtn);

        // Add all to row
        row.add(fromPanel);
        row.add(Box.createHorizontalStrut(30));
        row.add(toPanel);
        row.add(Box.createHorizontalStrut(30));
        row.add(datePanel);
        row.add(Box.createHorizontalStrut(30));
        row.add(searchPanel);

        // RESERVATIONS BUTTON PANEL
        JPanel resPanel = new JPanel();
        resPanel.setOpaque(false);
        JButton reservationsBtn = new JButton("See List of Reservations");
        resPanel.add(reservationsBtn);

        column.add(row);
        column.add(Box.createVerticalStrut(20));
        column.add(resPanel);

        outer.add(column, gbc);
        add(outer, BorderLayout.CENTER);

        reservationsBtn.addActionListener(e-> {
            dispose();
            new ReservationsPage().setVisible(true);
        });

        // BOTTOM LOGOUT BUTTON
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);
        JButton logoutBtn = new JButton("Logout");
        bottomPanel.add(logoutBtn, BorderLayout.EAST);

        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginPage().setVisible(true);
        });

        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
}
