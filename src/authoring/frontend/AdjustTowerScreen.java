/**
 * @author susiechoi
 */

package authoring.frontend;

import authoring.frontend.exceptions.MissingPropertiesException;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

abstract class AdjustTowerScreen extends AdjustNewOrExistingScreen {

	public static final String TOWER_IMAGES = "images/TowerImageNames.properties";
	public static final int DEFAULT_MAX_RANGE = 500; 
	public static final int DEFAULT_MAX_PRICE = 500;  

	protected AdjustTowerScreen(AuthoringView view) {
		super(view);
	}

	@Override
	public Parent populateScreenWithFields() {		
		VBox vb = new VBox(); 
		vb.getChildren().add(getUIFactory().makeScreenTitleText(getErrorCheckedPrompt("CustomizeTower")));

		makeTowerComponents(vb);
		makeHealthComponents(vb);
		
		Button goToProjectileLauncherButton = getUIFactory().makeTextButton("", getErrorCheckedPrompt("CustomizeProjectileLauncher"));
		vb.getChildren().add(goToProjectileLauncherButton);
		
		HBox backAndApply = setupBackAndApplyButton(); 
		vb.getChildren().add(backAndApply);
				
		ScrollPane sp = new ScrollPane(vb);
		sp.setFitToWidth(true);
		sp.setFitToHeight(true);
		
		return sp;
	}

	protected abstract void populateFieldsWithData(); 
	
	private void makeTowerComponents(VBox vb) {
		TextField nameInputField = getUIFactory().makeTextField(""); 
		HBox towerNameSelect = new HBox(); 
		towerNameSelect = getUIFactory().addPromptAndSetupHBox("", nameInputField, getErrorCheckedPrompt("TowerName"));
		vb.getChildren().add(towerNameSelect);

		HBox towerImageSelect = new HBox();
		ComboBox<String> towerImageDropdown;
		try {
			towerImageDropdown = getUIFactory().makeTextDropdown("", getPropertiesReader().allKeys(TOWER_IMAGES));
			towerImageSelect = getUIFactory().setupImageSelector(getPropertiesReader(), getErrorCheckedPrompt("Tower") + " " , TOWER_IMAGES, 50, getErrorCheckedPrompt("NewImage"), getErrorCheckedPrompt("LoadImage"),
					getErrorCheckedPrompt("NewImageName"), towerImageDropdown);
		} catch (MissingPropertiesException e) {
			getView().loadErrorScreen("NoImageFile");
		}
		vb.getChildren().add(towerImageSelect);
	}
	
	private void makeHealthComponents(VBox vb) {
		Slider towerHealthValueSlider = getUIFactory().setupSlider("TowerHealthValueSlider", DEFAULT_MAX_PRICE);
		HBox towerHealthValue = getUIFactory().setupSliderWithValue("TowerHealthValueSlider", towerHealthValueSlider, getErrorCheckedPrompt("TowerHealthValue"));
		vb.getChildren().add(towerHealthValue);

		Slider towerHealthUpgradeCostSlider = getUIFactory().setupSlider("TowerHealthUpgradeCostSlider", DEFAULT_MAX_PRICE);
		HBox towerHealthUpgradeCost = getUIFactory().setupSliderWithValue("TowerHealthUpgradeCostSlider", towerHealthUpgradeCostSlider, getErrorCheckedPrompt("TowerHealthUpgradeCost"));
		vb.getChildren().add(towerHealthUpgradeCost);

		Slider towerHealthUpgradeValueSlider = getUIFactory().setupSlider("TowerHealthUpgradeValueSlider", DEFAULT_MAX_PRICE);
		HBox towerHealthUpgradeValue = getUIFactory().setupSliderWithValue("TowerHealthUpgradeValueSlider", towerHealthUpgradeValueSlider, getErrorCheckedPrompt("TowerHealthUpgradeValue"));
		vb.getChildren().add(towerHealthUpgradeValue);
	}

}
