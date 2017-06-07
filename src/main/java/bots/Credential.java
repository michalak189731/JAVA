package bots;

public class Credential {
	
	private final String login;
	private final String password;
	public String getLogin() {
		return login;
	}
	public String getPassword() {
		return password;
	}
	
	
	public Credential(String login, String password)
	{
		this.login = login;
		this.password = password;
	}
}
