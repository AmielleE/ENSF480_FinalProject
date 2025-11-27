package model;

import java.util.List;
import controller.ManageFlightController;

public class SystemAdmin extends User {

    private ManageFlightController flightController;

    public SystemAdmin(int id, String fn, String ln, String email, String pw, ManageFlightController fc) {
        super(id, fn, ln, email, pw);
        this.flightController = fc;
        this.role = "Admin";
    }

    public void addFlight(Flight flight) {
        flightController.addFlight(flight);
    }

    public void removeFlight(String flightID) {
        flightController.removeFlight(flightID);
    }

    public void updateFlight(String flightID, String newOrigin, String newDestination, String newDate, String newDepartureTime, String newArrivalTime, Double newPrice, Plane newPlane) {
        flightController.updateFlight(flightID, newOrigin, newDestination, newDate, newDepartureTime, newArrivalTime, newPrice, newPlane);
    }

    public void manageAircraft(String aircraftID, String action) {
        System.out.println("Managing aircraft " + aircraftID + ": " + action);
    }

    public void manageRoute(String routeID, String action) {
        System.out.println("Managing route " + routeID + ": " + action);
    }

    public void updateSchedule(String flightID, String newDate) {
        System.out.println("Updating schedule for flight " + flightID + " to " + newDate);
    }

    public List<Flight> viewAllFlights() {
    return flightController.viewFlights("", "", ""); // empty strings mean no filter
}

}
