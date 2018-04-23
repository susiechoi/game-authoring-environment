package engine;

public class Settings {

	private String myGameName; 
	private double myStartingHealth;
	private double myStartingMoney; 
	private String myCSSTheme; // USE src/styling/CurrentCSS.properties TO FIND CSS FILEPATH CORRESPONDING WITH CSS THEME 

	public Settings(String gameName, double startingHealth, double starting$, String cssTheme) {
		myGameName = gameName; 
		myStartingHealth = startingHealth;
		myStartingMoney = starting$; 
		myCSSTheme = cssTheme; 
	}
	
	public void setGameName(String gameName) {
		myGameName = gameName;
	}

	public String getGameName() {
	    return myGameName;
	}
	
}
