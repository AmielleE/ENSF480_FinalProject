package model;

import java.util.ArrayList;
import java.util.List;

public class SeatMap {
    private Plane plane;
    private boolean[][] seatsOccupied;

    public SeatMap(Plane plane) {
        this.plane = plane;
        seatsOccupied = new boolean[plane.getRows()][plane.getCols()];
    }

    public boolean bookSeat(String seatLabel) { //to book a seat using label like 2A
        int row = Integer.parseInt(seatLabel.substring(0, seatLabel.length() - 1)) - 1;
        int col = seatLabel.charAt(seatLabel.length() - 1) - 'A';

        if (seatsOccupied[row][col]) return false;
        seatsOccupied[row][col] = true;
        return true;
    }

    public void cancelSeat(String seatLabel) {
        int row = Integer.parseInt(seatLabel.substring(0, seatLabel.length() - 1)) - 1;
        int col = seatLabel.charAt(seatLabel.length() - 1) - 'A';
        seatsOccupied[row][col] = false;
    }

    public void displaySeatMap() {
        System.out.println("Seat Map for Plane: " + plane.getAircraftID());
        System.out.print("  ");
        for (int c = 0; c < plane.getCols(); c++) {
            System.out.print((char)('A' + c) + " ");
        }
        System.out.println();

        for (int r = 0; r < plane.getRows(); r++) {
            System.out.print((r + 1) + " ");
            for (int c = 0; c < plane.getCols(); c++) {
                System.out.print(seatsOccupied[r][c] ? "X " : "O ");
            }
            System.out.println();
        }
    }

    public List<String> getAvailableSeats() {
        List<String> available = new ArrayList<>();
        for (int r = 0; r < plane.getRows(); r++) {
            for (int c = 0; c < plane.getCols(); c++) {
                if (!seatsOccupied[r][c]) {
                    available.add((r + 1) + String.valueOf((char)('A' + c)));
                }
            }
        }
        return available;
    }

    //Getters
    public int getRows() {
        return plane.getRows();
    }

    public int getCols() {
        return plane.getCols();
    }

    public String getSeatLabel(int row, int col) {
        if (row < 0 || row >= plane.getRows() || col < 0 || col >= plane.getCols()) return null;
        return (row + 1) + String.valueOf((char)('A' + col));
    }

    public boolean isAvailable(String seatLabel) {
        int row = Integer.parseInt(seatLabel.substring(0, seatLabel.length() - 1)) - 1;
        int col = seatLabel.charAt(seatLabel.length() - 1) - 'A';

        if (row < 0 || row >= plane.getRows() || col < 0 || col >= plane.getCols()) {
            return false;
        }

        return !seatsOccupied[row][col]; // true if seat is empty
    }
}
