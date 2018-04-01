package authoring.frontend;

import java.util.ArrayList;
import java.util.Map;

import authoring.frontend.exceptions.MissingPropertiesException;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

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
		HBox enemyImageSelect = setupImageSelector("Enemy ", ENEMY_IMAGES, enemyImageDisplay, 100); 
		
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

	private HBox setupImageSelector(String description, String propertiesFilepath, ImageView imageDisplay, double imageSize) {
		Map<String, Image> enemyImageOptions;
		try {
			enemyImageOptions = myPropertiesReader.keyToImageMap(propertiesFilepath, imageSize, imageSize);
		} catch (MissingPropertiesException e) {
			return new HBox(); // TODO fix
		}
		ArrayList<String> imageNames = new ArrayList<String>(enemyImageOptions.keySet());
		final ArrayList<Image> images = new ArrayList<Image>(enemyImageOptions.values()); 
		imageDisplay.setImage(images.get(0));
		ComboBox<String> imageOptionsDropdown = myUIFactory.makeTextDropdown("", imageNames);
		imageOptionsDropdown.getSelectionModel().selectFirst();
		
		HBox imageSelect = new HBox();
		Text prompt = new Text(description+"Image: ");
		Button loadNewImageButton = new Button("Load New Image");
		loadNewImageButton.setId("loadButton");
		imageSelect.getChildren().add(prompt);
		imageSelect.getChildren().add(imageOptionsDropdown);
		imageSelect.getChildren().add(loadNewImageButton);

		// image selection view 
		imageOptionsDropdown.getSelectionModel().selectedIndexProperty().addListener(( arg0, arg1,  arg2) ->{
			imageDisplay.setImage(images.get((int) arg2));
		});
		
		return imageSelect; 
	}
	
}
