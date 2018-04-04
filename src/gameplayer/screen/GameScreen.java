package gameplayer.screen;

import gameplayer.panel.TowerPanel;
import gameplayer.panel.GamePanel;
import gameplayer.panel.ScorePanel;
import gameplayer.panel.ControlsPanel;
import gameplayer.ScreenManager;


import javafx.scene.Node;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;



public class GameScreen implements Screen {

    private Parent ROOT;
    private TowerPanel TOWER_PANEL;
    private GamePanel GAME_PANEL;
    private ScorePanel SCORE_PANEL;
    private ControlsPanel CONTROLS_PANEL;
    private ScreenManager SCREEN_MANAGER;

    public GameScreen(ScreenManager ScreenController) {
        SCREEN_MANAGER = ScreenController;

    }

    @Override
    public void makeScreen() {
        BorderPane rootPane = new BorderPane();
        rootPane.setId("gameScreenRoot"); //Where is this set up / where does it get the gameScreenRoot from?
        rootPane.setRight(new TowerPanel(rootPane, this).getPanel());
        rootPane.setTop(new ScorePanel(rootPane, this).getPanel());
        rootPane.setCenter(new GamePanel(rootPane, this).getPanel());
        ROOT = rootPane;
    }

    @Override
    public Node getScreen(){
        if (ROOT == null) {
            makeScreen();
        }
        return ROOT;
    }


}
