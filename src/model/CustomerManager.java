package model;

import java.util.ArrayList;

import model.*;

public class CustomerManager {

    private ArrayList<Customer> customers = new ArrayList<>();

    public void addCustomer(Customer c) {
        customers.add(c);
    }

    public boolean removeCustomer(int id) {
        return customers.removeIf(c -> c.getId() == id);
    }

    public Customer findCustomer(int id) {
        for (Customer c : customers) {
            if (c.getId() == id) return c;
        }
        return null;
    }

    public boolean updateCustomer(int id, String fn, String ln, String email, String pw) {
        Customer c = findCustomer(id);
        if (c == null) return false;

        c.setFirstName(fn);
        c.setLastName(ln);
        c.setEmail(email);
        c.setPassword(pw);

        return true;
    }

    public ArrayList<Customer> getAllCustomers() {
        return customers;
    }
}
