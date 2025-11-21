public class Plane {
    private String aircraftID;
    private String model;
    private String airline;
    private int rows;
    private int cols;

    private boolean[][] seatMap;

    public Plane(String aircraftID, String model, String airline, int rows, int cols) {
        this.aircraftID = aircraftID;
        this.model = model;
        this.airline = airline;
        this.rows = rows;
        this.cols = cols;

        seatMap = new boolean[rows][cols];
    }

    public String getAircraftID() { return aircraftID; }
    public String getModel() { return model; }
    public String getAirline() { return airline; }
    public int getRows() { return rows; }
    public int getCols() { return cols; }
    public boolean[][] getSeatMap() { return seatMap; }

    public String getSeatLabel(int row, int col) {
        char rowLetter = (char) ('A' + row);
        return rowLetter + String.valueOf(col + 1);
    }

    public boolean isSeatAvailable(int row, int col) {
        return !seatMap[row][col];
    }

    public boolean bookSeat(int row, int col) {
        if (!seatMap[row][col]) {
            seatMap[row][col] = true;
            return true;
        }
        return false;
    }

    public void cancelSeat(int row, int col) {
        seatMap[row][col] = false;
    }
}
