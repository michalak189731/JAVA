package parking;

import java.time.LocalDateTime;

public class Person {
	
	private static int nextId =0;
	
	private String name;
	private String surname;
	private String email;
	private String login;

	private String password;
	private int id;
	
	private LocalDateTime joined;
	private LocalDateTime premiumExpires;
	
	private boolean isDisabled;
	private boolean isPremium;
	
	public static int getNextId()
	{
		return nextId;
	}
	
	public static void setNextId(int id)
	{
		if(id > nextId)
			nextId=id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDateTime getJoined() {
		return joined;
	}

	public void setJoined(LocalDateTime joined) {
		this.joined = joined;
	}

	public Person(String name, String surname, String email, String login, String password, int id,
			LocalDateTime joined, LocalDateTime premiumExpires, boolean isDisabled, boolean isPremium) {
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.setLogin(login);
		this.password = password;
		this.id = id;
		this.joined = joined;
		this.premiumExpires = premiumExpires;
		this.isDisabled = isDisabled;
		this.isPremium = isPremium;
	}
	
	public Boolean IsPasswordCorrect(String givenPassword){	
		return password.equals(givenPassword);
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}
	
	public boolean isDisabled() {
		return isDisabled;
	}

	public boolean isPremium() {
		return isPremium;
	}

	public LocalDateTime getPremiumExpires() {
		return premiumExpires;
	}

	public void setPremiumExpires(LocalDateTime premiumExpires) {
		this.premiumExpires = premiumExpires;
	}

	public void setDisabled(boolean isDisabled) {
		this.isDisabled = isDisabled;
	}

	public void setPremium(boolean isPremium) {
		this.isPremium = isPremium;
	}
	
	public String getPassword(){	
		return this.password;
	}
	public void increaseNextId(){
		this.nextId++;
	}
}
