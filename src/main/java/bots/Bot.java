package bots;

import parking.Parking;
import strategies.AbstractStrategy;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Bot {

	private static Logger logger = Logger.getAnonymousLogger();
	private static int nextID = 0;
	
	private AbstractStrategy myStrategy;
	private Parking parking;
	
	
	private String login;
	private String password;
	private int botID; 
	
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
		botID = nextID;
		nextID++;
		myStrategy = strategy;
		this.parking = parking;
		initialize();
	}
	
	private void initialize()
	{
		loadCredentials();
	}
	
	private void loadCredentials()
	{
		Credential cred = CredentialBroker.getInstance().getCredential();
		if(cred == null)
			return;
		this.login = cred.getLogin();
		this.password = cred.getPassword();
	}
	
	public void Start()
	{
		if(myStrategy==null)
		{
			logger.log(Level.WARNING, "Bot cannot find it's strategy. Exiting...");
			return;
		}
		
		myStrategy.execute(this, parking);
	}
	
	public String toString()
	{
		return "Bot "+ botID + ": ";
	}
	
}
