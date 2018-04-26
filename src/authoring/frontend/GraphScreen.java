package authoring.frontend;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import frontend.Screen;
import frontend.View;
import javafx.scene.Parent;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class GraphScreen extends AuthoringScreen {

	public static final String DEFAULT_CSS = "styling/GameAuthoringStartScreen.css";
	private String myGraphFilepath; 
	
	protected GraphScreen(AuthoringView view, String filepath) {
		super(view);
		myGraphFilepath = filepath; 
		setSaved(); 
	}

	@Override
	public Parent makeScreenWithoutStyling() {	
		VBox vb = new VBox();
		
		Text graphTitle = getUIFactory().makeScreenTitleText(getErrorCheckedPrompt("GraphTitle"));
		
		NumberAxis x = new NumberAxis();
		x.setLabel(getErrorCheckedPrompt("Time"));
		x.setTickLabelsVisible(false);
		NumberAxis y = new NumberAxis(); 
		y.setLabel(getErrorCheckedPrompt("Score"));
		LineChart<Number, Number> graph = new LineChart<Number, Number>(x, y);
		Series series = new XYChart.Series<>(); 
		graph.getData().add(series); 
		graph.setLegendVisible(false);

		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(myGraphFilepath));
		} catch (FileNotFoundException e) {
			getView().loadErrorScreen("NoGraph");
		}
		String point = null;
		try {
			while ((point = br.readLine()) != null) {
				String[] xyCoors = point.split("\\s+");
				if (xyCoors.length == 2) {
					series.getData().add(new XYChart.Data(Integer.parseInt(xyCoors[0]), Integer.parseInt(xyCoors[1])));
				}
			}
		} catch (NumberFormatException | IOException e) {
			getView().loadErrorAlert("InvalidValues");
		}
		
		vb.getChildren().addAll(graphTitle, graph, setupBackButton());
		
		return vb;
	}

}