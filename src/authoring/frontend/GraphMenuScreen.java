package authoring.frontend;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import frontend.Screen;
import frontend.View;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

public class GraphMenuScreen extends Screen {

	public static final String DEFAULT_GRAPHS_FOLDER = "graphing";

	@Override
	public Parent makeScreenWithoutStyling() {
		VBox vb = new VBox();

		List<String> availableGraphs = getFileNames(DEFAULT_GRAPHS_FOLDER);

		for (String graph : availableGraphs) {
			vb.getChildren().add(getUIFactory().makeTextButton("", graph));
		}
 
		return vb; 
	}

	
	@Override
	protected View getView() {
		// TODO Auto-generated method stub
		return null;
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