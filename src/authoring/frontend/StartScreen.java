package authoring.frontend;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import authoring.frontend.exceptions.MissingPropertiesException;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * Class to create the original screen users see when entering the Game Authoring environment. 
 * Dependent on the AuthoringView to create it correctly and on the PartsFactory to make UI
 * element
 * @author Sarahbland
 *
 *
 */
public class StartScreen extends AuthoringScreen {
    public static final String DEFAULT_XML_FOLDER = "/SavedModels";
    public static final String DEFAULT_STYLINGS  = "src/styling/CurrentCSS.properties";
    private AuthoringView myView; 
    private final List<String> myCSSFiles; 
    private int currCSSIndex; 
    
    protected StartScreen(AuthoringView view) {
	super(view);
	myView = view; 
	List<String> css = null;
	try {
		css = myView.getPropertiesReader().findVals(DEFAULT_STYLINGS);
	} catch (MissingPropertiesException e) {
		myView.loadErrorScreen("NoCSS");
	} 
	myCSSFiles = css; 
	currCSSIndex = 0; 
    }
    
    @Override
    public Parent makeScreenWithoutStyling() {
	Text startHeading = new Text();
	VBox vbox = new VBox();
	startHeading = getUIFactory().makeScreenTitleText(getErrorCheckedPrompt("StartScreenHeader"));
	List<String> dummyGameNames = new ArrayList<>();
	String gameNamePrompt = getErrorCheckedPrompt("GameEditSelector");
	dummyGameNames = getFileNames(DEFAULT_XML_FOLDER);
	Button newGameButton = new Button();
	String newGameButtonPrompt = getErrorCheckedPrompt("NewGameButtonLabel");
	newGameButton = getUIFactory().makeTextButton("editbutton", newGameButtonPrompt);
	newGameButton.setOnAction(e -> {
	    getView().goForwardFrom(this.getClass().getSimpleName()+"New", getErrorCheckedPrompt("NewGame"));
	});
	Button editButton = getUIFactory().makeTextButton("editbutton", getErrorCheckedPrompt("EditButtonLabel"));
	ComboBox<String> gameChooser = getUIFactory().makeTextDropdownSelectAction("", dummyGameNames, e -> {
	    editButton.setDisable(false);}, e -> {editButton.setDisable(true);}, gameNamePrompt);
	editButton.setDisable(true);
	editButton.setOnAction(e -> {getView().readFromFile(gameChooser.getValue());
	    getView().goForwardFrom(this.getClass().getSimpleName()+"Edit", gameChooser.getValue());});
	Button changeCSS = getUIFactory().makeTextButton("cssbutton", getErrorCheckedPrompt("ChangeStyling"));
	changeCSS.setOnAction(e -> {
		currCSSIndex++; 
		if (currCSSIndex > myCSSFiles.size()-1) {
			currCSSIndex = 0; 
		}
		System.out.println("change to "+myCSSFiles.get(currCSSIndex));
		myView.setCurrentCSS(myCSSFiles.get(currCSSIndex));
	});
	vbox.getChildren().add(startHeading);
	vbox.getChildren().add(newGameButton);
	vbox.getChildren().add(gameChooser);
	vbox.getChildren().add(editButton);
	vbox.getChildren().add(changeCSS);
	return vbox;

    }
    //Method by Ben Hodgson/Andrew Arnold!
    public List<String> getFileNames(String folderName) {
	String currentDir = System.getProperty("user.dir");
	try {
	    File file = new File(currentDir + File.separator + folderName);
	    File[] fileArray = file.listFiles();
	    List<String> fileNames = new ArrayList<String>();
	    for (File aFile : fileArray) {
		String colorName = aFile.getName();
		String[] nameSplit = colorName.split("\\.");
		String fileName = nameSplit[0];
		fileNames.add(fileName);
	    }
	    return Collections.unmodifiableList(fileNames);
	}
	catch (Exception e) {
	   getView().loadErrorScreen("NoFile");
	}
	return Collections.unmodifiableList(new ArrayList<String>());
    }

}
