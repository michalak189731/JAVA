package start;

import bots.Bot;
import bots.CredentialBroker;
import bots.RunnableBot;
import parking.Parking;
import strategies.NormalRentStrategy;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SimulationStart {
	
	
	static final int botsCount = 5;
	private static List<RunnableBot> bots = new ArrayList<RunnableBot>();
	private static Parking parking;
	
	public static void main(String[] args) {
		
		SimulationStart.parking = new Parking();
		Setup();
		CredentialBroker.getInstance();
		Bot bot;
	
			for(int i=0; i< 3; i++)
			{
				bot = new Bot(new NormalRentStrategy(5, 3), SimulationStart.parking);
				RunnableBot runbot = new RunnableBot(bot);
				Thread th = new Thread(runbot);
				th.start();

				bots.add(runbot);
			}
			
	}
	
	
	private static void Setup()
	{
		SimulationStart.parking.RegisterPerson("Thomas", "Newmann", "test@test.com", "normal", "test", LocalDateTime.MIN, false, false);
		SimulationStart.parking.RegisterPerson("Pavel", "Zadrok", "otherTest@test.com", "premium", "prem", LocalDateTime.now().plusMonths(2), false, true);
		SimulationStart.parking.RegisterPerson("Zober", "Szulc", "superTest@test.com", "disabled", "dis", LocalDateTime.MIN, true, false);
	}

}
