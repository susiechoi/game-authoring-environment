/**
 * @author susiechoi
 * Abstract class for developing the fields for customizing (new or existing) enemy object
 */

package authoring.frontend;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

class AdjustEnemyScreen extends AdjustNewOrExistingScreen {

    public static final String OBJECT_TYPE = "Enemy";
    public static final String ENEMY_IMAGE_PREFIX = "images/ThemeSpecificImages/EnemyImages/";
    public static final String ENEMY_IMAGE_SUFFIX = "EnemyImageNames.properties";
    public static final String ENEMY_FIELDS = "default_objects/EnemyFields.properties";

    private String myObjectName; 
    private TextField myNameField; 
    private Slider mySpeedSlider;
    private Slider myInitialHealthSlider; 
    private Slider myHealthImpactSlider; 
    private Slider myValueSlider; 

    private Object mySpeed;
    private Object myInitialHealth;
    private Object myHealthImpact;
    private Object myKillReward;

    protected AdjustEnemyScreen(AuthoringView view, String selectedObjectName) {
	super(view, selectedObjectName, ENEMY_FIELDS, OBJECT_TYPE);
	myObjectName = selectedObjectName; 
    }

    @Override
    protected Parent populateScreenWithFields() {
	VBox vb = new VBox(); 	
	vb.getChildren().add(getUIFactory().makeScreenTitleText(getErrorCheckedPrompt("CustomizeEnemy")));
	HBox enemyImageSelect = makeImageSelector("Enemy", "", ENEMY_IMAGE_PREFIX + getView().getTheme() + ENEMY_IMAGE_SUFFIX);
	vb.getChildren().add(enemyImageSelect);

	Slider enemySpeedSlider = getUIFactory().setupSlider("enemySpeedSlider",  getMyMaxSpeed()); 
	mySpeedSlider = enemySpeedSlider; 
	HBox enemySpeed = getUIFactory().setupSliderWithValue("", enemySpeedSlider, getErrorCheckedPrompt("EnemySpeed"));
	vb.getChildren().add(enemySpeed);
	mySpeedSlider.valueProperty().addListener((obs, oldValue, newValue) -> {
	    mySpeed = newValue;
	    //	getView().setObjectAttribute(OBJECT_TYPE, myObjectName, "mySpeed", newValue);
	});

	Slider enemyInitialHealthSlider = getUIFactory().setupSlider("enemyInitialHealthSlider",  getMyMaxHealthImpact()); 
	myInitialHealthSlider = enemyInitialHealthSlider; 
	HBox initialHealth = getUIFactory().setupSliderWithValue("enemyInitialHealthSlider", enemyInitialHealthSlider, getErrorCheckedPrompt("EnemyInitialHealth")); 
	vb.getChildren().add(initialHealth);
	myInitialHealthSlider.valueProperty().addListener((obs, oldValue, newValue) -> {
	    myInitialHealth = newValue;
	    //getView().setObjectAttribute(OBJECT_TYPE, myObjectName, "myInitialHealth", newValue);
	});

	Slider enemyHealthImpactSlider = getUIFactory().setupSlider("enemyImpactSlider",  getMyMaxHealthImpact()); 
	myHealthImpactSlider = enemyHealthImpactSlider; 
	HBox enemyImpact = getUIFactory().setupSliderWithValue("enemyImpactSlider", enemyHealthImpactSlider, getErrorCheckedPrompt("EnemyHealthImpact")); 
	vb.getChildren().add(enemyImpact);
	myHealthImpactSlider.valueProperty().addListener((obs, oldValue, newValue) -> {
	    myHealthImpact = newValue;
	    //getView().setObjectAttribute(OBJECT_TYPE, myObjectName, "myHealthImpact", newValue);
	});

	Slider enemyValueSlider = getUIFactory().setupSlider("EnemyValueSlider", getMyMaxPrice());
	myValueSlider = enemyValueSlider;
	HBox enemyValue = getUIFactory().setupSliderWithValue("EnemyValueSlider", enemyValueSlider, getErrorCheckedPrompt("EnemyValue"));
	vb.getChildren().add(enemyValue);
	myValueSlider.valueProperty().addListener((obs, oldValue, newValue) -> {
	    myKillReward = newValue;
	    //getView().setObjectAttribute(OBJECT_TYPE, myObjectName, "myKillReward", newValue);
	});

	Button backButton = setupBackButton();

	Button applyButton = getUIFactory().setupApplyButton();
	applyButton.setOnAction(e -> {
	    setProperty("ValueProperty", myKillReward);
	    setProperty("ConstantDamageProperty", myHealthImpact);
	    setProperty("HealthProperty", 0.0, 0.0, myInitialHealth);
	    setProperty("SpeedProperty", 0.0, 0.0, mySpeed);
	    System.out.println("my speed: " + mySpeed);
	    getView().goForwardFrom(this.getClass().getSimpleName()+"Apply");			
	});

	HBox backAndApplyButton = getUIFactory().setupBackAndApplyButton(backButton, applyButton);
	vb.getChildren().add(backAndApplyButton);

	return vb;
    }

    protected TextField getNameField() {
	return myNameField; 
    }

    private void setProperty(String propertyName, Object ...args) {
	List<Object> attributes = makeList(args);
	getView().setObjectAttributes(OBJECT_TYPE, myObjectName, propertyName, attributes);
    }

    private List<Object> makeList(Object ...attributes) {
	List<Object> list = new ArrayList<Object>();
	for(Object attribute : attributes) {
	    list.add(attribute);
	}
	return list;
    }


}
