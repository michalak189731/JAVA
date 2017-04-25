package parking;

import java.util.*;

import java.security.*;
import java.time.LocalDateTime;

public class Parking {
	
	private int normalParkingSpots = 5;
	private int premiumParkingSpots = 5;
	private int disabledParkingSpots = 3;
	
	private MessageDigest messageDigest;
	
	private ArrayList<ParkingSpot> AllParkingSpots = new ArrayList<ParkingSpot>();
	
	private ArrayList<ParkingSpot> NormalParkingSpots = new ArrayList<ParkingSpot>();
	private ArrayList<ParkingSpot> PremiumParkingSpots = new ArrayList<ParkingSpot>();
	private ArrayList<ParkingSpot> DisabledParkingSpots = new ArrayList<ParkingSpot>();
	
	private ArrayList<Rental> ActiveRentals = new ArrayList<Rental>();
	
	public Parking()
	{
		Initialize(normalParkingSpots, premiumParkingSpots, disabledParkingSpots);
	}
	
	public Parking(int normal, int premium, int disabled)
	{
		normalParkingSpots = normal;
		premiumParkingSpots = premium;
		disabledParkingSpots = disabled;
		Initialize(normal, premium, disabled);
		
	}
	
	
	public Person Login(String username, String password)
	{
		
		//For now mock target user
		Person targetUser = UserBroker.GetPerson(username);
		
		if(targetUser == null)
			return null;
		
		//TODO: Validate password
		
		if(targetUser.IsPasswordCorrect(password))
			return targetUser;
		else
			return null;
		
	}
	
	public int MakeRental(Person client)
	{
		if(GetPersonRental(client)!=null)
			return -2;
		
		if(client.isDisabled())
			return MakeDisabledRental(client);
		
		if(client.isPremium())
			return MakePremiumRental(client);
		
		return MakeNormalRental(client);
			
	} 
	
	public ArrayList<ParkingSpot> getAllParkingSpots() {
		return AllParkingSpots;
	}

	public ArrayList<ParkingSpot> getNormalParkingSpots() {
		return NormalParkingSpots;
	}

	public ArrayList<ParkingSpot> getPremiumParkingSpots() {
		return PremiumParkingSpots;
	}

	public ArrayList<ParkingSpot> getDisabledParkingSpots() {
		return DisabledParkingSpots;
	}

	public ArrayList<Rental> getActiveRentals() {
		return ActiveRentals;
	}

	public void CancelRental(Person client)
	{
		Rental targetRental = GetPersonRental(client);
		
		if(targetRental == null)
			return;
		
		ActiveRentals.remove(targetRental);
		targetRental.setRentalEnd(LocalDateTime.now());
		targetRental.getParkingSpot().setTaken(false);
		
		//TODO: Add rental to database
	}
	
	private void Initialize(int normal, int premium, int disabled)
	{
		for(int i=0; i<normal; i++)
		{
			ParkingSpot spot = new ParkingSpot();
			AllParkingSpots.add(spot);
			NormalParkingSpots.add(spot);
		}
		
		for(int i=0; i<premium; i++)
		{
			ParkingSpot spot = new ParkingSpot();
			AllParkingSpots.add(spot);
			PremiumParkingSpots.add(spot);
		}
		
		for(int i=0; i<disabled; i++)
		{
			ParkingSpot spot = new ParkingSpot();
			AllParkingSpots.add(spot);
			DisabledParkingSpots.add(spot);
		}
	}

	private int MakeDisabledRental(Person client)
	{
		for (ParkingSpot spot : DisabledParkingSpots) {
			if(!spot.isTaken())
			{
				return Rent(client, spot);
			}
		}
		return MakePremiumRental(client);
	}
	
	private int MakePremiumRental(Person client)
	{
		for (ParkingSpot spot : PremiumParkingSpots) {
			if(!spot.isTaken())
			{
				return Rent(client, spot);
			}
		}
		
		return MakeNormalRental(client);
		
	}
	
	private int MakeNormalRental(Person client)
	{
		for (ParkingSpot spot : NormalParkingSpots) {
			if(!spot.isTaken())
			{
				return Rent(client, spot);
			}
		}
		return -1;
	}
	
	private int Rent(Person client, ParkingSpot spot)
	{
		Rental newRental = new Rental(client, spot, LocalDateTime.now());
		ActiveRentals.add(newRental);
		
		return newRental.getRentalID();
	}
	
	private Rental GetPersonRental(Person client)
	{
		for (Rental rental : ActiveRentals) {
			if(rental.getClient() == client)
				return rental;
		}
		return null;
	}
	
	

		
}
