public class Flight {
    private String flightID;
    private String origin;
    private String destination;
    private String date;
    private String flightTime;
    private double price;
    private Plane plane;
    private int seatsAvailable;

    public Flight(String flightID, String origin, String destination, String date, String time, double price, Plane plane) {
        this.flightID = flightID;
        this.origin = origin;
        this.destination = destination;
        this.date = date;
        this.flightTime = time;
        this.price = price;
        this.plane = plane;
        this.seatsAvailable = plane.getCapacity();
    }

    //Getters
    public String getFlightID() {
        return flightID;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return flightTime;
    }

    public double getPrice() {
        return price;
    }

    //Setters
    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setFlightTime(String flightTime) {
        this.flightTime = flightTime;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setPlane(Plane plane) {
        this.plane = plane;
    }
    
    //Booking
    public boolean bookSeats(int count) {
        if (count <= seatsAvailable) {
            seatsAvailable -= count;
            return true;
        }
        return false;
    }

    public void restoreSeats(int count) {
        this.seatsAvailable += count;
    }
}
