package authoring.frontend;

import java.util.ArrayList;
import java.util.List;

import com.sun.javafx.tools.packager.Log;

import authoring.frontend.exceptions.MissingPropertiesException;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * Class to create the tool bar component of the path authoring screen.
 * @author Erik Riis
 *
 */

public class CreatePathToolBar extends PathToolBar {

    //	public static final String BACKGROUND_IMAGES = "images/BackgroundImageNames.properties";
    public static final String BACKGROUND_IMAGE_PREFIX = "images/ThemeSpecificImages/BackGroundImages/";
    public static final String BACKGROUND_IMAGE_SUFFIX = "BackGroundImageNames.properties";
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


	//		HBox backGroundImageSelect = makeImageSelector("BackGround", "", BACKGROUND_IMAGE_PREFIX + getView().getTheme() + BACKGROUND_IMAGE_SUFFIX);
	//		HBox startImage = getUIFactory().setupImageSelector(getPropertiesReader(), "", propertiesFilepath, 50, getErrorCheckedPrompt("NewImage"), getErrorCheckedPrompt("LoadImage"),
	//				getErrorCheckedPrompt("NewImageName"),imageDropdown, imageDisplay);

	//		getToolBar().getChildren().addAll(backGroundImageSelect, startImageChooser, pathImageChooser, endImageChooser);
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

//    protected HBox makeImageSelector(String objectType, String imageName, String propertiesFilepath){
//	HBox imageSelect = new HBox();
//	ComboBox<String> imageDropdown = new ComboBox<String>();
//	ImageView imageDisplay = new ImageView(); 
//	try {
//	    imageDropdown = getUIFactory().makeTextDropdown("", getPropertiesReader().allKeys(propertiesFilepath));
//	} catch (MissingPropertiesException e) {
//	    Log.debug(e);
//	    getView().loadErrorScreen("NoImageFile");
//	} 
//	ComboBox<String> imageDropdownCopy = imageDropdown;
//	imageDropdown.addEventHandler(ActionEvent.ACTION,e -> {
//	    try {
////		getView().setObjectAttribute(objectType, mySelectedObjectName, "my" + imageName + "Image", getPropertiesReader().findVal(propertiesFilepath, imageDropdownCopy.getSelectionModel().getSelectedItem())); 
//	    }
//	    catch(MissingPropertiesException e2) {
//		Log.debug(e2);
//		getView().loadErrorScreen("NoImageFile");
//	    }
//	});
//
//	try {
//	    imageSelect = getUIFactory().setupImageSelector(getPropertiesReader(), "", propertiesFilepath, 50, getErrorCheckedPrompt("NewImage"), getErrorCheckedPrompt("LoadImage"),
//		    getErrorCheckedPrompt("NewImageName"),imageDropdown, imageDisplay);
////	    String key = getPropertiesReader().findKey(propertiesFilepath, (String)getView().getObjectAttribute(objectType, mySelectedObjectName, "myImage"));
//	    ActionEvent fakeSelection = new ActionEvent();
////	    if(key.equals("")) {
//		imageDropdown.getSelectionModel().select(0);
//	    }
//	    else {
//		imageDropdown.getSelectionModel().select(key);
//		imageDropdown.fireEvent(fakeSelection);
//	    }
//	} catch (MissingPropertiesException e) {
//	    Log.debug(e);
//	    getView().loadErrorScreen("NoImageFile");
//	}
//	return imageSelect;
//    }


    @Override
    public Parent makeScreenWithoutStyling() {
	//TODO Auto-generated method stub
	return null;
    }
}

