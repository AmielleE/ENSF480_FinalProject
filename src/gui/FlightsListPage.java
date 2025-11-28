package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import controller.BookingController;

import model.Customer;
import model.Flight;
import model.Plane;
import model.SeatMap;

// A simple GUI to test bookings and seat map
public class FlightsListPage extends JFrame {

    public FlightsListPage(List<Flight> flights) {
        setTitle("Flight Lists Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height - 100;
        setSize(screenWidth, screenHeight);

        Color bg = new Color(90, 200, 200);
        getContentPane().setBackground(bg);
        setLayout(new BorderLayout());

        // ===== TOP BAR WITH SEPARATED BUTTONS =====
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(bg);

        // Left side: Promotion
        JButton promotionButton = new JButton("Promotion!");
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.setBackground(bg);
        leftPanel.add(promotionButton);

        // Right side: Back to Home
        JButton backButton = new JButton("Back to Home");
        backButton.addActionListener(e -> {
            dispose();
            new HomePage().setVisible(true);
        });

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setBackground(bg);
        rightPanel.add(backButton);

        topBar.add(leftPanel, BorderLayout.WEST);
        topBar.add(rightPanel, BorderLayout.EAST);

        add(topBar, BorderLayout.SOUTH);

        // ---- MAIN PANEL ----
        JPanel mainPanel = new JPanel(new GridLayout(1, 1));
        add(mainPanel, BorderLayout.CENTER);

        // =========================
        // LEFT COLUMN (SCROLLABLE)
        // =========================
        JPanel innerPanel = new JPanel();
        innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS));
        innerPanel.setBackground(bg);

        JLabel leftTitle = new JLabel("Listed Flights", SwingConstants.CENTER);
        leftTitle.setFont(new Font("Arial", Font.BOLD, 26));
        leftTitle.setForeground(Color.WHITE);
        leftTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        innerPanel.add(Box.createVerticalStrut(10));
        innerPanel.add(leftTitle);
        innerPanel.add(Box.createVerticalStrut(10));

        int panelHeight = screenHeight / 4;

        // ---- FLIGHT CARD PANES ----
        for (Flight f : flights) {
            JPanel whitePane = new JPanel();
            whitePane.setPreferredSize(new Dimension(300, panelHeight));
            whitePane.setBackground(Color.WHITE);
            whitePane.setLayout(new BorderLayout());
            whitePane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JPanel leftInfo = new JPanel();
            leftInfo.setLayout(new BoxLayout(leftInfo, BoxLayout.Y_AXIS));
            leftInfo.setBackground(Color.WHITE);

            JLabel airlineLabel  = new JLabel(f.getPlane().getAirline());
            JLabel cityLabel     = new JLabel(f.getOrigin() + " → " + f.getDestination());
            JLabel fromLabel     = new JLabel("Departure: " + f.getDate() + " " + f.getDepartureTime());
            JLabel toLabel       = new JLabel("Arrival: " + f.getArrivalTime());
            JLabel flightNumber  = new JLabel("Flight Number: " + f.getFlightID());

            leftInfo.add(airlineLabel);
            leftInfo.add(cityLabel);
            leftInfo.add(fromLabel);
            leftInfo.add(toLabel);
            leftInfo.add(flightNumber);

            JPanel rightInfo = new JPanel();
            rightInfo.setLayout(new BoxLayout(rightInfo, BoxLayout.Y_AXIS));
            rightInfo.setBackground(Color.WHITE);

            JLabel price = new JLabel("$" + f.getPrice());
            JButton bookButton = new JButton("Book Now");

            rightInfo.add(price);
            rightInfo.add(Box.createVerticalGlue());
            rightInfo.add(bookButton);

            whitePane.add(leftInfo, BorderLayout.WEST);
            whitePane.add(rightInfo, BorderLayout.EAST);

            innerPanel.add(Box.createVerticalStrut(10));
            innerPanel.add(whitePane);
        }


        JPanel leftOuter = new JPanel(new BorderLayout());
        leftOuter.setBackground(bg);
        leftOuter.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        leftOuter.add(innerPanel, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(leftOuter);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getViewport().setBackground(bg);

        mainPanel.add(scrollPane);

        setVisible(true);
    }
}
