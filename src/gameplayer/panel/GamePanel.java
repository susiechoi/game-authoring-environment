package gameplayer.panel;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import engine.sprites.FrontEndSprite;
import engine.sprites.towers.CannotAffordException;
import engine.sprites.towers.FrontEndTower;
import gameplayer.screen.GameScreen;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;

public class GamePanel extends Panel{

    private final GameScreen GAME_SCREEN;
    private FrontEndTower towerSelected;
    private boolean towerPlaceMode = false;
    private List<FrontEndTower> towersPlaced;
    private Pane spriteAdd;


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
	spriteAdd = panelRoot;
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
		ImageView towerImage = newTower.getImageView();
		towerImage.setLayoutX(-towerImage.getFitWidth()/2);
		towerImage.setLayoutY(-towerImage.getFitHeight()/2);
		if(newTower!= null) {
		    addTowerImageViewAction(newTower);
		    towersPlaced.add(newTower);
		    spriteAdd.getChildren().add(towerImage);
		    towerPlaceMode = false;
		    System.out.println("paneWidth: " + spriteAdd.getWidth() + " height: "+ spriteAdd.getHeight());
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

    public void setPath(Map<String, List<Point2D>> imageMap, int numRow, int numCol) {
	GridPane grid = new GridPane();
	grid.setGridLinesVisible(true);
	//TODO this must be fixed, shouldn't be manual
	grid.setMaxSize(1020.0, 650.0);
	grid.setPrefSize(1020.0, 650.0);
	grid.setMinSize(1020.0, 650.0);


	for (int i = 0; i < numCol; i++) {
	    ColumnConstraints colConst = new ColumnConstraints();
	    colConst.setPercentWidth(100.0 / numCol);
	    grid.getColumnConstraints().add(colConst);
	}
	for (int i = 0; i < numRow; i++) {
	    RowConstraints rowConst = new RowConstraints();
	    rowConst.setPercentHeight(100.0 / numRow);
	    grid.getRowConstraints().add(rowConst);         
	}
	addImagesToGrid(imageMap, grid);
	spriteAdd.getChildren().add(grid);
    }

    private void addImagesToGrid(Map<String, List<Point2D>> imageMap, GridPane grid) {
	for (String key: imageMap.keySet()) { //goes through images
	    for (int i = 0; i < imageMap.keySet().size(); i++) {
		Point2D point = imageMap.get(key).get(0);
		grid.add(new ImageView(new Image(key)), (int)point.getX(), (int)point.getY());
	    }
	}
    }

}
