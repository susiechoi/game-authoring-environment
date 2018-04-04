package gameplayer.panel;

import gameplayer.screen.GameScreen;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;

public class GamePanel extends Panel{
    
    private final GameScreen GAME_SCREEN;
    
    
    public GamePanel(GameScreen gameScreen) {
	GAME_SCREEN = gameScreen;
	
    }
    

    @Override
    public void makePanel() {
	
	BorderPane panelRoot = new BorderPane();
	PANEL = panelRoot;
    }
}
