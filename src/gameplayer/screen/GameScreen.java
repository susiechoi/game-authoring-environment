package gameplayer.screen;

import authoring.AuthoringModel;
import authoring.frontend.exceptions.MissingPropertiesException;
import controller.PlayController;
import java.awt.Point;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import authoring.AuthoringController;
import engine.Mediator;
import engine.sprites.FrontEndSprite;
import engine.sprites.towers.CannotAffordException;
import engine.sprites.towers.FrontEndTower;
import frontend.PromptReader;
import frontend.Screen;
import frontend.View;
import gameplayer.ScreenManager;
import gameplayer.panel.BuyPanel;
import gameplayer.panel.ControlsPanel;
import gameplayer.panel.GamePanel;
import gameplayer.panel.ScorePanel;
import gameplayer.panel.SettingsPanel;
import gameplayer.panel.TowerInfoPanel;
import gameplayer.panel.TowerPanel;
import gameplayer.panel.UpgradePanel;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import sound.ITRTSoundFactory;


public class GameScreen extends Screen {

	//TODO delete this and re-factor to abstract
	private static final String DEFAULT_SHARED_STYLESHEET = "styling/jungleTheme.css";

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
	private ITRTSoundFactory SOUND_FACTORY;
	private Map<String,String> GAMEPLAYER_PROPERTIES;

	public GameScreen(ScreenManager ScreenController, PromptReader promptReader, Mediator mediator) {
		SCREEN_MANAGER = ScreenController;
		GAMEPLAYER_PROPERTIES = SCREEN_MANAGER.getGameplayerProperties();
		SOUND_FACTORY = new ITRTSoundFactory();
		PROMPTS = promptReader;
		MEDIATOR = mediator;
		TOWER_PANEL = new TowerPanel(this);
		CONTROLS_PANEL = new ControlsPanel(this, PROMPTS);
		SCORE_PANEL = new ScorePanel(this);
		GAME_PANEL = new GamePanel(this);
		//TODO the null argument on creation is terrible, needs to change once
		//actual functionality of panels is changed
	}


	@Override
	public Parent makeScreenWithoutStyling() {
		rootPane = new BorderPane();

		displayPane = new BorderPane();
		displayPane.setCenter(TOWER_PANEL.getPanel());
		displayPane.setBottom(CONTROLS_PANEL.getPanel());
		VBox.setVgrow(TOWER_PANEL.getPanel(), Priority.ALWAYS);

		gamePane = new BorderPane();
		gamePane.setMaxWidth(Double.MAX_VALUE);
		gamePane.setMaxHeight(Double.MAX_VALUE);


		gamePane.setTop(SCORE_PANEL.getPanel());
		gamePane.setCenter(GAME_PANEL.getPanel());

		rootPane.setId(GAMEPLAYER_PROPERTIES.get("GameScreenRootID"));
		rootPane.setCenter(gamePane);
		setVertPanelsLeft();

		rootPane.getStylesheets().add(DEFAULT_SHARED_STYLESHEET);
//		rootPane.getStylesheets().add(MEDIATOR.getStyling());
		//rootPane.getStylesheets().add(DEFAULT_ENGINE_STYLESHEET);
		return rootPane;
	}

	public void towerSelectedForPlacement(FrontEndTower tower) {
		GAME_PANEL.towerSelected(tower);
	}

	//	public void setStyling() {
	//		String style = MEDIATOR.getStyling();
	//		if (style != null) {
	//			rootPane.getStylesheets().add(style);
	//		}
	//	}

	@Override
	protected View getView() {
		return SCREEN_MANAGER;
	}

	public void displaySprite(FrontEndSprite sprite) {
		GAME_PANEL.addSprite(sprite);
	}

	public void remove(FrontEndSprite sprite) {
		GAME_PANEL.removeSprite(sprite);
	}

	public void setAvailbleTowers(List<FrontEndTower> availableTowers) {
		TOWER_PANEL.setAvailableTowers(availableTowers);
	}

	public void loadErrorScreen(String message) {
		SCREEN_MANAGER.loadErrorScreen(message);
	}

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
		else if(control.equals(GAMEPLAYER_PROPERTIES.get("quit"))) //WHY DO I HAVE TO MAKE A NEW PLAY-CONTROLLER OH MY GOD
		    getView().playControllerInstructions(new AuthoringModel());
		else if (control.equals(GAMEPLAYER_PROPERTIES.get("quit"))) { // Susie added this
			MEDIATOR.endLoop();
			AuthoringController authoringController = new AuthoringController(SCREEN_MANAGER.getStageManager(), SCREEN_MANAGER.getLanguage());
			authoringController.setModel(SCREEN_MANAGER.getGameFilePath());
		}
		else if (control.equals(GAMEPLAYER_PROPERTIES.get("settings"))) {
			settingsClickedOn();
		}
	}

	public void settingsTriggered(String setting) {
		if (setting.equals(GAMEPLAYER_PROPERTIES.get("volumeToggle"))) {
			SOUND_FACTORY.mute();
		}
		else if (setting.equals(GAMEPLAYER_PROPERTIES.get("playMusic"))) {
			try{
				SOUND_FACTORY.setBackgroundMusic("epic");
			}
			catch (FileNotFoundException e) {

			}
			SOUND_FACTORY.playBackgroundMusic();


		}
		else if (setting.equals(GAMEPLAYER_PROPERTIES.get("pauseMusic"))) {
			SOUND_FACTORY.pauseBackgroundMusic();
		}
		else if (setting.equals(GAMEPLAYER_PROPERTIES.get("instructions"))) {

		}
		else if (setting.equals(GAMEPLAYER_PROPERTIES.get("help"))) {

		}
	}


	public void attachListeners(IntegerProperty myCurrency, IntegerProperty myScore, IntegerProperty myLives) {
		myCurrency.addListener(TOWER_PANEL.createCurrencyListener(myCurrency.get()));
		myScore.addListener(SCORE_PANEL.createScoreListener(myScore.get()));
		myLives.addListener(SCORE_PANEL.createHealthListener(myLives.get()));
	}


	public void updateLevel(Integer newLevel) {
		SCORE_PANEL.updateLevel(newLevel);
	}

	public FrontEndTower placeTower(FrontEndTower tower, Point position) throws CannotAffordException {
		return MEDIATOR.placeTower(position, tower.getName());
	}

	public void towerClickedOn(FrontEndTower tower) {
		TowerInfoPanel TOWER_INFO_PANEL = new TowerInfoPanel(this,PROMPTS,tower);
		UPGRADE_PANEL = new UpgradePanel(this, tower);
		displayPane.setBottom(TOWER_INFO_PANEL.getPanel());
		gamePane.setBottom(UPGRADE_PANEL.getPanel());
	}

	public void upgradeClickedOn(FrontEndTower tower, String upgradeName) {
		BuyPanel BUY_PANEL = new BuyPanel(this,PROMPTS, tower,upgradeName);
		displayPane.setBottom(BUY_PANEL.getPanel());
		gamePane.setBottom(UPGRADE_PANEL.getPanel());
	}

	private void settingsClickedOn() {
		SettingsPanel SETTINGS_PANEL = new SettingsPanel(this);

		displayPane.setBottom(SETTINGS_PANEL.getPanel());
	}

	public void blankGamePanelClick() {
		gamePane.setBottom(null);
		displayPane.setBottom(CONTROLS_PANEL.getPanel());
	}

	public void sellTower(FrontEndTower tower) {
		GAME_PANEL.removeTower(tower);
		MEDIATOR.sellTower(tower);
		blankGamePanelClick();
	}


	public void setPath(Map<String, List<Point>> imageMap, String backgroundImageFilePath, int pathSize) {
		GAME_PANEL.setPath(imageMap, backgroundImageFilePath, pathSize);
	}

	private void setVertPanelsLeft() {
		rootPane.getChildren().remove(displayPane);
		rootPane.setRight(null);
		rootPane.setLeft(displayPane);

	}
	private void setVertPanelsRight() {
		rootPane.getChildren().remove(displayPane);
		rootPane.setLeft(null);
		rootPane.setRight(displayPane);
	}

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

	public void upgradeBought(FrontEndTower tower, String upgradeName) {
		MEDIATOR.upgradeTower(tower, upgradeName);
	}


	public ITRTSoundFactory getSoundFactory() {
		return SOUND_FACTORY;
	}

	public Map<String,String> getGameplayerProperties() {
		return GAMEPLAYER_PROPERTIES;
	}
}
