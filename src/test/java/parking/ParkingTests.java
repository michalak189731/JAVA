package parking;
import static org.junit.Assert.*;

import org.junit.Test;

public class ParkingTests {

	@Test
	public void TotalParkingSpotsCountTest() {
		
		Parking parking = new Parking(3, 3, 3);
		
		assertEquals(9, parking.getAllParkingSpots().size());
		
		
	}
	
	
	@Test 
	public void LoginCorrectDataTest()
	{
		Parking parking = new Parking();
		Person targetPerson = UserBroker.NormalPerson;
		
		Person person = parking.Login("normal", "test");
		
		assertEquals(targetPerson, person);
		
	}
	
	@Test 
	public void LoginIncorrectDataTest()
	{
		Parking parking = new Parking();
		
		Person person = parking.Login("noone", "wrong");
		
		assertEquals(null, person);
		
	}
	
	@Test
	public void TestRentalNumber()
	{
		Parking parking = new Parking();
		
		
	}

}
