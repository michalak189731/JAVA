package parking;

import parking.exceptions.CurrentlyRentingException;
import parking.exceptions.IncorrectCredentialsException;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Parking {
	
	private int normalParkingSpots = 5;
	private int premiumParkingSpots = 5;
	private int disabledParkingSpots = 3;
	
	private static MessageDigest messageDigest;
	
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
	
	public Person Login(String username, String password) throws IncorrectCredentialsException
	{
		
		//For now mock target user
		Person targetUser = UserBroker.GetPerson(username);
		
		if(targetUser == null)
			throw new IncorrectCredentialsException("No such user.");
		
		String hashedPass = DigestMessage(password);
		
		if(targetUser.IsPasswordCorrect(hashedPass))
			return targetUser;
		else
			throw new IncorrectCredentialsException("Incorrect password.");
		
	}
	
	public Person RegisterPerson(String name, String surname, String email, String login, String password, LocalDateTime premiumExpires, boolean isDisabled, boolean isPremium)
	{
		int id = Person.getNextId();
		String hashedPassword = DigestMessage(password);
		
		Person result = new Person(name, surname, email, login, hashedPassword, id, LocalDateTime.now(), premiumExpires, isDisabled, isPremium);
		//TODO: Validate if person is OK
		
		
		Person.setNextId(id+1);
		//TODO: Send Person to database 
		
		UserBroker.AddPerson(result);
		
		return result;
	}
	
	
	public int MakeRental(Person client, Duration duration) throws CurrentlyRentingException
	{
		if(GetPersonRental(client)!=null)
			throw new CurrentlyRentingException("User " + client.getName() + "is already renting spot.");
		
		if(client.isDisabled())
			return MakeDisabledRental(client, duration);
		
		if(client.isPremium())
			return MakePremiumRental(client, duration);
		
		return MakeNormalRental(client, duration);
			
	} 
	
	void ProlongRental(Person client, Duration duration)
	{
		final Rental rental = GetPersonRental(client);
		if(rental==null)
			return;
		
		rental.getTimer().cancel();
		TimerTask timerTask = new TimerTask() {
			
			@Override
			public void run() {
				FinishRental(rental.getClient());
			}
		};
		
		Timer timer = new Timer();
		timer.schedule(timerTask, duration.toMillis());
		rental.setTimer(timer);
	}
	
	
	ArrayList<ParkingSpot> getAllParkingSpots() {
		return AllParkingSpots;
	}

	 ArrayList<ParkingSpot> getNormalParkingSpots() {
		return NormalParkingSpots;
	}

	 ArrayList<ParkingSpot> getPremiumParkingSpots() {
		return PremiumParkingSpots;
	}

	ArrayList<ParkingSpot> getDisabledParkingSpots() {
		return DisabledParkingSpots;
	}

	ArrayList<Rental> getActiveRentals() {
		return ActiveRentals;
	}

	ArrayList<Rental> getTimedOutRentals() {
		return TimedOutRentals;
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
	
	public Rental getRental(int rentalID)
	{
		for(Rental rent : ActiveRentals)
		{
			if(rent.getRentalID() == rentalID)
				return rent;
		}
		
		//TODO: Ask database for rental.
		return null;
	}
	
	//TODO: Test this method
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
		try{
		Parking.messageDigest = MessageDigest.getInstance("SHA");}
		catch(NoSuchAlgorithmException e)
		{

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
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {			
			@Override
			public void run() {
				CancelRental(newRental.getClient());
			}
		}, duration.toMillis());
		newRental.setTimer(timer);
		
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
	
	private static String DigestMessage(String string)
	{
		try {
			messageDigest.update(string.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return new String(messageDigest.digest());
	}

		
}
