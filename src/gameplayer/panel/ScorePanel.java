package gameplayer.panel;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import gameplayer.screen.GameScreen;
import java.util.Map;
import gameplayer.screen.GameScreen;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.Priority;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;

public class ScorePanel extends Panel {

	public static final String DEFAULT_DATAPOINTS_FILEPATH = "graphing/";
	public static final String DEFAULT_SHARED_STYLESHEET = "styling/SharedStyling.css";
	private PrintWriter myScoreWriter; 
	private long myScoreXIncrement;

	private final GameScreen GAME_SCREEN;
	private Map<String,String> GAMEPLAYER_PROPERTIES;

	private Label ScoreText;
	private Label LevelText;
	private Label HealthText;


	public ScorePanel(GameScreen gameScreen) {
		GAME_SCREEN = gameScreen;
		GAMEPLAYER_PROPERTIES = GAME_SCREEN.getGameplayerProperties();
		makePanel();

		Calendar c = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy_hh-mm-ss");
		String formattedDate = df.format(c.getTime());

		try {
			myScoreWriter = new PrintWriter(new FileWriter(DEFAULT_DATAPOINTS_FILEPATH+GAME_SCREEN.getGameName()+"_"+formattedDate), true);
		} catch (IOException e) {
			GAME_SCREEN.loadErrorScreen("NoFile");
		}
	}



	@Override
	public void makePanel() {

		//TODO Read words SCORE, LEVEL, and + from properties file
		ScoreText = new Label();
		LevelText = new Label();
		HealthText = new Label();

		ScoreText.setMaxWidth(Double.MAX_VALUE);

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
		PANEL = panelRoot;
	}

	private void updateScore(Integer newScore) {		
		myScoreXIncrement = System.currentTimeMillis() / 1000; 
		myScoreWriter.write(Long.toString(myScoreXIncrement)+" ");
		myScoreWriter.write(Integer.toString(newScore)+"\n");

		myScoreWriter.flush();

		ScoreText.setText(GAMEPLAYER_PROPERTIES.get("scoreText") + newScore);
	}

	private void updateHealth(Integer newHealth) {
		HealthText.setText(GAMEPLAYER_PROPERTIES.get("healthText")+ newHealth);
	}


	public void updateLevel(Integer newLevel) {
		LevelText.setText(GAMEPLAYER_PROPERTIES.get("levelText")+ newLevel);
	}


	public ChangeListener<Number> createScoreListener(int startScore) {
		updateScore(startScore);
		return new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				updateScore((Integer)arg0.getValue());
			}
		};
	}

	public ChangeListener<Number> createHealthListener(int startHealth) {
		updateHealth(startHealth);
		return new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				updateHealth((Integer)arg0.getValue());
			}
		};
	}
}
