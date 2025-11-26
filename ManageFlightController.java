import java.util.ArrayList;
import java.util.List;

public class ManageFlightController {

    private List<Flight> flights = new ArrayList<>();

    // Get ALL flights (returns a copy so the list cannot be externally modified)
    public List<Flight> getAllFlights() {
        return new ArrayList<>(flights);
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
    public boolean addFlight(Flight flight) {
        for (Flight f : flights) {
            if (f.getFlightID().equals(flight.getFlightID())) {
                return false; // Already exists
            }
        }
        flights.add(flight);
        return true;
    }

    // Remove flight by ID
    public boolean removeFlight(String flightID) {
        return flights.removeIf(f -> f.getFlightID().equals(flightID));
    }

    // Update Flight — only updates fields that are != null
    public boolean updateFlight(
            String flightID,
            String newOrigin,
            String newDestination,
            String newDate,
            String newDepartureTime,
            String newArrivalTime,
            Double newPrice,
            Plane newPlane
    ) {
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
        return false; // flight not found
    }
}
