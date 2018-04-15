
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class GamePanel extends Panel{


	private final GameScreen GAME_SCREEN;
	private FrontEndTower towerSelected;
	private boolean towerPlaceMode = false;
	private List<FrontEndTower> towersPlaced;
	private Pane spriteAdd;

	//TODO changes this to be passed from mediator
	private final String BACKGROUND_FILE_PATH = "images/BackgroundImageNames.properties";


	public GamePanel(GameScreen gameScreen) {
		GAME_SCREEN = gameScreen;
		towersPlaced = new ArrayList<FrontEndTower>();
	}


	@Override
	public void makePanel() {

		//TODO potentially fix needed?


		Pane panelRoot = new Pane();
		panelRoot.setId("gamePanel");
		//panelRoot.setBottom(new Up);
		panelRoot.setMaxWidth(Double.MAX_VALUE);
		panelRoot.setMaxHeight(Double.MAX_VALUE);

		panelRoot.setOnMouseClicked(e -> handleMouseInput(e.getX(), e.getY()));


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
					panelRoot.getChildren().add(imageView);
				}

			}
		} catch (MissingPropertiesException e1) {
			//TODO should fix but who cares since this will be refactored 
			//to be gotten from mediator
		}

		spriteAdd = panelRoot;
		PANEL = panelRoot;
	}


	public void setPath(Map<String, List<Point>> imageMap, String backgroundImageFilePath) {

		PathMaker pathMaker = new PathMaker();
		GridPane grid = pathMaker.initGrid(imageMap, backgroundImageFilePath);
		//	setGridConstraints(grid, imageMap);
		spriteAdd.getChildren().add(grid);
	}


	public void towerSelected(FrontEndTower tower) {
		towerSelected = tower;
		towerPlaceMode = true;
	}

	private void addTowerImageViewAction(FrontEndTower tower) {
		ImageView towerImage = tower.getImageView();
		towerImage.setOnMouseClicked((args) ->GAME_SCREEN.towerClickedOn(tower));
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
					towerPlaceMode = false;
				}
			}
			catch(CannotAffordException e){
				//GameScreen popup for cannot afford
			}
		}
	}
}

