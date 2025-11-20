import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class FlightManagementGUI extends JFrame {

    private SystemAdmin admin;

    // GUI components
    private JTextField flightIDField, originField, destinationField, dateField, departureTimeField, arrivalTimeField, priceField;
    private JButton addButton, updateButton, removeButton;
    private JTextArea flightListArea;

    public FlightManagementGUI(SystemAdmin admin) {
        this.admin = admin;

        setTitle("Flight Management System");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        // Input panel
        JPanel inputPanel = new JPanel(new GridLayout(7, 2, 5, 5));

        inputPanel.add(new JLabel("Flight ID:"));
        flightIDField = new JTextField();
        inputPanel.add(flightIDField);

        inputPanel.add(new JLabel("Origin:"));
        originField = new JTextField();
        inputPanel.add(originField);

        inputPanel.add(new JLabel("Destination:"));
        destinationField = new JTextField();
        inputPanel.add(destinationField);

        inputPanel.add(new JLabel("Date (YYYY-MM-DD):"));
        dateField = new JTextField();
        inputPanel.add(dateField);

        inputPanel.add(new JLabel("Departure Time (HH:MM):"));
        departureTimeField = new JTextField();
        inputPanel.add(departureTimeField);

        inputPanel.add(new JLabel("Arrival Time (HH:MM):"));
        arrivalTimeField = new JTextField();
        inputPanel.add(arrivalTimeField);

        inputPanel.add(new JLabel("Price:"));
        priceField = new JTextField();
        inputPanel.add(priceField);

        add(inputPanel, BorderLayout.NORTH);

        // Buttons panel
        JPanel buttonPanel = new JPanel();
        addButton = new JButton("Add Flight");
        updateButton = new JButton("Update Flight");
        removeButton = new JButton("Remove Flight");

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(removeButton);

        add(buttonPanel, BorderLayout.CENTER);

        // Flight display
        flightListArea = new JTextArea();
        flightListArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(flightListArea);
        scrollPane.setPreferredSize(new Dimension(680, 200));
        add(scrollPane, BorderLayout.SOUTH);

        // Button actions
        addButton.addActionListener(e -> addFlight());
        updateButton.addActionListener(e -> updateFlight());
        removeButton.addActionListener(e -> removeFlight());

        refreshFlightList();
    }

    private void addFlight() {
        try {
            String id = flightIDField.getText();
            String origin = originField.getText();
            String destination = destinationField.getText();
            String date = dateField.getText();
            String departureTime = departureTimeField.getText();
            String arrivalTime = arrivalTimeField.getText();
            double price = Double.parseDouble(priceField.getText());
            Plane plane = new Plane("C123", "Boeing 737", "WestJet", 100);

            Flight flight = new Flight(id, origin, destination, date, departureTime, arrivalTime, price, plane);
            admin.addFlight(flight);

            refreshFlightList();
            clearFields();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error adding flight: " + ex.getMessage());
        }
    }

    private void updateFlight() {
        try {
            String id = flightIDField.getText();
            String origin = originField.getText().isEmpty() ? null : originField.getText();
            String destination = destinationField.getText().isEmpty() ? null : destinationField.getText();
            String date = dateField.getText().isEmpty() ? null : dateField.getText();
            String departureTime = departureTimeField.getText().isEmpty() ? null : departureTimeField.getText();
            String arrivalTime = arrivalTimeField.getText().isEmpty() ? null : arrivalTimeField.getText();
            Double price = priceField.getText().isEmpty() ? null : Double.parseDouble(priceField.getText());
            Plane plane = new Plane("C124", "Boeing 747", "AirCanada", 100);

            admin.updateFlight(id, origin, destination, date, departureTime, arrivalTime, price, plane);

            refreshFlightList();
            clearFields();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error updating flight: " + ex.getMessage());
        }
    }

    private void removeFlight() {
        String id = flightIDField.getText();
        if (!id.isEmpty()) {
            admin.removeFlight(id);
            refreshFlightList();
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Enter Flight ID to remove.");
        }
    }

    private void refreshFlightList() {
        flightListArea.setText("");
        for (Flight f : admin.viewAllFlights()) {
            flightListArea.append(
                "ID: " + f.getFlightID() + ", " +
                f.getOrigin() + " -> " + f.getDestination() + ", " +
                "Date: " + f.getDate() + ", " +
                "Departure: " + f.getDepartureTime() + ", " +
                "Arrival: " + f.getArrivalTime() + ", " +
                "Price: $" + f.getPrice() + ", " + "\n"
            );
        }
    }

    private void clearFields() {
        flightIDField.setText("");
        originField.setText("");
        destinationField.setText("");
        dateField.setText("");
        departureTimeField.setText("");
        arrivalTimeField.setText("");
        priceField.setText("");
    }

    // Main method to run the GUI
    public static void main(String[] args) {
        ManageFlightController controller = new ManageFlightController();
        SystemAdmin admin = new SystemAdmin(1, "Admin", "User", "admin@example.com", "pass", controller);

        SwingUtilities.invokeLater(() -> new FlightManagementGUI(admin));
    }
}
