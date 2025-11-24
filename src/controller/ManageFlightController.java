package controller;

import java.util.ArrayList;
import java.util.List;

public class ManageFlightController {

    private List<Flight> flights = new ArrayList<>();

    public List<Flight> viewFlights(String origin, String destination, String date) {
        List<Flight> result = new ArrayList<>();
        for (Flight f : flights) {
            if (f.getOrigin().equals(origin) && f.getDestination().equals(destination) && f.getDate().equals(date)) {
                result.add(f);
            }
        }
        return result;
    }

    public void addFlight(Flight flight) {
        flights.add(flight);
    }

    public void removeFlight(String flightID) {
        flights.removeIf(f -> f.getFlightID().equals(flightID));
    }

    public void updateFlight(String flightID, String newOrigin, String newDestination, String newDate, String newDepartureTime, String newArrivalTime, Double newPrice, Plane newPlane) {
        for (Flight f : flights) {
            if (f.getFlightID().equals(flightID)) {
                if (newOrigin != null && !newOrigin.isEmpty()) {f.setOrigin(newOrigin);}
                if (newDestination != null && !newDestination.isEmpty()) {f.setDestination(newDestination);}
                if (newDate != null && !newDate.isEmpty()) {f.setDate(newDate);}
                if (newDepartureTime != null && !newDepartureTime.isEmpty()) {f.setDepartureTime(newDepartureTime);}
                if (newArrivalTime != null && !newArrivalTime.isEmpty()) {f.setArrivalTime(newArrivalTime);}
                if (newPrice != null) {f.setPrice(newPrice);}
                if (newPlane != null) {f.setPlane(newPlane);}
                return;
            }
        }
    }


}
