package gameplayer.panel;

import authoring.frontend.exceptions.MissingPropertiesException;
import frontend.PropertiesReader;
import gameplayer.screen.GameScreen;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.Map;

public class SplashPanel extends Panel {

    private GameScreen GAME_SCREEN;
    private String GAME_STATE;
    private Map<String,String> GAMEPLAYER_PROPERTIES;

    public SplashPanel(GameScreen gameScreen, String gameState) {
        GAME_SCREEN = gameScreen;
        GAMEPLAYER_PROPERTIES = GAME_SCREEN.getGameplayerProperties();
        GAME_STATE = gameState;
    }

    @Override
    public void makePanel() {
        Label titleLabel = new Label();
        Label infoLabel = new Label();
        VBox panelRoot = new VBox();
        if (GAME_STATE.equals(GAMEPLAYER_PROPERTIES.get("nextLevel"))) {
            titleLabel.setText(GAMEPLAYER_PROPERTIES.get("nextLevelTitle"));
            infoLabel.setText(GAMEPLAYER_PROPERTIES.get("nextLevelInfo"));
        }
        else if (GAME_STATE.equals(GAMEPLAYER_PROPERTIES.get("gameWon"))) {
            titleLabel.setText(GAMEPLAYER_PROPERTIES.get("gameWinTitle"));
            infoLabel.setText(GAMEPLAYER_PROPERTIES.get("gameWinInfo"));
        }
        else if (GAME_STATE.equals(GAMEPLAYER_PROPERTIES.get("gameLost"))) {
            titleLabel.setText(GAMEPLAYER_PROPERTIES.get("gameLoseTitle"));
            infoLabel.setText(GAMEPLAYER_PROPERTIES.get("gameLoseInfo"));
        }
        else {
            //TODO error here for wrong input
        }

        PANEL = panelRoot;

    }
}
