
package gameplayer.panel;

import java.util.Map;

import engine.sprites.towers.FrontEndTower;
import frontend.PromptReader;
import frontend.PropertiesReader;
import frontend.UIFactory;
import gameplayer.screen.GameScreen;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class TowerInfoPanel extends SpecificPanel {

	private final GameScreen GAME_SCREEN;
	private final UIFactory UI_FACTORY;
	private PromptReader PROMPTS;

	public TowerInfoPanel(GameScreen gameScreen, PromptReader promptReader, FrontEndTower tower) {
		super(tower);
		GAME_SCREEN = gameScreen;
		PROMPTS = promptReader;	
		UI_FACTORY = new UIFactory();
	}

	@Override
	public void makePanel() {
		//TODO add SellTower info pri
		Map<String,Integer> towerStats = TOWER.getTowerStats();
		Label TowerInfo = new Label(prepareStats(towerStats));
		Button sellTower = UI_FACTORY.makeTextButton(".button", PROMPTS.resourceDisplayText("SellTowerButton"));
		sellTower.setOnMouseClicked(arg0 -> GAME_SCREEN.sellTower(TOWER));

		VBox panelRoot = new VBox(TowerInfo, sellTower);
		VBox.setVgrow(sellTower, Priority.ALWAYS);
		panelRoot.setAlignment(Pos.CENTER);
		panelRoot.setId("sellTowerPanel");
		PANEL = panelRoot;
	}

	private String prepareStats(Map<String,Integer> towerStats) {
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String,Integer> key: towerStats.entrySet()) {
			sb.append(key.getKey() + ":" + towerStats.get(key.getKey()) + "\n");
		}
		return sb.toString();
	}

	public String getTowerInfoOnClick() {
		//TODO Once backend is linked, write method to take information from button click on upgradePanel
		//TODO and send it over to the buyPanel
		return null;
	}

}

