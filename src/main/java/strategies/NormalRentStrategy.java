package strategies;

import java.time.Duration;

import bots.Bot;
import parking.Parking;
import parking.Person;

public class NormalRentStrategy implements AbstractStrategy{

public synchronized void execute(Bot user, Parking parking) {
		
	Person person = user.getPerson();
	
	parking.MakeRental(person, Duration.ofSeconds(30));
	
	try {
		Thread.sleep(1000);
	} catch (InterruptedException e) {
		e.printStackTrace();
	}
	
	parking.FinishRental(person);
	
	
	}	
}
