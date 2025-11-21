import javax.swing.*;
import java.awt.*;

public class ReservationsPage extends JFrame {

    public ReservationsPage() {
        // Title of the window
        setTitle("Flight Booking System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height - 100;
        setSize(screenWidth, screenHeight);

        // Use BorderLayout for main layout so columns don't have to be equal width
        setLayout(new BorderLayout());

        // ================================
        // LEFT COLUMN (SCROLLABLE BUTTONS)
        // ================================
        JPanel leftColumn = new JPanel();
        leftColumn.setLayout(new BoxLayout(leftColumn, BoxLayout.Y_AXIS));

        // Add a bunch of buttons as rows
        for (int i = 1; i <= 30; i++) {
            JButton btn = new JButton("Reservation " + i);
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            leftColumn.add(btn);
            leftColumn.add(Box.createVerticalStrut(5)); // small gap
        }

        // Put left column inside a scroll pane
        JScrollPane leftScroll = new JScrollPane(leftColumn);
        leftScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Make the left column thinner: a bit wider than the buttons
        leftScroll.setPreferredSize(new Dimension(200, screenHeight)); // adjust width as you like

        // Add left scrollable column to WEST so it stays narrow
        add(leftScroll, BorderLayout.WEST);

        // ====================================
        // RIGHT SIDE: SPLIT INTO 2 COLUMNS
        // ====================================
        JPanel rightSide = new JPanel(new GridLayout(1, 2)); // middle + right column
        add(rightSide, BorderLayout.CENTER);

        // ================================
        // MIDDLE COLUMN (TEXT)
        // ================================
        JPanel middleColumn = new JPanel();
        middleColumn.setLayout(new BoxLayout(middleColumn, BoxLayout.Y_AXIS));
        middleColumn.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        for (int i = 1; i <= 5; i++) {
            JLabel label = new JLabel("Details line " + i);
            label.setAlignmentX(Component.LEFT_ALIGNMENT);
            middleColumn.add(label);
            middleColumn.add(Box.createVerticalStrut(10));
        }

        // ================================
        // RIGHT COLUMN (3 BUTTONS VERTICALLY)
        // ================================
        JPanel rightColumn = new JPanel();
        rightColumn.setLayout(new BoxLayout(rightColumn, BoxLayout.Y_AXIS));
        rightColumn.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        String[] buttonNames = { "Action 1", "Action 2", "Action 3" };
        for (String name : buttonNames) {
            JButton btn = new JButton(name);
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            rightColumn.add(btn);
            rightColumn.add(Box.createVerticalStrut(15));
        }

        // Add middle and right columns to the right side
        rightSide.add(middleColumn);
        rightSide.add(rightColumn);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ReservationsPage::new);
    }
}

