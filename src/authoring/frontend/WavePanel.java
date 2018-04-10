package authoring.frontend;

import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class WavePanel extends PathPanel{
    private String myPathString;
    private ComboBox<String> myEnemyDropdown;
    private TextField myNumberTextField;
    private Integer myEnemyNumber;
    private String myWaveNumber;
    public WavePanel(AuthoringView view, DraggableImage grid, String waveNumber) {
	super(view);
	myPathString = grid.getPathName();
	myWaveNumber = waveNumber;
	setUpPanel();
    }
    public void setUpPanel() {
	List<String> enemyOptions = new ArrayList<>(); //TODO!
	myEnemyDropdown = getUIFactory().makeTextDropdown("", getView().getCurrentObjectOptions("Enemy"));
	myNumberTextField = new TextField();
	Button backButton = setupBackButton();
	Button applyButton = getUIFactory().makeTextButton("", getErrorCheckedPrompt("Apply"));
	applyButton.setOnAction(e -> {
	    errorcheckResponses();
	    getView().addWaveEnemy(getView().getLevel(), myPathString, Integer.parseInt(myWaveNumber), 
		    myEnemyDropdown.getValue(), myEnemyNumber);
	});
    }
    private void errorcheckResponses() {
	if(myEnemyDropdown.getValue() == null ) {
	    getView().loadErrorAlert("BadValue");
	}
	try {
	    myEnemyNumber = Integer.parseInt(myNumberTextField.getText());
	}
	catch(NumberFormatException e) {
	    getView().loadErrorAlert("BadValue");
	}
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
