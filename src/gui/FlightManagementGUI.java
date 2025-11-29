package gui;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

import controller.*;
import model.*;

//This is the page that the system admin will see when login in. They can then add, remove, modify flights.

public class FlightManagementGUI extends JFrame {

    private ManageFlightController flightController;

    private JTable flightTable;
    private DefaultTableModel tableModel;

    private JTextField idField, originField, destinationField, dateField, departureField, arrivalField, priceField;
    private JComboBox<Plane> planeCombo;

    private List<Plane> availablePlanes = new ArrayList<>();

    public FlightManagementGUI(ManageFlightController controller, List<Plane> planes) {
        super("Flight Booking System");
        this.flightController = controller;
        this.availablePlanes = planes;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        initComponents();
        refreshTable();
    }

    private void initComponents() {
        setLayout(new BorderLayout(8, 8));

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6,6,6,6);
        c.fill = GridBagConstraints.HORIZONTAL;

        idField = new JTextField();
        originField = new JTextField();
        destinationField = new JTextField();
        dateField = new JTextField();
        departureField = new JTextField();
        arrivalField = new JTextField();
        priceField = new JTextField();

        planeCombo = new JComboBox<>(availablePlanes.toArray(new Plane[0]));
        planeCombo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Plane) {
                    Plane p = (Plane) value;
                    setText(p.getModel() + " (" + p.getAircraftID() + ") " + p.getRows() + "x" + p.getCols());
                } else {
                    setText("");
                }
                return this;
            }
        });

        int row = 0;

        c.gridx = 0; c.gridy = row; form.add(new JLabel("Flight ID:"), c);
        c.gridx = 1; c.gridy = row++; form.add(idField, c);

        c.gridx = 0; c.gridy = row; form.add(new JLabel("Origin:"), c);
        c.gridx = 1; c.gridy = row++; form.add(originField, c);

        c.gridx = 0; c.gridy = row; form.add(new JLabel("Destination:"), c);
        c.gridx = 1; c.gridy = row++; form.add(destinationField, c);

        c.gridx = 0; c.gridy = row; form.add(new JLabel("Date (YYYY-MM-DD):"), c);
        c.gridx = 1; c.gridy = row++; form.add(dateField, c);

        c.gridx = 0; c.gridy = row; form.add(new JLabel("Departure Time:"), c);
        c.gridx = 1; c.gridy = row++; form.add(departureField, c);

        c.gridx = 0; c.gridy = row; form.add(new JLabel("Arrival Time:"), c);
        c.gridx = 1; c.gridy = row++; form.add(arrivalField, c);

        c.gridx = 0; c.gridy = row; form.add(new JLabel("Price:"), c);
        c.gridx = 1; c.gridy = row++; form.add(priceField, c);

        c.gridx = 0; c.gridy = row; form.add(new JLabel("Plane:"), c);
        c.gridx = 1; c.gridy = row++; form.add(planeCombo, c);

        add(form, BorderLayout.WEST);

        JPanel center = new JPanel(new BorderLayout(6,6));

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 6));
        JButton addBtn = new JButton("Add Flight");
        JButton updateBtn = new JButton("Update Flight");
        JButton removeBtn = new JButton("Remove Flight");

        buttons.add(addBtn);
        buttons.add(updateBtn);
        buttons.add(removeBtn);

        center.add(buttons, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[]{"Flight ID", "Origin", "Destination", "Date", "Departure", "Arrival", "Price", "Plane"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        flightTable = new JTable(tableModel);
        flightTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scroll = new JScrollPane(flightTable);
        center.add(scroll, BorderLayout.CENTER);

        add(center, BorderLayout.CENTER);

        //Button actions linking to the methods, and the database
        addBtn.addActionListener(e -> addFlightAction());
        removeBtn.addActionListener(e -> removeFlightAction());
        updateBtn.addActionListener(e -> updateFlightAction());

        flightTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) populateFieldsFromSelection();
            }
        });
    }

    private void addFlightAction() {
        try {
            String id = idField.getText().trim();
            String origin = originField.getText().trim();
            String dest = destinationField.getText().trim();
            String date = dateField.getText().trim();
            String dep = departureField.getText().trim();
            String arr = arrivalField.getText().trim();
            double price = Double.parseDouble(priceField.getText().trim());
            Plane selectedPlane = (Plane) planeCombo.getSelectedItem();

            if (id.isEmpty() || origin.isEmpty() || dest.isEmpty() || date.isEmpty() || selectedPlane == null) {
                JOptionPane.showMessageDialog(this, "Please fill in Flight ID, Origin, Destination, Date and choose a plane.");
                return;
            }

            Flight f = new Flight(id, origin, dest, date, dep, arr, price, selectedPlane);
            boolean ok = flightController.addFlight(f);
            if (!ok) {
                JOptionPane.showMessageDialog(this, "A flight with that ID already exists.");
            } else {
                refreshTable();
                clearForm();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Price must be a number.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error adding flight: " + ex.getMessage());
        }
    }

    private void removeFlightAction() {
        int row = flightTable.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Select a flight to remove.");
            return;
        }
        String flightID = (String) tableModel.getValueAt(row, 0);
        boolean removed = flightController.removeFlight(flightID);
        if (!removed) {
            JOptionPane.showMessageDialog(this, "Failed to remove flight (not found).");
        } else {
            refreshTable();
            clearForm();
        }
    }

    private void updateFlightAction() {
        int row = flightTable.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Select a flight to update.");
            return;
        }

        try {
            String flightID = idField.getText().trim();
            String newOrigin = originField.getText().trim();
            String newDest = destinationField.getText().trim();
            String newDate = dateField.getText().trim();
            String newDep = departureField.getText().trim();
            String newArr = arrivalField.getText().trim();
            Double newPrice = priceField.getText().trim().isEmpty() ? null : Double.parseDouble(priceField.getText().trim());
            Plane newPlane = (Plane) planeCombo.getSelectedItem();

            boolean updated = flightController.updateFlight(flightID,
                    newOrigin.isEmpty() ? null : newOrigin,
                    newDest.isEmpty() ? null : newDest,
                    newDate.isEmpty() ? null : newDate,
                    newDep.isEmpty() ? null : newDep,
                    newArr.isEmpty() ? null : newArr,
                    newPrice,
                    newPlane);

            if (!updated) {
                JOptionPane.showMessageDialog(this, "Update failed: flight not found.");
            } else {
                refreshTable();
                clearForm();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Price must be a number.");
        }
    }

    private void populateFieldsFromSelection() {
        int row = flightTable.getSelectedRow();
        if (row < 0) return;

        idField.setText((String) tableModel.getValueAt(row, 0));
        originField.setText((String) tableModel.getValueAt(row, 1));
        destinationField.setText((String) tableModel.getValueAt(row, 2));
        dateField.setText((String) tableModel.getValueAt(row, 3));
        departureField.setText((String) tableModel.getValueAt(row, 4));
        arrivalField.setText((String) tableModel.getValueAt(row, 5));
        priceField.setText(String.valueOf(tableModel.getValueAt(row, 6)));

        String aircraftID = (String) tableModel.getValueAt(row, 7);

        for (int i = 0; i < planeCombo.getItemCount(); i++) {
            Plane p = planeCombo.getItemAt(i);
            if (p.getAircraftID().equals(aircraftID)) {
                planeCombo.setSelectedIndex(i);
                break;
            }
        }
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Flight f : flightController.getAllFlights()) {
            Plane p = f.getPlane();
            String planeDesc = p.getAircraftID(); // only store aircraftID
            tableModel.addRow(new Object[]{
                    f.getFlightID(),
                    f.getOrigin(),
                    f.getDestination(),
                    f.getDate(),
                    f.getDepartureTime(),
                    f.getArrivalTime(),
                    f.getPrice(),
                    planeDesc
            });
        }
    }

    private void clearForm() {
        idField.setText("");
        originField.setText("");
        destinationField.setText("");
        dateField.setText("");
        departureField.setText("");
        arrivalField.setText("");
        priceField.setText("");
        if (planeCombo.getItemCount() > 0) planeCombo.setSelectedIndex(0);
        flightTable.clearSelection();
    }

    public static void main(String[] args) {
        //sample planes to have some available when we start
        Plane p1 = new Plane("A320-01", "Airbus A320", "TestAir", 30, 6);
        Plane p2 = new Plane("CRJ-200", "Bombardier CRJ200", "RegionalAir", 15, 4);
        Plane p3 = new Plane("G650", "Gulfstream G650", "PrivateJets", 10, 4);
        List<Plane> planes = new ArrayList<>();
        planes.add(p1); planes.add(p2); planes.add(p3);

        //sample flights
        ManageFlightController controller = new ManageFlightController();
        controller.addFlight(new Flight("F100", "Calgary", "Toronto", "2025-12-01", "08:00", "12:00", 320.0, p1));
        controller.addFlight(new Flight("F200", "Edmonton", "Vancouver", "2025-12-02", "09:30", "11:00", 180.0, p2));

        SwingUtilities.invokeLater(() -> {
            FlightManagementGUI gui = new FlightManagementGUI(controller, planes);
            gui.setVisible(true);
        });
    }
}