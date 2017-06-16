package bots;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class CredentialBroker {

	private static final String filePath = "Credentials"; 
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
		try {
			Stream<String> stream = Files.lines(Paths.get(filePath));
			stream.forEach(s -> ProcessLine(s));
			stream.close();
		} catch (IOException e) {
			Logger.getAnonymousLogger().severe("Error while reading file: ");
			e.printStackTrace();
		}
	}
	
	
	private void ProcessLine(String line)
	{
		String[] str = line.split("\\s+");
		credentials.add(new Credential(str[0], str[1]));
	}
	
	public Credential getCredential()
	{
		
		if(credentials.size()<=0)
			return null;
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
