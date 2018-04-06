package gameplayer.panel;

import gameplayer.screen.GameScreen;
import javafx.scene.control.ScrollPane;

public class GamePanel extends Panel{
    
    private final GameScreen GAME_SCREEN;
    
    
    public GamePanel(GameScreen gameScreen) {
	GAME_SCREEN = gameScreen;
	
    }
    

    @Override
    public void makePanel() {
	
	ScrollPane panelRoot = new ScrollPane();
	panelRoot.setId("gamePanel");
	//panelRoot.setBottom(new Up);
	panelRoot.setMaxWidth(Double.MAX_VALUE);
	panelRoot.setMaxHeight(Double.MAX_VALUE);

	PANEL = panelRoot;
    }
}
