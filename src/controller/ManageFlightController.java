package controller;

import java.util.ArrayList;
import java.util.List;

import model.Flight;
import model.Plane;

//Manage Flight controller with a list of flights and the required operations (view, add, remove, update flight)

public class ManageFlightController {

    private List<Flight> flights = new ArrayList<>();

    public List<Flight> getAllFlights() {
        return new ArrayList<>(flights);
    }

    public List<Flight> viewFlights(String origin, String destination, String date) { //using search criteria
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

    public boolean addFlight(Flight flight) { //creating unique flights
        for (Flight f : flights) {
            if (f.getFlightID().equals(flight.getFlightID())) {
                return false; 
            }
        }
        flights.add(flight);
        return true;
    }

    public boolean removeFlight(String flightID) {
        return flights.removeIf(f -> f.getFlightID().equals(flightID));
    }

    public boolean updateFlight(String flightID, String newOrigin, String newDestination, String newDate, String newDepartureTime, String newArrivalTime, Double newPrice, Plane newPlane) 
    { //only updates the fields that are entered (not null) 
        for (Flight f : flights) {
            if (f.getFlightID().equals(flightID)) {

                if (newOrigin != null && !newOrigin.isEmpty()) f.setOrigin(newOrigin);
                if (newDestination != null && !newDestination.isEmpty()) f.setDestination(newDestination);
                if (newDate != null && !newDate.isEmpty()) f.setDate(newDate);
                if (newDepartureTime != null && !newDepartureTime.isEmpty()) f.setDepartureTime(newDepartureTime);
                if (newArrivalTime != null && !newArrivalTime.isEmpty()) f.setArrivalTime(newArrivalTime);
                if (newPrice != null) f.setPrice(newPrice);
                if (newPlane != null) f.setPlane(newPlane);

                return true;
            }
        }
        return false;
    }
}
