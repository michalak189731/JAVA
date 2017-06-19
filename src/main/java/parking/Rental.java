package parking;

import java.time.LocalDateTime;
import java.util.Timer;



public class Rental {
	
	private static int globalRentalCounter = 0;
	
	public boolean isFinished() {
		return isFinished;
	}

	public void setFinished(boolean isFinished) {
		this.isFinished = isFinished;
	}

	public LocalDateTime getRentalEnd() {
		return rentalEnd;
	}

	public void setRentalEnd(LocalDateTime rentalEnd) {
		this.rentalEnd = rentalEnd;
	}

	public int getRentalID() {
		return rentalID;
	}

	public Person getClient() {
		return client;
	}

	public ParkingSpot getParkingSpot() {
		return parkingSpot;
	}

	public LocalDateTime getRentalStart() {
		return rentalStart;
	}
	
	public Timer getTimer(){
		return timer;
	}
	
	public void setTimer(Timer timer)
	{
		this.timer = timer;
	}

	private int rentalID;
	private Person client;
	private ParkingSpot parkingSpot;
	private boolean isFinished;
	private Timer timer;
	
	private LocalDateTime rentalStart;
	private LocalDateTime rentalEnd;
	
	public Rental(Person client, ParkingSpot spot, LocalDateTime rentalStart)
	{
		this.client = client;
		this.parkingSpot = spot;		
		this.rentalStart = LocalDateTime.now();
		
		spot.setTaken(true);
		
		this.rentalID = globalRentalCounter;
		globalRentalCounter++;
	}
	
	public void setRentalID(int RentalID) {
		this.rentalID = RentalID;
	}
}
