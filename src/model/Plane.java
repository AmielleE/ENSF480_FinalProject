package model;

public class Plane {
    private String aircraftID;
    private String model;
    private String airline;
    private int rows;
    private int cols;
    private SeatMap seatMap;

    public Plane(String aircraftID, String model, String airline, int rows, int cols) {
        this.aircraftID = aircraftID;
        this.model = model;
        this.airline = airline;
        this.rows = rows;
        this.cols = cols;
        this.seatMap = new SeatMap(this); // initialize seat map
    }

    // Getters
    public String getAircraftID() { return aircraftID; }
    public String getModel() { return model; }
    public String getAirline() { return airline; }
    public int getRows() { return rows; }
    public int getCols() { return cols; }
    public SeatMap getSeatMap() { return seatMap; }

    public boolean bookSeat(String seatLabel) {
        return seatMap.bookSeat(seatLabel);
    }

    public void cancelSeat(String seatLabel) {
        seatMap.cancelSeat(seatLabel);
    }
}
