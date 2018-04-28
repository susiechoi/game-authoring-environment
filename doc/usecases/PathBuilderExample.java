package usecases;

import api.Panel;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;


public class PathBuilderExample implements Panel {

	private Node examplePanel;

	public PathBuilderExample(){
		examplePanel = initPanel();
	}

	private Node initPanel() {
		Pane myPanel = new Pane();
		Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

		myPanel.setPrefHeight(primaryScreenBounds.getHeight());
		myPanel.setPrefWidth(primaryScreenBounds.getWidth()/4); //Panel will fit part of the left side of the screen
		return myPanel;
	}
	
	private void populatePanel() {
		//add buttons for start, end, middle, and drag and drop options for each path type
	}
	
	public Node getPanel() {
		return examplePanel;
	}

	@Override
	public void makePanel() {
		// TODO Auto-generated method stub
		
	}
}
