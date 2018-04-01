package authoring.frontend;

import java.util.ArrayList;
import java.util.List;

import authoring.frontend.exceptions.MissingPropertiesException;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SelectionModel;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

class AdjustTowerScreen extends AdjustScreen {

	public static final String DEFAULT_OWN_STYLESHEET = "styling/AdjustEnemyTower.css";
	public static final String TOWER_IMAGES = "images/TowerImageNames.properties";
	public static final String PROJECTILE_IMAGES = "images/ProjectileImageNames.properties";
	
	private PropertiesReader myPropertiesReader; 
	
	protected AdjustTowerScreen() {
		myStylesheet = DEFAULT_OWN_STYLESHEET; 
		myPropertiesReader = new PropertiesReader();
	}

	@Override
	protected Scene makeScreenWithoutStyling() {
		VBox vb = new VBox(); 

		HBox towerNameSelect = myUIFactory.setupPromptAndTextField("Tower Name: "); 

		List<String> towerImageNames = new ArrayList<String>();
		try {
			towerImageNames = myPropertiesReader.allKeys(TOWER_IMAGES);
		} catch (MissingPropertiesException e) {
			// TODO FIX
			e.printStackTrace();
		}
		ComboBox<String> towerImageOptions = myUIFactory.makeTextDropdown(towerImageNames);
		towerImageOptions.getSelectionModel().selectFirst();
		
		List<Image> towerImages = new ArrayList<Image>();
		try {
			towerImages = myPropertiesReader.allValsAsImages(TOWER_IMAGES, 50, 50);
		} catch (MissingPropertiesException e) {
			// TODO FIX
			e.printStackTrace();
		}
		List<Image> finalTowerImages = towerImages; 
		
		Text towerImagePrompt = new Text("Tower Image: ");
		HBox towerImageSelect = new HBox();
		towerImageSelect.getChildren().add(towerImagePrompt);
		towerImageSelect.getChildren().add(towerImageOptions);
		towerImageSelect.setId("imageOptionsDropdown");
		ImageView towerImageDisplay = new ImageView(); 
		towerImageOptions.getSelectionModel().selectedIndexProperty().addListener(( arg0, arg1,  arg2) ->{
		    towerImageDisplay.setImage(finalTowerImages.get((int) arg2));
		});
		
		HBox projectileImage = new HBox(); 
		HBox towerAbility = new HBox(); 
		HBox towerRange = new HBox(); 
		HBox towerDamage = new HBox(); 
		HBox towerPrice = new HBox(); 
		HBox backAndApply = setupBackAndApply(); 

		vb.getChildren().add(towerNameSelect);
		vb.getChildren().add(towerImageSelect);
		vb.getChildren().add(towerImageDisplay);
		return new Scene(vb, 1500, 900); 
	}
}
