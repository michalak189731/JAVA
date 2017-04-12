package parking;

import java.time.*;

public class Person {
	
	private String name;
	private String surname;
	private String email;
	private int id;
	
	private LocalDateTime joined;
	private LocalDateTime premiumExpires;
	
	private boolean isDisabled;
	private boolean isPremium;
	
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
}
