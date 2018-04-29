package gameplayer.panel;

import java.awt.Point;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.sun.javafx.tools.packager.Log;

import java.util.Map.Entry;

import authoring.frontend.exceptions.MissingPropertiesException;
import engine.sprites.FrontEndSprite;
import engine.sprites.towers.CannotAffordException;
import engine.sprites.towers.FrontEndTower;
import frontend.PropertiesReader;
import gameplayer.screen.GameScreen;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.shape.Circle;

/**
 * @Author Alexi Kontos & Andrew Arnold
 */



//note to andrew: delete if you see this not on his branch
//need two seperate rangeIndicators, one for on click of placed tower, one for display when placing when cursor is changed
//as is if you click on tower, the mouseonexit method will make range disappear if you leave panel
public class GamePanel extends Panel{


    private final GameScreen GAME_SCREEN;
    private FrontEndTower towerSelected;
    private Map<String,String> GAMEPLAYER_PROPERTIES;
    private PropertiesReader PROP_READ;
    private boolean towerPlaceMode = false;
    private Pane spriteAdd;
    private Boolean towerClick = false;
    private Circle rangeIndicator;

    //TODO changes this to be passed from mediator ******************************************************************************
    private final String BACKGROUND_FILE_PATH;
    private String CONSTANTS_FILE_PATH;

    public GamePanel(GameScreen gameScreen) {
	GAME_SCREEN = gameScreen;
	GAMEPLAYER_PROPERTIES = GAME_SCREEN.getGameplayerProperties();
	BACKGROUND_FILE_PATH = GAMEPLAYER_PROPERTIES.get("backgroundFilePath");
	PROP_READ = new PropertiesReader();
	//TODO probably a better way of doing this (thread canceling towerPlacement)
	towerSelected =  null;
	CONSTANTS_FILE_PATH = GAMEPLAYER_PROPERTIES.get("constantsFilePath");
    }

    @Override
    public void makePanel() {

	//TODO potentially fix needed?

	Pane gamePane = new Pane();
	ScrollPane panelRoot = new ScrollPane(gamePane);
	gamePane.setId(GAMEPLAYER_PROPERTIES.get("gamePanelID"));
	//panelRoot.setBottom(new Up);
	gamePane.setMaxWidth(Double.MAX_VALUE);
	gamePane.setMaxHeight(Double.MAX_VALUE);

	gamePane.setOnMouseClicked(e -> handleMouseInput(e.getX(), e.getY()));

	setBackgroundImage(gamePane);

	spriteAdd = gamePane;
	PANEL = panelRoot;
    }

    private void setBackgroundImage(Pane gamePane) {
	PropertiesReader propReader = new PropertiesReader();
	Random rand = new Random();

	try {
	    //TODO fix this hardcoding, should just expand to fill space given(don't care about scaling ************************************
	    Map<String, Image> backgroundMap = propReader.keyToImageMap(BACKGROUND_FILE_PATH, 1020.0, 650.0);
	    int random = rand.nextInt(backgroundMap.size());
	    int count = 0;

	    for(Entry<String, Image> entry : backgroundMap.entrySet()) {
		if(entry.getKey().equals(GAMEPLAYER_PROPERTIES.get("general"))) {
		    ImageView imageView = new ImageView();
		    imageView.setImage(entry.getValue());
		    gamePane.getChildren().add(imageView);
		}
	    }
	} catch (MissingPropertiesException e1) {
	    Log.debug(e1);
		e1.printStackTrace();
	}
    }

    public void setPath(Map<String, List<Point>> imageMap, String backgroundImageFilePath, int pathSize, int col, int row) {
		PathMaker pathMaker = new PathMaker();
		GridPane grid = pathMaker.initGrid(imageMap, backgroundImageFilePath, pathSize, col, row);
		//	setGridConstraints(grid, imageMap);
		if (spriteAdd == null) {
		    makePanel();
		}
		spriteAdd.getChildren().add(grid);
    }


    private void resetCursor() {
	GAME_SCREEN.getScreenManager().getStageManager().getScene().setCursor(Cursor.DEFAULT);
	spriteAdd.setOnMouseEntered(e -> GAME_SCREEN.getScreenManager().getStageManager().getScene().setCursor(Cursor.DEFAULT));
	spriteAdd.setOnMouseExited(e -> GAME_SCREEN.getScreenManager().getStageManager().getScene().setCursor(Cursor.DEFAULT));
	spriteAdd.setOnMouseMoved(null);
	removeTowerRangeIndicator();

    }

    /**
     * 
     * @param tower will be null if tower placement is canceled
     */
    public void towerSelected(FrontEndTower tower) {

	if(tower!= null && tower != towerSelected ) { //TODO (thread canceling towerPlacement)
	    towerSelected = tower;
	    ImageView towerImage = tower.getImageView();
	    towerPlaceMode = true;
	    ImageCursor cursor = new ImageCursor(tower.getImageView().getImage());

	    spriteAdd.setOnMouseEntered(e -> {
		GAME_SCREEN.getScreenManager().getStageManager().getScene().setCursor(cursor);
		addRangeIndicator(tower); });

	    spriteAdd.setOnMouseExited(e -> {
		GAME_SCREEN.getScreenManager().getStageManager().getScene().setCursor(Cursor.DEFAULT);
		spriteAdd.getChildren().remove(rangeIndicator); });

	    spriteAdd.setOnMouseMoved(e -> {
		rangeIndicator.setCenterX(e.getX()+(towerImage.getImage().getWidth()/2));
		rangeIndicator.setCenterY(e.getY()+(towerImage.getImage().getHeight()/2)); });
	} else { //TODO (thread canceling towerPlacement)
	    //maybe make a new towerContructor which creates a null tower?
	    resetCursor();
	    towerPlaceMode = false;
	}
    }

    private void addTowerImageViewAction(FrontEndTower tower) {
	ImageView towerImage = tower.getImageView();
	towerImage.setOnMouseClicked(args ->{
	    GAME_SCREEN.towerClickedOn(tower);
	    // applySelectionGlow(towerImage);
	    addRangeIndicator(tower);
	    towerClick = true;
	});
    }

    private void removeTowerRangeIndicator() {
	spriteAdd.getChildren().remove(rangeIndicator);
	towerSelected = null;
    }

    private void addRangeIndicator(FrontEndTower tower) {
	spriteAdd.getChildren().remove(rangeIndicator);
	ImageView towImage = tower.getImageView();
	//TODO replace hardcoded constant with tower's range AA
	rangeIndicator = new Circle(towImage.getX()+(towImage.getImage().getWidth()/2),
		towImage.getY()+(towImage.getImage().getHeight()/2), tower.getTowerRange());
	rangeIndicator.setStroke(Color.ORANGE);
	try {
	    String opacity = PROP_READ.findVal(CONSTANTS_FILE_PATH, "TowerRangeIndicatorOpacity");
	    rangeIndicator.setOpacity(Double.parseDouble(opacity));
	    spriteAdd.getChildren().add(rangeIndicator);
	    towImage.toFront();
	} catch (MissingPropertiesException e) {
	    Log.debug(e);
	    //TODO let's not fail please!!
	    System.out.println("Constants property file not found");
	}



    }

    //TODO delete if not used in end
    /**
     * Makes the tower glow on click, looks kinda tacky
     * @param towerImage
     */
    private void applySelectionGlow(ImageView towerImage) {
	try {
	    String glowIntensity =PROP_READ.findVal(CONSTANTS_FILE_PATH, "TowerGlowOnSelection");
	    towerImage.setEffect(new Glow(Integer.parseInt(glowIntensity)));

	} catch (MissingPropertiesException e) {
	    // TODO Auto-generated catch block
	    Log.debug(e);
	    e.printStackTrace();
	}
    }

    public void addSprite(FrontEndSprite sprite) {
	ImageView spriteImage = sprite.getImageView();
	spriteImage.setLayoutX(-spriteImage.getFitWidth()/2);
	spriteImage.setLayoutY(-spriteImage.getFitHeight()/2);
	spriteAdd.getChildren().add(sprite.getImageView());
    }

    public void removeSprite(FrontEndSprite sprite) {
	spriteAdd.getChildren().remove(sprite.getImageView());
    }

    public void removeTower(FrontEndTower tower) {
	spriteAdd.getChildren().remove(tower.getImageView());
	removeTowerRangeIndicator();
    }

    public void exitTowerPlace() {
	towerPlaceMode = false;
    }

    public void handleMouseInput(double x, double y) {
	if(towerPlaceMode) {
	    Point position = new Point((int)x,(int)y);
	    try {
		FrontEndTower newTower = GAME_SCREEN.placeTower(towerSelected, position);
		ImageView towerImage = newTower.getImageView();
		Image towerImageActual = towerImage.getImage();

		addTowerImageViewAction(newTower);
		spriteAdd.getChildren().add(towerImage);
		resetCursor();
		towerPlaceMode = false;
		//TODO (thread canceling towerPlacement) maybe make a new towerContructor which creates a null tower?

	    }
	    catch(CannotAffordException e){
		 Log.debug(e);
		 //TODO aaahhhhhhhhh
		//GameScreen popup for cannot afford
	    }
	}
	else if(!towerClick) {
	    GAME_SCREEN.blankGamePanelClick();
	    removeTowerRangeIndicator();
	}
	towerClick = false;
    }
}
