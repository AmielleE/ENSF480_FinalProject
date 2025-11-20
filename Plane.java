public class Plane {
    private String aircraftID;
    private String model;
    private String airline;
    private int capacity;

    public Plane(String aircraftID, String model, String airline, int capacity) {
        this.aircraftID = aircraftID;
        this.model = model;
        this.airline = airline;
        this.capacity = capacity;
    }

    public int getCapacity() { return capacity; }
    public String getAircraftID() { return aircraftID; }
    public String getAirline() { return airline; }
    public String getModel() { return model; }
}
