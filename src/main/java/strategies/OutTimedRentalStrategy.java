package strategies;

import java.time.Duration;
import java.util.logging.Logger;

import bots.Bot;
import parking.Parking;
import parking.Person;
import parking.exceptions.CurrentlyRentingException;
import parking.exceptions.IncorrectCredentialsException;

public class OutTimedRentalStrategy implements AbstractStrategy {
	private int rentalSeconds = 2;
	private int sleepSeconds = 3;
	
	public OutTimedRentalStrategy(int rentTime, int sleepTime) {
		rentalSeconds = rentTime;
		sleepSeconds = sleepTime;
	}
	
	@Override
	public synchronized void execute(Bot user, Parking parking) {
		
		Logger log = Logger.getLogger(StrategyLoggerName);
		Person per = null;
		try {
			per = parking.Login(user.getLogin(), user.getPassword());
		} catch (IncorrectCredentialsException e) {
			e.printStackTrace();
		}
		
		log.info(user + " logged in...");
		
		try {
			parking.MakeRental(per, Duration.ofSeconds(rentalSeconds));
		} catch (CurrentlyRentingException e) {
			e.printStackTrace();
		}
		
		log.info(user + "started renting, sleeping...");
		
		try {
			Thread.sleep(sleepSeconds * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		parking.ClearRental(per);
		
		log.info("Cleared rental finished...");
		
		
		
	}
	
	
}
