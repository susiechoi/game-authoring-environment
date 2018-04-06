package gameplayer.panel;

import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import java.util.List;
import java.util.Map;

import frontend.PropertiesReader;
import frontend.UIFactory;
import authoring.frontend.exceptions.MissingPropertiesException;
import gameplayer.PromptReader;
import gameplayer.screen.GameScreen;


public class TowerPanel extends Panel {

    //TODO read this from settings or properties file, even better would be autoscaling to fit space
    private final int TOWER_IMAGE_SIZE = 70;

    private BorderPane PANE;
    private PromptReader PROMPTS;
    private GameScreen GAME_SCREEN;
    private PropertiesReader PROP_READ;
    private Integer money;
    private final UIFactory UIFACTORY;
    private Panel bottomPanel;

    //TODO change to only use availibleTowers
    private final String TOWER_NAMES_FILE_PATH = "images/TowerImageNames.properties"; 

    //private final FileIO FILE_READER;

    private final String[] Button_IDS = {}; //How should we create the buttons for selecting towers since there are so many?


    public TowerPanel(BorderPane pane, GameScreen gameScreen, PromptReader promptReader) {
	PANE = pane;
	GAME_SCREEN = gameScreen;
	PROMPTS = promptReader;
	PROP_READ = new PropertiesReader();
	UIFACTORY = new UIFactory();
	money = GAME_SCREEN.getMoney();
    }


    @Override
    public void makePanel() {
	List<Button> buttons = makeButtons();

	VBox towerHolderLeft = new VBox();
	VBox towerHolderRight = new VBox();

	fillScrollWithTowers(towerHolderLeft,towerHolderRight);

	towerHolderLeft.setFillWidth(true);
	towerHolderRight.setFillWidth(true);

	HBox fullTowerHold = new HBox(towerHolderLeft,towerHolderRight);
	//TODO need to check if this static stuff is okay
	HBox.setHgrow(towerHolderRight, Priority.ALWAYS);
	HBox.setHgrow(towerHolderLeft, Priority.ALWAYS);

	fullTowerHold.setAlignment(Pos.CENTER);
	ScrollPane towerDisplay = new ScrollPane(fullTowerHold);
	towerDisplay.setFitToWidth(true); //makes hbox take full width of scrollpane

	Button currencyDisplay = new Button();
	currencyDisplay.setId("currencyButton");
	currencyDisplay.setText("$" +money.toString());
	currencyDisplay.setDisable(true);
	currencyDisplay.setMaxWidth(Double.MAX_VALUE);;
	VBox towersAndCurr = new VBox(towerDisplay,currencyDisplay);
	VBox.setVgrow(towerDisplay, Priority.ALWAYS);
	towersAndCurr.setAlignment(Pos.CENTER);


	//might want to remove this as control implementation changes but we'll see



	//  panelRoot.getChildren().addAll(buttons);
	towersAndCurr.setId("towerPanel");
	PANEL = towersAndCurr;
	//PANEL = panelRoot; In y'all's panel class for Slogo you had a protected PANEL variable in the abstract panel class, but we
	//can't do that with interfaces. How would you want to approach that?
    }

    private void fillScrollWithTowers(VBox towerHolderLeft, VBox towerHolderRight) {
	VBox towerHolder;
	try {
	    Map<String, Image> towerMap = PROP_READ.keyToImageMap(TOWER_NAMES_FILE_PATH, TOWER_IMAGE_SIZE,TOWER_IMAGE_SIZE);
	    int alternator = 0;
	    for(String towerType : towerMap.keySet()) {
		Button towerButton = UIFACTORY.makeImageButton("button",towerMap.get(towerType));
		towerButton.setOnMouseClicked((arg0) -> GAME_SCREEN.towerSelectedForPlacement(towerType));
		if(alternator%2 == 0) {
		    towerHolder = towerHolderLeft;
		}
		else {
		    towerHolder = towerHolderRight;
		}

		towerHolder.getChildren().add(towerButton);
		VBox.setVgrow(towerButton, Priority.ALWAYS);
		towerButton.setMaxWidth(Double.MAX_VALUE);
		towerButton.setMaxHeight(Double.MAX_VALUE);
		alternator++;
	    }
	    //TODO pretty bad code that doesn't work, towers should be same height in columns
//	    if(alternator%2 == 1) {
//		Button voidButton = UIFACTORY.makeTextButton("voidButton", "");
//		towerHolderRight.getChildren().add(voidButton);
//		VBox.setVgrow(voidButton, Priority.ALWAYS);
//		voidButton.setMaxWidth(Double.MAX_VALUE);
//		voidButton.setMaxHeight(Double.MAX_VALUE);
//		voidButton.setDisable(true);
//	    }
	} catch (MissingPropertiesException e) {
	    System.out.println("PropertiesReadFailed: TowerPanel");
	    //something went wrong and we don't have the towers
	    //TODO something reasonable here
	    //probably have default images that aren't the ones specified by authoring
	}
    }

    private void towerSelected(String towerPropName) {

    }



    private List<Button> makeButtons(){
	//do some stuff
	return null;
    }


}
