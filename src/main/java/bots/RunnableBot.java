package bots;

public class RunnableBot implements Runnable {

	private Bot targetBot;
	
	public RunnableBot(Bot targetBot) {
		this.targetBot = targetBot;
	}
	
	@Override
	public void run() {
		targetBot.Start();
	}
	
}
