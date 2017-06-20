package parking;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import parking.exceptions.CurrentlyRentingException;
import parking.exceptions.IncorrectCredentialsException;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.Assert.*;
public class ParkingTests {

	Parking parking;
	Duration rentDuration;
	Duration longRentDuration;
	Person normalPerson, disabledPerson, premiumPerson;

	@Rule
	public ExpectedException expected  = ExpectedException.none();

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


		Person person = null;
		try {
			person = parking.Login("normal", "test");
		} catch (IncorrectCredentialsException e) {
			fail("Given credentials were incorrect");
		}

		assertEquals(targetPerson.getName(), person.getName());
		
	}
	
	@Test
	public void LoginIncorrectDataTest() throws  IncorrectCredentialsException{
		expected.expect(IncorrectCredentialsException.class);
		Person person = parking.Login("noone", "wrong");

		assertEquals(null, person);
		
	}
	
	@Test()
	public void RentalTimeOutTest()
	{
		Person person;

		try
		{
			person = parking.Login("normal", "test");
			parking.MakeRental(person, rentDuration);
		}
		catch (Exception e)
		{
			fail("Error encountered " + e.getMessage());
		}

		
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
		Person person = null;

		try {
			person = parking.Login("normal", "test");
			parking.MakeRental(person, rentDuration);
		}
		catch (Exception e)
		{
			fail(e.getMessage());
		}

		try {
			Thread.sleep(rentDuration.toMillis() + 5);
		} catch (InterruptedException e) {
			fail("Test was interupted");
		}
		
		parking.ClearRental(person);
		
		assertEquals(0,parking.getTimedOutRentals().size());
	}
	
	@Test
	public void UserAlreadyRentingTest() throws CurrentlyRentingException, IncorrectCredentialsException {
		Person person = null;

		person = parking.Login("normal", "test");

		expected.expect(CurrentlyRentingException.class);
		parking.MakeRental(person, longRentDuration);
		parking.MakeRental(person, longRentDuration);

		fail("No exception was raised.");
		
	}
	
	@Test
	public void NoPlaceForParking() throws IncorrectCredentialsException, CurrentlyRentingException {
		Parking customParking = new Parking(0, 1, 1); 

		Person person = null;
		int result = 0;

			person = customParking.Login("normal", "test");
			result = customParking.MakeRental(person, longRentDuration);

		assertEquals(-1, result);
	}
	
	@Test
	public void ProlongRentalTest() throws InterruptedException, IncorrectCredentialsException, CurrentlyRentingException {
		Duration sec = Duration.ofSeconds(4);
		Person person = parking.Login("normal", "test");

		parking.MakeRental(person, sec);
		
		Thread.sleep(2500);
		
		parking.ProlongRental(person, sec);
		
		Thread.sleep(3000);
		
		assertEquals(0, parking.getTimedOutRentals().size());
	}
	
	@Test
	public void RentDisabledSpotTest() throws IncorrectCredentialsException, CurrentlyRentingException
	{
		Person res = parking.Login("disabled", "dis");
		
		int rentID = parking.MakeRental(res, longRentDuration);
		Rental rent = parking.getActiveRental(rentID);
		
		assertTrue(parking.getDisabledParkingSpots().contains(rent.getParkingSpot()));
	}
	
	@Test
	public void RentPremiumSpotTest() throws  CurrentlyRentingException, IncorrectCredentialsException
	{
		Person res = parking.Login("premium", "prem");
		
		int rentID = parking.MakeRental(res, longRentDuration);
		Rental rent = parking.getActiveRental(rentID);
		
		assertTrue(parking.getPremiumParkingSpots().contains(rent.getParkingSpot()));
	}
	
	@Test
	public void LoginCorrectData() throws IncorrectCredentialsException
	{
		Person result = parking.Login("normal", "test");	
		
		assertNotEquals(null, result);
	}
	
	@Test
	public void RegisterNewUserTest() throws  IncorrectCredentialsException
	{
		int currentUsers = UserBroker.getCurrentUsers();
		
		Person per = parking.RegisterPerson("TEsting", "Test", "weqwe@dsadsa.com", "mylogin", "pass", null, false, false);
		if(currentUsers != UserBroker.getCurrentUsers()-1)
			fail("Person not registered");
		
	   per = parking.Login("mylogin", "pass");
	   
	   assertNotEquals(null, per);
	}
}
