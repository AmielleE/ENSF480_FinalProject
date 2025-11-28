package gui;

import javax.swing.*;
import java.awt.*;

import model.*;

public class CustomerManagementGUI extends JFrame {

    private CustomerManager manager = new CustomerManager();

    private JTextField idField, fnField, lnField, emailField, pwField;
    private JTextArea output;

    public CustomerManagementGUI() {
        super("Customer Management");

        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // ---------------- INPUTS ----------------
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 10, 10));

        inputPanel.add(new JLabel("Customer ID:"));
        idField = new JTextField();
        inputPanel.add(idField);

        inputPanel.add(new JLabel("First Name:"));
        fnField = new JTextField();
        inputPanel.add(fnField);

        inputPanel.add(new JLabel("Last Name:"));
        lnField = new JTextField();
        inputPanel.add(lnField);

        inputPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        inputPanel.add(emailField);

        inputPanel.add(new JLabel("Password:"));
        pwField = new JTextField();
        inputPanel.add(pwField);

        // ---------------- BUTTONS ----------------
        JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 10, 10));

        JButton addBtn = new JButton("Add");
        JButton updateBtn = new JButton("Update");
        JButton removeBtn = new JButton("Remove");
        JButton showBtn = new JButton("Show All");

        buttonPanel.add(addBtn);
        buttonPanel.add(updateBtn);
        buttonPanel.add(removeBtn);
        buttonPanel.add(showBtn);

        // ---------------- OUTPUT ----------------
        output = new JTextArea();
        output.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(output);

        // ---------------- LAYOUT ----------------
        setLayout(new BorderLayout(10, 10));
        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        // ---------------- ACTIONS ----------------
        addBtn.addActionListener(e -> addCustomer());
        updateBtn.addActionListener(e -> updateCustomer());
        removeBtn.addActionListener(e -> removeCustomer());
        showBtn.addActionListener(e -> showCustomers());
    }

    private void addCustomer() {
        try {
            int id = Integer.parseInt(idField.getText());
            Customer c = new Customer(
                    id,
                    fnField.getText(),
                    lnField.getText(),
                    emailField.getText(),
                    pwField.getText()
            );

            manager.addCustomer(c);
            JOptionPane.showMessageDialog(this, "Customer added!");
            clearFields();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID must be a number.");
        }
    }

    private void updateCustomer() {
        try {
            int id = Integer.parseInt(idField.getText());
            boolean ok = manager.updateCustomer(id,
                    fnField.getText(), lnField.getText(),
                    emailField.getText(), pwField.getText());

            if (ok) JOptionPane.showMessageDialog(this, "Customer updated!");
            else JOptionPane.showMessageDialog(this, "Customer not found!");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID must be a number.");
        }
    }

    private void removeCustomer() {
        try {
            int id = Integer.parseInt(idField.getText());
            boolean ok = manager.removeCustomer(id);

            if (ok) {
                JOptionPane.showMessageDialog(this, "Customer removed!");
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Customer not found!");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID must be a number.");
        }
    }

    private void showCustomers() {
        StringBuilder sb = new StringBuilder();
        for (Customer c : manager.getAllCustomers()) {
            sb.append(c.getId()).append(" - ")
                    .append(c.getFirstName()).append(" ")
                    .append(c.getLastName()).append(" - ")
                    .append(c.getEmail()).append("\n");
        }
        output.setText(sb.toString());
    }

    private void clearFields() {
        idField.setText("");
        fnField.setText("");
        lnField.setText("");
        emailField.setText("");
        pwField.setText("");
    }

    public static void main(String[] args) {
        new CustomerManagementGUI().setVisible(true);
    }
}
