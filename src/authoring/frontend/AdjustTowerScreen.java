package authoring.frontend;

import java.util.ArrayList;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;

class AdjustTowerScreen extends AdjustScreen {

	public static final String DEFAULT_OWN_STYLESHEET = "styling/AdjustEnemyTower.css";

	protected AdjustTowerScreen() {
		myStylesheet = DEFAULT_OWN_STYLESHEET; 
	}

	@Override
	protected Scene makeScreenWithoutStyling() {
		VBox vb = new VBox(); 

		HBox towerName = setupPromptAndTextField("Tower Name: "); 

		ArrayList<Image> towerImageList = new ArrayList<Image>(); 
		towerImageList.add(new Image("https://png.icons8.com/metro/1600/eiffel-tower.png", 20, 30, true, false));
		towerImageList.add(new Image("https://t00.deviantart.net/iqxvvb1e5hzSmNMUSKffNuUnxIw=/fit-in/700x350/filters:fixed_height(100,100):origin()/pre00/ea9f/th/pre/i/2015/205/6/d/fantasy_castle_stock_parts__31_kingdom_front_gate_by_madetobeunique-d80t3ey.png", 30, 30, true, false));
		ComboBox<Image> towerImages = myUIFactory.makeImageDropdown(towerImageList, 300, 100);
		
		HBox projectileImage = new HBox(); 
		HBox towerAbility = new HBox(); 
		HBox towerRange = new HBox(); 
		HBox towerDamage = new HBox(); 
		HBox towerPrice = new HBox(); 
		HBox backAndApply = setupBackAndApply(); 

		vb.getChildren().add(towerName);
		vb.getChildren().add(towerImages);
		return new Scene(vb, 1500, 900); 
	}
}
