package authoring.frontend;

import com.sun.javafx.tools.packager.Log;

import java.util.ArrayList;
import java.util.List;

import com.sun.javafx.tools.packager.Log;

import authoring.frontend.exceptions.MissingPropertiesException;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;

/**
 * Abstract class representing the bottom toolbar portion of screens displaying a Path.
 * Used to add basic sizing functionality to those toolbars and extended to provide other 
 * functionality.
 * @author Sarahbland
 *
 */
public abstract class PathToolBar extends AdjustScreen {
	public static final String DEFAULT_PLUS_IMAGE = "file:images/plus.png";
	public static final String DEFAULT_MINUS_IMAGE = "file:images/minus.png";
	private List<Button> mySizingButtons;
	private Button myPlusButton;
	private Button myMinusButton;
	private HBox myPathToolBar;
	public PathToolBar(AuthoringView view) {
		super(view);
		mySizingButtons = new ArrayList<>();
		setUpSizing();
		makePanel();
	}
	
	protected void setUpSizing() {
		myPathToolBar = new HBox();
		try {
		myPathToolBar.setMaxSize(Integer.parseInt(getPropertiesReader().findVal(DEFAULT_CONSTANTS_FILEPATH, "ToolbarHeight")), Integer.parseInt(getPropertiesReader().findVal(DEFAULT_CONSTANTS_FILEPATH, "ToolbarWidth")));
		}
		catch(MissingPropertiesException e) {
		    Log.debug(e);
		    getView().loadErrorScreen("NoFile");
		}
		myPathToolBar.getStylesheets();
		myPathToolBar.getChildren().add(makeSizingButtons());
	}
	
	protected HBox getToolBar() {
		return myPathToolBar;
	}
	
	protected HBox getPanel() { //TODO: fix this!!
		return myPathToolBar;
	}
	
	protected abstract void makePanel();
	
	protected HBox makeSizingButtons() {
	    try {
	    	int buttonWidth = Integer.parseInt(getPropertiesReader().findVal(DEFAULT_CONSTANTS_FILEPATH, "SizeButtonWidth"));
		int buttonHeight = Integer.parseInt(getPropertiesReader().findVal(DEFAULT_CONSTANTS_FILEPATH, "SizeButtonHeight"));
	    	HBox hb = new HBox();
		Image plusImg = new Image(DEFAULT_PLUS_IMAGE, buttonWidth, buttonHeight, true, false);
		myPlusButton = getUIFactory().makeImageButton("plusButton", plusImg);
		mySizingButtons.add(myPlusButton);

		Image minusImg = new Image(DEFAULT_MINUS_IMAGE, buttonWidth, buttonHeight, true, false);
		myMinusButton = getUIFactory().makeImageButton("minusButton", minusImg);
		mySizingButtons.add(myMinusButton);
		hb.getChildren().addAll(mySizingButtons);
		return hb;
	    }
	    catch(MissingPropertiesException e) {
		 Log.debug(e);
		getView().loadErrorScreen("NoConstants");
		return null;
	    }
	}
	
	protected Button getPlusButton() {
		return myPlusButton;
	}

	protected Button getMinusButton() {
		return myMinusButton;
	}
}
