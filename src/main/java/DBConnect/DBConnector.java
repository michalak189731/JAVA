package DBConnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import parking.ParkingSpot;
import parking.Person;
import parking.Rental;
import parking.UserBroker;

public class DBConnector {
	
	private Connection con;
	private Statement st;
	private ResultSet rs;

	public DBConnector(){
		try{
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/parkingDB","root","");
			System.out.println("Conn Sucessful");
			st=con.createStatement();
		}catch(Exception e){
			System.out.println("Error "+e);
		}
	}
	
	public List<Person> getPersonsFromDb() {
		List<Person> p = new ArrayList<Person>();
		try {
			String query = "SELECT * FROM `person`";
			st = con.createStatement();
			rs = st.executeQuery(query);
			while (rs.next()) {
				String name = rs.getString("name");
				String surname = rs.getString("surname");
				String email = rs.getString("email");
				String login = rs.getString("login");
				String password = rs.getString("password");
				int id = rs.getInt("ClientID");

				Date d1 = rs.getDate("joined");
				Instant instant = Instant.ofEpochMilli(d1.getTime());
				LocalDateTime joined = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);

				Date d2 = rs.getDate("premiumExpires");
				Instant instant2 = Instant.ofEpochMilli(d2.getTime());
				LocalDateTime premiumExpires = LocalDateTime.ofInstant(instant2, ZoneOffset.UTC);

				Boolean isDisabled = rs.getBoolean("isDisabled");
				Boolean isPremium = rs.getBoolean("isPremium");

				p.add(new Person(name, surname, email, login, password, id, joined, premiumExpires, isDisabled,
						isPremium));
				System.out.println(" " + name + " " + surname + " " + email + " " + login + " " + password + " " + id
						+ " " + joined + " " + premiumExpires + " " + isDisabled + " " + isPremium);
			}
		} catch (Exception e) {

		}
		return p;
	}

	public void addPersonToDb(Person p) {
		
		try {
			String query = "INSERT INTO `person` (`ClientID`, `name`, `surname`, `email`, `login`, `password`, "
					+ "`joined`, `premiumExpires`, `isDisabled`, `isPremium`) VALUES "
					+ "("+Integer.toString(p.getId())
					+ ",'"+p.getName()+"'"
					+ ",'"+p.getSurname()+"'"
					+ ",'"+p.getEmail()+"'"
					+ ",'"+p.getLogin()+"'"
					+ ",'"+p.getPassword()+"'"
					+ ",'"+p.getJoined().toString()+"'"
					+ ",'"+p.getPremiumExpires().toString()+"'"
					+ ",'"+((p.isDisabled()) ? "1" : "0")+"'"
					+ ",'"+((p.isPremium()) ? "1" : "0")+"')";


			st = con.createStatement();
			st.executeUpdate(query);
			System.out.println("new person added");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void updatePersonIsPremiumInDb(Person p) {
		try {
				String query = "UPDATE `person` SET `isPremium` = '"+((p.isPremium()) ? "0" : "1") +
						"' WHERE `person`.`ClientID` = "+Integer.toString(p.getId())+"";	
	
			
			st = con.createStatement();
			st.executeUpdate(query);
			System.out.println(" person isPremium updated");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void updatePersonIsDisabledInDb(Person p) {
		try {
				String query = "UPDATE `person` SET `isDisabled` = '"+((p.isDisabled()) ? "0" : "1") +
						"' WHERE `person`.`ClientID` = "+Integer.toString(p.getId())+"";	
	
			
			st = con.createStatement();
			st.executeUpdate(query);
			System.out.println(" person isDisabled updated");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	public List<Rental> getRentalsFromDb(){
		List<Rental> r = new ArrayList<>();
		try {
			String query = "SELECT * FROM `rental`";

			st = con.createStatement();
			rs = st.executeQuery(query);
			while (rs.next()) {

				int RentalID = rs.getInt("rentalID");
				int client = rs.getInt("client");
				ParkingSpot ps = new ParkingSpot();
				int parkingSpot = rs.getInt("parkingSpot");
				
				Boolean isFinished = rs.getBoolean("isFinished");
			
				Date d1 = rs.getDate("rentalStart");
				Instant instant = Instant.ofEpochMilli(d1.getTime());
				LocalDateTime rentalStart = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
				
				Date d2 = rs.getDate("rentalEnd");
				Instant instant2 = Instant.ofEpochMilli(d2.getTime());
				LocalDateTime rentalEnd = LocalDateTime.ofInstant(instant2, ZoneOffset.UTC);
				
				Rental r1 = new Rental(UserBroker.GetPerson(client),ps, rentalStart);
				r1.setRentalID(RentalID);
				ps.setSpotNumber(parkingSpot);
				r1.setFinished(isFinished);
				r1.setRentalEnd(rentalEnd);
				r.add(r1);

				
				System.out.println(RentalID+" " + client + " " + ps.getSpotNumber() 
				+ " " + isFinished + " " + rentalStart + " " + rentalEnd );
			}
		} catch (Exception e) {
			System.out.println("Error " + e);
		}
		return r;
	}

	public void addRentalToDb(Rental r){
		try {
			String query = "INSERT INTO `rental` (`rentalID`, `client`, `parkingSpot`, `isFinished`,"
					+ " `rentalStart`, `rentalEnd`) VALUES "
					+ "("+Integer.toString(r.getRentalID())
					+ ",'"+Integer.toString(r.getClient().getId())+"'"
					+ ",'"+Integer.toString(r.getParkingSpot().getSpotNumber())+"'"
					+ ",'"+((r.isFinished()) ? "1" : "0")+"'"
					+ ",'"+r.getRentalStart().toString()+"'"
					+ ",'"+r.getRentalEnd().toString()+"')";


		
			st = con.createStatement();
			st.executeUpdate(query);
			System.out.println("new rental added");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Person returnPerson(ArrayList<Person> p,int client){
		for(Person per : p ){
			if(per.getId()==client){
				return per;
			}
		}
		return null;
	}
	
}
