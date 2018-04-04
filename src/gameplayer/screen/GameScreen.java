package gameplayer.screen;

import gameplayer.panel.TowerPanel;
import gameplayer.panel.GamePanel;
import gameplayer.panel.ScorePanel;
import gameplayer.panel.ControlsPanel;
import authoring.frontend.UIFactory;
import gameplayer.ScreenManager;


import javafx.scene.Node;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;



public class GameScreen extends Screen {

    private Parent ROOT;
    private final UIFactory UIFACTORY;
    private TowerPanel TOWER_PANEL;
    private GamePanel GAME_PANEL;
    private ScorePanel SCORE_PANEL;
    private ControlsPanel CONTROLS_PANEL;
    private ScreenManager SCREEN_MANAGER;

    public GameScreen(ScreenManager ScreenController) {
        SCREEN_MANAGER = ScreenController;
        UIFACTORY = new UIFactory();

    }

    @Override
    public void makeScreen() {
        BorderPane rootPane = new BorderPane();
        rootPane.setId("gameScreenRoot"); //Where is this set up / where does it get the gameScreenRoot from?
        rootPane.setRight(new TowerPanel(rootPane, this).getPanel());
        rootPane.setTop(new ScorePanel(this).getPanel());
        rootPane.setCenter(new GamePanel(this).getPanel());
        ROOT = rootPane;
    }

    @Override
    public Parent getScreenRoot(){
        if (ROOT == null) {
            makeScreen();
        }
        return ROOT;
    }
    
    public void towerSelectedForPlacement(String towerPropName) {
	
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
    
    //TODO make this method do things
    public void controlHit(String control) {
	if(control.equals("play")) {
	    //do play stuff
	    System.out.println(control);
	}
	else if(control.equals("pause")) {
	    //do pause stuff
	    System.out.println(control);
	}
	else
	    System.out.println(control);
    }


}
