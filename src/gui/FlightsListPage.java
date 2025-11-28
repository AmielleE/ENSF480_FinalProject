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


//The page that the customers will see when booking a flight
public class FlightsListPage extends JFrame {

    public FlightsListPage() {
        setTitle("Flight Booking System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height - 100;
        setSize(screenWidth, screenHeight);

        Color bg = new Color(90, 200, 200);
        getContentPane().setBackground(bg);
        setLayout(new BorderLayout());

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(bg);

        JButton promotionButton = new JButton("Promotion!");
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.setBackground(bg);
        leftPanel.add(promotionButton);

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

        JPanel mainPanel = new JPanel(new GridLayout(1, 1));
        add(mainPanel, BorderLayout.CENTER);

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

        for (int i = 0; i < 4; i++) {

            JPanel whitePane = new JPanel();
            whitePane.setPreferredSize(new Dimension(300, panelHeight));
            whitePane.setBackground(Color.WHITE);
            whitePane.setLayout(new BorderLayout());
            whitePane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JPanel leftInfo = new JPanel();
            leftInfo.setLayout(new BoxLayout(leftInfo, BoxLayout.Y_AXIS));
            leftInfo.setBackground(Color.WHITE);

            JLabel airlineLabel = new JLabel("Airline " + (i + 1));
            airlineLabel.setFont(new Font("Arial", Font.BOLD, 20));
            airlineLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

            JLabel cityLabel = new JLabel("Calgary to Vancouver");
            cityLabel.setFont(new Font("Arial", Font.ITALIC, 20));
            cityLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

            JLabel fromLabel = new JLabel("Departure: January 1, 2025, 0" + i + ":00");
            JLabel toLabel   = new JLabel("Arrival: January 1, 2025, 0" + (i + 1) + ":30");

            fromLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            toLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

            JLabel flightNumberLabel = new JLabel("Flight Number: 123ABC");
            flightNumberLabel.setFont(new Font("Arial", Font.BOLD, 16));
            flightNumberLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

            leftInfo.add(airlineLabel);
            leftInfo.add(Box.createVerticalStrut(5));
            leftInfo.add(cityLabel);
            leftInfo.add(Box.createVerticalStrut(5));
            leftInfo.add(fromLabel);
            leftInfo.add(Box.createVerticalStrut(5));
            leftInfo.add(toLabel);
            leftInfo.add(Box.createVerticalGlue());
            leftInfo.add(flightNumberLabel);

            JPanel rightInfo = new JPanel();
            rightInfo.setLayout(new BoxLayout(rightInfo, BoxLayout.Y_AXIS));
            rightInfo.setBackground(Color.WHITE);

            JLabel priceLabel = new JLabel("$" + (200 + i * 25));
            priceLabel.setFont(new Font("Arial", Font.BOLD, 18));
            priceLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);

            JButton bookButton = new JButton("Book Now");
            bookButton.setAlignmentX(Component.RIGHT_ALIGNMENT);

            rightInfo.add(priceLabel);
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