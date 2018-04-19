package gameplayer.panel;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import authoring.frontend.exceptions.MissingPropertiesException;
import engine.sprites.FrontEndSprite;
import engine.sprites.towers.CannotAffordException;
import engine.sprites.towers.FrontEndTower;
import frontend.PropertiesReader;
import gameplayer.screen.GameScreen;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;

public class GamePanel extends Panel{


	private final GameScreen GAME_SCREEN;
	private FrontEndTower towerSelected;
	private boolean towerPlaceMode = false;
	private List<FrontEndTower> towersPlaced;
	private Pane spriteAdd;
	private Boolean towerClick = false;

	//TODO changes this to be passed from mediator
	private final String BACKGROUND_FILE_PATH = "images/BackgroundImageNames.properties";

	public GamePanel(GameScreen gameScreen) {
		GAME_SCREEN = gameScreen;
		towersPlaced = new ArrayList<FrontEndTower>();
	}

	@Override
	public void makePanel() {

		//TODO potentially fix needed?

		Pane gamePane = new Pane();
		ScrollPane panelRoot = new ScrollPane(gamePane);
		gamePane.setId("gamePanel");
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
			Map<String, Image> backgroundMap = propReader.keyToImageMap(BACKGROUND_FILE_PATH, 1020.0, 650.0);
			int random = rand.nextInt(backgroundMap.size());
			int count = 0;
			for(String s:  backgroundMap.keySet()) {
				if(s.equals("general")) {
					ImageView imageView = new ImageView();
					imageView.setImage(backgroundMap.get(s));
					gamePane.getChildren().add(imageView);
				}
			}
		} catch (MissingPropertiesException e1) {
			//TODO should fix but who cares since this will be refactored
			//to be gotten from mediator
			System.out.println("Background Images failed to load");
		}
	}


	public void setPath(Map<String, List<Point>> imageMap, String backgroundImageFilePath, int pathSize) {
		PathMaker pathMaker = new PathMaker();
		GridPane grid = pathMaker.initGrid(imageMap, backgroundImageFilePath, pathSize);
		//	setGridConstraints(grid, imageMap);
		spriteAdd.getChildren().add(grid);
	}

	private void resetCursor() {
		GAME_SCREEN.getScreenManager().getStageManager().getScene().setCursor(Cursor.DEFAULT);
		spriteAdd.setOnMouseEntered(e -> GAME_SCREEN.getScreenManager().getStageManager().getScene().setCursor(Cursor.DEFAULT));
	}

	public void towerSelected(FrontEndTower tower) {
		towerSelected = tower;
		towerPlaceMode = true;
		ImageCursor cursor = new ImageCursor(tower.getImageView().getImage());
		spriteAdd.setOnMouseEntered(e -> GAME_SCREEN.getScreenManager().getStageManager().getScene().setCursor(cursor));
		spriteAdd.setOnMouseExited(e -> GAME_SCREEN.getScreenManager().getStageManager().getScene().setCursor(Cursor.DEFAULT));
	}

	private void addTowerImageViewAction(FrontEndTower tower) {
		ImageView towerImage = tower.getImageView();
		towerImage.setOnMouseClicked((args) ->{
			GAME_SCREEN.towerClickedOn(tower);
			towerClick = true;
		});
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

				towerImage.setLayoutX(-towerImageActual.getWidth()/2);
				towerImage.setLayoutY(-towerImageActual.getHeight()/2);


				if(newTower!= null) {

					addTowerImageViewAction(newTower);
					towersPlaced.add(newTower);
					spriteAdd.getChildren().add(towerImage);
					resetCursor();
					towerPlaceMode = false;
				}
			}
			catch(CannotAffordException e){
				//GameScreen popup for cannot afford
			}
		}
		else if(!towerClick) {
			GAME_SCREEN.blankGamePanelClick();
		}
		towerClick = false;
	}
}
