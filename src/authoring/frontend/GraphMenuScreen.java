package authoring.frontend;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import authoring.AuthoringModel;
import frontend.Screen;
import frontend.View;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class GraphMenuScreen extends AuthoringScreen {

	public static final String DEFAULT_GRAPHS_FOLDER = "graphing";
	public static final String DEFAULT_CSS = "styling/GameAuthoringStartScreen.css";

	private AuthoringView myView; 
	
	protected GraphMenuScreen(AuthoringView view, AuthoringModel model) {
		super(view);
		setSaved();
	}

	@Override
	public Parent makeScreenWithoutStyling() {
		VBox vb = new VBox();

		Text screenTitle = getUIFactory().makeScreenTitleText(getErrorCheckedPrompt("GraphMenu"));
		vb.getChildren().add(screenTitle);
		
		List<String> availableGraphs = getFileNames(DEFAULT_GRAPHS_FOLDER);

		for (String graphName : availableGraphs) {
			if (graphName.indexOf(getView().getGameName()) > -1) {
				Button relevantGraph = getUIFactory().makeTextButton("", graphName); 
				relevantGraph.setOnAction(e -> {
					getView().goForwardFrom(this.getClass().getSimpleName()+"Graph", DEFAULT_GRAPHS_FOLDER+"/"+graphName);
				});
				vb.getChildren().add(relevantGraph);
			}
		}
		
		vb.getChildren().add(setupBackButton());

		return vb; 
	}


	//Method by Ben Hodgson/Andrew Arnold!
	private List<String> getFileNames(String folderName) {
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