
package gameplayer.panel;

import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

	private BorderPane PANE;
	private PromptReader PROMPTS;
	private GameScreen GAME_SCREEN;
	private PropertiesReader PROP_READ;
	private Integer money;
	private final UIFactory UIFACTORY;
	private Panel bottomPanel;
	private Button currencyDisplay;

	//TODO change to only use availibleTowers
	private final String TOWER_NAMES_FILE_PATH = "images/TowerImageNames.properties"; 

	//private final FileIO FILE_READER;

	private final String[] Button_IDS = {}; //How should we create the buttons for selecting towers since there are so many?
	private Pane towerGroup;

	public TowerPanel( GameScreen gameScreen, PromptReader promptReader) {
		GAME_SCREEN = gameScreen;
		PROMPTS = promptReader;
		PROP_READ = new PropertiesReader();
		UIFACTORY = new UIFactory();
		money = GAME_SCREEN.getMoney();
	}


	@Override
	public void makePanel() {

		//TODO need to check if this static stuff is okay

		towerGroup = new Pane();
		ScrollPane towerDisplay = new ScrollPane(towerGroup);
		towerDisplay.setFitToWidth(true); //makes hbox take full width of scrollpane

		currencyDisplay = new Button();
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

	private HBox fillScrollWithTowers(List<FrontEndTower> availableTowers) {
		VBox towerHolderLeft = new VBox();
		VBox towerHolderRight = new VBox();
		towerHolderLeft.setId("towerHolders");
		towerHolderRight.setId("towerHolders");
		VBox towerHolder;
		int alternator = 0;

		for(FrontEndTower tower : availableTowers) {
		    	ImageView imageView = tower.getImageView();
		    	imageView.setFitHeight(TOWER_IMAGE_SIZE);
		    	imageView.setFitWidth(TOWER_IMAGE_SIZE);
			Button towerButton = UIFACTORY.makeImageViewButton("button",tower.getImageView());
			towerButton.setOnMouseClicked((arg0) -> GAME_SCREEN.towerSelectedForPlacement(tower));
			if(alternator%2 == 0) {
				towerHolder = towerHolderLeft;
			}
			else {
				towerHolder = towerHolderRight;
			}

			towerButton.setMaxWidth(Double.MAX_VALUE);
			towerButton.setMaxHeight(Double.MAX_VALUE);
			towerHolder.getChildren().add(towerButton);
			VBox.setVgrow(towerButton, Priority.ALWAYS);
			System.out.println(towerButton.getHeight() + ": height");
			System.out.println(towerButton.getWidth() + ": width");

			alternator++;
		}

		towerHolderLeft.setFillWidth(true);
		towerHolderRight.setFillWidth(true);
		HBox fullTowerHold = new HBox(towerHolderLeft,towerHolderRight);
		System.out.println("numChild: " + fullTowerHold.getChildren().size());
		fullTowerHold.setAlignment(Pos.CENTER);
		HBox.setHgrow(towerHolderRight, Priority.ALWAYS);
		HBox.setHgrow(towerHolderLeft, Priority.ALWAYS);
		return fullTowerHold;
		//TODO pretty bad code that doesn't work, towers should be same height in columns
		//	    if(alternator%2 == 1) {
		//		Button voidButton = UIFACTORY.makeTextButton("voidButton", "");
		//		towerHolderRight.getChildren().add(voidButton);
		//		VBox.setVgrow(voidButton, Priority.ALWAYS);
		//		voidButton.setMaxWidth(Double.MAX_VALUE);
		//		voidButton.setMaxHeight(Double.MAX_VALUE);
		//		voidButton.setDisable(true);
		//	    }

		//something went wrong and we don't have the towers
		//TODO something reasonable here
		//probably have default images that aren't the ones specified by authoring
	}

	private void towerSelected(String towerPropName) {

	}

	public void setAvailableTowers(List<FrontEndTower> availableTowers) {
		towerGroup.getChildren().clear();
		towerGroup.getChildren().add(fillScrollWithTowers(availableTowers));
		System.out.print("in setAvailableTowers in TowerPanel class: ");
		System.out.println(availableTowers.get(0).getImageView() == null);
	}

	public void updateCurrency(Integer newBalence) {
		money = newBalence;
		currencyDisplay.setText("$" +money.toString());
	}

}

