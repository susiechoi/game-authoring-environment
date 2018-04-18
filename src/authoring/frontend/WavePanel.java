package authoring.frontend;

import java.util.List;
import java.util.Map;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;


/**
 * Class to create the panel seen on the righthand side of the WaveScreen that allows
 * users to specify Enemy information/numbers for a specific wave for a specific path.
 * Dependent on AuthoringModel to make/retrieve waves correctly and on UIFactory to correctly
 * create UI elements.
 * @author Sarahbland
 *
 */

public class WavePanel extends PathPanel{


	private int myPathNumber;
	private VBox myRoot;
	private ComboBox<String> myEnemyDropdown;
	private TextField myNumberTextField;
	private Integer myEnemyNumber;
	private Integer myWaveNumber;
	public WavePanel(AuthoringView view, DraggableImage grid, String waveNumber) {
		super(view);
//		if (grid == null) {
//			myPathNumber = 1;
//		}
		if(waveNumber.equals("Default")) {
		    myWaveNumber = getView().getHighestWaveNumber(getView().getLevel());
		}
		else {
		    myWaveNumber = Integer.parseInt(waveNumber.split(" ")[1]) - 1;
		}
		myPathNumber = 1; //TODO!!
		
		setUpPanel();
	}

	private void setUpPanel() {
		Map<String, Integer> enemyMap = getView().getEnemyNameToNumberMap(getView().getLevel(), myPathNumber, myWaveNumber);
		myRoot = new VBox();
		myRoot.setMaxSize(280, 900);
		VBox pseudoRoot = new VBox();
		Label waveText = new Label(getErrorCheckedPrompt("WavescreenHeader") + (myWaveNumber+1));
		List<String> enemyOptions = getView().getCurrentObjectOptions("Enemy");
		myEnemyDropdown = getUIFactory().makeTextDropdown("", enemyOptions);
		myEnemyDropdown.setOnAction(e -> {
			myNumberTextField.setText(enemyMap.get(myEnemyDropdown.getValue()).toString());
		});
		for(String enemyName: enemyOptions) {//TODO: refactor - this is getting pretty messy/repeated!!
			if(!enemyMap.containsKey(enemyName)) {
				enemyMap.put(enemyName, 0);
			}
		}
		if(enemyOptions.size()>0) {
			myEnemyDropdown.setValue(enemyOptions.get(0));
		}
		Text enemyDropdownText = new Text(getView().getErrorCheckedPrompt("ChooseEnemy"));
		myNumberTextField = new TextField();
		if(myEnemyDropdown.getValue()!=null) {
		    myNumberTextField.setText(enemyMap.get(myEnemyDropdown.getValue()).toString());
		}
		//		HBox sizingButtons = makeSizingButtons();
		Text textFieldPrompt = new Text(getView().getErrorCheckedPrompt("ChooseEnemyNumber"));
		//HBox textFieldPrompted = getUIFactory().addPromptAndSetupHBox("", myNumberTextField, "ChooseEnemyNumber");
		Button backButton = setupBackButton();
		Button applyButton = getUIFactory().makeTextButton("", getErrorCheckedPrompt("Apply"));
		applyButton.setOnAction(e -> {
		    	setSaved();
			if(errorcheckResponses()) {
				//System.out.println("addin a wave");
				//System.out.println("highest wave number" + getView().getHighestWaveNumber(getView().getLevel()));
				//System.out.println("myWaveNumber" + myWaveNumber);
				getView().addWaveEnemy(getView().getLevel(),((Integer)myPathNumber).toString(), myWaveNumber, 
						myEnemyDropdown.getValue(), myEnemyNumber);
				//System.out.println("highest wave number" + getView().getHighestWaveNumber(getView().getLevel()));
			}

		});
		pseudoRoot.getChildren().addAll(waveText, enemyDropdownText, myEnemyDropdown, textFieldPrompt, myNumberTextField, backButton, applyButton);
		myRoot.getChildren().add(pseudoRoot);
		myRoot.getStyleClass().add("rootPanel");
	}


	private boolean errorcheckResponses() {
		if(myEnemyDropdown.getValue() == null ) {
			getView().loadErrorAlert("BadValue");
			return false;
		}
		try {
			myEnemyNumber = Integer.parseInt(myNumberTextField.getText());
		}
		catch(NumberFormatException e) {
			getView().loadErrorAlert("BadValue");
			return false;
		}
		return true;
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

	/**
	 * Do-nothing method for now - to refactor!
	 * @see frontend.Screen#makeScreenWithoutStyling()
	 */
	@Override
	public Parent makeScreenWithoutStyling() {
		// TODO Auto-generated method stub
		return null;
	}
}
