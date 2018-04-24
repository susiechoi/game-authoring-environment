
package gameplayer.panel;

import gameplayer.screen.GameScreen;
import javafx.scene.layout.Priority;
import javafx.scene.layout.HBox;
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

	public ScorePanel(GameScreen gameScreen) {
		GAME_SCREEN = gameScreen;
		SCORE = 0;
		HEALTH = 100;
		LEVEL = 1;
		
		//TODO Read words SCORE, LEVEL, and + from properties file
		ScoreText = new Label("Score: " + SCORE);
		LevelText = new Label("Level " + LEVEL);
		HealthText = new Label("+" + HEALTH);
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
		ScoreText.setText("Score: " + newScore);
	}

	public void updateHealth(double  myHealth) {
		HealthText.setText("+" + Double.valueOf(Math.floor(myHealth)).intValue());
	}

	public void updateLevel(Integer newLevel) {
		LevelText.setText("Level: " + newLevel);
	}
}
