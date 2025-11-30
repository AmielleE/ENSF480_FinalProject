package gui;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import model.Customer;
import dao.users_dao;

//This is the page that the flight agent will be able to access to manage the customer data.

public class CustomerManagementGUI extends JFrame {

    private users_dao userDao = new users_dao();

    private JTextField idField, fnField, lnField, emailField, pwField;
    private JTextArea output;

    public CustomerManagementGUI() {
        super("Flight Booking System");

        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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

        JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 10, 10));

        JButton addBtn = new JButton("Add");
        JButton updateBtn = new JButton("Update");
        JButton removeBtn = new JButton("Remove");
        JButton showBtn = new JButton("Show All");

        buttonPanel.add(addBtn);
        buttonPanel.add(updateBtn);
        buttonPanel.add(removeBtn);
        buttonPanel.add(showBtn);

        output = new JTextArea();
        output.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(output);

        setLayout(new BorderLayout(10, 10));
        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        // Button logic
        addBtn.addActionListener(e -> addCustomer());
        updateBtn.addActionListener(e -> updateCustomer());
        removeBtn.addActionListener(e -> removeCustomer());
        showBtn.addActionListener(e -> showCustomers());

        // ===== LOGOUT BUTTON =====
        JButton logoutBtn = new JButton("Logout");
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(logoutBtn);

        add(bottomPanel, BorderLayout.PAGE_END);

        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginPage().setVisible(true);
        });
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

            boolean ok = userDao.insertCustomer(c);

            if (ok) JOptionPane.showMessageDialog(this, "Customer added.");
            else JOptionPane.showMessageDialog(this, "Insert failed.");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "ID must be a number.");
        }
    }

    private void updateCustomer() {
        try {
            int id = Integer.parseInt(idField.getText());

            boolean ok = userDao.updateCustomer(
                    id,
                    fnField.getText(),
                    lnField.getText(),
                    emailField.getText(),
                    pwField.getText()
            );

            if (ok) JOptionPane.showMessageDialog(this, "Customer updated.");
            else JOptionPane.showMessageDialog(this, "Customer not found.");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "ID must be a number.");
        }
    }

    private void removeCustomer() {
        try {
            int id = Integer.parseInt(idField.getText());

            boolean ok = userDao.removeCustomer(id);
            if (ok) JOptionPane.showMessageDialog(this, "Customer removed.");
            else JOptionPane.showMessageDialog(this, "Customer not found.");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "ID must be a number.");
        }
    }

    private void showCustomers() {
        List<Customer> list = userDao.getAllCustomers();

        StringBuilder sb = new StringBuilder();
        for (Customer c : list) {
            sb.append(c.getId()).append(" - ")
              .append(c.getFirstName()).append(" ")
              .append(c.getLastName()).append(" - ")
              .append(c.getEmail()).append("\n");
        }

        output.setText(sb.toString());
    }

    public static void main(String[] args) {
        new CustomerManagementGUI().setVisible(true);
    }
}
