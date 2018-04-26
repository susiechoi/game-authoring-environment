package gameplayer.panel;

import engine.sprites.towers.FrontEndTower;
import frontend.PromptReader;
import gameplayer.screen.GameScreen;
import frontend.UIFactory;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class BuyPanel extends SpecificPanel {

    private final GameScreen GAME_SCREEN;
    private final UIFactory UI_FACTORY;
    private PromptReader PROMPTS;
    private final String UPGRADE_TYPE;
 

    public BuyPanel(GameScreen gameScreen, PromptReader promptReader, FrontEndTower tower, String upgradeName) {
        super(tower);
	GAME_SCREEN = gameScreen;
        PROMPTS = promptReader;
        UI_FACTORY = new UIFactory();
        UPGRADE_TYPE= upgradeName;
    }

    @Override
    public void makePanel() {

        Label TowerInfo = new Label("return value from getUpgradeInfoFromClick method");
        TowerInfo.setWrapText(true);
        Button buyUpgrade = UI_FACTORY.makeTextButton(".button", PROMPTS.resourceDisplayText("BuyTowerUpgrade"));
        buyUpgrade.setOnMouseClicked(arg0 -> GAME_SCREEN.upgradeBought(TOWER, UPGRADE_TYPE));

        VBox panelRoot = new VBox(TowerInfo, buyUpgrade);
        VBox.setVgrow(TowerInfo, Priority.ALWAYS);
        panelRoot.setAlignment(Pos.CENTER);
        panelRoot.setId("buyUpgradePanel");
        panelRoot.setMaxWidth(Double.parseDouble(PROMPTS.resourceDisplayText("upgradePanelWidth")));
        PANEL = panelRoot;
    }

    public String getUpgradeInfoFromClick() {
        //TODO Once backend is linked, write method to take information from button click on upgradePanel
        //TODO and send it over to the buyPanel
        return null;
    }


}
