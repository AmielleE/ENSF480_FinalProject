package model;

import java.util.ArrayList;
import java.util.List;

public class Flight {
    private String flightID;
    private String origin;
    private String destination;
    private String date;
    private String departureTime;
    private String arrivalTime;
    private double price;
    private Plane plane;
    private SeatMap seatMap;
    private int seatsAvailable;

    private List<Customer> passengers;
    private List<Booking> reservations;

    public Flight(String flightID, String origin, String destination, String date, String departureTime, String arrivalTime, double price, Plane plane) {
        this.flightID = flightID;
        this.origin = origin;
        this.destination = destination;
        this.date = date;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.price = price;
        this.plane = plane;

        this.seatMap = new SeatMap(plane);
        this.passengers = new ArrayList<>();
        this.reservations = new ArrayList<>();
    }

    // Getters
    public String getFlightID() { return flightID; }
    public String getOrigin() { return origin; }
    public String getDestination() { return destination; }
    public String getDate() { return date; }
    public String getDepartureTime() { return departureTime; }
    public String getArrivalTime() { return arrivalTime; }
    public double getPrice() { return price; }
    public Plane getPlane() { return plane; }
    public SeatMap getSeatMap() { return seatMap; }
    public int getSeatsAvailable() {return seatsAvailable;}
    public List<Customer> getPassengers() { return passengers; }
    public List<Booking> getReservations() { return reservations; }

    // Setters
    public void setOrigin(String origin) { this.origin = origin; }
    public void setDestination(String destination) { this.destination = destination; }
    public void setDate(String date) { this.date = date; }
    public void setDepartureTime(String departureTime) { this.departureTime = departureTime; }
    public void setArrivalTime(String arrivalTime) { this.arrivalTime = arrivalTime; }
    public void setPrice(double price) { this.price = price; }
    public void setPlane(Plane plane) { 
        this.plane = plane; 
        this.seatMap = new SeatMap(plane); // reset seat map for new plane
    }
    public void setSeatsAvailable(int seatsAvailable) {
        this.seatsAvailable = seatsAvailable;
    }

    // Add passenger/reservation
    public void addPassenger(Customer c) { passengers.add(c); }
    public void addReservation(Booking b) { reservations.add(b); }
}
