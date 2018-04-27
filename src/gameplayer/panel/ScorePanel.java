
package gameplayer.panel;

import java.util.Map;
import gameplayer.screen.GameScreen;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.Priority;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;

public class ScorePanel extends Panel {

    private final GameScreen GAME_SCREEN;
    private Map<String,String> GAMEPLAYER_PROPERTIES;

    private Label ScoreText;
    private Label LevelText;
    private Label HealthText;


    public ScorePanel(GameScreen gameScreen) {
		GAME_SCREEN = gameScreen;
		GAMEPLAYER_PROPERTIES = GAME_SCREEN.getGameplayerProperties();
		makePanel();
	}



    @Override
    public void makePanel() {

	//TODO Read words SCORE, LEVEL, and + from properties file
	ScoreText = new Label();
	LevelText = new Label();
	HealthText = new Label();



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
	ScoreText.setText(GAMEPLAYER_PROPERTIES.get("scoreText") + newScore);
    }

    private void updateHealth(Integer newHealth) {
	HealthText.setText(GAMEPLAYER_PROPERTIES.get("healthText")+ newHealth);
    }

    public void updateLevel(Integer newLevel) {
	LevelText.setText(GAMEPLAYER_PROPERTIES.get("levelText")+ newLevel);
    }


   
    public ChangeListener createScoreListener(Integer startScore) {
	updateScore(startScore);
	return new ChangeListener() {
	    @Override
	    public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
		updateScore((Integer)observableValue.getValue());
	    }
	};
    }
    
    public ChangeListener createHealthListener(Integer startHealth) {
	updateHealth(startHealth);
   	return new ChangeListener() {
   	    @Override
   	    public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
   		updateHealth((Integer)observableValue.getValue());
   	    }
   	};
       }

}
