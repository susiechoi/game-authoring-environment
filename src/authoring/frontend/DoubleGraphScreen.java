package authoring.frontend;

import javafx.scene.Parent;
import javafx.scene.chart.LineChart;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class DoubleGraphScreen extends GraphScreen {

	private String myGame1FilePath; 
	private String myGame2FilePath; 

	protected DoubleGraphScreen(AuthoringView view, String game1Path, String game2Path) {
		super(view);
		myGame1FilePath = game1Path; 
		myGame2FilePath = game2Path; 
	}

	@Override
	public Parent makeScreenWithoutStyling() {
		VBox vb = new VBox();

		Text graphTitle = getUIFactory().makeScreenTitleText(getErrorCheckedPrompt("GraphTitle"));
		LineChart<Number, Number> graph = makeGraph(true); 
		addPointsToGraph(myGame1FilePath);
		addPointsToGraph(myGame2FilePath);
		
		vb.getChildren().addAll(graphTitle, graph, setupBackButton());

		return vb;
	}

}
