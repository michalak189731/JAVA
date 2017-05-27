package parking;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.Duration;
import java.util.*;
public class ParkingTests {

	Parking parking;
	Duration rentDuration;
	Duration longRentDuration;
	
	@Before
	public void SetUp()
	{
		parking = new Parking();
		rentDuration = Duration.ofSeconds(1);
		longRentDuration = Duration.ofHours(1);
	}
	
	@Test
	public void TotalParkingSpotsCountTest() {
		
		Parking customParking = new Parking(3, 3, 3);
		
		assertEquals(9, customParking.getAllParkingSpots().size());
		
		
	}
		
	@Test 
	public void LoginCorrectDataTest()
	{
		Person targetPerson = UserBroker.NormalPerson;
		
		Person person = parking.Login("normal", "test");
		
		assertEquals(targetPerson, person);
		
	}
	
	@Test 
	public void LoginIncorrectDataTest()
	{		
		Person person = parking.Login("noone", "wrong");
		
		assertEquals(null, person);
		
	}
	
	@Test
	public void RentalTimeOutTest()
	{	
		Person person = parking.Login("normal", "test");
		parking.MakeRental(person, rentDuration);
		
		try {
			Thread.sleep(rentDuration.toMillis() + 5);
		} catch (InterruptedException e) {
			fail("Test was interupted");
		}
		
		assertEquals(1,parking.getTimedOutRentals().size());	
	}
	
	@Test
	public void CleanTimedOutRentalTest()
	{
		Person person = parking.Login("normal", "test");
		parking.MakeRental(person, rentDuration);
		
		try {
			Thread.sleep(rentDuration.toMillis() + 5);
		} catch (InterruptedException e) {
			fail("Test was interupted");
		}
		
		parking.ClearRental(person);
		
		assertEquals(0,parking.getTimedOutRentals().size());
	}
	
	@Test
	public void UserAlreadyRentingTest()
	{
		Person person = parking.Login("normal", "test");
		parking.MakeRental(person, longRentDuration);
		int result = parking.MakeRental(person, longRentDuration);
		
		assertEquals(-2, result);
		
	}
	
	@Test
	public void NoPlaceForParking()
	{
		Parking customParking = new Parking(0, 1, 1); 
		
		Person person = customParking.Login("normal", "test");
		int result = customParking.MakeRental(person, longRentDuration);
		
		assertEquals(-1, result);
	}
	
	@Test
	public void ProlongRentalTest() throws InterruptedException
	{
		Duration sec = Duration.ofSeconds(4);
		Person person = parking.Login("normal", "test");
		
		parking.MakeRental(person, sec);
		
		Thread.sleep(2500);
		
		parking.ProlongRental(person, sec);
		
		Thread.sleep(3000);
		
		assertEquals(0, parking.getTimedOutRentals().size());
	}
	
}
