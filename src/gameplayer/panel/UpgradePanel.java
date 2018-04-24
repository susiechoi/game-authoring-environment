package gameplayer.panel;

import gameplayer.screen.GameScreen;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import java.util.Map;
import javafx.scene.control.ScrollPane;
import frontend.PromptReader;
import frontend.PropertiesReader;
import frontend.UIFactory;
import authoring.frontend.exceptions.MissingPropertiesException;
import engine.sprites.towers.FrontEndTower;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;


public class UpgradePanel extends SpecificPanel {

    //TODO don't have this be a static value
    private final int UPGRADE_IMAGE_SIZE = 70;

    private final GameScreen GAME_SCREEN;
    private final UIFactory UI_FACTORY;
    private PropertiesReader PROP_READ;
    private PromptReader PROMPTS;

    //TODO use available tower upgrades
    private final String UPGRADE_NAMES_FILE_PATH = "images/UpgradeImageNames.properties";


    public UpgradePanel (GameScreen gameScreen, PromptReader promptReader, FrontEndTower tower) {
	super(tower);
	GAME_SCREEN = gameScreen;
	UI_FACTORY = new UIFactory();
	PROP_READ = new PropertiesReader();
	PROMPTS = promptReader;
    }


    @Override
    public void makePanel() {
	//TODO read in text from properties file
	HBox towerUpgrades = new HBox();
	fillUpgrades(towerUpgrades);
	towerUpgrades.setFillHeight(true);
	towerUpgrades.setAlignment(Pos.CENTER);
	towerUpgrades.setMaxWidth(Double.MAX_VALUE);
	ScrollPane upgradeDisplay = new ScrollPane(towerUpgrades);
	upgradeDisplay.setFitToWidth(true);

	VBox panelRoot = new VBox(upgradeDisplay);
	VBox.setVgrow(upgradeDisplay, Priority.ALWAYS);
	panelRoot.setAlignment(Pos.CENTER);
	panelRoot.setId("upgradePanel");
	PANEL = panelRoot;
    }

    private void fillUpgrades(HBox availUpgrades) {
	HBox upgrades = availUpgrades;
	try {
	    Map<String, Image> upgradeMap = PROP_READ.keyToImageMap(UPGRADE_NAMES_FILE_PATH, UPGRADE_IMAGE_SIZE, UPGRADE_IMAGE_SIZE);
	    for (String upgradeType: upgradeMap.keySet()) {
			Button upgradeButton = UI_FACTORY.makeImageButton("button", upgradeMap.get(upgradeType));
			upgradeButton.setOnMouseClicked((arg0) -> GAME_SCREEN.upgradeClickedOn(TOWER));
			upgrades.getChildren().add(upgradeButton);
			HBox.setHgrow(upgradeButton, Priority.ALWAYS);
			upgradeButton.setMaxWidth(Double.MAX_VALUE);
			upgradeButton.setMaxHeight(Double.MAX_VALUE);
	    }
	}
	catch (MissingPropertiesException e) {
	    System.out.println("upgrade image load fail");
	}
    }
}
