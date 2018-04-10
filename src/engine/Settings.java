package engine;

public class Settings {

	private String myGameName; 
	private double myStartingHealth;
	private double myStartingMoney; 

	public Settings(String gameName, double startingHealth, double starting$) {
		myGameName = gameName; 
		myStartingHealth = startingHealth;
		myStartingMoney = starting$; 
		System.out.println("settings obj made "+gameName+" "+startingHealth+" "+starting$);
	}
	
	public void setGameName(String gameName) {
		myGameName = gameName;
	}
	
	public void setStartingHealth(double startingHealth) {
		myStartingHealth = startingHealth;
	}
	
	public void setStartingMoney(double startingMoney) {
		myStartingMoney = startingMoney; 
	}
	
	public String getGameName() {
	    return myGameName;
	}
	
}
