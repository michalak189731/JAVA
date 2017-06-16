package strategies;

import bots.Bot;
import parking.Parking;
import parking.Person;
import parking.exceptions.CurrentlyRentingException;
import parking.exceptions.IncorrectCredentialsException;

import java.time.Duration;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NormalRentStrategy implements AbstractStrategy{

	private int rentalSeconds = 30;
	private int sleepSeconds = 1;
	
	public NormalRentStrategy(int rentalSeconds, int sleepSeconds)
	{
		this.rentalSeconds = rentalSeconds;
		this.sleepSeconds = sleepSeconds;
	}
	
	
public synchronized void execute(Bot user, Parking parking) {
	
	Logger strategyLogger = Logger.getLogger("Strategy logger");
	Logger debugLogger = Logger.getLogger("Debug logger");
	
	strategyLogger.log(Level.INFO, "Trying to log in as " + user.getLogin());

	Person person = null;

	try {
		person = parking.Login(user.getLogin(), user.getPassword());
	}
	catch (IncorrectCredentialsException e)
	{
		strategyLogger.warning(user + "It was impossible to login: " + e.getMessage());
		debugLogger.severe(user + "Failed to login with credentials: username: " + user.getLogin() + " password: " + user.getPassword());
		return;
	}

	strategyLogger.info(user+"OK");
	strategyLogger.info(user+"Trying to rent for: " + rentalSeconds + " seconds.");

	try {
		parking.MakeRental(person, Duration.ofSeconds(rentalSeconds));
	}
	catch (CurrentlyRentingException e)
	{
		strategyLogger.warning(user+"Failed renting: " + e.getMessage());
		return;
	}

	strategyLogger.info(user + "Successfully rented space. Going to sleep for " + sleepSeconds);
	try {
		Thread.sleep(sleepSeconds * 1000);
	} catch (InterruptedException e) {
		e.printStackTrace();
	}
	
	strategyLogger.info(user + "Done sleeping - returning.");
	parking.FinishRental(person);
	strategyLogger.info(user + "Done.");
	
	}	
}
