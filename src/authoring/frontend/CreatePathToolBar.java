package authoring.frontend;


import com.sun.javafx.tools.packager.Log;
import authoring.frontend.exceptions.MissingPropertiesException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Class to create the tool bar component of the path authoring screen.
 * @author Erik Riis
 *
 */

public class CreatePathToolBar extends PathToolBar {

    public static final String BACKGROUND_IMAGE_PREFIX = "images/ThemeSpecificImages/BackGroundImages/";
    public static final String BACKGROUND_IMAGE_SUFFIX = "BackGroundImageNames.properties";
    public static final String PATH_IMAGE_PREFIX = "images/ThemeSpecificImages/PathImages/";
    public static final String PATH_IMAGE_SUFFIX = "PathImageNames.properties";
    public static final String START_IMAGE_PREFIX = "images/ThemeSpecificImages/StartImages/";
    public static final String START_IMAGE_SUFFIX = "StartImageNames.properties";
    public static final String END_IMAGE_PREFIX = "images/ThemeSpecificImages/EndImages/";
    public static final String END_IMAGE_SUFFIX = "EndImageNames.properties";
    public static final int TOOLBAR_HEIGHT = 120;
    public static final int TOOLBAR_WIDTH = 600;
    private HBox startImageSelect;
    private HBox endImageSelect;
    private HBox pathImageSelect;
    private HBox backgroundImageSelect;
    private AuthoringView myView;

    public CreatePathToolBar(AuthoringView view) {
	super(view);
	myView = view;
    }

    @Override
    public void makePanel() {

	backgroundImageSelect = makeImageSelector("BackGround", "", BACKGROUND_IMAGE_PREFIX + getView().getTheme() + BACKGROUND_IMAGE_SUFFIX);
	pathImageSelect = makeImageSelector("BackGround", "", PATH_IMAGE_PREFIX + getView().getTheme() + PATH_IMAGE_SUFFIX);
	startImageSelect = makeImageSelector("BackGround", "", START_IMAGE_PREFIX + getView().getTheme() + START_IMAGE_SUFFIX);
	endImageSelect = makeImageSelector("BackGround", "", END_IMAGE_PREFIX + getView().getTheme() + END_IMAGE_SUFFIX);

	Button backgroundImageButton = getUIFactory().makeTextButton("", "Choose Background Image");
	setImageButtonEvent(backgroundImageButton, backgroundImageSelect);

	Button pathImageButton = getUIFactory().makeTextButton("", "Choose Path Image");
	setImageButtonEvent(pathImageButton, pathImageSelect);

	Button startImageButton = getUIFactory().makeTextButton("", "Choose Start Image");
	setImageButtonEvent(startImageButton, startImageSelect);

	Button endImageButton = getUIFactory().makeTextButton("", "Choose End Image");
	setImageButtonEvent(endImageButton, endImageSelect);

	getToolBar().getChildren().addAll(backgroundImageButton, startImageButton, pathImageButton, endImageButton);
    }

    public HBox getPathHBox() {
	return pathImageSelect;
    }

    public HBox getStartHBox() {
	return startImageSelect;
    }

    public HBox getEndHBox() {
	return endImageSelect;
    }

    public HBox getBackgroundHBox() {
	return backgroundImageSelect;
    }

    private void setImageButtonEvent(Button button, HBox imageSelector) {
	button.setOnAction(new EventHandler<ActionEvent>() {
	    @Override
	    public void handle(ActionEvent event) {
		Stage dialog = new Stage();
		VBox dialogVbox = new VBox();
		//			dialogVbox.setMaxSize(600, 300);
		dialogVbox.getChildren().add(imageSelector);
		Scene dialogScene = new Scene(dialogVbox);
		dialogScene.getStylesheets().add(myView.getCurrentCSS());
		dialog.setScene(dialogScene);
		dialog.show();
	    }
	});
    }

    protected HBox makeImageSelector(String objectType, String imageName, String propertiesFilepath){
	HBox imageSelect = new HBox();
	ComboBox<String> imageDropdown = new ComboBox<String>();
	ImageView imageDisplay = new ImageView(); 
	try {
	    imageDropdown = getUIFactory().makeTextDropdown("", getPropertiesReader().allKeys(propertiesFilepath));
	} catch (MissingPropertiesException e) {
	    Log.debug(e);
	    getView().loadErrorScreen("NoImageFile");
	} 
	ComboBox<String> imageDropdownCopy = imageDropdown;
	imageDropdown.addEventHandler(ActionEvent.ACTION,e -> {
	 
	    //	    try {
	    //		getView().setObjectAttribute(objectType, mySelectedObjectName, "my" + imageName + "Image", getPropertiesReader().findVal(propertiesFilepath, imageDropdownCopy.getSelectionModel().getSelectedItem())); 
	    //	    }
	    //	    catch(MissingPropertiesException e2) {
	    //		Log.debug(e2);
	    //		getView().loadErrorScreen("NoImageFile");
	    //	    }
	});

	try {
	    imageSelect = getUIFactory().setupImageSelector(getPropertiesReader(), "", propertiesFilepath, 50, getErrorCheckedPrompt("NewImage"), getErrorCheckedPrompt("LoadImage"),
		    getErrorCheckedPrompt("NewImageName"),imageDropdown, imageDisplay);
	    //	    String key = getPropertiesReader().findKey(propertiesFilepath, (String)getView().getObjectAttribute(objectType, mySelectedObjectName, "myImage"));
	    //	    ActionEvent fakeSelection = new ActionEvent();
	    //	    if(key.equals("")) {
	    //		imageDropdown.getSelectionModel().select(0);
	    //	    }
	    //	    else {
	    //		imageDropdown.getSelectionModel().select(key);
	    //		imageDropdown.fireEvent(fakeSelection);
	    //	    }
	} catch (MissingPropertiesException e) {
	    Log.debug(e);
	    getView().loadErrorScreen("NoImageFile");
	}
	return imageSelect;
    }


    @Override
    public Parent makeScreenWithoutStyling() {
	//TODO Auto-generated method stub
	return null;
    }
}

