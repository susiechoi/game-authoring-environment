package gameplayer.screen;

import gameplayer.panel.TowerPanel;
import gameplayer.panel.UpgradePanel;
import gameplayer.panel.GamePanel;
import gameplayer.panel.ScorePanel;
import gameplayer.panel.TowerInfoPanel;
import gameplayer.panel.BuyPanel;
import gameplayer.panel.ControlsPanel;

import java.awt.Point;
import java.util.List;
import java.util.Map;

import engine.Mediator;
import engine.sprites.FrontEndSprite;
import engine.sprites.towers.CannotAffordException;
import engine.sprites.towers.FrontEndTower;
import frontend.PromptReader;
import frontend.Screen;
import frontend.UIFactory;
import frontend.View;
import gameplayer.ScreenManager;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;



public class GameScreen extends Screen {

    //TODO delete this and re-factor to abstract
    private  final String DEFAULT_SHARED_STYLESHEET = "styling/SharedStyling.css";
    private  final String DEFAULT_ENGINE_STYLESHEET = "styling/EngineFrontEnd.css";


    private final UIFactory UIFACTORY;
    private final PromptReader PROMPTS;
    private TowerPanel TOWER_PANEL;
    private TowerInfoPanel TOWER_INFO_PANEL;
    private GamePanel GAME_PANEL;
    private ScorePanel SCORE_PANEL;
    private ControlsPanel CONTROLS_PANEL;
    private UpgradePanel UPGRADE_PANEL;
    private ScreenManager SCREEN_MANAGER;
    private BuyPanel BUY_PANEL;
    private VBox rightPane;
    private final Mediator MEDIATOR;


    public GameScreen(ScreenManager ScreenController, PromptReader promptReader, Mediator mediator) {
	SCREEN_MANAGER = ScreenController;
	UIFACTORY = new UIFactory();
	PROMPTS = promptReader;
	MEDIATOR = mediator;

    }

    @Override
    public Parent makeScreenWithoutStyling() {
	BorderPane rootPane = new BorderPane();
	TOWER_PANEL = new TowerPanel(this, PROMPTS);
	CONTROLS_PANEL = new ControlsPanel(this);
	SCORE_PANEL = new ScorePanel(this);
	GAME_PANEL = new GamePanel(this);
	UPGRADE_PANEL = new UpgradePanel(this, PROMPTS);
	BUY_PANEL = new BuyPanel(this, PROMPTS);



	rightPane = new VBox(TOWER_PANEL.getPanel(), CONTROLS_PANEL.getPanel());
	VBox.setVgrow(TOWER_PANEL.getPanel(), Priority.ALWAYS);

	BorderPane leftPane = new BorderPane();
	leftPane.setMaxWidth(Double.MAX_VALUE);
	leftPane.setMaxHeight(Double.MAX_VALUE);

	leftPane.setTop(SCORE_PANEL.getPanel());
	leftPane.setCenter(GAME_PANEL.getPanel());
	leftPane.setBottom(UPGRADE_PANEL.getPanel());

	rootPane.setId("gameScreenRoot"); //Where is this set up / where does it get the gameScreenRoot from?
		rootPane.setCenter(leftPane);
	rootPane.setRight(rightPane);

	rootPane.getStylesheets().add(DEFAULT_SHARED_STYLESHEET);
	rootPane.getStylesheets().add(DEFAULT_ENGINE_STYLESHEET);
	return rootPane;
    }

    public void towerSelectedForPlacement(FrontEndTower tower) {
	GAME_PANEL.towerSelected(tower);
    }

    public Integer getMoney() {
	//TODO call ObserveHandler.triggerEvent(NeedMoney) to get money sent from playState
	/**
	 * also might implement money tracking by passing Integer object of 
	 * currency from playState in initialization of GameScreen/TowerPanel
	 * 	-if this is the case this method isn't needed and an updateCurrency Method 
	 * 	should instead be called in towerPanel upon any action which would spend currency 
	 */
	Integer money = 1000; //placeholder
	return money;
    }


    @Override
    protected View getView() {
	// TODO Auto-generated method stub
	return null;
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
    public void controlTriggered(String control) {
	if(control.equals("play"))
	    MEDIATOR.play();
	else if(control.equals("pause"))
	    MEDIATOR.pause();
	else if(control.equals("speedup"))
	    MEDIATOR.fastForward(10);    
    }
    
    public void updateCurrency(Integer newBalence) {
	TOWER_PANEL.updateCurrency(newBalence);
    }
    
    public void updateHealth(Integer newHealth) {
	SCORE_PANEL.updateHealth(newHealth);
    }
    
    public void updateScore(Integer newScore) {
	SCORE_PANEL.updateScore(newScore);
    }
    
    public void updateLevel(Integer newLevel) {
	SCORE_PANEL.updateLevel(newLevel);
    }
    
    public FrontEndTower placeTower(FrontEndTower tower, Point position) throws CannotAffordException {
	FrontEndTower placedTower = MEDIATOR.placeTower(position, tower.getName());
	System.out.println(placedTower.getImageView().getFitWidth() + " width ");
	return placedTower;
    }
    
    public void towerClickedOn(FrontEndTower tower) {
	TOWER_INFO_PANEL = new TowerInfoPanel(this,PROMPTS,tower);
	rightPane.getChildren().clear();
	rightPane.getChildren().addAll(TOWER_PANEL.getPanel(), TOWER_INFO_PANEL.getPanel());
    }
    
    public void sellTower(FrontEndTower tower) {
	GAME_PANEL.removeTower(tower);
	MEDIATOR.sellTower(tower);
    }
    
    public void setPath(Map<String, List<Point2D>> imageMap, int numRow, int numCol) {
	
    }
    
    
    
}
