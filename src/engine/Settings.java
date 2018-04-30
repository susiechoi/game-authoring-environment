package engine;

import authoring.frontend.exceptions.MissingPropertiesException;
import frontend.PropertiesReader;

public class Settings {

	public static final String DEFAULT_CSS_THEME_PROPS = "src/styling/CurrentCSS.properties";
	public static final String DEFAULT_GAMEPLAYER_PROPS = "engine/gameplayer/gameplayer.properties";
	
	private String myGameName; 
	private double myStartingHealth;
	private double myStartingMoney; 
	private String myCSSTheme; // USE src/styling/CurrentCSS.properties TO FIND CSS FILEPATH CORRESPONDING WITH CSS THEME 
	private String myGameTheme;
	private String myInstructions;
	
	public Settings(String gameName, double startingHealth, double starting$, String cssTheme, String gameTheme, String instructions) {
		myGameName = gameName; 
		myStartingHealth = startingHealth;
		myStartingMoney = starting$; 
		myCSSTheme = cssTheme; 
		myGameTheme = gameTheme;
		myInstructions = instructions;
	}
	
	public void setGameName(String gameName) {
		myGameName = gameName;
	}

	public String getGameName() {
	    return myGameName;
	}
	
	public double startingMoney() {
	    return myStartingMoney;
	}
	
	public double startingHealth() {
	    return myStartingHealth;
	}
	
	public String getInstructions() {
	    return myInstructions;
	}
	public String getCSSTheme() throws MissingPropertiesException {
		PropertiesReader pr = new PropertiesReader();
		return pr.findVal(DEFAULT_CSS_THEME_PROPS, myCSSTheme);
	}
	
}
