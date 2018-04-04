package gameplayer.panel;

import gameplayer.screen.GameScreen;
import javafx.scene.layout.HBox;

public class UpgradePanel extends SpecificPanel {

    private final GameScreen GAME_SCREEN;
    
    public UpgradePanel (GameScreen gameScreen) {
	GAME_SCREEN = gameScreen;
    }
    
    
    @Override
    public void makePanel() {
	HBox panelRoot = new HBox();
	panelRoot.setMaxWidth(Double.MAX_VALUE);
	panelRoot.setMaxHeight(Double.MAX_VALUE);

	PANEL = panelRoot;

    }
}
