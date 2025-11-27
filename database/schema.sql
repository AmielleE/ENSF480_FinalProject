PRAGMA foreign_keys = ON;

DROP TABLE IF EXISTS bookings;
DROP TABLE IF EXISTS payment_info;
DROP TABLE IF EXISTS flights;
DROP TABLE IF EXISTS planes;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
	userID INTEGER PRIMARY KEY AUTOINCREMENT,
    firstName TEXT NOT NULL,
    lastName TEXT NOT NULL,
    email TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL,
    role TEXT NOT NULL CHECK (role IN ('Customer', 'Agent', 'Admin'))
);

CREATE TABLE planes (
	aircraftID TEXT PRIMARY KEY,
    model TEXT NOT NULL,
    airline TEXT NOT NULL,
    rows INTEGER NOT NULL,
    cols INTEGER NOT NULL,
    capacity INTEGER NOT NULL
);

CREATE TABLE flights (
	flightID TEXT PRIMARY KEY,
    origin TEXT NOT NULL,
    destination TEXT NOT NULL,
    flightDate TEXT NOT NULL,
    flightTime TEXT NOT NULL,
    price REAL NOT NULL,
    planeID TEXT NOT NULL,
    seatsAvailable INTEGER NOT NULL,
    FOREIGN KEY (planeID) REFERENCES planes(aircraftID)
);
    
CREATE TABLE bookings (
	confirmationNumber INTEGER PRIMARY KEY,
    userID INTEGER NOT NULL,
    flightID TEXT NOT NULL,
    seatsBooked INTEGER NOT NULL,
    FOREIGN KEY (userID) REFERENCES users(userID),
    FOREIGN KEY (flightID) REFERENCES flights(flightID)
);

CREATE TABLE payment_info (
    name TEXT PRIMARY KEY,
    cardNumber TEXT NOT NULL,
    secuirtyCode INTEGER NOT NULL,
    expiryDate TEXT NOT NULL
);