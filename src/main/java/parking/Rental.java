package parking;

import java.time.*;

public class Rental {
	
	private static int globalRentalCounter;
	
	private int rentalID;
	private Person client;
	private ParkingSpot parkingSpot;
	private boolean isFinished;
	
	private LocalDateTime rentalStart;
	private LocalDateTime rentalEnd;
	
}
