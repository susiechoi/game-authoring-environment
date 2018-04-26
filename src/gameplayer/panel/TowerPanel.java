
package gameplayer.panel;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import java.util.List;
import java.util.Map;

import frontend.PromptReader;
import frontend.PropertiesReader;
import frontend.UIFactory;
import authoring.frontend.exceptions.MissingPropertiesException;
import engine.sprites.towers.FrontEndTower;
import gameplayer.screen.GameScreen;


public class TowerPanel extends Panel {

    //TODO read this from settings or properties file, even better would be autoscaling to fit space
    private final int TOWER_IMAGE_SIZE = 70;
    private final int SWAP_BUTTON_SIZE = 25;


    private Integer money;
    private BorderPane PANE;
    private PromptReader PROMPTS;
    private GameScreen GAME_SCREEN;
    private PropertiesReader PROP_READ;
    private final UIFactory UIFACTORY;
    private Panel bottomPanel;
    private Button currencyDisplay;
    private ScrollPane towerDisplay;
    private final String ASSORTED_BUTTON_FILEPATH = "src/images/GamePlayerAssorted/GamePlayerAssorted.properties";

    //TODO change to only use availibleTowers

    //private final FileIO FILE_READER;

    private HBox towerPane;


    public TowerPanel( GameScreen gameScreen, PromptReader promptReader) {
	GAME_SCREEN = gameScreen;
	PROMPTS = promptReader;
	PROP_READ = new PropertiesReader();
	UIFACTORY = new UIFactory();
	makePanel();
    }


    @Override
    public void makePanel() {

	//TODO need to check if this static stuff is okay

	towerPane = new HBox();
	towerDisplay = new ScrollPane(towerPane);
	towerDisplay.setFitToWidth(true); //makes hbox take full width of scrollpane
	towerDisplay.setFitToHeight(true); //remove this line if seen



	currencyDisplay = new Button();
	currencyDisplay.setId("currencyButton");
	currencyDisplay.setText("$" + money);
	currencyDisplay.setDisable(true);
	//currencyDisplay.setMaxWidth(Double.MAX_VALUE);


	VBox currencyAndSwap = new VBox(currencyDisplay);
	currencyAndSwap.setOnMouseClicked(e -> handleMouseInput(e.getX(), e.getY()));

	currencyAndSwap.setAlignment(Pos.CENTER);
	try {
	    Map<String, Image> buttonMap = PROP_READ.keyToImageMap(ASSORTED_BUTTON_FILEPATH, SWAP_BUTTON_SIZE, SWAP_BUTTON_SIZE);
	    Button swapButton = UIFACTORY.makeImageButton("swapButton", buttonMap.get("swap"));
	    swapButton.setOnMouseClicked((arg0) -> GAME_SCREEN.swapVertPanel());
	    HBox swapWrap = new HBox(swapButton);
	    swapWrap.setId("swapWrap");
	    swapWrap.setAlignment(Pos.CENTER_RIGHT);
	    currencyAndSwap.getChildren().add(swapWrap);
	} catch (MissingPropertiesException e) {
	    //SWAPBUTTONIMAGEMISSING
	}



	VBox towersAndCurr = new VBox(towerDisplay,currencyAndSwap);
	VBox.setVgrow(towerDisplay, Priority.ALWAYS);
	towersAndCurr.setAlignment(Pos.CENTER);

	//might want to remove this as control implementation changes but we'll see

	//  panelRoot.getChildren().addAll(buttons);
	towersAndCurr.setId("towerPanel");
	PANEL = towersAndCurr;
    }
    
    public void setInitalMoney(Integer moneyIn) {
	updateCurrency(moneyIn);
    }

    private void handleMouseInput(double x, double y) {
	GAME_SCREEN.towerSelectedForPlacement(null);
    }


    private VBox fillScrollWithTowers(List<FrontEndTower> availableTowers) {
	VBox fullTowerHold = new VBox();
	HBox towerHolder = new HBox();
	int alternator = 0;
	Button prevTowerButton = new Button();

	for(FrontEndTower tower : availableTowers) {
	    ImageView imageView = tower.getImageView();
	    imageView.setFitWidth(TOWER_IMAGE_SIZE);
	    //    imageView.setFitHeight(TOWER_IMAGE_SIZE); 
	    //    imageView.setPreserveRatio(false);
	    Button towerButton = UIFACTORY.makeImageViewButton("button",imageView);

	    towerButton.setMaxWidth(Double.MAX_VALUE);
	    towerButton.setMaxHeight(Double.MAX_VALUE);
	    towerButton.setOnMouseClicked((arg0) -> GAME_SCREEN.towerSelectedForPlacement(tower));
	    if(alternator%2 == 0) {
		towerHolder = new HBox();
		towerHolder.setFillHeight(true);

		fullTowerHold.getChildren().add(towerHolder);
		VBox.setVgrow(towerHolder, Priority.ALWAYS);

		prevTowerButton = towerButton;
	    }
	    else {
		towerButton.setPrefWidth(towerHolder.getPrefWidth());
		prevTowerButton.setPrefWidth(towerHolder.getPrefWidth());
	    }

	    towerHolder.getChildren().add(towerButton);

	    HBox.setHgrow(towerButton, Priority.ALWAYS);

	    alternator++;
	}
	if(alternator%2 ==1) {
	    ImageView voidView = new ImageView();
	    voidView.setFitWidth(TOWER_IMAGE_SIZE);
	    //	    voidView.setFitHeight(TOWER_IMAGE_SIZE);
	    Button voidButton = UIFACTORY.makeImageViewButton("button", voidView);
	    voidButton.setOnMouseClicked((arg0) ->{ 
		GAME_SCREEN.towerSelectedForPlacement(null);
		System.out.println("nullhit");

	    });

	    voidButton.setGraphic(voidView);

	    voidButton.setMaxWidth(Double.MAX_VALUE);
	    voidButton.setMaxHeight(Double.MAX_VALUE);
	    // voidButton.setDisable(true);

	    towerHolder.getChildren().add(voidButton);
	    HBox.setHgrow(voidButton, Priority.ALWAYS);
	}



	fullTowerHold.setAlignment(Pos.CENTER);
	//fullTowerHold.setMaxWidth(Double.MAX_VALUE);

	return fullTowerHold;
    }



    public void setAvailableTowers(List<FrontEndTower> availableTowers) {
	towerPane.getChildren().clear();

	VBox filledWithTowers = fillScrollWithTowers(availableTowers);

	towerPane.getChildren().add(filledWithTowers);
	HBox.setHgrow(filledWithTowers, Priority.ALWAYS);
	//filledWithTowers.prefWidthProperty().bind(towerDisplay.prefWidthProperty());
	//towerPane.prefWidthProperty().bind(towerDisplay.prefWidthProperty());

    }

    private void updateCurrency(Integer newValue) {
	currencyDisplay.setText("$" +newValue);
    }

    public ChangeListener createCurrencyListener() {
	ChangeListener changeListener = new ChangeListener() {
	    @Override
	    public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
		updateCurrency((Integer)observableValue.getValue());
	    }
	};
	return changeListener;

    }
}

