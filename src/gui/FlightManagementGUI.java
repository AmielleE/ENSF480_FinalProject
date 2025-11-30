package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import controller.ManageFlightController;
import dao.flights_dao;
import model.Flight;
import model.Plane;

// Admin page – add / update / remove flights
public class FlightManagementGUI extends JFrame {

    private ManageFlightController controller;
    private List<Plane> planes;

    private JTextField flightIdField;
    private JTextField originField;
    private JTextField destField;
    private JTextField dateField;
    private JTextField depField;
    private JTextField arrField;
    private JTextField priceField;
    private JComboBox<String> planeCombo;

    private DefaultTableModel flightsModel;
    private JTable flightsTable;

    private flights_dao flightsDao = new flights_dao();

    public FlightManagementGUI(ManageFlightController controller, List<Plane> planes) {
        this.controller = controller;
        this.planes = planes;

        setTitle("Flight Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(8, 8));

        buildFormPanel();
        buildTablePanel();

        // load initial flights from DB
        reloadFlightsTable();
    }

    // ---------------- UI BUILDING ----------------

    private void buildFormPanel() {
        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 4, 4, 4);
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.HORIZONTAL;

        flightIdField = new JTextField(10);
        originField   = new JTextField(10);
        destField     = new JTextField(10);
        dateField     = new JTextField(10); // yyyy-MM-dd
        depField      = new JTextField(10);
        arrField      = new JTextField(10);
        priceField    = new JTextField(10);
        planeCombo    = new JComboBox<>();

        // fill combo with planes
        planeCombo.removeAllItems();
        for (Plane p : planes) {
            // show: aircraftID (model - airline)
            planeCombo.addItem(p.getAircraftID() + " (" + p.getModel() + " - " + p.getAirline() + ")");
        }

        int row = 0;

        c.gridx = 0; c.gridy = row; form.add(new JLabel("Flight ID:"), c);
        c.gridx = 1; c.gridy = row++; form.add(flightIdField, c);

        c.gridx = 0; c.gridy = row; form.add(new JLabel("Origin:"), c);
        c.gridx = 1; c.gridy = row++; form.add(originField, c);

        c.gridx = 0; c.gridy = row; form.add(new JLabel("Destination:"), c);
        c.gridx = 1; c.gridy = row++; form.add(destField, c);

        c.gridx = 0; c.gridy = row; form.add(new JLabel("Date (yyyy-MM-dd):"), c);
        c.gridx = 1; c.gridy = row++; form.add(dateField, c);

        c.gridx = 0; c.gridy = row; form.add(new JLabel("Departure Time:"), c);
        c.gridx = 1; c.gridy = row++; form.add(depField, c);

        c.gridx = 0; c.gridy = row; form.add(new JLabel("Arrival Time:"), c);
        c.gridx = 1; c.gridy = row++; form.add(arrField, c);

        c.gridx = 0; c.gridy = row; form.add(new JLabel("Price:"), c);
        c.gridx = 1; c.gridy = row++; form.add(priceField, c);

        c.gridx = 0; c.gridy = row; form.add(new JLabel("Plane:"), c);
        c.gridx = 1; c.gridy = row++; form.add(planeCombo, c);

        // Buttons
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
        JButton addBtn    = new JButton("Add Flight");
        JButton updateBtn = new JButton("Update Flight");
        JButton removeBtn = new JButton("Remove Flight");
        JButton backBtn   = new JButton("Back to Login");

        buttons.add(addBtn);
        buttons.add(updateBtn);
        buttons.add(removeBtn);
        buttons.add(backBtn);

        c.gridx = 0; c.gridy = row; c.gridwidth = 2;
        form.add(buttons, c);

        add(form, BorderLayout.WEST);

        // button logic
        addBtn.addActionListener(e -> onAddFlight());
        updateBtn.addActionListener(e -> onUpdateFlight());
        removeBtn.addActionListener(e -> onRemoveFlight());
        backBtn.addActionListener(e -> {
            dispose();
            new LoginPage().setVisible(true);
        });
    }

    private void buildTablePanel() {
        flightsModel = new DefaultTableModel(
                new Object[]{"Flight ID", "Origin", "Destination", "Date",
                             "Departure", "Arrival", "Price", "Plane ID"}, 0);

        flightsTable = new JTable(flightsModel);
        flightsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // when a row is clicked, load values into form for editing
        flightsTable.getSelectionModel().addListSelectionListener(e -> {
            int row = flightsTable.getSelectedRow();
            if (row < 0) return;

            flightIdField.setText((String) flightsModel.getValueAt(row, 0));
            originField.setText((String) flightsModel.getValueAt(row, 1));
            destField.setText((String) flightsModel.getValueAt(row, 2));
            dateField.setText((String) flightsModel.getValueAt(row, 3));
            depField.setText((String) flightsModel.getValueAt(row, 4));
            arrField.setText((String) flightsModel.getValueAt(row, 5));
            priceField.setText(String.valueOf(flightsModel.getValueAt(row, 6)));

            String planeID = (String) flightsModel.getValueAt(row, 7);
            // select matching plane in combo
            for (int i = 0; i < planes.size(); i++) {
                if (planes.get(i).getAircraftID().equals(planeID)) {
                    planeCombo.setSelectedIndex(i);
                    break;
                }
            }
        });

        JScrollPane scroll = new JScrollPane(flightsTable);
        add(scroll, BorderLayout.CENTER);
    }

    // ---------------- BUTTON HANDLERS ----------------

    private Plane getSelectedPlane() {
        int idx = planeCombo.getSelectedIndex();
        if (idx < 0 || idx >= planes.size()) return null;
        return planes.get(idx);
    }

    private void onAddFlight() {
        try {
            String id   = flightIdField.getText().trim();
            String orig = originField.getText().trim();
            String dest = destField.getText().trim();
            String date = dateField.getText().trim();
            String dep  = depField.getText().trim();
            String arr  = arrField.getText().trim();
            double price = Double.parseDouble(priceField.getText().trim());
            Plane plane  = getSelectedPlane();

            if (id.isEmpty() || orig.isEmpty() || dest.isEmpty() || date.isEmpty()
                    || dep.isEmpty() || plane == null) {
                JOptionPane.showMessageDialog(this, "Fill all fields and select a plane.");
                return;
            }

            Flight f = new Flight(id, orig, dest, date, dep, arr, price, plane);

            boolean ok = flightsDao.insertFlight(f);
            if (ok) {
                JOptionPane.showMessageDialog(this, "Flight added.");
                reloadFlightsTable();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add flight.");
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Price must be a number.");
        }
    }

    private void onUpdateFlight() {
        try {
            String id   = flightIdField.getText().trim();
            String orig = originField.getText().trim();
            String dest = destField.getText().trim();
            String date = dateField.getText().trim();
            String dep  = depField.getText().trim();
            String arr  = arrField.getText().trim();
            double price = Double.parseDouble(priceField.getText().trim());
            Plane plane  = getSelectedPlane();

            if (id.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter an existing Flight ID to update.");
                return;
            }

            Flight f = new Flight(id, orig, dest, date, dep, arr, price, plane);
            boolean ok = flightsDao.updateFlight(f);

            if (ok) {
                JOptionPane.showMessageDialog(this, "Flight updated.");
                reloadFlightsTable();
            } else {
                JOptionPane.showMessageDialog(this, "Update failed. Check Flight ID.");
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Price must be a number.");
        }
    }

    private void onRemoveFlight() {
        String id = flightIdField.getText().trim();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter Flight ID to remove.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Delete flight " + id + "?",
                "Confirm",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) return;

        boolean ok = flightsDao.deleteFlight(id);
        if (ok) {
            JOptionPane.showMessageDialog(this, "Flight removed.");
            reloadFlightsTable();
        } else {
            JOptionPane.showMessageDialog(this, "Delete failed.");
        }
    }

    // ---------------- DATA LOADING ----------------

    private void reloadFlightsTable() {
        flightsModel.setRowCount(0);

        List<Flight> list = flightsDao.getAllFlights();
        for (Flight f : list) {
            flightsModel.addRow(new Object[]{
                    f.getFlightID(),
                    f.getOrigin(),
                    f.getDestination(),
                    f.getDate(),
                    f.getDepartureTime(),
                    f.getArrivalTime(),
                    f.getPrice(),
                    f.getPlane().getAircraftID()
            });
        }
    }
}
