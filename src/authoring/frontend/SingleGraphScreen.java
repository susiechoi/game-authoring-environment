package authoring.frontend;

import javafx.scene.Parent;
import javafx.scene.chart.LineChart;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class SingleGraphScreen extends GraphScreen {
	
	private String myGameFilepath; 

	protected SingleGraphScreen(AuthoringView view, String filepath) {
		super(view);
		myGameFilepath = filepath; 
		setSaved(); 
	}

	@Override
	public Parent makeScreenWithoutStyling() {	
		VBox vb = new VBox();
		
		Text graphTitle = getUIFactory().makeScreenTitleText(getErrorCheckedPrompt("GraphTitle"));
		LineChart<Number, Number> graph = makeGraph(parseTitle(myGameFilepath)); 
		addPointsToGraph(myGameFilepath, graph);
		vb.getChildren().addAll(graphTitle, graph, setupBackButton());
		
		return vb;
	}
}
