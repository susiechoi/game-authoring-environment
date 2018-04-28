package authoring.frontend;

import authoring.frontend.exceptions.MissingPropertiesException;
import authoring.frontend.exceptions.ObjectNotFoundException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import jdk.internal.jline.internal.Log;

public class WaveDirectionsPanel extends PathPanel{
    private int myWaveNumber;
    private Slider myTimeSlider;
    private VBox myRoot;
    private Button myApplyButton;
    public WaveDirectionsPanel(AuthoringView view, String waveNumber) {
	super(view);
	if(waveNumber.equals("Default")) { //coming from new wave
	    // System.out.println("level it thinks" + getView().getLevel());
	    myWaveNumber = getView().getHighestWaveNumber(getView().getLevel())+1;
	    try {
		getView().setWaveTime(myWaveNumber,Integer.parseInt(getPropertiesReader().findVal(AdjustNewOrExistingScreen.DEFAULT_CONSTANTS, "DefaultWaveTime")));
		//System.out.println("highest wave number directions: " + getView().getHighestWaveNumber(getView().getLevel()));
	    }
	    catch(MissingPropertiesException e) {
		 Log.error(e);
		getView().loadErrorScreen("NoFile");
	    }
	}
	else {
	    String[] splitUpWave = waveNumber.split(" ");
	    if(splitUpWave.length>1) {
		myWaveNumber = Integer.parseInt(splitUpWave[1]) - 1; //Coming from combobox
	    }
	    else {
		myWaveNumber = Integer.parseInt(splitUpWave[0]);  //coming from wavepanel
	    }
	}


	setUpPanel();
    }
    private void setUpPanel() {
	myRoot = new VBox();
	myRoot.setMaxSize(280, 900);
	Button backButton = getUIFactory().makeTextButton("", getErrorCheckedPrompt("Cancel"));
	backButton.setOnAction(e -> {getView().goForwardFrom(this.getClass().getSimpleName()+"Back", "Wave");});
	Label waveText = new Label(getErrorCheckedPrompt("WavescreenHeader") + (myWaveNumber+1));
	VBox pseudoRoot = new VBox();
	Text directions = new Text(getErrorCheckedPrompt("WaveDirections")); //TODO
	HBox waveTimeSliderPrompted = new HBox();
	try {
	    myTimeSlider = getUIFactory().setupSlider("", Integer.parseInt(getPropertiesReader().findVal(AdjustNewOrExistingScreen.DEFAULT_CONSTANTS, "MaxWaveTime")));
	    waveTimeSliderPrompted = getUIFactory().setupSliderWithValue("", myTimeSlider, getErrorCheckedPrompt("WaveTime"));
	    String time = getView().getObjectAttribute("Wave", ((Integer) myWaveNumber).toString(), "myTime").toString();
	    myTimeSlider.setValue(Double.parseDouble(time));
	    myTimeSlider.valueProperty().addListener(new ChangeListener<Number>() {
		@Override
		public void changed(ObservableValue<? extends Number> ov,
			Number old_val, Number new_val) {
		    myApplyButton.setDisable(false);
		}
	    });
	}
	catch(MissingPropertiesException e) {
	    Log.error(e);
	    getView().loadErrorScreen("NoFile");
	}
	myApplyButton = getUIFactory().makeTextButton("", getErrorCheckedPrompt("Apply"));
	myApplyButton.setOnAction(e -> {
	    myApplyButton.setDisable(true);
	    setSaved();
	    getView().setWaveTime(myWaveNumber,(int) Math.round(myTimeSlider.getValue()));
	});
	pseudoRoot.getChildren().add(waveText);
	pseudoRoot.getChildren().add(directions);
	pseudoRoot.getChildren().add(waveTimeSliderPrompted);
	pseudoRoot.getChildren().add(myApplyButton);
	pseudoRoot.getChildren().add(backButton);
	myRoot.getChildren().add(pseudoRoot);
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
    protected Node getPanel() {
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
