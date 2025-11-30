package gui;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

import model.Customer;
import model.Flight;
import model.Booking;
import dao.bookings_dao;
import dao.bookings_dao.BookingSummary;

public class ReservationsPage extends JFrame {

    private Customer customer;
    private List<BookingSummary> bookings;

    private static final Color BG_COLOR = new Color(90, 200, 200);
    private static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 26);
    private static final Font SECTION_TITLE_FONT = new Font("Arial", Font.BOLD, 20);
    private static final Font LABEL_FONT = new Font("Arial", Font.BOLD, 14);
    private static final Font VALUE_FONT = new Font("Arial", Font.PLAIN, 14);

    private JPanel middleCard;

    // Constructor for Book Now
    public ReservationsPage(Customer customer, Flight flight) {
        this.customer = customer;
        this.bookings = null;

        setTitle("Booking Details");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height - 100;
        setSize(screenWidth, screenHeight);
        setLocationRelativeTo(null);

        getContentPane().setBackground(BG_COLOR);
        setLayout(new BorderLayout(10, 10));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(BG_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel headerTitle = new JLabel("Booking Details");
        headerTitle.setFont(TITLE_FONT);
        headerTitle.setForeground(Color.WHITE);
        headerPanel.add(headerTitle, BorderLayout.WEST);

        add(headerPanel, BorderLayout.NORTH);

        JPanel rightSide = new JPanel(new BorderLayout());
        rightSide.setBackground(BG_COLOR);
        add(rightSide, BorderLayout.CENTER);

        middleCard = new JPanel();
        middleCard.setLayout(new BoxLayout(middleCard, BoxLayout.Y_AXIS));
        middleCard.setBackground(Color.WHITE);
        middleCard.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        rightSide.add(middleCard, BorderLayout.CENTER);

        // Display flight details
        showSingleFlightDetails(flight);

        // Confirm booking button
        JButton confirmBtn = new JButton("Confirm Booking");
        confirmBtn.addActionListener(e -> {
            bookings_dao dao = new bookings_dao();

            int confirmationNum = (int)(Math.random() * 900000) + 100000;
            Booking booking = new Booking(confirmationNum, flight, new ArrayList<>());

            boolean ok = dao.addBooking(booking, customer.getId());

            if (ok) {
                JOptionPane.showMessageDialog(this, "Booking Confirmed. Confirmation #: " + confirmationNum);
            } else {
                JOptionPane.showMessageDialog(this, "Booking Failed.");
            }
        });

        middleCard.add(Box.createVerticalStrut(20));
        middleCard.add(confirmBtn);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(BG_COLOR);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            dispose();
            new HomePage(customer).setVisible(true);
        });

        JPanel rightAlign = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightAlign.setBackground(BG_COLOR);
        rightAlign.add(backButton);

        bottomPanel.add(rightAlign, BorderLayout.EAST);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    // Constructor for viewing existing reservations
    public ReservationsPage(Customer customer, List<BookingSummary> bookings) {
        this.customer = customer;
        this.bookings = bookings;

        setTitle("My Reservations");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height - 100;
        setSize(screenWidth, screenHeight);
        setLocationRelativeTo(null);

        getContentPane().setBackground(BG_COLOR);
        setLayout(new BorderLayout(10, 10));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(BG_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel headerTitle = new JLabel("My Reservations");
        headerTitle.setFont(TITLE_FONT);
        headerTitle.setForeground(Color.WHITE);

        headerPanel.add(headerTitle, BorderLayout.WEST);
        add(headerPanel, BorderLayout.NORTH);

        JPanel rightSide = new JPanel(new BorderLayout());
        rightSide.setBackground(BG_COLOR);
        add(rightSide, BorderLayout.CENTER);

        JPanel leftColumn = new JPanel();
        leftColumn.setLayout(new BoxLayout(leftColumn, BoxLayout.Y_AXIS));
        leftColumn.setBackground(BG_COLOR);

        JLabel leftTitle = new JLabel("Your Bookings");
        leftTitle.setFont(SECTION_TITLE_FONT);
        leftTitle.setForeground(Color.WHITE);
        leftTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        leftColumn.add(leftTitle);
        leftColumn.add(Box.createVerticalStrut(10));

        for (BookingSummary b : bookings) {
            JButton btn = new JButton("Reservation #" + b.getConfirmationNumber());
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setMaximumSize(new Dimension(180, 30));

            btn.addActionListener(e -> showBookingDetails(b));

            leftColumn.add(btn);
            leftColumn.add(Box.createVerticalStrut(5));
        }

        JScrollPane leftScroll = new JScrollPane(leftColumn);
        leftScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        leftScroll.setPreferredSize(new Dimension(230, screenHeight));
        leftScroll.getViewport().setBackground(BG_COLOR);

        add(leftScroll, BorderLayout.WEST);

        middleCard = new JPanel();
        middleCard.setLayout(new BoxLayout(middleCard, BoxLayout.Y_AXIS));
        middleCard.setBackground(Color.WHITE);
        middleCard.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        rightSide.add(middleCard, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(BG_COLOR);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            dispose();
            new HomePage(customer).setVisible(true);
        });

        JPanel rightAlign = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightAlign.setBackground(BG_COLOR);
        rightAlign.add(backButton);

        bottomPanel.add(rightAlign, BorderLayout.EAST);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void showSingleFlightDetails(Flight f) {
        middleCard.removeAll();

        middleCard.add(createDetailRow("Flight Number:", f.getFlightID()));
        middleCard.add(createDetailRow("Departure:", f.getOrigin()));
        middleCard.add(createDetailRow("Destination:", f.getDestination()));
        middleCard.add(createDetailRow("Date:", f.getDate()));
        middleCard.add(createDetailRow("Time:", f.getDepartureTime()));
        middleCard.add(createDetailRow("Arrival:", f.getArrivalTime()));
        middleCard.add(createDetailRow("Price:", "$" + f.getPrice()));

        middleCard.revalidate();
        middleCard.repaint();
    }

    private void showBookingDetails(BookingSummary b) {
        Flight f = b.getFlight();

        middleCard.removeAll();

        middleCard.add(createDetailRow("Confirmation #:", String.valueOf(b.getConfirmationNumber())));
        middleCard.add(createDetailRow("Departure:", f.getOrigin()));
        middleCard.add(createDetailRow("Arrival:", f.getDestination()));
        middleCard.add(createDetailRow("Date:", f.getDate()));
        middleCard.add(createDetailRow("Time:", f.getDepartureTime()));
        middleCard.add(createDetailRow("Seats Booked:", String.valueOf(b.getSeatsBooked())));

        middleCard.revalidate();
        middleCard.repaint();
    }

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
