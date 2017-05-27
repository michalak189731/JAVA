package parking;

import java.util.ArrayList;

public class UserBroker {
	private final static ArrayList<Person> NewPeople = new ArrayList<Person>();
	
	public final static Person GetPerson(String username)
	{
		for(Person p : NewPeople)
		{
			if(p.getLogin() == username)
				return p;
		}
		
		return null;
	}
	
	public final static void AddPerson(Person person)
	{
		NewPeople.add(person);
	}
	
	public final static int getCurrentUsers()
	{
		return NewPeople.size();
	}
}