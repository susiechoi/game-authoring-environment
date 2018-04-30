package gameplayer.panel;

import engine.sprites.towers.FrontEndTower;
import frontend.PromptReader;
import gameplayer.screen.GameScreen;
import frontend.UIFactory;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import java.util.Map;


/**
 * @Author Alexi Kontos & Andrew Arnold
 * Class that creates the panel needed to buy upgrades for specific tower when clicked on.
 */

public class BuyPanel extends SpecificPanel {

    private final GameScreen GAME_SCREEN;
    private final UIFactory UI_FACTORY;
    private PromptReader PROMPTS;
    private final String UPGRADE_TYPE;
    private Map<String,String> GAMEPLAYER_PROPERTIES;
 

    public BuyPanel(GameScreen gameScreen, PromptReader promptReader, FrontEndTower tower, String upgradeName) {
        super(tower);
	    GAME_SCREEN = gameScreen;
	    GAMEPLAYER_PROPERTIES = GAME_SCREEN.getGameplayerProperties();
        PROMPTS = promptReader;
        UI_FACTORY = new UIFactory();
        UPGRADE_TYPE= upgradeName;
    }

    @Override
    public void makePanel() {

        String upgradeName = splitInput(UPGRADE_TYPE);
        Label TowerInfo = new Label(upgradeName);
        TowerInfo.setWrapText(true);

        Button buyUpgrade = UI_FACTORY.makeTextButton(GAMEPLAYER_PROPERTIES.get("buttonID"), PROMPTS.resourceDisplayText("BuyTowerUpgrade"));
        buyUpgrade.setOnMouseClicked(arg0 -> GAME_SCREEN.upgradeBought(TOWER, UPGRADE_TYPE));

        VBox panelRoot = new VBox(TowerInfo, buyUpgrade);
        VBox.setVgrow(TowerInfo, Priority.ALWAYS);
        panelRoot.setAlignment(Pos.CENTER);
        panelRoot.setId(GAMEPLAYER_PROPERTIES.get("buyUpgradePanelID"));
        panelRoot.setMaxWidth(Double.parseDouble(PROMPTS.resourceDisplayText("upgradePanelWidth")));
        PANEL = panelRoot;
    }

    /**
     * private method to split input to show correct upgrade name in the panel
     * @param input
     * @return
     */
    private String splitInput(String input) {
        StringBuilder sb = new StringBuilder();
        if (input.contains("_")) {
            String[] split = input.split("_");
            for (String s: split) {
                sb.append(s);
                sb.append(" ");
            }
        }
        return sb.toString().trim();
    }
    private void checkAffordUpgrade() {
	
    }


    /**
     * Method to show the buying price of the upgrade on the button
     * @return
     */
    public ChangeListener<Number> createCurrencyListener() {
  	return new ChangeListener<Number>() {
  	    @Override
  	    public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
  	    }
  	};
      }


}
