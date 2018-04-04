package gameplayer.panel;

import gameplayer.screen.GameScreen;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

public class ScorePanel extends Panel{
    
    private final GameScreen GAME_SCREEN;

    public ScorePanel( GameScreen gameScreen) {
	GAME_SCREEN = gameScreen;
    }
    

    @Override
    public void makePanel() {
	VBox panelRoot = new VBox();
	PANEL = panelRoot;
    }
}
