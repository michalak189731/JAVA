package start;

import java.util.ArrayList;

import bots.Bot;
import parking.Parking;
import strategies.LoginStrategy;

public class SimulationStart {
	
	
	static final int botsCount = 5;
	
	
	public static void main(String[] args) {
		
		Parking parking = new Parking();
		
		ArrayList<Bot> bots = new ArrayList<Bot>();
		Bot bot;
		
		for(int i=0; i<botsCount; i++)
		{
			bot = new Bot(new LoginStrategy(), parking) {
			};
			bots.add(bot);
		
		}
		
		
		
		
		

	}

}
