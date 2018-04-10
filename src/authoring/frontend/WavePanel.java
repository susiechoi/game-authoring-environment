package authoring.frontend;

import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout.Alignment;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class WavePanel extends PathPanel{
    private String myPathString;
    private VBox myRoot;
    private ComboBox<String> myEnemyDropdown;
    private TextField myNumberTextField;
    private Integer myEnemyNumber;
    private String myWaveNumber;
    public WavePanel(AuthoringView view, DraggableImage grid, String waveNumber) {
	super(view);
	if (grid == null) {
	    myPathString = "1";
	}
	
	//myPathString = grid.getPathName();
	if(waveNumber==null) {
	    waveNumber = "1";
	}
	myWaveNumber = waveNumber;
	setUpPanel();
    }
    public void setUpPanel() {
	myRoot = new VBox();
	myRoot.setMaxSize(280, 900);
	VBox pseudoRoot = new VBox();
	Label waveText = new Label(getErrorCheckedPrompt("WavescreenHeader") + myWaveNumber);
	myEnemyDropdown = getUIFactory().makeTextDropdown("", getView().getCurrentObjectOptions("Enemy"));
	Text enemyDropdownText = new Text(getView().getErrorCheckedPrompt("ChooseEnemy"));
	myNumberTextField = new TextField();
	HBox sizingButtons = makeSizingButtons();
	Text textFieldPrompt = new Text(getView().getErrorCheckedPrompt("ChooseEnemyNumber"));
	//HBox textFieldPrompted = getUIFactory().addPromptAndSetupHBox("", myNumberTextField, "ChooseEnemyNumber");
	Button backButton = setupBackButton();
	Button applyButton = getUIFactory().makeTextButton("", getErrorCheckedPrompt("Apply"));
	applyButton.setOnAction(e -> {
	    errorcheckResponses();
	    getView().addWaveEnemy(getView().getLevel(), myPathString, Integer.parseInt(myWaveNumber), 
		    myEnemyDropdown.getValue(), myEnemyNumber);
	});
	
	pseudoRoot.getChildren().addAll(waveText, enemyDropdownText, myEnemyDropdown, textFieldPrompt, myNumberTextField,sizingButtons, backButton, applyButton);
	myRoot.getChildren().add(pseudoRoot);
	myRoot.getStyleClass().add("rootPanel");
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
	return myRoot;
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
