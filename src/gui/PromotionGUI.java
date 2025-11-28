package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PromotionGUI extends JFrame {

    public PromotionGUI() {
        setTitle("Monthly Promotions");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel title = new JLabel("Monthly Promotion News", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));

        JButton showPromoButton = new JButton("Show Monthly Promotion");
        showPromoButton.setFont(new Font("Arial", Font.PLAIN, 16));

        // Button listener
        showPromoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPromotionMessage();
            }
        });

        panel.add(title, BorderLayout.NORTH);
        panel.add(showPromoButton, BorderLayout.CENTER);
        add(panel);
    }

    private void showPromotionMessage() {
        JOptionPane.showMessageDialog(
                this,
                "Special Monthly Offer! \n\n" +
                "Get 15% off all domestic flights booked this week.\n" +
                "Hurry — limited time offer!",
                "Monthly Promotion",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PromotionGUI().setVisible(true));
    }
}
