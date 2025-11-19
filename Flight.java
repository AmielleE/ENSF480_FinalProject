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
