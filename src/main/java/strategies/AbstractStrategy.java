package strategies;

import bots.Bot;
import parking.Parking;

public interface AbstractStrategy {
	public void execute(Bot user, Parking parking);
}
