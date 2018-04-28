package gameplayer.panel;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

import com.sun.javafx.tools.packager.Log;

import frontend.PropertiesReader;
import frontend.UIFactory;
import authoring.frontend.exceptions.MissingPropertiesException;
import engine.sprites.towers.FrontEndTower;
import file.DataPointWriter;
import gameplayer.screen.GameScreen;

public class TowerPanel extends ListenerPanel {
	
	public static final String DEFAULT_SUBFOLDER_FILEPATH = "Currency/";
	
    //TODO read this from settings or properties file, even better would be autoscaling to fit space
    private int TOWER_IMAGE_SIZE;

    private GameScreen GAME_SCREEN;
    private Map<String,String> GAMEPLAYER_PROPERTIES;
    private PropertiesReader PROP_READ;
    private final UIFactory UIFACTORY;
    private Button currencyDisplay;

    private DataPointWriter myCurrencyWriter; 
    
    //TODO change to only use availibleTowers
    //private final FileIO FILE_READER;
    private HBox towerPane;


    public TowerPanel(GameScreen gameScreen) {
	GAME_SCREEN = gameScreen;
	GAMEPLAYER_PROPERTIES = GAME_SCREEN.getGameplayerProperties();
	//	money = Integer.parseInt(GAMEPLAYER_PROPERTIES.get("defaultMoney"));
	PROP_READ = new PropertiesReader();
	UIFACTORY = new UIFactory();

	setupWriters(); 
    }
    
    private void setupWriters() {
    	try {
			myCurrencyWriter = new DataPointWriter(GAME_SCREEN.getGameName(), DEFAULT_SUBFOLDER_FILEPATH);
		} catch (FileNotFoundException e) {
			GAME_SCREEN.loadErrorScreen("NoFile");
		} 
    }


    @Override
    public void makePanel() {
	TOWER_IMAGE_SIZE = Integer.parseInt(GAMEPLAYER_PROPERTIES.get("TowerImageSize"));
	int swapButtonSize = Integer.parseInt(GAMEPLAYER_PROPERTIES.get("SwapButtonSize"));
	String assortedButtonFilePath = GAMEPLAYER_PROPERTIES.get("AssortedButtonFilepath");

	towerPane = new HBox();
	ScrollPane towerDisplay = new ScrollPane(towerPane);

	towerDisplay.setFitToWidth(true); //makes hbox take full width of scrollpane
	towerDisplay.setFitToHeight(true); //remove this line if seen
	//towerDisplay.setMaxHeight(Double.MAX_VALUE);

	currencyDisplay = new Button();
	currencyDisplay.setId(GAMEPLAYER_PROPERTIES.get("currencyButton"));
	currencyDisplay.setText(GAMEPLAYER_PROPERTIES.get("currencyText"));
	currencyDisplay.setDisable(true);
	//currencyDisplay.setMaxWidth(Double.MAX_VALUE);


	VBox currencyAndSwap = new VBox(currencyDisplay);
	currencyAndSwap.setOnMouseClicked(e -> handleMouseInput(e.getX(), e.getY()));

	currencyAndSwap.setAlignment(Pos.CENTER);
	try {

	    Map<String, Image> buttonMap = PROP_READ.keyToImageMap(assortedButtonFilePath, swapButtonSize, swapButtonSize);
	    Button swapButton = UIFACTORY.makeImageButton(GAMEPLAYER_PROPERTIES.get("swapButtonID"), buttonMap.get(GAMEPLAYER_PROPERTIES.get("swapButton")));
	    swapButton.setOnMouseClicked(arg0 -> GAME_SCREEN.swapVertPanel());
	    swapButton.setTooltip(new Tooltip(GAMEPLAYER_PROPERTIES.get("swapTooltip"))); //TODO make properties file
	    HBox swapWrap = new HBox(swapButton);
	    swapWrap.setId(GAMEPLAYER_PROPERTIES.get("swapWrapID"));
	    swapWrap.setAlignment(Pos.CENTER_RIGHT);
	    currencyAndSwap.getChildren().add(swapWrap);
	} catch (MissingPropertiesException e) {
	    Log.debug(e);
	    System.out.println("SwapButton Image Missing"); //TODO!!!
	}



	VBox towersAndCurr = new VBox(towerDisplay,currencyAndSwap);
	VBox.setVgrow(towerDisplay, Priority.ALWAYS);
	towersAndCurr.setAlignment(Pos.CENTER);

	//might want to remove this as control implementation changes but we'll see
	//  panelRoot.getChildren().addAll(buttons);
	towersAndCurr.setId(GAMEPLAYER_PROPERTIES.get("towerPanelID"));
	PANEL = towersAndCurr;
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
	    ImageView imageView = tower.getImageView(); // Currently creating null imageview
	    imageView.setFitWidth(TOWER_IMAGE_SIZE);
	    //    imageView.setFitHeight(TOWER_IMAGE_SIZE); 
	    //    imageView.setPreserveRatio(false);
	    Button towerButton = UIFACTORY.makeImageViewButton(GAMEPLAYER_PROPERTIES.get("buttonID"),imageView);

	    towerButton.setMaxWidth(Double.MAX_VALUE);
	    towerButton.setMaxHeight(Double.MAX_VALUE);
	    towerButton.setOnMouseClicked(arg0 -> GAME_SCREEN.towerSelectedForPlacement(tower));
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
	    Button voidButton = UIFACTORY.makeImageViewButton(GAMEPLAYER_PROPERTIES.get("buttonID"), voidView);
	    voidButton.setOnMouseClicked(arg0 ->{ 

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
    	myCurrencyWriter.recordDataPoint(newValue);
	currencyDisplay.setText(GAMEPLAYER_PROPERTIES.get("currencyText") +newValue);
    }

    /**
     * Wrapper method on score to reduce order of call dependencies
     * @param score	initial health of the level
     */
    private void setInitialCurrency(int currency) {
	checkForPanelCreation(currencyDisplay);
	updateCurrency(currency);
    }


    public ChangeListener<Number> createCurrencyListener(int startCurrency) {
	setInitialCurrency(startCurrency);
	return new ChangeListener<Number>() {
	    @Override
	    public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
		updateCurrency((Integer)arg0.getValue());	
	    }
	};
    }
}
