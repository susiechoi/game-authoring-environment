package gameplayer.panel;

import gameplayer.screen.GameScreen;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;

public class GamePanel extends Panel{
    
    private final GameScreen GAME_SCREEN;
    
    
    public GamePanel(GameScreen gameScreen) {
	GAME_SCREEN = gameScreen;
    }
    

    @Override
    public void makePanel() {
	ScrollPane panelRoot = new ScrollPane();
	PANEL = panelRoot;
    }
}
