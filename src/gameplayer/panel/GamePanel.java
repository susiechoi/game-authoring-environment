package gameplayer.panel;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import engine.sprites.FrontEndSprite;
import engine.sprites.towers.CannotAffordException;
import engine.sprites.towers.FrontEndTower;
import gameplayer.screen.GameScreen;
import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;

public class GamePanel extends Panel{

    private final GameScreen GAME_SCREEN;
    private FrontEndTower towerSelected;
    private boolean towerPlaceMode = false;
    private List<FrontEndTower> towersPlaced;
    private Group spriteAdd;


    public GamePanel(GameScreen gameScreen) {
	GAME_SCREEN = gameScreen;
	towersPlaced = new ArrayList<FrontEndTower>();
    }


    @Override
    public void makePanel() {

	//TODO potentially fix needed?
	spriteAdd = new Group();

	ScrollPane panelRoot = new ScrollPane(spriteAdd);
	panelRoot.setId("gamePanel");
	//panelRoot.setBottom(new Up);
	panelRoot.setMaxWidth(Double.MAX_VALUE);
	panelRoot.setMaxHeight(Double.MAX_VALUE);

	panelRoot.setOnMouseClicked(e -> handleMouseInput(e.getX(), e.getY()));

	PANEL = panelRoot;
    }

    public void towerSelected(FrontEndTower tower) {
	towerSelected = tower;
	towerPlaceMode = true;
    }

    public void exitTowerPlace() {
	towerPlaceMode = false;
    }

    public void handleMouseInput(double x, double y) {
	if(towerPlaceMode) {
	    Point position = new Point((int)x,(int)y);
	    try {
		FrontEndTower newTower = GAME_SCREEN.placeTower(towerSelected, position);
		System.out.println(newTower.getImageView().getX() + " #andrewProblem " + newTower.getImageView().getY());
		if(newTower!= null) {
		    addTowerImageViewAction(newTower);
		    towersPlaced.add(newTower);
		    spriteAdd.getChildren().add(newTower.getImageView());
		    towerPlaceMode = false;
		}
	    }
	    catch(CannotAffordException e){
		//GameScreen popup for cannot afford
	    }
	}
    }

    private void addTowerImageViewAction(FrontEndTower tower) {
	ImageView towerImage = tower.getImageView();
	towerImage.setOnMouseClicked((args) ->GAME_SCREEN.towerClickedOn(tower));
    }

    public void addSprite(FrontEndSprite sprite) {
	spriteAdd.getChildren().add(sprite.getImageView());
    }

    public void removeSprite(FrontEndSprite sprite) {
	spriteAdd.getChildren().remove(sprite.getImageView());
    }

    public void removeTower(FrontEndTower tower) {
	spriteAdd.getChildren().remove(tower.getImageView());
    }


}
