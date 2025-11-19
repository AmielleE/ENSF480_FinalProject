public class SystemAdmin extends User {

    private ManageFlightController flightController;

    public SystemAdmin(int id, String fn, String ln, String email, String pw, ManageFlightController fc) {

        super(id, fn, ln, email, pw);
        this.flightController = fc;
    }

    public void addFlight(Flight flight) {
        flightController.addFlight(flight);
    }

    public void removeFlight(String flightID) {
        flightController.removeFlight(flightID);
    }

    public void updateFlight(Flight updatedFlight) {
        flightController.updateFlight(updatedFlight);
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
}
