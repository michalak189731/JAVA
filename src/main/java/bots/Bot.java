package bots;

import parking.Parking;
import parking.Person;
import strategies.AbstractStrategy; 

public abstract class Bot {

	private AbstractStrategy myStrategy;
	private Parking parking;
	
	private String login;
	private String password;
	private Person person;
	
	public AbstractStrategy getMyStrategy() {
		return myStrategy;
	}

	public void setMyStrategy(AbstractStrategy myStrategy) {
		this.myStrategy = myStrategy;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public Bot(AbstractStrategy strategy, Parking parking)
	{
		myStrategy = strategy;
		initialize();
	}
	
	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	private void initialize()
	{
		loadCredentials();
	}
	
	private void loadCredentials()
	{
		Credential cred = CredentialBroker.getInstance().getCredential();
		this.login = cred.getLogin();
		this.password = cred.getPassword();
	}
	
	public void Start()
	{
		if(myStrategy==null)
			return;
		
		myStrategy.execute(this, parking);
	}
	
}
