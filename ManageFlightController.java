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
}
