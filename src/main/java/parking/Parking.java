package parking;

import java.util.*;

public class Parking {
	
	private ArrayList<ParkingSpot> AllParkingSpots = new ArrayList<ParkingSpot>();
	private ArrayList<ParkingSpot> FreeParkingSpots = new ArrayList<ParkingSpot>();
	private ArrayList<ParkingSpot> TakenParkingSpots = new ArrayList<ParkingSpot>();
	
	private ArrayList<ParkingSpot> PremiumParkingSpots = new ArrayList<ParkingSpot>();
	private ArrayList<ParkingSpot> DisabledParkingSpots = new ArrayList<ParkingSpot>();
	
	private ArrayList<Rental> ActiveRentals = new ArrayList<Rental>();
}
