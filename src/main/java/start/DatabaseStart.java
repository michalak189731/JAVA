package start;

import bots.Bot;
import bots.CredentialBroker;
import bots.RunnableBot;
import parking.Parking;
import parking.ParkingSpot;
import parking.Person;
import parking.Rental;
import parking.UserBroker;
import parking.exceptions.CurrentlyRentingException;
import strategies.NormalRentStrategy;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import DBConnect.DBConnector;

public class DatabaseStart {

	private static Parking parking;
	private static UserBroker userBroker;

	public static void main(String[] args) {

		DatabaseStart.parking = new Parking();
		DBConnector dbc = new DBConnector();

		// getPersonsFromDb method
		System.out.println("getPersonsFromDb method");
		
		System.out.println(userBroker.getCurrentUsers());
		dbc.getPersonsFromDb(userBroker.returnListOfPersons());
		System.out.println(userBroker.getCurrentUsers());

		// addPersonToDb method
		System.out.println("addPersonToDb method");

		DatabaseStart.parking.RegisterPerson("Mark", "Hill", "test@test.com", "normal", "jane", LocalDateTime.now(),
				false, false);
		System.out.println(userBroker.getCurrentUsers());

		// getRentalsFromDb method
		System.out.println("getRentalsFromDb method");

		ParkingSpot ps = new ParkingSpot();
		ps.setSpotNumber(1);
		System.out.println(parking.getAllRentals().size());
		dbc.getRentalsFromDb(parking.getAllRentals(), userBroker.returnListOfPersons());
		System.out.println(parking.getAllRentals().size());

		// addRentalToDb method
		System.out.println("addRentalToDb method");
		
		ps.setSpotNumber(3);
		Rental r1 = new Rental(userBroker.GetPerson("szary"), ps, LocalDateTime.now());
		r1.setRentalID(8);
		r1.setFinished(false);
		r1.setRentalEnd(LocalDateTime.now());
		dbc.addRentalToDb(r1);

	}

}
