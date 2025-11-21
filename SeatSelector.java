import java.util.Scanner;

public class SeatSelector {

    private Plane plane;
    private Scanner scanner = new Scanner(System.in);

    public SeatSelector(Plane plane) {
        this.plane = plane;
    }

    // Display seat map in console
    public void printSeatMap() {
        System.out.println("Seat Map for " + plane.getModel());

        for (int r = 0; r < plane.getRows(); r++) {
            for (int c = 0; c < plane.getCols(); c++) {

                String label = plane.getSeatLabel(r, c);

                if (plane.isSeatAvailable(r, c)) {
                    System.out.print("[" + label + "] ");
                } else {
                    System.out.print("[XX] ");
                }
            }
            System.out.println();
        }
    }

    // Let user pick a seat
    public String selectSeat() {
        while (true) {

            printSeatMap();

            System.out.print("Enter seat (e.g. A1): ");
            String input = scanner.nextLine().toUpperCase();

            int row = input.charAt(0) - 'A';
            int col = Integer.parseInt(input.substring(1)) - 1;

            if (row < 0 || row >= plane.getRows() || col < 0 || col >= plane.getCols()) {
                System.out.println("Invalid seat. Try again.");
                continue;
            }

            if (!plane.isSeatAvailable(row, col)) {
                System.out.println("Seat already taken. Choose another.");
                continue;
            }

            plane.bookSeat(row, col);
            System.out.println("Seat " + input + " successfully booked!");

            return input;
        }
    }
}
