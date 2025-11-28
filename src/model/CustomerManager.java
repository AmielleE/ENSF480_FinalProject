package model;

import java.util.ArrayList;

public class CustomerManager {

    private static CustomerManager instance; //the single instance
    private ArrayList<Customer> customers = new ArrayList<>();
    private CustomerManager() { }

    public static CustomerManager getInstance() { //singleton pattern
        if (instance == null) {
            instance = new CustomerManager();
        }
        return instance;
    }

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