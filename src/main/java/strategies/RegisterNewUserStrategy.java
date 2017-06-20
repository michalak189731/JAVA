package strategies;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.logging.Logger;

import bots.Bot;
import bots.Credential;
import bots.CredentialBroker;
import parking.Parking;
import parking.Person;
import strategies.AbstractStrategy;

public class RegisterNewUserStrategy implements AbstractStrategy {

	private static int IDOfNextUser = 1;
	
	
	@Override
	public synchronized void execute(Bot user, Parking parking) {
		String newUserLogin = "user" + IDOfNextUser;
		IDOfNextUser++;
		String newUserPassword = "mypass";
		
		
		Logger strategyLogger = Logger.getLogger(StrategyLoggerName);
		
		strategyLogger.info(user + "trying to register user " + newUserLogin);
		
		Person newPerson = parking.RegisterPerson("Tom", "Baker", "tombaker@whatnot.com", newUserLogin, newUserPassword, LocalDateTime.now().minus( Duration.ofDays(2)), false, false);
		if(newPerson==null)
		{
			strategyLogger.warning(user + "couldn't register user canceling...");
			return;
		}
		
		Credential cred = new Credential(newUserLogin, newUserPassword);
		CredentialBroker.getInstance().returnCredential(cred);
		
		strategyLogger.info("Successfuly registered user " + newUserLogin);

	}

}
