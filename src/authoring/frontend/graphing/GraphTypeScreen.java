package authoring.frontend.graphing;

import java.util.ArrayList;
import java.util.List;

import authoring.frontend.AuthoringScreen;
import authoring.frontend.AuthoringView;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class GraphTypeScreen extends AuthoringScreen {
	
	public static final String DEFAULT_GRAPHING_FOLDER = "graphing/";

	public GraphTypeScreen(AuthoringView view) {
		super(view);
		setSaved(); 
	}

	@Override
	public Parent makeScreenWithoutStyling() {
		VBox vb = new VBox(); 
		
		String title = getErrorCheckedPrompt("GraphTopicTitle"); 
		Text titleText = getUIFactory().makeScreenTitleText(title);
		
		List<String> graphOptions = new ArrayList<String>();
		graphOptions.add(title);
		graphOptions.addAll(getUIFactory().getFileNames(DEFAULT_GRAPHING_FOLDER));
		Button graphButton = getUIFactory().makeTextButton("", getErrorCheckedPrompt("GraphTopic"));
		ComboBox<String> dropdown = getUIFactory().makeTextDropdownSelectAction("", graphOptions, 
				e -> {graphButton.setDisable(false);}, 
				e -> {graphButton.setDisable(true);}, title);
		graphButton.setDisable(true);
		graphButton.setOnAction(e -> {
			getView().getStageManager().switchScreen(new GraphMenuScreen(getView(), dropdown.getSelectionModel().getSelectedItem()).getScreen());
		});
		
		vb.getChildren().addAll(titleText, dropdown, graphButton, setupBackButton());
		return vb;
		
	}

}
