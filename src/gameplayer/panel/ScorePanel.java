
package gameplayer.panel;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import gameplayer.screen.GameScreen;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class ScorePanel extends Panel {

	public static final String DEFAULT_DATAPOINTS_FILEPATH = "graphing/";
	public static final String DEFAULT_SHARED_STYLESHEET = "styling/SharedStyling.css";

	private final GameScreen GAME_SCREEN;
	private Integer SCORE;
	private Integer HEALTH;
	private Integer LEVEL;
	private Label ScoreText;
	private Label LevelText;
	private Label HealthText;

	private PrintWriter myScoreWriter; 
	private int myScoreXIncrement;

	public ScorePanel(GameScreen gameScreen) {
		GAME_SCREEN = gameScreen;
		SCORE = 0;
		HEALTH = 100;
		LEVEL = 1;

		//TODO Read words SCORE, LEVEL, and + from properties file
		ScoreText = new Label("Score: " + SCORE);
		LevelText = new Label("Level " + LEVEL);
		HealthText = new Label("+" + HEALTH);
		
		Calendar c = Calendar.getInstance();
	    SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy_hh:mm:ss");
	    String formattedDate = df.format(c.getTime());
	   
		try {
			myScoreWriter = new PrintWriter(new FileWriter(DEFAULT_DATAPOINTS_FILEPATH+GAME_SCREEN.getGameName()+"_"+formattedDate), true);
		} catch (IOException e) {
			GAME_SCREEN.loadErrorScreen("NoFile");
		}
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
		myScoreXIncrement++; 

		myScoreWriter.write(Integer.toString(myScoreXIncrement)+" ");
		myScoreWriter.write(Integer.toString(newScore)+"\n");
		
		if (myScoreXIncrement % 1000 == 0) {
			myScoreWriter.flush();
		}
		
		ScoreText.setText("Score: " + newScore);
	}

	public void updateHealth(double newHealth) {
		HealthText.setText("+" + Double.valueOf(Math.floor(newHealth)).intValue());
	}

	public void updateLevel(Integer newLevel) {
		LevelText.setText("Level: " + newLevel);
	}
}
