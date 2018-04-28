package authoring.frontend.graphing;

import authoring.frontend.AuthoringView;
import javafx.scene.Parent;
import javafx.scene.chart.LineChart;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class DoubleGraphScreen extends GraphScreen {

	private String myGame1FilePath; 
	private String myGame2FilePath; 
	private String myGraphType; 

	protected DoubleGraphScreen(AuthoringView view, String game1Path, String game2Path, String graphType) {
		super(view);
		myGame1FilePath = game1Path; 
		myGame2FilePath = game2Path; 
		myGraphType = graphType; 
	}

	@Override
	public Parent makeScreenWithoutStyling() {
		VBox vb = new VBox();

		Text graphTitle = getUIFactory().makeScreenTitleText(getErrorCheckedPrompt("GraphTitle"));
		LineChart<Number, Number> graph = makeGraph(parseTitle(myGame1FilePath)); 
		addPointsToGraph(myGame1FilePath, graph);
		LineChart<Number, Number> graph2 = makeGraph(parseTitle(myGame2FilePath)); 
		addPointsToGraph(myGame2FilePath, graph2);
		
		vb.getChildren().addAll(graphTitle, graph, graph2, setupBackButtonCustom(e -> {
			getView().getStageManager().switchScreen(new GraphMenuScreen(getView(), myGraphType).getScreen());
		}));

		return vb;
	}

}
