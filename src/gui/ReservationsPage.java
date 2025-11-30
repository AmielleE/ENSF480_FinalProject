package gui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import dao.bookings_dao;
import dao.bookings_dao.BookingSummary;
import model.Booking;
import model.Customer;
import model.Flight;
import model.Plane;

public class ReservationsPage extends JFrame {

    private static final Color BG_COLOR = new Color(90, 200, 200);
    private static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 26);
    private static final Font SECTION_TITLE_FONT = new Font("Arial", Font.BOLD, 20);
    private static final Font LABEL_FONT = new Font("Arial", Font.BOLD, 14);
    private static final Font VALUE_FONT = new Font("Arial", Font.PLAIN, 14);

    private Customer customer;
    private Flight flight;                      // used in seat-selection mode

    // for list-view mode
    private List<BookingSummary> bookings;
    private JPanel middleCard;

    // for seat-selection mode
    private JPanel seatGridPanel;
    private JLabel selectedSeatLabel;
    private String selectedSeat;

    // =========================
    // CONSTRUCTOR 1: SEAT MAP (Customer + Flight)
    // =========================
    public ReservationsPage(Customer customer, Flight flight) {
        this.customer = customer;
        this.flight = flight;
        this.bookings = null;

        buildSeatSelectionUI();
    }

    // =========================
    // CONSTRUCTOR 2: VIEW ALL BOOKINGS (Customer + list)
    // =========================
    public ReservationsPage(Customer customer, List<BookingSummary> bookings) {
        this.customer = customer;
        this.bookings = bookings;
        this.flight = null;

        buildReservationListUI();
    }

    // =========================
    // SEAT SELECTION UI
    // =========================
    private void buildSeatSelectionUI() {
        setTitle("Flight Booking System - Seat Selection");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height - 100;
        setSize(screenWidth, screenHeight);
        setLocationRelativeTo(null);

        getContentPane().setBackground(BG_COLOR);
        setLayout(new BorderLayout(10, 10));

        // HEADER
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(BG_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel headerTitle = new JLabel("Select Your Seat");
        headerTitle.setFont(TITLE_FONT);
        headerTitle.setForeground(Color.WHITE);

        JLabel headerSubtitle = new JLabel(
                "Flight " + flight.getFlightID() + " - " +
                        flight.getOrigin() + " → " + flight.getDestination()
        );
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

        // CENTER
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        centerPanel.setBackground(BG_COLOR);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(centerPanel, BorderLayout.CENTER);

        // LEFT: flight details
        JPanel detailsCard = new JPanel();
        detailsCard.setLayout(new BoxLayout(detailsCard, BoxLayout.Y_AXIS));
        detailsCard.setBackground(Color.WHITE);
        detailsCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        JLabel detailsTitle = new JLabel("Flight Details");
        detailsTitle.setFont(SECTION_TITLE_FONT);
        detailsTitle.setForeground(new Color(50, 50, 50));
        detailsTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        detailsCard.add(detailsTitle);
        detailsCard.add(Box.createVerticalStrut(15));

        detailsCard.add(createDetailRow("Flight ID: ", flight.getFlightID()));
        detailsCard.add(createDetailRow("Origin: ", flight.getOrigin()));
        detailsCard.add(createDetailRow("Destination: ", flight.getDestination()));
        detailsCard.add(createDetailRow("Date: ", flight.getDate()));
        detailsCard.add(createDetailRow("Departure: ", flight.getDepartureTime()));
        detailsCard.add(createDetailRow("Arrival: ", flight.getArrivalTime()));
        detailsCard.add(Box.createVerticalStrut(10));

        selectedSeatLabel = new JLabel("Selected seat: (none)");
        selectedSeatLabel.setFont(LABEL_FONT);
        selectedSeatLabel.setForeground(new Color(60, 60, 60));
        detailsCard.add(selectedSeatLabel);

        centerPanel.add(detailsCard);

        // RIGHT: seat map
        JPanel seatCard = new JPanel(new BorderLayout(10, 10));
        seatCard.setBackground(Color.WHITE);
        seatCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        JLabel seatTitle = new JLabel("Seat Map");
        seatTitle.setFont(SECTION_TITLE_FONT);
        seatTitle.setForeground(new Color(50, 50, 50));

        seatCard.add(seatTitle, BorderLayout.NORTH);

        seatGridPanel = new JPanel();
        seatGridPanel.setBackground(Color.WHITE);
        seatCard.add(seatGridPanel, BorderLayout.CENTER);

        buildSeatMap();

        centerPanel.add(seatCard);

        // BOTTOM BUTTONS
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(BG_COLOR);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton confirmBtn = new JButton("Confirm Booking");
        JButton cancelBtn = new JButton("Cancel");

        JPanel buttonsRight = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonsRight.setBackground(BG_COLOR);
        buttonsRight.add(cancelBtn);
        buttonsRight.add(confirmBtn);

        bottomPanel.add(buttonsRight, BorderLayout.EAST);
        add(bottomPanel, BorderLayout.SOUTH);

        confirmBtn.addActionListener(e -> confirmBooking());
        cancelBtn.addActionListener(e -> {
            dispose();
            new HomePage(customer).setVisible(true);
        });

        setVisible(true);
    }

    private void buildSeatMap() {
        seatGridPanel.removeAll();

        Plane plane = flight.getPlane();
        int rows = plane.getRows();
        int cols = plane.getCols();

        seatGridPanel.setLayout(new GridLayout(rows, cols, 4, 4));

        ButtonGroup group = new ButtonGroup();

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                String label = (r + 1) + String.valueOf((char) ('A' + c));
                JToggleButton seatBtn = new JToggleButton(label);

                if (!flight.getSeatMap().isAvailable(label)) {
                    seatBtn.setEnabled(false);
                } else {
                    seatBtn.addActionListener(e -> {
                        selectedSeat = label;
                        selectedSeatLabel.setText("Selected seat: " + label);
                    });
                }

                group.add(seatBtn);
                seatGridPanel.add(seatBtn);
            }
        }

        seatGridPanel.revalidate();
        seatGridPanel.repaint();
    }

    private void confirmBooking() {
        if (selectedSeat == null) {
            JOptionPane.showMessageDialog(this,
                    "Please select a seat before confirming.",
                    "No Seat Selected",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!flight.getSeatMap().isAvailable(selectedSeat)
                || !flight.getSeatMap().bookSeat(selectedSeat)) {
            JOptionPane.showMessageDialog(this,
                    "That seat has just been taken. Please choose another seat.",
                    "Seat Unavailable",
                    JOptionPane.ERROR_MESSAGE);
            buildSeatMap();
            selectedSeat = null;
            selectedSeatLabel.setText("Selected seat: (none)");
            return;
        }

        int confirmationNum = (int) (Math.random() * 900000) + 100000;
        List<String> seats = new ArrayList<>();
        seats.add(selectedSeat);

        Booking booking = new Booking(confirmationNum, customer, flight, seats);

        bookings_dao dao = new bookings_dao();
        boolean ok = dao.addBooking(booking, customer.getId());

        if (ok) {
            JOptionPane.showMessageDialog(this,
                    "Booking confirmed! Confirmation #: " + confirmationNum);
            dispose();
            new HomePage(customer).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this,
                    "Booking failed. Please try again.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // =========================
    // RESERVATION LIST UI
    // =========================
    private void buildReservationListUI() {
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

    // =========================
    // DETAIL ROW HELPER
    // =========================
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
