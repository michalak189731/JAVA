package start;

import bots.Bot;
import bots.CredentialBroker;
import bots.RunnableBot;
//import bots.RunnableBot;
import parking.Parking;
import strategies.NormalRentStrategy;
import strategies.RegisterNewUserStrategy;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SimulationStart {
	
	
	static final int botsCount = 5;
	private static List<RunnableBot> bots = new ArrayList<RunnableBot>();
	private static Parking parking;
	
	public static void main(String[] args) {
		
		SimulationStart.parking = new Parking();
		CredentialBroker.getInstance();
	
			for(int i=0; i< 3; i++)
			{
				final Bot bot = new Bot(new RegisterNewUserStrategy(), SimulationStart.parking);
				RunnableBot runbot = new RunnableBot(bot);
				Thread th = new Thread(runbot);
				th.start();
				
				bots.add(runbot);
				
			}
			
	}

}
