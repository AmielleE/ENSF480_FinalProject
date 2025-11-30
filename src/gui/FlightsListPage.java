package gui;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import model.Customer;
import model.Flight;

public class FlightsListPage extends JFrame {

    private Customer currentCustomer;

    public FlightsListPage(Customer customer, List<Flight> flights) {
        this.currentCustomer = customer;

        setTitle("Flight Lists Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height - 100;
        setSize(screenWidth, screenHeight);

        Color bg = new Color(90, 200, 200);
        getContentPane().setBackground(bg);
        setLayout(new BorderLayout());

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(bg);

        // Promotion popup directly
        JButton promotionButton = new JButton("Promotion!");
        promotionButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(
                    this,
                    "Special Monthly Offer!\n\n" +
                    "Get 15% off all domestic flights booked this week.\n" +
                    "Hurry — limited time offer!",
                    "Monthly Promotion",
                    JOptionPane.INFORMATION_MESSAGE
            );
        });

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.setBackground(bg);
        leftPanel.add(promotionButton);

        JButton backButton = new JButton("Back to Home");
        backButton.addActionListener(e -> {
            dispose();
            new HomePage(currentCustomer).setVisible(true);
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

        for (Flight f : flights) {
            JPanel whitePane = new JPanel();
            whitePane.setPreferredSize(new Dimension(300, panelHeight));
            whitePane.setBackground(Color.WHITE);
            whitePane.setLayout(new BorderLayout());
            whitePane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JPanel leftInfo = new JPanel();
            leftInfo.setLayout(new BoxLayout(leftInfo, BoxLayout.Y_AXIS));
            leftInfo.setBackground(Color.WHITE);

            leftInfo.add(new JLabel(f.getPlane().getAirline()));
            leftInfo.add(new JLabel(f.getOrigin() + " → " + f.getDestination()));
            leftInfo.add(new JLabel("Departure: " + f.getDate() + " " + f.getDepartureTime()));
            leftInfo.add(new JLabel("Arrival: " + f.getArrivalTime()));
            leftInfo.add(new JLabel("Flight Number: " + f.getFlightID()));

            JPanel rightInfo = new JPanel();
            rightInfo.setLayout(new BoxLayout(rightInfo, BoxLayout.Y_AXIS));
            rightInfo.setBackground(Color.WHITE);

            rightInfo.add(new JLabel("$" + f.getPrice()));

            JButton bookButton = new JButton("Book Now");
            bookButton.addActionListener(e -> {
                new ReservationsPage(currentCustomer, f).setVisible(true);
            });

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
