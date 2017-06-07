package strategies;

import bots.Bot;
import parking.Parking;
import parking.Person;
import strategies.AbstractStrategy;

public class LoginStrategy implements AbstractStrategy {

	public void execute(Bot user, Parking parking) {
		
		String login, password;
		Person person;
		
		login = user.getLogin();
		password = user.getPassword();
		
		person = parking.Login(login, password);
		
		if(person ==null)
		{
			//TODO: log error
			
			
		}
		
		
		
		
		user.setPerson(person);
		
	}
	
}
