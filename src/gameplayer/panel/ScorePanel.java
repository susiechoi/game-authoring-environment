
package gameplayer.panel;

import gameplayer.screen.GameScreen;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

public class ScorePanel extends Panel {

	private final String DEFAULT_SHARED_STYLESHEET = "styling/SharedStyling.css";

	private final GameScreen GAME_SCREEN;
	private Integer SCORE;
	private Integer HEALTH;
	private Integer LEVEL;
	private Label ScoreText;
	private Label LevelText;
	private Label HealthText;
	
	private NumberAxis myX; 
	private NumberAxis myY;
	private LineChart<Number, Number> myLineChart; 
	private XYChart.Series mySeries; 
	private int myXIncrement; 
	
	public ScorePanel(GameScreen gameScreen) {
		GAME_SCREEN = gameScreen;
		SCORE = 0;
		HEALTH = 100;
		LEVEL = 1;
		
		//TODO Read words SCORE, LEVEL, and + from properties file
		ScoreText = new Label("Score: " + SCORE);
		LevelText = new Label("Level " + LEVEL);
		HealthText = new Label("+" + HEALTH);
		
		setupScoreGraphing();
	}
	
	private void setupScoreGraphing() {
		Stage graphingStage = new Stage(); 
		myX = new NumberAxis();
		myY = new NumberAxis(); 
		myLineChart = new LineChart<Number, Number>(myX, myY);
		mySeries = new XYChart.Series<>(); 
		myLineChart.getData().add(mySeries); 
		Scene graphingScene = new Scene(myLineChart, 800, 600);
		graphingStage.setScene(graphingScene);
		graphingStage.show();
	}


	@Override
	public void makePanel() {
		ScoreText.setMaxWidth(Double.MAX_VALUE);
		LevelText.setMaxWidth(Double.MAX_VALUE);
		HealthText.setMaxWidth(Double.MAX_VALUE);
		
		HBox panelRoot = new HBox();

		HBox.setHgrow(ScoreText, Priority.ALWAYS);
		HBox.setHgrow(LevelText, Priority.ALWAYS);
		HBox.setHgrow(HealthText, Priority.ALWAYS);
		panelRoot.getChildren().addAll(ScoreText, LevelText, HealthText);

		panelRoot.setMaxWidth(Double.MAX_VALUE);
		panelRoot.setMaxHeight(Double.MAX_VALUE);
		panelRoot.getStylesheets().add(DEFAULT_SHARED_STYLESHEET);
		PANEL = panelRoot;
	}

	public void updateScore(Integer newScore) {
		myXIncrement++; 
		mySeries.getData().add(new XYChart.Data(myXIncrement, newScore));
		ScoreText.setText("Score: " + newScore);
	}

	public void updateHealth(double  myHealth) {
		HealthText.setText("+" + Double.valueOf(Math.floor(myHealth)).intValue());
	}

	public void updateLevel(Integer newLevel) {
		LevelText.setText("Level: " + newLevel);
	}
}
