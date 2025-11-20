public class Flight {
    private String flightID;
    private String origin;
    private String destination;
    private String date;         
    private String flightTime;
    private double price;
    private Plane plane;
    private int seatsAvailable;
    private String airportCode;

    public Flight(String flightID, String origin, String destination, String date,
                  String time, double price, Plane plane, String airportCode) {
        this.flightID = flightID;
        this.origin = origin;
        this.destination = destination;
        this.date = date;
        this.flightTime = time;
        this.price = price;
        this.plane = plane;
        this.seatsAvailable = (plane != null ? plane.getCapacity() : 0);
        this.airportCode = airportCode;
    }

    public String getFlightID() { return flightID; }
    public String getOrigin() { return origin; }
    public String getDestination() { return destination; }
    public String getDate() { return date; }
    public String getFlightTime() { return flightTime; }
    public double getPrice() { return price; }
    public int getSeatsAvailable() { return seatsAvailable; }
    public Plane getPlane() { return plane; }
    public String getAirportCode() { return airportCode; }

    @Override
    public String toString() {
        return "Flight{" +
                "flightID='" + flightID + '\'' +
                ", origin='" + origin + '\'' +
                ", destination='" + destination + '\'' +
                ", date='" + date + '\'' +
                ", flightTime='" + flightTime + '\'' +
                ", price=" + price +
                ", seatsAvailable=" + seatsAvailable +
                ", plane=" + (plane != null ? plane.toString() : "null") +
                '}';
    }

    public synchronized boolean bookSeats(int count) {
        if (count <= 0) return false;
        if (count <= seatsAvailable) {
            seatsAvailable -= count;
            return true;
        }
        return false;
    }

    public synchronized void restoreSeats(int count) {
        if (count <= 0) return;
        seatsAvailable += count;
        if (plane != null && seatsAvailable > plane.getCapacity()) {
            seatsAvailable = plane.getCapacity();
        }
    }
}
