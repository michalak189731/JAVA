package bots;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class CredentialBroker {

	private static final String filePath = "Credentials.txt"; 
	private static CredentialBroker instance;
	
	public static CredentialBroker getInstance()
	{
		if(instance==null)
			instance = new CredentialBroker();
		
		return instance;
	}
	
	private ArrayList<Credential> credentials = new ArrayList<Credential>();
	
	private CredentialBroker()
	{
		String line;
		Scanner scanner = new Scanner(filePath);
		
		while(scanner.hasNextLine())
		{
			line = scanner.nextLine();
			ProcessLine(line);
		}
		
		scanner.close();
	}
	
	
	private void ProcessLine(String line)
	{
		String[] str = line.split("\\s+");
		credentials.add(new Credential(str[0], str[1]));
	}
	
	public Credential getCredential()
	{
		int index = new Random().nextInt(credentials.size());
		Credential cre = credentials.get(index);
		credentials.remove(index);
		return cre;
	}
	
	public void returnCredential(Credential cred)
	{
		credentials.add(cred);
	}
	
}
