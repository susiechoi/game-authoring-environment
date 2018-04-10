//package gameplayer.panel;
//
//import frontend.PromptReader;
//import frontend.PropertiesReader;
//import frontend.UIFactory;
//import gameplayer.screen.GameScreen;
//import javafx.geometry.Pos;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//import javafx.scene.layout.Priority;
//import javafx.scene.layout.VBox;
//
//public class TowerInfoPanel extends Panel {
//
//    private final GameScreen GAME_SCREEN;
//    private final UIFactory UI_FACTORY;
//    private PropertiesReader PROP_READ;
//    private PromptReader PROMPTS;
//
//    public TowerInfoPanel(GameScreen gameScreen, PromptReader promptReader) {
//        GAME_SCREEN = gameScreen;
//        PROMPTS = promptReader;	
//        UI_FACTORY = new UIFactory();
//        PROP_READ = new PropertiesReader();
//    }
//
//    @Override
//    public void makePanel() {
//
//        //TODO add SellTower info pri
//        Label TowerInfo = new Label("return value from getTowerInfoOnClick method");
//        Button sellTower = UI_FACTORY.makeTextButton(".button", PROMPTS.resourceDisplayText("SellTowerButton"));
//        //sellTower.setOnMouseClicked((arg0) -> /**BACKENDUPGRADETOWERMETHOD**/);
//
//        VBox panelRoot = new VBox(TowerInfo, sellTower);
//        VBox.setVgrow(sellTower, Priority.ALWAYS);
//        panelRoot.setAlignment(Pos.CENTER);
//        panelRoot.setId("sellTowerPanel");
//        PANEL = panelRoot;
//    }
//
//    public String getTowerInfoOnClick() {
//        //TODO Once backend is linked, write method to take information from button click on upgradePanel
//        //TODO and send it over to the buyPanel
//        return null;
//    }
//
//}
