package gui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

// A simple GUI to test bookings and seat map
public class FlightBookingGUI {

    private JFrame frame;
    private Customer customer;
    private BookingController bookingController;
    private Flight testFlight;

    private JPanel seatPanel;
    private List<JToggleButton> seatButtons = new ArrayList<>();

    public FlightBookingGUI() {
        bookingController = new BookingController();

        // Mock plane with 4 rows, 4 cols
        Plane plane = new Plane("A1", "Boeing 737", "TestAir", 4, 4);
        testFlight = new Flight("F100", "Calgary", "Toronto", "2025-12-01", "08:00", "12:00", 300.0, plane);

        // Mock customer
        customer = new Customer(1, "Amielle", "E", "a@example.com", "1234", bookingController, null, null);

        frame = new JFrame("Flight Booking Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLayout(new BorderLayout());

        // Flight info
        JTextArea flightInfo = new JTextArea(testFlight.getFlightID() + ": " + testFlight.getOrigin() + " -> " + testFlight.getDestination() +
                "\nDate: " + testFlight.getDate() + "  Departure: " + testFlight.getDepartureTime());
        flightInfo.setEditable(false);
        frame.add(flightInfo, BorderLayout.NORTH);

        // Seat panel
        seatPanel = new JPanel();
        seatPanel.setLayout(new GridLayout(testFlight.getPlane().getRows(), testFlight.getPlane().getCols()));
        createSeatButtons();
        frame.add(seatPanel, BorderLayout.CENTER);

        // Book button
        JButton bookBtn = new JButton("Book Selected Seats");
        bookBtn.addActionListener(e -> bookSelectedSeats());
        frame.add(bookBtn, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void createSeatButtons() {
        SeatMap seatMap = testFlight.getPlane().getSeatMap();
        seatButtons.clear();
        seatPanel.removeAll();

        for (int r = 0; r < seatMap.getRows(); r++) {
            for (int c = 0; c < seatMap.getCols(); c++) {
                String seatLabel = seatMap.getSeatLabel(r, c);
                JToggleButton btn = new JToggleButton(seatLabel);
                if (!seatMap.isAvailable(seatLabel)) {
                    btn.setEnabled(false);
                }
                seatButtons.add(btn);
                seatPanel.add(btn);
            }
        }
    }

    private void bookSelectedSeats() {
        List<String> selectedSeats = new ArrayList<>();
        for (JToggleButton btn : seatButtons) {
            if (btn.isSelected() && btn.isEnabled()) {
                selectedSeats.add(btn.getText());
            }
        }
        if (selectedSeats.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No seats selected!");
            return;
        }

        int conf = customer.makeBooking(testFlight, selectedSeats);
        if (conf != -1) {
            JOptionPane.showMessageDialog(frame, "Booking successful! Confirmation #: " + conf +
                    "\nSeats: " + selectedSeats);
            createSeatButtons(); // refresh availability
        } else {
            JOptionPane.showMessageDialog(frame, "Some seats are not available. Try again.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FlightBookingGUI());
    }
}
