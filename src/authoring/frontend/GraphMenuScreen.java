package authoring.frontend;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import authoring.AuthoringModel;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class GraphMenuScreen extends AuthoringScreen {

	public static final String DEFAULT_GRAPHS_FOLDER = "graphing";
	public static final String DEFAULT_CSS = "styling/GameAuthoringStartScreen.css";
	
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
		List<String> relevantGraphs = new ArrayList<String>(); 

		for (String graphName : availableGraphs) {
			System.out.println(graphName);
			String currGameName = getView().getGameName();
			if (graphName.indexOf(currGameName) > -1) {
				relevantGraphs.add(graphName);
				String abbrevGraphName = graphName.substring(graphName.indexOf(currGameName)+currGameName.length()); 
				Button relevantGraphButt = getUIFactory().makeTextButton("", abbrevGraphName); 
				relevantGraphButt.setOnAction(e -> {
					String fullFilepath  = DEFAULT_GRAPHS_FOLDER+"/"+graphName; 
					getView().getStageManager().switchScreen(new SingleGraphScreen(getView(), fullFilepath).getScreen());
				});
				vb.getChildren().add(relevantGraphButt);
			}
		}
		
		vb.getChildren().add(new Text(getErrorCheckedPrompt("or")));
		
		Button compareButton = getUIFactory().makeTextButton("", getErrorCheckedPrompt("CompareGames"));
		String choosePrompt = getView().getErrorCheckedPrompt("ChooseGame");
		List<String> dropdownOptions = new ArrayList<String>();
		dropdownOptions.add(choosePrompt);
		dropdownOptions.addAll(relevantGraphs);
		ComboBox<String> game1Chooser = getUIFactory().makeTextDropdownSelectAction("", dropdownOptions, e -> {
			;}, e -> {compareButton.setDisable(true);}, choosePrompt);
		ComboBox<String> game2Chooser = getUIFactory().makeTextDropdownSelectAction("", dropdownOptions, e -> {
			compareButton.setDisable(false);}, e -> {compareButton.setDisable(true);}, choosePrompt);
		compareButton.setDisable(true);
		compareButton.setOnAction(e -> {
			String game1Path = DEFAULT_GRAPHS_FOLDER+"/"+game1Chooser.getSelectionModel().getSelectedItem(); 
			String game2Path = DEFAULT_GRAPHS_FOLDER+"/"+game2Chooser.getSelectionModel().getSelectedItem(); 
			getView().getStageManager().switchScreen(new DoubleGraphScreen(getView(), game1Path, game2Path).getScreen());
		});

		vb.getChildren().addAll(game1Chooser, game2Chooser, compareButton);
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