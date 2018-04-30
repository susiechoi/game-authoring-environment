package gameplayer.screen;

import authoring.frontend.exceptions.MissingPropertiesException;
import java.awt.Point;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import com.sun.javafx.tools.packager.Log;
import authoring.AuthoringController;
import engine.GameEngine;
import engine.Mediator;
import engine.sprites.FrontEndSprite;
import engine.sprites.towers.CannotAffordException;
import engine.sprites.towers.FrontEndTower;
import frontend.PromptReader;
import frontend.Screen;
import frontend.View;
import gameplayer.BrowserPopup;
import gameplayer.ScreenManager;
import gameplayer.panel.*;
import javafx.beans.property.IntegerProperty;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import voogasalad.util.soundfactory.*;



/**
 * @Author Alexi Kontos & Andrew Arnold
 * Class that creates the screen that is essentially the gameplayer. The screen holds all relevant panels and handles
 * switching and some control for the game itself.
 */
public class GameScreen extends Screen {


	private final String DEFAULT_SHARED_STYLESHEET;
	private static final String PROPERTIES_FILE_PATH = "src/sound/resources/soundFiles.properties";

	private final PromptReader PROMPTS;
	private TowerPanel TOWER_PANEL;
	private GamePanel GAME_PANEL;
	private ScorePanel SCORE_PANEL;
	private ControlsPanel CONTROLS_PANEL;
	private UpgradePanel UPGRADE_PANEL;
	private ScreenManager SCREEN_MANAGER;
	private BorderPane displayPane;
	private BorderPane gamePane;
	private final Mediator MEDIATOR;
	private BorderPane rootPane;
	private SoundFactory SOUND_FACTORY;
	private Map<String,String> GAMEPLAYER_PROPERTIES;

	public GameScreen(ScreenManager ScreenController, PromptReader promptReader, Mediator mediator) {
		SCREEN_MANAGER = ScreenController;
		GAMEPLAYER_PROPERTIES = SCREEN_MANAGER.getGameplayerProperties();
		DEFAULT_SHARED_STYLESHEET = GAMEPLAYER_PROPERTIES.get("defaultSharedStyleSheet");
		PROMPTS = promptReader;
		MEDIATOR = mediator;
		SOUND_FACTORY = MEDIATOR.getSoundFactory();
		TOWER_PANEL = new TowerPanel(this);
		CONTROLS_PANEL = new ControlsPanel(this, PROMPTS, SCREEN_MANAGER);
		SCORE_PANEL = new ScorePanel(this);
		GAME_PANEL = new GamePanel(this);
	}


	@Override
	public Parent makeScreenWithoutStyling() {
		rootPane = new BorderPane();
		rootPane.setId(GAMEPLAYER_PROPERTIES.get("GameScreenRootID"));
		rootPane.getStylesheets().add(DEFAULT_SHARED_STYLESHEET);

		displayPane = new BorderPane();
		displayPane.setCenter(TOWER_PANEL.getPanel());
		displayPane.setBottom(CONTROLS_PANEL.getPanel());

		SplashPanel SPLASH_PANEL = new SplashPanel(this, GAMEPLAYER_PROPERTIES.get("gameStart"));
		SPLASH_PANEL.getPanel().setOnMouseClicked(arg0 -> gameStart());

		gamePane = new BorderPane();
		gamePane.setTop(SCORE_PANEL.getPanel());
		gamePane.setCenter(SPLASH_PANEL.getPanel());
		MEDIATOR.pause();
		rootPane.setCenter(gamePane);
		setVertPanelsLeft();
		return rootPane;
	}

	/**
	 * Method that takes information from a selected tower, creates a new TowerInfoPanel, and sets it to the bottom pane
	 * to allow the user to see that tower's stats.
	 * @param tower
	 */
	public void towerSelectedForPlacement(FrontEndTower tower) {
		GAME_PANEL.towerSelected(tower);
		TowerInfoPanel TOWER_INFO_PANEL = new TowerInfoPanel(this,PROMPTS,tower);
		displayPane.setBottom(TOWER_INFO_PANEL.getPanel());
	}



	@Override
	protected View getView() {
		return SCREEN_MANAGER;
	}

	/**
	 * Method to add a sprite to the GAME_PANEL
	 * @param sprite
	 */
	public void displaySprite(FrontEndSprite sprite) {
		GAME_PANEL.addSprite(sprite);
	}

	/**
	 * Method to remove a sprite from the GAME_PANEL
	 * @param sprite
	 */
	public void remove(FrontEndSprite sprite) {
		GAME_PANEL.removeSprite(sprite);
	}

	/**
	 * Method to give the TOWER_PANEL the available towers for the game
	 * @param availableTowers
	 */
	public void setAvailbleTowers(List<FrontEndTower> availableTowers) {
		TOWER_PANEL.setAvailableTowers(availableTowers);
	}


	public void loadErrorScreen(String message) {
		SCREEN_MANAGER.loadErrorScreen(message);
	}

	/**
	 * Method that is called within the control panel to give functionality to each of the control buttons
	 * @param control
	 * @throws MissingPropertiesException
	 */
	//TODO implement reflection//rest of controls
	public void controlTriggered(String control) throws MissingPropertiesException {
		if(control.equals(GAMEPLAYER_PROPERTIES.get("play"))) {
			MEDIATOR.play();
		}
		else if(control.equals(GAMEPLAYER_PROPERTIES.get("pause"))) {
			MEDIATOR.pause();
		}
		else if(control.equals(GAMEPLAYER_PROPERTIES.get("speedup"))) {
			MEDIATOR.fastForward(Integer.parseInt(GAMEPLAYER_PROPERTIES.get("fastForwardSize")));
		}
		else if(control.equals(GAMEPLAYER_PROPERTIES.get("quit"))) { //WHY DO I HAVE TO MAKE A NEW PLAY-CONTROLLER OH MY GOD
			getView().playControllerInstructions();
		}
		else if (control.equals(GAMEPLAYER_PROPERTIES.get("edit"))) { // Susie added this
			MEDIATOR.endLoop();
			AuthoringController authoringController = new AuthoringController(SCREEN_MANAGER.getStageManager(), SCREEN_MANAGER.getLanguage());
			authoringController.setModel(SCREEN_MANAGER.getGameFilePath());
		}
		else if (control.equals(GAMEPLAYER_PROPERTIES.get("settings"))) {
			settingsClickedOn();
		}
		else if (control.equals(GAMEPLAYER_PROPERTIES.get("restart"))) {
			MEDIATOR.restartGame();
		}
	}

	/**
	 * Method that is called within the settings panel to give functionality to each of the settings buttons
	 * @param setting
	 */
	public void settingsTriggered(String setting) {
		if (setting.equals(GAMEPLAYER_PROPERTIES.get("volumeToggle"))) {
			SOUND_FACTORY.mute();
		} else if (setting.equals(GAMEPLAYER_PROPERTIES.get("playMusic"))) {
			try {
				SOUND_FACTORY.setBackgroundMusic("stillDre");
			} catch (FileNotFoundException e) {
				Log.debug(e);
			}
			SOUND_FACTORY.playBackgroundMusic();


		} else if (setting.equals(GAMEPLAYER_PROPERTIES.get("pauseMusic"))) {
			SOUND_FACTORY.pauseBackgroundMusic();
		} else if (setting.equals(GAMEPLAYER_PROPERTIES.get("instructions"))) {
			BrowserPopup pop = new BrowserPopup(GAMEPLAYER_PROPERTIES.get("instrURL"), GAMEPLAYER_PROPERTIES);
			pop.makePopupBrowser();
		} else if (setting.equals(GAMEPLAYER_PROPERTIES.get("help"))) {
			BrowserPopup pop = new BrowserPopup(GAMEPLAYER_PROPERTIES.get("helpURL"), GAMEPLAYER_PROPERTIES);
			pop.makePopupBrowser();
		}
	}


	/**
	 * Attaches listener which trigger automatic GamePlayer updates to the Engine's currency, score and health
	 * Additionally synchronizes the initial display value of each to the passed values
	 * @param myCurrency	Engine's currency object
	 * @param myScore	Engine's score object
	 * @param myLives	Engine's lives object
	 */
	public void attachListeners(IntegerProperty myCurrency, IntegerProperty myScore, IntegerProperty myLives) {
		myCurrency.addListener(TOWER_PANEL.createCurrencyListener(myCurrency.get()));
		myScore.addListener(SCORE_PANEL.createScoreListener(myScore.get()));
		myLives.addListener(SCORE_PANEL.createHealthListener(myLives.get()));
	}


	public void updateLevel(Integer newLevel) {
		SCORE_PANEL.updateLevel(newLevel);
	}


	/**
	 * Method to place a tower at a specified place on the GAME_PANEL
	 * @param tower
	 * @param position
	 * @return
	 * @throws CannotAffordException
	 */
	public FrontEndTower placeTower(FrontEndTower tower, Point position) throws CannotAffordException {
		return MEDIATOR.placeTower(position, tower.getName());
	}


	/**
	 * Method to show the available upgrades for a selected tower, along with its statistics. Handles the
	 * replacement of the CONTROL_PANEL to the TOWER_INFO_PANEL and sets the UPGRADE_PANEL to the bottom of the GAME_SCREEN.
	 * Also allows the user to control the tower with W,A,S,D
	 * @param tower
	 */
	public void towerClickedOn(FrontEndTower tower) {
		SCREEN_MANAGER.moveTower(tower);
		TowerInfoPanel TOWER_INFO_PANEL = new TowerInfoPanel(this,PROMPTS,tower);
		UPGRADE_PANEL = new UpgradePanel(this, tower);
		displayPane.setBottom(TOWER_INFO_PANEL.getPanel());
		gamePane.setBottom(UPGRADE_PANEL.getPanel());
	}

	/**
	 * Method to show the information about a specific upgrade when it is selected in the UPGRADE_PANEL. Replaces the TOWER_INFO_PANEL
	 * with the BUY_PANEL.
	 * @param tower
	 * @param upgradeName
	 */
	public void upgradeClickedOn(FrontEndTower tower, String upgradeName) {
		BuyPanel BUY_PANEL = new BuyPanel(this,PROMPTS, tower,upgradeName);
		displayPane.setBottom(BUY_PANEL.getPanel());
		gamePane.setBottom(UPGRADE_PANEL.getPanel());
	}

	/**
	 * Method to show the settings panel when the settings button is clicked in the CONTROLS_PANEL. Replaces the CONTROLS_PANEL
	 * with the SETTINGS_PANEL.
	 */
	private void settingsClickedOn() {
		SettingsPanel SETTINGS_PANEL = new SettingsPanel(this);
		displayPane.setBottom(SETTINGS_PANEL.getPanel());
	}

	/**
	 * Method to reset the bottom panel back to the CONTROLS_PANEL. This is accomplished when the user clicks anywhere
	 * on the GAME_PANEL that is not a tower.
	 */
	public void blankGamePanelClick() {
		gamePane.setBottom(null);
		displayPane.setBottom(CONTROLS_PANEL.getPanel());
	}

	/**
	 * Method to access the MEDIATOR to sell a tower, remove it from the screen, and update the player's currency.
	 * @param tower
	 */
	public void sellTower(FrontEndTower tower) {
		GAME_PANEL.removeTower(tower);
		MEDIATOR.sellTower(tower);
		blankGamePanelClick();
	}


	/**
	 * Method to set up the path of the game in the GAME_PANEL
	 * @param imageMap
	 * @param backgroundImageFilePath
	 * @param pathSize
	 * @param width
	 * @param height
	 * @return
	 */
	public boolean setPath(Map<String, List<Point>> imageMap, String backgroundImageFilePath, int pathSize, int width, int height) {
		return GAME_PANEL.setPath(imageMap, backgroundImageFilePath, pathSize, width, height);
	}


	/**
	 * Method to Swap the Vertical panels on the side to the left side of the screen.
	 */
	private void setVertPanelsLeft() {
		rootPane.getChildren().remove(displayPane);
		rootPane.setRight(null);
		rootPane.setLeft(displayPane);

	}

	/**
	 * Method to Swap the Vertical panels on the side to the right side of the screen.
	 */
	private void setVertPanelsRight() {
		rootPane.getChildren().remove(displayPane);
		rootPane.setLeft(null);
		rootPane.setRight(displayPane);
	}

	/**
	 * Method implemented by the swap button to swap the vertical panels from side to side at the user's discretion.
	 */
	public void swapVertPanel() {
		if(rootPane.getRight() == null) {
			setVertPanelsRight();
		}
		else {
			setVertPanelsLeft();
		}
	}


	public String getGameName() {
		return SCREEN_MANAGER.getGameFilePath();
	}


	public ScreenManager getScreenManager() {
		return SCREEN_MANAGER;
	}

	/**
	 * Method to inform the backend that an upgrade has been bought.
	 * @param tower
	 * @param upgradeName
	 */
	public void upgradeBought(FrontEndTower tower, String upgradeName) {
		MEDIATOR.upgradeTower(tower, upgradeName);
	}


	public SoundFactory getSoundFactory() {
		return SOUND_FACTORY;
	}

	public Map<String,String> getGameplayerProperties() {
		return GAMEPLAYER_PROPERTIES;
	}

	/**
	 * Method to replace the GAME_SCREEN with an appropriate SPLASH_PANEL on a game win
	 */
	public void gameWon() {
		SplashPanel SPLASH_PANEL = new SplashPanel(this, GAMEPLAYER_PROPERTIES.get("gameWon"));
		gamePane.setCenter(SPLASH_PANEL.getPanel());
	}

	/**
	 * Method to replace the GAME_SCREEN with an appropriate SPLASH_PANEL on a game loss
	 */
	public void gameLost() {
		SplashPanel SPLASH_PANEL = new SplashPanel(this,GAMEPLAYER_PROPERTIES.get("gameLost"));
		gamePane.setCenter(SPLASH_PANEL.getPanel());
	}

	/**
	 * Method to replace the GAME_SCREEN with an appropriate SPLASH_PANEL on the completion of a level
	 */
	public void nextLevel() {
		SplashPanel SPLASH_PANEL = new SplashPanel(this, GAMEPLAYER_PROPERTIES.get("nextLevel"));
		gamePane.setCenter(SPLASH_PANEL.getPanel());
		MEDIATOR.pause();
		SPLASH_PANEL.getPanel().setOnMouseClicked(arg0 -> newLevel());

	}

	/**
	 * Method to be called in nextLevel() to pause the game and replace the splash with the GAME_PANEL
	 */
	private void newLevel() {
		gamePane.setCenter(GAME_PANEL.getPanel());
		MEDIATOR.play();

	}

	/**
	 * Method to be called at the beginning of a game so the welcome splash is replaced with the GAME_PANEL.
	 */
	private void gameStart() {
		gamePane.setCenter(GAME_PANEL.getPanel());
		MEDIATOR.play();
	}

//	public String getInstructions() {
//		return MEDIATOR.getInstructions();
//	}

}
