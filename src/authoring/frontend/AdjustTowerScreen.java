package authoring.frontend;

import java.util.ArrayList;

import authoring.frontend.exceptions.MissingPropertiesException;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

class AdjustTowerScreen extends AdjustScreen {

	public static final String DEFAULT_OWN_STYLESHEET = "styling/AdjustEnemyTower.css";
	public static final String TOWER_IMAGES = "images/TowerImageNames.properties";
	public static final String PROJECTILE_IMAGES = "images/ProjectileImageNames.properties";
	public static final int DEFAULT_TOWER_MAX_RANGE = 500; 
	public static final int DEFAULT_TOWER_MAX_PRICE = 500; 

	private PropertiesReader myPropertiesReader; 

	protected AdjustTowerScreen() {
		myStylesheet = DEFAULT_OWN_STYLESHEET; 
		myPropertiesReader = new PropertiesReader();
	}

	@Override
	protected Scene makeScreenWithoutStyling() {
		VBox vb = new VBox(); 

		HBox towerNameSelect = myUIFactory.setupPromptAndTextField("", "Tower Name: "); 

		ImageView towerImageDisplay = new ImageView(); 
		HBox towerImageSelect = new HBox();
		try {
			towerImageSelect = myUIFactory.setupImageSelector(myPropertiesReader, "Tower ", TOWER_IMAGES, towerImageDisplay, 50);
		} catch (MissingPropertiesException e) {
			// TODO FIX
			e.printStackTrace();
		} 
		ImageView projectileImageDisplay = new ImageView(); 
		HBox projectileImageSelect = new HBox(); 
		try {
			projectileImageSelect = myUIFactory.setupImageSelector(myPropertiesReader, "Projectile ", PROJECTILE_IMAGES, projectileImageDisplay, 30);
		} catch (MissingPropertiesException e) {
			// TODO FIX
			e.printStackTrace();
		}

		ArrayList<String> dummyTowerAbilities = new ArrayList<String>();
		dummyTowerAbilities.add("Freeze");
		dummyTowerAbilities.add("Fire");
		HBox towerAbility = myUIFactory.setupPromptAndDropdown("", "Tower Ability: ", dummyTowerAbilities);

		VBox towerRange = myUIFactory.setupPromptAndSlider("towerRangeSlider", "Tower Range: ", DEFAULT_TOWER_MAX_RANGE); 
		VBox towerPrice = myUIFactory.setupPromptAndSlider("towerPriceSlider", "Tower Price: ", DEFAULT_TOWER_MAX_PRICE); 

		HBox backAndApply = setupBackAndApplyButton(); 

		vb.getChildren().add(towerNameSelect);
		vb.getChildren().add(towerImageSelect);
		vb.getChildren().add(towerImageDisplay);
		vb.getChildren().add(projectileImageSelect);
		vb.getChildren().add(projectileImageDisplay);
		vb.getChildren().add(towerAbility);
		vb.getChildren().add(towerRange);
		vb.getChildren().add(towerPrice);
		vb.getChildren().add(backAndApply);
		return new Scene(vb, 1500, 900); 
	}

}
