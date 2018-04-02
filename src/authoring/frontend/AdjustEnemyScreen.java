package authoring.frontend;

import authoring.frontend.exceptions.MissingPropertiesException;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

class AdjustEnemyScreen extends AdjustScreen {
	
	public static final String DEFAULT_OWN_STYLESHEET = "styling/AdjustEnemyTower.css";
	public static final String ENEMY_IMAGES = "images/EnemyImageNames.properties";
	public static final int DEFAULT_ENEMY_MAX_HEALTH_IMPACT = 3; 
	public static final int DEFAULT_ENEMY_MAX_$_IMPACT = 50; 
	public static final int DEFAULT_ENEMY_MAX_SPEED = 100; 
	
	private PropertiesReader myPropertiesReader; 

	protected AdjustEnemyScreen() {
		setStyleSheet(DEFAULT_OWN_STYLESHEET); 
		myPropertiesReader = new PropertiesReader();
	}

	@Override
	protected Scene makeScreenWithoutStyling() {
		VBox vb = new VBox(); 
		
		HBox enemyNameSelect = getUIFactory().setupPromptAndTextField("", "Enemy Name: "); 

//		ImageView enemyImageDisplay = new ImageView(); 
		HBox enemyImageSelect = new HBox();
		try {
			enemyImageSelect = getUIFactory().setupImageSelector(myPropertiesReader, "Enemy ", ENEMY_IMAGES, 100);
		} catch (MissingPropertiesException e) {
			// TODO FIX
		} 
		
		HBox enemySpeed = getUIFactory().setupPromptAndSlider("enemySpeedSlider", "Enemy Speed: ", DEFAULT_ENEMY_MAX_SPEED); 
		HBox enemyHealthImpact = getUIFactory().setupPromptAndSlider("enemyHealthImpactSlider", "Enemy Health Impact: ", DEFAULT_ENEMY_MAX_HEALTH_IMPACT); 
		HBox enemy$Impact = getUIFactory().setupPromptAndSlider("enemyMoneyImpactSlider", "Enemy $ Impact: ", DEFAULT_ENEMY_MAX_$_IMPACT); 
		
		HBox backAndApply = setupBackAndApplyButton(); 

		vb.getChildren().add(enemyNameSelect);
		vb.getChildren().add(enemyImageSelect);
//		vb.getChildren().add(enemyImageDisplay);
		vb.getChildren().add(enemySpeed);
		vb.getChildren().add(enemyHealthImpact);
		vb.getChildren().add(enemy$Impact);
		vb.getChildren().add(backAndApply);
		
		return new Scene(vb, 1500, 900); 
	}
	
}
