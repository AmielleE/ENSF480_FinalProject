package gui;

import javax.swing.*;
import dao.flights_dao;
import dao.bookings_dao;
import model.Customer;
import model.Flight;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class HomePage extends JFrame {

    private Customer currentCustomer;

    public HomePage(Customer customer) {
        this.currentCustomer = customer;

        setTitle("Flight Booking System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height - 100;
        setSize(screenWidth, screenHeight);

        getContentPane().setBackground(new Color(90, 200, 200));
        setLayout(new BorderLayout());

        JPanel outer = new JPanel(new GridBagLayout());
        outer.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();

        JPanel column = new JPanel();
        column.setOpaque(false);
        column.setLayout(new BoxLayout(column, BoxLayout.Y_AXIS));

        JPanel row = new JPanel();
        row.setOpaque(false);
        row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));

        Color fieldBg = new Color(255, 255, 255);
        Color textBlack = Color.BLACK;

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

        JPanel searchPanel = new JPanel();
        searchPanel.setOpaque(false);
        JButton searchBtn = new JButton("Search");
        searchPanel.add(searchBtn);

        row.add(fromPanel);
        row.add(Box.createHorizontalStrut(30));
        row.add(toPanel);
        row.add(Box.createHorizontalStrut(30));
        row.add(datePanel);
        row.add(Box.createHorizontalStrut(30));
        row.add(searchPanel);

        JPanel resPanel = new JPanel();
        resPanel.setOpaque(false);
        JButton reservationsBtn = new JButton("See List of Reservations");
        resPanel.add(reservationsBtn);

        column.add(row);
        column.add(Box.createVerticalStrut(20));
        column.add(resPanel);

        outer.add(column, gbc);
        add(outer, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);
        JButton logoutBtn = new JButton("Logout");
        bottomPanel.add(logoutBtn, BorderLayout.EAST);

        // SEARCH FLIGHTS
        searchBtn.addActionListener(e -> {
            String origin = fromField.getText().trim();
            String dest = toField.getText().trim();

            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
            String date = fmt.format((Date) dateSpinner.getValue());

            flights_dao dao = new flights_dao();
            List<Flight> results = dao.searchFlights(origin, dest, date);

            dispose();
            new FlightsListPage(currentCustomer, results).setVisible(true);
        });

        // VIEW RESERVATIONS
        reservationsBtn.addActionListener(e -> {
            bookings_dao dao = new bookings_dao();
            int userID = currentCustomer.getId();

            List<Integer> confNums = dao.getBookingConfirmationNumbersForUser(userID);
            List<bookings_dao.BookingSummary> list = new ArrayList<>();

            for (int c : confNums) {
                bookings_dao.BookingSummary s = dao.getBookingSummary(c);
                if (s != null) list.add(s);
            }

            dispose();
            new ReservationsPage(currentCustomer, list).setVisible(true);
        });

        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginPage().setVisible(true);
        });

        add(bottomPanel, BorderLayout.SOUTH);
        setVisible(true);
    }
}
