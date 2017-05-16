package parking;

import java.util.*;

import java.security.*;
import java.time.LocalDateTime;
import java.time.Duration;

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
	private ArrayList<Rental> TimedOutRentals = new ArrayList<Rental>();
	
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
	
	public int MakeRental(Person client, Duration duration)
	{
		if(GetPersonRental(client)!=null)
			return -2;
		
		if(client.isDisabled())
			return MakeDisabledRental(client, duration);
		
		if(client.isPremium())
			return MakePremiumRental(client, duration);
		
		return MakeNormalRental(client, duration);
			
	} 
	
	public void ProlongRental(Person client, Duration duration)
	{
		final Rental rental = GetPersonRental(client);
		if(rental==null)
			return;
		
		rental.getRentTimer().purge();
		rental.getRentTimer().schedule(new TimerTask() {
			
			@Override
			public void run() {
				FinishRental(rental.getClient());
			}
		}, duration.toMillis());
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

	public void FinishRental(Person client)
	{
		Rental targetRental = GetPersonRental(client);
		
		if(targetRental == null)
			return;
		
		ActiveRentals.remove(targetRental);
		targetRental.setRentalEnd(LocalDateTime.now());
		targetRental.getParkingSpot().setTaken(false);
		
		//TODO: Add rental to database
	}
	
	public void CancelRental(Person client)
	{
		Rental targetRental = GetPersonRental(client);
		
		if(targetRental == null)
			return;
		
		ActiveRentals.remove(targetRental);
		TimedOutRentals.add(targetRental);
		targetRental.setRentalEnd(LocalDateTime.now());
	}

	public void ClearRental(Person client)
	{
		Rental targetRental = GetPersonalTimedOutRental(client);
		
		if(targetRental == null)
			return;
		
		TimedOutRentals.remove(targetRental);
		targetRental.getParkingSpot().setTaken(false);
	}
	
	public void BuyPremium(Person client, Duration duration)
	{
		if(client.isPremium())
		{
			LocalDateTime time = client.getPremiumExpires();
			time.plus(duration);
			client.setPremiumExpires(time);
		}			
		else
		{
			client.setPremium(true);
			client.setPremiumExpires(LocalDateTime.now().plus(duration));
		}
	}
	
	public void SetDisabled(Person client)
	{
		client.setDisabled(true);
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

	private int MakeDisabledRental(Person client, Duration duration)
	{
		for (ParkingSpot spot : DisabledParkingSpots) {
			if(!spot.isTaken())
			{
				return Rent(client, spot, duration);
			}
		}
		return MakePremiumRental(client, duration);
	}
	
	private int MakePremiumRental(Person client, Duration duration)
	{
		for (ParkingSpot spot : PremiumParkingSpots) {
			if(!spot.isTaken())
			{
				return Rent(client, spot, duration);
			}
		}
		
		return MakeNormalRental(client, duration);
		
	}
	
	private int MakeNormalRental(Person client, Duration duration)
	{
		for (ParkingSpot spot : NormalParkingSpots) {
			if(!spot.isTaken())
			{
				return Rent(client, spot, duration);
			}
		}
		return -1;
	}
	
	private int Rent(Person client, ParkingSpot spot, Duration duration)
	{
		final Rental newRental = new Rental(client, spot, LocalDateTime.now());
		ActiveRentals.add(newRental);
		newRental.getRentTimer().schedule(new TimerTask() {			
			@Override
			public void run() {
				FinishRental(newRental.getClient());
				
			}
		}, duration.toMillis());
		
		
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
	
	private Rental GetPersonalTimedOutRental(Person client)
	{
		for (Rental rental : TimedOutRentals) {
			if(rental.getClient() == client)
				return rental;
		}
		return null;
	}
	
	

		
}
