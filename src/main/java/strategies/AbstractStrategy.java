package strategies;

import bots.Bot;
import parking.Parking;

public interface AbstractStrategy {
	
	public static final String StrategyLoggerName = "Strategy Logger";
	public void execute(Bot user, Parking parking);
}
