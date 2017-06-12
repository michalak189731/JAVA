package start;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import bots.Bot;
import bots.CredentialBroker;
import parking.Parking;
import strategies.NormalRentStrategy;

public class SimulationStart {
	
	
	static final int botsCount = 5;
	private static List<Bot> bots = new ArrayList<Bot>();
	private static Parking parking;
	
	public static void main(String[] args) {
		
		SimulationStart.parking = new Parking();
		Setup();
		CredentialBroker.getInstance();
		Bot bot;
	
			bot = new Bot(new NormalRentStrategy(5, 3), SimulationStart.parking);
			bot.Start();
			bots.add(bot);
	}
	
	
	private static void Setup()
	{
		SimulationStart.parking.RegisterPerson("Thomas", "Newmann", "test@test.com", "normal", "test", LocalDateTime.MIN, false, false);
		SimulationStart.parking.RegisterPerson("Pavel", "Zadrok", "otherTest@test.com", "premium", "prem", LocalDateTime.now().plusMonths(2), false, true);
		SimulationStart.parking.RegisterPerson("Zober", "Szulc", "superTest@test.com", "disabled", "dis", LocalDateTime.MIN, true, false);
	}

}
