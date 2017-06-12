package parking;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.time.Duration;
import java.time.LocalDateTime;
public class ParkingTests {

	Parking parking;
	Duration rentDuration;
	Duration longRentDuration;
	Person normalPerson, disabledPerson, premiumPerson;
	
	@Before
	public void SetUp()
	{
		parking = new Parking();
		rentDuration = Duration.ofSeconds(1);
		longRentDuration = Duration.ofHours(1);
		normalPerson = parking.RegisterPerson("Thomas", "Newmann", "test@test.com", "normal", "test", LocalDateTime.MIN, false, false);
		premiumPerson = parking.RegisterPerson("Pavel", "Zadrok", "otherTest@test.com", "premium", "prem", LocalDateTime.now().plusMonths(2), false, true);
		disabledPerson = parking.RegisterPerson("Zober", "Szulc", "superTest@test.com", "disabled", "dis", LocalDateTime.MIN, true, false);
	}
	
	@Test
	public void TotalParkingSpotsCountTest() {
		
		Parking customParking = new Parking(3, 3, 3);
		
		assertEquals(9, customParking.getAllParkingSpots().size());
		
		
	}
		
	@Test 
	public void LoginCorrectDataTest()
	{
		Person targetPerson = normalPerson;
		
		Person person = parking.Login("normal", "test");
		
		assertEquals(targetPerson.getName(), person.getName());
		
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
	
	//TODO: Make test for disabled and premium rentals
	
	@Test
	public void LoginCorrectData()
	{
		Person result = parking.Login("normal", "test");
		
		assertNotEquals(null, result);
	}
	
	@Test
	public void RegisterNewUserTest()
	{
		int currentUsers = UserBroker.getCurrentUsers();
		
		Person per = parking.RegisterPerson("TEsting", "Test", "weqwe@dsadsa.com", "mylogin", "pass", null, false, false);
		if(currentUsers != UserBroker.getCurrentUsers()-1)
			fail("Person not registered");
		
	   per = parking.Login("mylogin", "pass");
	   
	   assertNotEquals(null, per);
	}
}
