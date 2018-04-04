package gameplayer.panel;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import java.util.List;

import gameplayer.screen.GameScreen;
import file.FileIO;


public class TowerPanel implements Panel{

    private BorderPane PANE;
    private GameScreen GAME_SCREEN;
    //private final FileIO FILE_READER;

    private final String[] Button_IDS = {}; //How should we create the buttons for selecting towers since there are so many?

    public TowerPanel(BorderPane pane, GameScreen gameScreen) {
        PANE = pane;
        GAME_SCREEN = gameScreen;
    }



    @Override
    public void makePanel() {
        List<Button> buttons = makeButtons();
        VBox panelRoot = new VBox();
        panelRoot.getChildren().addAll(buttons);
        panelRoot.setId("towerPanel");
        panelRoot.setAlignment(Pos.CENTER);
        //PANEL = panelRoot; In y'all's panel class for Slogo you had a protected PANEL variable in the abstract panel class, but we
        //can't do that with interfaces. How would you want to approach that?
    }


    @Override
    public Node getPanel() {
        return null;
    }

    private List<Button> makeButtons(){
        //do some stuff
        return null;
    }


}
