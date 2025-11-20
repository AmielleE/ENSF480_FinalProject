import java.util.ArrayList;
import java.util.List;

public class ManageFlightController {
    private List<Flight> flights = new ArrayList<>();

    public List<Flight> viewFlights(String origin, String destination, String date) {
        List<Flight> result = new ArrayList<>();
        for (Flight f : flights) {
            if (f.getOrigin().equals(origin) &&
                f.getDestination().equals(destination) &&
                f.getDate().equals(date)) {
                result.add(f);
            }
        }
        return result;
    }

    public void addFlight(Flight flight) {
        if (flight != null) flights.add(flight);
    }

    public void removeFlight(String flightID) {
        flights.removeIf(f -> f.getFlightID().equals(flightID));
    }

    public void updateFlight(Flight updatedFlight) {
        if (updatedFlight == null) return;
        for (int i = 0; i < flights.size(); i++) {
            if (flights.get(i).getFlightID().equals(updatedFlight.getFlightID())) {
                flights.set(i, updatedFlight);
                return;
            }
        }
    }

    public List<Flight> getAllFlights() {
        return new ArrayList<>(flights);
    }
}
