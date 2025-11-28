package controller;

import java.util.ArrayList;
import java.util.List;

import dao.flights_dao;
import model.Flight;
import model.Plane;
import dao.*;

public class ManageFlightController {

    private List<Flight> flights = new ArrayList<>();

    public boolean addFlight(Flight flight) {
        flights_dao dao = new flights_dao();
        return dao.insertFlight(flight);   // or dao.addFlight(flight) if your DAO uses that name
    }
    
    // Get ALL flights (returns a copy so the list cannot be externally modified)
    public List<Flight> getAllFlights() {
        flights_dao dao = new flights_dao();
        return dao.getAllFlights();
    }

    // View flights matching search criteria
    public List<Flight> viewFlights(String origin, String destination, String date) {
        List<Flight> result = new ArrayList<>();

        for (Flight f : flights) {
            boolean matchOrigin = (origin == null || origin.isEmpty() || f.getOrigin().equalsIgnoreCase(origin));
            boolean matchDestination = (destination == null || destination.isEmpty() || f.getDestination().equalsIgnoreCase(destination));
            boolean matchDate = (date == null || date.isEmpty() || f.getDate().equals(date));

            if (matchOrigin && matchDestination && matchDate) {
                result.add(f);
            }
        }

        return result;
    }

    // Add a new flight — ensures no duplicates
    public boolean removeFlight(String flightID) {
        flights_dao dao = new flights_dao();
        return dao.deleteFlight(flightID);
    }

    // Update Flight — only updates fields that are != null
    public boolean updateFlight(String flightID, String origin, String dest,
                            String date, String dep, String arr,
                            Double price, Plane plane) {

    // Build updated Flight object
    Flight updated = new Flight(flightID, origin, dest, date, dep, arr, price, plane);

    flights_dao dao = new flights_dao();
        return dao.updateFlight(updated);
    }
}
