package parking;

import java.time.LocalDateTime;

public class UserBroker {
	
	public final static Person NormalPerson = new Person("Thomas", "Newmann", "test@test.com", "normal", "test", 1, LocalDateTime.now().minusMonths(4), LocalDateTime.MIN, false, false);
	public final static Person PremiumPerson = new Person("Pavel", "Zadrok", "otherTest@test.com", "premium", "prem", 1, LocalDateTime.now().minusMonths(4), LocalDateTime.now().plusMonths(2), false, true);
	public final static Person DisabledPerson = new Person("Zober", "Szulc", "superTest@test.com", "disabled", "dis", 1, LocalDateTime.now().minusMonths(4), LocalDateTime.MIN, true, false);
	
	
	public final static Person GetPerson(String username)
	{
		if(NormalPerson.getLogin() == username)
			return NormalPerson;
		
		if(PremiumPerson.getLogin() == username)
			return PremiumPerson;
		
		if(DisabledPerson.getLogin() == username)
			return DisabledPerson;
		
		return null;
	}
	
}
