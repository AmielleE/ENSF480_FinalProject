package gui;

import javax.swing.*;
import java.awt.*;

public class ReservationsPage extends JFrame {

    private static final Color BG_COLOR = new Color(90, 200, 200);
    private static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 26);
    private static final Font SECTION_TITLE_FONT = new Font("Arial", Font.BOLD, 20);
    private static final Font LABEL_FONT = new Font("Arial", Font.BOLD, 14);
    private static final Font VALUE_FONT = new Font("Arial", Font.PLAIN, 14);

    public ReservationsPage() {
        // Title of the window
        setTitle("Flight Booking System - Reservations");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height - 100;
        setSize(screenWidth, screenHeight);
        setLocationRelativeTo(null);

        getContentPane().setBackground(BG_COLOR);
        setLayout(new BorderLayout(10, 10));

        // ================================
        // TOP HEADER
        // ================================
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(BG_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel headerTitle = new JLabel("My Reservations");
        headerTitle.setFont(TITLE_FONT);
        headerTitle.setForeground(Color.WHITE);

        JLabel headerSubtitle = new JLabel("Select a reservation on the left to view details");
        headerSubtitle.setFont(new Font("Arial", Font.PLAIN, 14));
        headerSubtitle.setForeground(Color.WHITE);

        JPanel headerTextPanel = new JPanel();
        headerTextPanel.setLayout(new BoxLayout(headerTextPanel, BoxLayout.Y_AXIS));
        headerTextPanel.setBackground(BG_COLOR);
        headerTextPanel.add(headerTitle);
        headerTextPanel.add(Box.createVerticalStrut(4));
        headerTextPanel.add(headerSubtitle);

        headerPanel.add(headerTextPanel, BorderLayout.WEST);
        add(headerPanel, BorderLayout.NORTH);

        // ================================
        // LEFT COLUMN (SCROLLABLE LIST)
        // ================================
        JPanel leftColumn = new JPanel();
        leftColumn.setLayout(new BoxLayout(leftColumn, BoxLayout.Y_AXIS));
        leftColumn.setBackground(BG_COLOR);
        leftColumn.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel leftTitle = new JLabel("Reservations");
        leftTitle.setFont(SECTION_TITLE_FONT);
        leftTitle.setForeground(Color.WHITE);
        leftTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        leftColumn.add(leftTitle);
        leftColumn.add(Box.createVerticalStrut(10));

        for (int i = 1; i <= 30; i++) {
            JButton btn = new JButton("Reservation #" + i);
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setMaximumSize(new Dimension(180, 30));
            leftColumn.add(btn);
            leftColumn.add(Box.createVerticalStrut(5));
        }

        JScrollPane leftScroll = new JScrollPane(leftColumn);
        leftScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        leftScroll.setPreferredSize(new Dimension(230, screenHeight));
        leftScroll.getViewport().setBackground(BG_COLOR);
        leftScroll.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.WHITE));

        add(leftScroll, BorderLayout.WEST);

        // ====================================
        // RIGHT SIDE (DETAILS + ACTIONS)
        // ====================================
        JPanel rightSide = new JPanel(new GridLayout(1, 2, 10, 0));
        rightSide.setBackground(BG_COLOR);
        rightSide.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(rightSide, BorderLayout.CENTER);

        // ================================
        // MIDDLE COLUMN (DETAILS)
        // ================================
        JPanel middleCard = new JPanel();
        middleCard.setLayout(new BoxLayout(middleCard, BoxLayout.Y_AXIS));
        middleCard.setBackground(Color.WHITE);
        middleCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        JLabel detailsTitle = new JLabel("Reservation Details");
        detailsTitle.setFont(SECTION_TITLE_FONT);
        detailsTitle.setForeground(new Color(50, 50, 50));
        detailsTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        middleCard.add(detailsTitle);
        middleCard.add(Box.createVerticalStrut(15));

        middleCard.add(createDetailRow("Departure Location: ", "Calgary (YYC)"));
        middleCard.add(createDetailRow("Departure Time: ", "13:30"));
        middleCard.add(Box.createVerticalStrut(4));
        middleCard.add(createDetailRow("Arrival Location: ", "Vancouver (YVR)"));
        middleCard.add(createDetailRow("Arrival Time: ", "13:30"));
        middleCard.add(Box.createVerticalStrut(4));
        middleCard.add(createDetailRow("Departure Date: ", "2025-12-20"));
        middleCard.add(Box.createVerticalStrut(4));
        middleCard.add(createDetailRow("Seat Number: ", "12A"));
        middleCard.add(Box.createVerticalStrut(4));
        middleCard.add(createDetailRow("Checked Bags: ", "2"));
        middleCard.add(Box.createVerticalStrut(4));
        middleCard.add(createDetailRow("Status: ", "Confirmed"));

        rightSide.add(middleCard);

        // ================================
        // RIGHT COLUMN (ACTIONS)
        // ================================
        JPanel rightCard = new JPanel();
        rightCard.setLayout(new BoxLayout(rightCard, BoxLayout.Y_AXIS));
        rightCard.setBackground(Color.WHITE);
        rightCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        JLabel actionsTitle = new JLabel("Actions");
        actionsTitle.setFont(SECTION_TITLE_FONT);
        actionsTitle.setForeground(new Color(50, 50, 50));
        actionsTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        rightCard.add(actionsTitle);
        rightCard.add(Box.createVerticalStrut(15));

        String[] buttonNames = {
                "Modify Seat Number",
                "Change Number of Checked Bags",
                "Cancel Reservation"
        };

        for (String name : buttonNames) {
            JButton btn = new JButton(name);
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));

            // **ACTION LISTENER FOR BAGS**
            if (name.equals("Change Number of Checked Bags")) {
                btn.addActionListener(e -> openBagChangeWindow());
            }

            rightCard.add(btn);
            rightCard.add(Box.createVerticalStrut(15));
        }

        rightSide.add(rightCard);

        // ================================
        // BOTTOM "BACK" BUTTON
        // ================================
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        bottomPanel.setBackground(BG_COLOR);

        JButton backButton = new JButton("Go Back to Homepage");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.addActionListener(e -> {
            dispose();
            new HomePage().setVisible(true);
        });

        JPanel rightAlign = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightAlign.setBackground(BG_COLOR);
        rightAlign.add(backButton);

        bottomPanel.add(rightAlign, BorderLayout.EAST);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    // ====================================================
    // POPUP WINDOW FOR MODIFYING CHECKED BAGS
    // ====================================================
    private void openBagChangeWindow() {
        JFrame bagFrame = new JFrame("Modify Checked Bags");
        bagFrame.setSize(350, 180);
        bagFrame.setLocationRelativeTo(this);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel label = new JLabel("Enter new number of checked bags:");
        label.setFont(new Font("Arial", Font.BOLD, 14));

        // Spinner for number of bags (with arrows)
        SpinnerNumberModel model = new SpinnerNumberModel(2, 0, 10, 1);
        JSpinner bagSpinner = new JSpinner(model);
        Dimension spinnerSize = new Dimension(80, 28);
        bagSpinner.setMaximumSize(spinnerSize);
        bagSpinner.setPreferredSize(spinnerSize);
        bagSpinner.setMinimumSize(spinnerSize);

        JButton saveBtn = new JButton("Save");
        saveBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        saveBtn.addActionListener(e -> {
            int newVal = (int) bagSpinner.getValue();

            JOptionPane.showMessageDialog(bagFrame,
                    "Number of checked bags updated to: " + newVal);

            bagFrame.dispose();
        });

        panel.add(label);
        panel.add(Box.createVerticalStrut(10));
        panel.add(bagSpinner);
        panel.add(Box.createVerticalStrut(15));
        panel.add(saveBtn);

        bagFrame.add(panel);
        bagFrame.setVisible(true);
    }

    // ================================
    // DETAIL ROW BUILDER
    // ================================
    private JPanel createDetailRow(String labelText, String valueText) {
        JPanel row = new JPanel(new BorderLayout());
        row.setOpaque(false);

        JLabel label = new JLabel(labelText);
        label.setFont(LABEL_FONT);
        label.setForeground(new Color(80, 80, 80));

        JLabel value = new JLabel(valueText);
        value.setFont(VALUE_FONT);
        value.setForeground(new Color(40, 40, 40));

        row.add(label, BorderLayout.WEST);
        row.add(value, BorderLayout.CENTER);

        return row;
    }
}