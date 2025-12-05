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

INSERT INTO users (firstName, lastName, email, password, role)
VALUES 
('Admin', 'Admin', 'admin@admin.com', 'admin', 'Admin'),
('Agent', 'Agent', 'agent@agent.com', 'agent', 'Agent');

INSERT INTO planes (aircraftID, model, airline, rows, cols, capacity)
VALUES
('A320-CA1', 'Airbus A320', 'Air Canada', 30, 6, 180),
('B737-WS1', 'Boeing 737-800', 'WestJet', 28, 6, 168),
('DH8D-PO1', 'Dash 8 Q400', 'Porter', 20, 4, 80);

INSERT INTO flights (flightID, origin, destination, flightDate, flightTime, price, planeID, seatsAvailable)
VALUES
('AC101', 'Calgary', 'Vancouver', '2025-12-01', '09:00', 189.99, 'A320-CA1', 180),
('WS202', 'Vancouver', 'Calgary', '2025-12-01', '14:00', 175.49, 'B737-WS1', 168),
('AC303', 'Calgary', 'Toronto', '2025-12-02', '07:30', 299.99, 'A320-CA1', 180),
('WS404', 'Toronto', 'Calgary', '2025-12-02', '18:00', 309.99, 'B737-WS1', 168),
('AC505', 'Toronto', 'Vancouver', '2025-12-03', '12:00', 349.50, 'A320-CA1', 180),
('WS606', 'Vancouver', 'Toronto', '2025-12-03', '16:00', 329.75, 'B737-WS1', 168),
('PO707', 'Calgary', 'Edmonton', '2025-12-04', '08:15', 99.99, 'DH8D-PO1', 80),
('PO808', 'Edmonton', 'Calgary', '2025-12-04', '11:30', 102.50, 'DH8D-PO1', 80),
('AC909', 'Winnipeg', 'Toronto', '2025-12-05', '13:20', 210.00, 'A320-CA1', 180),
('WS010', 'Montreal', 'Halifax', '2025-12-05', '17:45', 165.25, 'B737-WS1', 168);
