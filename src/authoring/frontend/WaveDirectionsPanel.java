package authoring.frontend;

import java.util.List;
import java.util.Map;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class WaveDirectionsPanel extends PathPanel{
    private String myWaveNumber;
	private VBox myRoot;
  public WaveDirectionsPanel(AuthoringView view, String waveNumber) {
      	super(view);
      	if(waveNumber.equals("Default")) {
	    waveNumber = ((Integer)(getView().getHighestWaveNumber(getView().getLevel()))).toString();
	}
	myWaveNumber = waveNumber;
	setUpPanel();
  }
  private void setUpPanel() {
	myRoot = new VBox();
	myRoot.setMaxSize(280, 900);
	VBox pseudoRoot = new VBox();
	Label directions = new Label("Test directions"); //TODO
	pseudoRoot.getChildren().add(directions);
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
