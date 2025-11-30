package model;

public class Plane {
    private String aircraftID;
    private String model;
    private String airline;
    private int rows;
    private int cols;
    private int capacity;
    private SeatMap seatMap;

    // Convenience constructor (no capacity passed → rows * cols)
    public Plane(String aircraftID, String model, String airline, int rows, int cols) {
        this(aircraftID, model, airline, rows, cols, rows * cols);
    }

    // Full constructor (used by DAOs)
    public Plane(String aircraftID, String model, String airline,
                 int rows, int cols, int capacity) {
        this.aircraftID = aircraftID;
        this.model = model;
        this.airline = airline;
        this.rows = rows;
        this.cols = cols;
        this.capacity = capacity;
    }

    // Getters
    public String getAircraftID() { return aircraftID; }
    public String getModel() { return model; }
    public String getAirline() { return airline; }
    public int getRows() { return rows; }
    public int getCols() { return cols; }
    public SeatMap getSeatMap() { return seatMap; }
    public int getCapacity() { return capacity; }

    public void setRows(int rows) {
        this.rows = rows;
        this.capacity = rows * this.cols;
    }

    public void setCols(int cols) {
        this.cols = cols;
        this.capacity = this.rows * cols;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public boolean bookSeat(String seatLabel) {
        return seatMap.bookSeat(seatLabel);
    }

    public void cancelSeat(String seatLabel) {
        seatMap.cancelSeat(seatLabel);
    }
}
