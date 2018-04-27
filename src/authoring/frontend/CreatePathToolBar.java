package authoring.frontend;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.Parent;
import javafx.scene.control.Button;

/**
 * Class to create the tool bar component of the path authoring screen.
 * @author Erik Riis
 *
 */

public class CreatePathToolBar extends PathToolBar {

	public static final String BACKGROUND_IMAGES = "images/BackgroundImageNames.properties";
	private Button startImageChooser;
	private Button endImageChooser;
	private Button pathImageChooser;
	private Button backgroundButton;
	private List<Button> myImageButtons;

	public CreatePathToolBar(AuthoringView view) {
		super(view);
	}


	@Override
	public void makePanel() {
		myImageButtons = new ArrayList<>();
		backgroundButton = getUIFactory().makeTextButton("", "Choose Background Image");

		pathImageChooser = getUIFactory().makeTextButton("", "Choose Path Image");
		myImageButtons.add(pathImageChooser);

		startImageChooser = getUIFactory().makeTextButton("", "Choose Start Image");
		myImageButtons.add(startImageChooser);

		endImageChooser = getUIFactory().makeTextButton("", "Choose End Image");
		myImageButtons.add(endImageChooser);

		getToolBar().getChildren().addAll(backgroundButton, startImageChooser, pathImageChooser, endImageChooser);
	}

	public Button getPathImageButton() {
		return pathImageChooser;
	}

	public Button getStartImageButton() {
		return startImageChooser;
	}

	public Button getEndImageButton() {
		return endImageChooser;
	}

	public Button getBackgroundButton() {
		return backgroundButton;
	}


	@Override
	public Parent makeScreenWithoutStyling() {
		//TODO Auto-generated method stub
		return null;
	}

}

