package authoring.frontend;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.MissingResourceException;

import authoring.frontend.exceptions.MissingPropertiesException;
import frontend.PromptReader;
import frontend.PropertiesReader;
import frontend.Screen;
import frontend.StageManager;
import javafx.stage.Stage;
import frontend.View;
import gameplayer.ScreenManager;

class AuthoringView extends View {
    public static final String DEFAULT_SCREENFLOW_FILEPATH = "src/frontend/ScreenFlow.properties";
    private StageManager myStageManager; 
    private PromptReader myPromptReader;
    private PropertiesReader myPropertiesReader;
    protected AuthoringView(StageManager stageManager, String languageIn) {
	super(stageManager);
	myPromptReader = new PromptReader(languageIn, this);
	myPropertiesReader = new PropertiesReader();
	myStageManager = stageManager; 
	//myStage.setScene(new SpecifyTowerScreen(this).getScreen());
	//myStage.setScene(new CustomizationChoicesScreen(this, "English", "Test Game").getScreen());
	//myStage.setScene((new CustomizationChoicesScreen(this, "Test Game")).getScreen());
	//		myStage.setScene((new SettingsScreen(this).getScreen()));
	myStageManager.switchScreen((new SpecifyTowerScreen(this)).getScreen());
    }
    protected void goForwardFrom(String id) {
	try {
	    System.out.println("early screen class: " + id);
	    String nextScreenClass = myPropertiesReader.findVal(DEFAULT_SCREENFLOW_FILEPATH, id);
	    System.out.println("next screen class: " + nextScreenClass);
	    Class<?> clazz = Class.forName(nextScreenClass);
	  
	    Constructor<?> constructor = clazz.getDeclaredConstructors()[0];
	    if(constructor.getParameterTypes()[0].equals(AuthoringView.class)) {
		AuthoringScreen nextScreen = (AuthoringScreen) constructor.newInstance(this);
		myStageManager.switchScreen(nextScreen.getScreen());
	    }
	    else if(constructor.getParameterTypes()[0].equals(ScreenManager.class)) {
		Screen nextScreen = (Screen) constructor.newInstance(new ScreenManager(myStageManager, myPromptReader));
		myStageManager.switchScreen(nextScreen.getScreen());
	    } //TODO: handle case where switching to gameplay
	    else {
		throw new MissingPropertiesException("");
	    }
	    
	}
	catch(MissingPropertiesException | ClassNotFoundException | InvocationTargetException
		| IllegalAccessException | InstantiationException e) {
	    e.printStackTrace();
	    loadErrorScreen("NoScreenFlow");
	}
    }

//    protected void goForwardFrom(Screen currentScreen) {
//	String currentScreenName = currentScreen.getClass().getSimpleName();
//	if (currentScreenName.equals("SpecifyEnemyScreen")) {
//	    myStageManager.switchScreen((new AdjustEnemyScreen(this)).getScreen()); 	// TODO replace with reflection?
//	}
//	else if (currentScreenName.equals("SpecifyTowerScreen")) {
//	    myStageManager.switchScreen((new AdjustTowerScreen(this)).getScreen());
//	}
//    }

//    protected void goBackFrom(Screen currentScreen) {
//	String currentScreenName = currentScreen.getClass().getSimpleName();
//	if (currentScreenName.equals("AdjustEnemyScreen")) {
//	    myStageManager.switchScreen((new SpecifyEnemyScreen(this).getScreen())); 	// TODO replace with reflection?
//	}
//	else if (currentScreenName.equals("AdjustTowerScreen")) {
//	    myStageManager.switchScreen((new SpecifyTowerScreen(this).getScreen())); 	
//	}
//    }
    protected String getErrorCheckedPrompt(String prompt) {
	return myPromptReader.resourceDisplayText(prompt);
    }

}
