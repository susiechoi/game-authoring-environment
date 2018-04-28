package gameplayer.panel;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import gameplayer.screen.GameScreen;
import java.util.Map;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.Priority;
import jdk.internal.jline.internal.Log;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;

public class ScorePanel extends ListenerPanel {

	public static final String DEFAULT_DATE_FORMAT = "MM-dd-yyyy_hh-mm-ss"; 
	public static final String DEFAULT_SCORE_PATH = "Score/"; 
	public static final String DEFAULT_HEALTH_PATH = "Health/"; 
	public static final String DEFAULT_SCORE_IDENTIFIER = "Score"; 
	public static final String DEFAULT_HEALTH_IDENTIFIER = "Health";
	public static final String DEFAULT_DATAPOINTS_FILEPATH = "graphing/";
	public static final String DEFAULT_SHARED_STYLESHEET = "styling/SharedStyling.css";
	private PrintWriter myScoreWriter; 
	private long myScoreXIncrement;
	private PrintWriter myHealthWriter; 
	private long myHealthXIncrement;

	private final GameScreen GAME_SCREEN;
	private Map<String,String> GAMEPLAYER_PROPERTIES;

	private Label ScoreText;
	private Label LevelText;
	private Label HealthText;
	private Integer initialScore;
	private Integer initialHealth;


	public ScorePanel(GameScreen gameScreen) {
		GAME_SCREEN = gameScreen;
		GAMEPLAYER_PROPERTIES = GAME_SCREEN.getGameplayerProperties();

		setupWriters(); 
	}

	private void setupWriters() {
		initialScore = Integer.parseInt(GAMEPLAYER_PROPERTIES.get("defaultScore"));
		initialHealth = Integer.parseInt(GAMEPLAYER_PROPERTIES.get("defaultHealth"));
		Calendar c = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
		String formattedDate = df.format(c.getTime());

		try {
			String scoreFileName = DEFAULT_DATAPOINTS_FILEPATH+DEFAULT_SCORE_PATH+GAME_SCREEN.getGameName()+"_"+formattedDate; 
			File scoreFile = new File(scoreFileName);
			scoreFile.getParentFile().mkdirs(); 
			myScoreWriter = new PrintWriter(scoreFile);
		} catch (IOException e) {
			GAME_SCREEN.loadErrorScreen("NoFile");
		}

		try {
			String healthFileName = DEFAULT_DATAPOINTS_FILEPATH+DEFAULT_HEALTH_PATH+GAME_SCREEN.getGameName()+"_"+formattedDate;
			File healthFile = new File(healthFileName);
			healthFile.getParentFile().mkdirs(); 
			myHealthWriter = new PrintWriter(healthFile);
		} catch (IOException e) {
			Log.error(e);
			GAME_SCREEN.loadErrorScreen("NoFile");
		}
	}

	@Override
	public void makePanel() {
		//TODO Read words SCORE, LEVEL, and + from properties file
		ScoreText = new Label(GAMEPLAYER_PROPERTIES.get("scoreText") + initialScore);
		LevelText = new Label();
		HealthText = new Label(GAMEPLAYER_PROPERTIES.get("healthText") + initialHealth);

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
		recordDataPoint(DEFAULT_SCORE_IDENTIFIER, newScore);
		ScoreText.setText(GAMEPLAYER_PROPERTIES.get("scoreText") + newScore);
	}

	private void updateHealth(Integer newHealth) {
		recordDataPoint(DEFAULT_HEALTH_IDENTIFIER, newHealth);
		HealthText.setText(GAMEPLAYER_PROPERTIES.get("healthText")+ newHealth);
	}

	private void recordDataPoint(String toRecord, int newVal) {
		if (toRecord.equals(DEFAULT_SCORE_IDENTIFIER)) {
			myScoreXIncrement = System.currentTimeMillis() / 1000;
			myScoreWriter.write(Long.toString(myScoreXIncrement)+" ");
			myScoreWriter.write(Integer.toString(newVal)+"\n");
			myScoreWriter.flush();
		}
		if (toRecord.equals(DEFAULT_HEALTH_IDENTIFIER)) {
			myHealthXIncrement = System.currentTimeMillis() / 1000; 
			myHealthWriter.write(Long.toString(myHealthXIncrement)+" ");
			myHealthWriter.write(Integer.toString(newVal)+"\n");
			myHealthWriter.flush();
		}
	}


	public void updateLevel(Integer newLevel) {
		LevelText.setText(GAMEPLAYER_PROPERTIES.get("levelText")+ newLevel);
	}

	/**
	 * Wrapper method on score to reduce order of call dependencies
	 * @param score	initial score of the level
	 */
	private void setInitalScore(int score) {
		if(setInitalProperty(ScoreText, score, initialScore)) {
			updateScore(score);
		}
	}

	/**
	 * Wrapper method on score to reduce order of call dependencies
	 * @param score	initial health of the level
	 */
	private void setInitialHealth(int health) {
		if(setInitalProperty(HealthText, health, initialHealth)) {
			updateHealth(health);
		}
	}



	public ChangeListener<Number> createScoreListener(int startScore) {
		setInitalScore(startScore);
		return new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				updateScore((Integer)arg0.getValue());
			}
		};
	}

	public ChangeListener<Number> createHealthListener(int startHealth) {
		setInitialHealth(startHealth);
		return new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				updateHealth((Integer)arg0.getValue());
			}
		};
	}
}
