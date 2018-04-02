package authoring.frontend;

import authoring.frontend.exceptions.MissingPropertiesException;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
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
		myStylesheet = DEFAULT_OWN_STYLESHEET; 
		myPropertiesReader = new PropertiesReader();
	}

	@Override
	protected Scene makeScreenWithoutStyling() {
		VBox vb = new VBox(); 
		
		HBox enemyNameSelect = myUIFactory.setupPromptAndTextField("", "Enemy Name: "); 

		ImageView enemyImageDisplay = new ImageView(); 
		HBox enemyImageSelect = new HBox();
		try {
			enemyImageSelect = myUIFactory.setupImageSelector(myPropertiesReader, "Enemy ", ENEMY_IMAGES, enemyImageDisplay, 100);
		} catch (MissingPropertiesException e) {
			// TODO FIX
		} 
		
		VBox enemySpeed = myUIFactory.setupPromptAndSlider("enemySpeedSlider", "Enemy Speed: ", DEFAULT_ENEMY_MAX_SPEED); 
		VBox enemyHealthImpact = myUIFactory.setupPromptAndSlider("enemyHealthImpactSlider", "Enemy Health Impact: ", DEFAULT_ENEMY_MAX_HEALTH_IMPACT); 
		VBox enemy$Impact = myUIFactory.setupPromptAndSlider("enemyMoneyImpactSlider", "Enemy $ Impact: ", DEFAULT_ENEMY_MAX_$_IMPACT); 
		
		HBox backAndApply = setupBackAndApplyButton(); 

		vb.getChildren().add(enemyNameSelect);
		vb.getChildren().add(enemyImageSelect);
		vb.getChildren().add(enemyImageDisplay);
		vb.getChildren().add(enemySpeed);
		vb.getChildren().add(enemyHealthImpact);
		vb.getChildren().add(enemy$Impact);
		vb.getChildren().add(backAndApply);
		
		return new Scene(vb, 1500, 900); 
	}
	
}
