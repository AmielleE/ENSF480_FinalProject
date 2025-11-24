CREATE DATABASE ENSF490_TermProject_DataBase;

CREATE TABLE users (
	userID INT auto_increment PRIMARY KEY,
    firstName varchar(50) NOT NULL,
    lastName varchar(50) NOT NULL,
    email varchar(100) UNIQUE NOT NULL,
    password varchar(100) NOT NULL,
    role ENUM('Customer', 'Agent', 'Admin') NOT NULL
);

CREATE TABLE planes (
	aircraftID VARCHAR(10) PRIMARY KEY,
    model VARCHAR(50) NOT NULL,
    numRows INT NOT NULL,
    seatsPerRow INT NOT NULL,
    capacity INT NOT NULL
);

CREATE TABLE flights (
	flightID VARCHAR(20) PRIMARY KEY,
    origin VARCHAR(15) NOT NULL,
    destination VARCHAR(15) NOT NULL,
    flightDate DATE NOT NULL,
    flightTime TIME NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    planeID INT NOT NULL,
    seatsAvailable INT NOT NULL,
    FOREIGN KEY (planeID) REFERENCES planes(planeID)
);
    
CREATE TABLE bookings (
	confirmationNumber INT PRIMARY KEY,
    userID INT NOT NULL,
    flightID VARCHAR(20) NOT NULL,
    seatsBooked INT NOT NULL,
    FOREIGN KEY (userID) REFERENCES users(userID),
    FOREIGN KEY (flightID) REFERENCES flights(flightID)
);

CREATE TABLE payment_info (
    userID INT PRIMARY KEY,
    cardNumber INT NOT NULL,
    secuirtyCode INT NOT NULL,
    expiryDate VARCHAR(5) NOT NULL,
    FOREIGN KEY (userID) REFERENCES users(userID)
)