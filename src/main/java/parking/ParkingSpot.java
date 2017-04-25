package parking;

public class ParkingSpot {
	
	private static int currentSpotNumber = 0;
	
	private int spotNumber;
	private boolean isTaken;
	
	public ParkingSpot()
	{
		spotNumber = currentSpotNumber;
		currentSpotNumber++;
	}

	public int getSpotNumber() {
		return spotNumber;
	}

	public boolean isTaken() {
		return isTaken;
	}

	public void setTaken(boolean isTaken) {
		this.isTaken = isTaken;
	}

}
