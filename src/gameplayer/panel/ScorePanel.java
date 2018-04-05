package gameplayer.panel;

import gameplayer.screen.GameScreen;
import javafx.geometry.Pos;
import javafx.scene.layout.Priority;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;

public class ScorePanel extends Panel{

    private final GameScreen GAME_SCREEN;
    private Integer SCORE;
    private Integer HEALTH;
    private Integer LEVEL;

    public ScorePanel(GameScreen gameScreen) {
	GAME_SCREEN = gameScreen;
	SCORE = 696969;
	HEALTH = 100;
	LEVEL = 4;
    }


    @Override
    public void makePanel() {

	//TODO Read words SCORE, LEVEL, and + from properties file
	Label ScoreText = new Label("Score: " + SCORE);
	Label LevelText = new Label("Level " + LEVEL);
	Label HealthText = new Label("+" + HEALTH);


	ScoreText.setMaxWidth(Double.MAX_VALUE);
	ScoreText.setAlignment(Pos.CENTER_LEFT);
	
	LevelText.setMaxWidth(Double.MAX_VALUE);
	LevelText.setAlignment(Pos.CENTER);
	
	HealthText.setMaxWidth(Double.MAX_VALUE);
	HealthText.setAlignment(Pos.BASELINE_RIGHT);
	
	HBox panelRoot = new HBox();

	HBox.setHgrow(ScoreText, Priority.ALWAYS);
	HBox.setHgrow(LevelText, Priority.ALWAYS);
	HBox.setHgrow(HealthText, Priority.ALWAYS);
	panelRoot.getChildren().addAll(ScoreText, LevelText, HealthText);
	
	panelRoot.setMaxWidth(Double.MAX_VALUE);
	panelRoot.setMaxHeight(Double.MAX_VALUE);

	PANEL = panelRoot;
    }
}
