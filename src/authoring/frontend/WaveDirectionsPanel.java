package authoring.frontend;

import authoring.frontend.exceptions.MissingPropertiesException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class WaveDirectionsPanel extends PathPanel{
    private int myWaveNumber;
    private Slider myTimeSlider;
    private VBox myRoot;
    public WaveDirectionsPanel(AuthoringView view, String waveNumber) {
	super(view);
	if(waveNumber.equals("Default")) {
	   // System.out.println("level it thinks" + getView().getLevel());
	    myWaveNumber = getView().getHighestWaveNumber(getView().getLevel())+1;
		try {
			getView().setWaveTime(myWaveNumber,Integer.parseInt(getPropertiesReader().findVal(AdjustNewOrExistingScreen.DEFAULT_CONSTANTS, "DefaultWaveTime")));
			System.out.println("highest wave number directions: " + getView().getHighestWaveNumber(getView().getLevel()));
		}
			catch(MissingPropertiesException e) {
			    getView().loadErrorScreen("NoFile");
			}
	}
	else {
	    myWaveNumber = Integer.parseInt(waveNumber.split(" ")[1]) - 1;
	}

	
	setUpPanel();
    }
    private void setUpPanel() {
	myRoot = new VBox();
	myRoot.setMaxSize(280, 900);
	Label waveText = new Label(getErrorCheckedPrompt("WavescreenHeader") + (myWaveNumber+1));
	VBox pseudoRoot = new VBox();
	Label directions = new Label("Test directions"); //TODO
	HBox waveTimeSliderPrompted = new HBox();
	try {
	myTimeSlider = getUIFactory().setupSlider("", Integer.parseInt(getPropertiesReader().findVal(AdjustNewOrExistingScreen.DEFAULT_CONSTANTS, "MaxWaveTime")));
	waveTimeSliderPrompted = getUIFactory().setupSliderWithValue("", myTimeSlider, getErrorCheckedPrompt("WaveTime"));
	myTimeSlider.setValue(Double.parseDouble(getPropertiesReader().findVal(AdjustNewOrExistingScreen.DEFAULT_CONSTANTS, "DefaultWaveTime")));
	}
	catch(MissingPropertiesException e) {
	    getView().loadErrorScreen("NoFile");
	}
	Button applyButton = getUIFactory().makeTextButton("", getErrorCheckedPrompt("Apply"));
	applyButton.setOnAction(e -> {
	    setSaved();
	    getView().setWaveTime(myWaveNumber,(int) Math.round(myTimeSlider.getValue()));
	});
	pseudoRoot.getChildren().add(waveText);
	pseudoRoot.getChildren().add(directions);
	pseudoRoot.getChildren().add(waveTimeSliderPrompted);
	pseudoRoot.getChildren().add(applyButton);
	myRoot.getChildren().add(pseudoRoot);
    }
    @Override
    protected Node getPanel() {
	return myRoot;
    }
    @Override
    protected void makePanel() {
	// TODO Auto-generated method stub

    }
    @Override
    protected Button getApplyButton() {
	// TODO Auto-generated method stub
	return null;
    }
    @Override
    protected void setApplyButtonAction(EventHandler<ActionEvent> e) {
	// TODO Auto-generated method stub

    }
    @Override
    public Parent makeScreenWithoutStyling() {
	// TODO Auto-generated method stub
	return null;
    }

}
