package gameplayer.panel;

import authoring.frontend.exceptions.MissingPropertiesException;
import javafx.scene.control.Button;
import frontend.PromptReader;
import frontend.PropertiesReader;
import frontend.UIFactory;
import gameplayer.screen.GameScreen;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.layout.Priority;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import gameplayer.screen.GameScreen;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.Map;

public class SettingsPanel extends Panel {


    private final int DEFAULT_SETTINGS_BUTTON_HEIGHT = 50;
    private final int DEFAULT_SETTINGS_BUTTON_WIDTH = 150;

    private final String UPGRADE_BUTTON_FILEPATH = "images/settingsPanelImages/SettingsButtonNames.properties";
    private final GameScreen GAME_SCREEN;
    private final PropertiesReader PROP_READ;
    private final PromptReader PROMPTS;
    private final UIFactory UIFACTORY;


    public SettingsPanel(GameScreen gameScreen, PromptReader promptReader) {
        GAME_SCREEN = gameScreen;
        PROMPTS =  promptReader;
        PROP_READ = new PropertiesReader();
        UIFACTORY = new UIFactory();
    }

    @Override
    public void makePanel() {

        VBox panelRoot = new VBox();
        panelRoot.setAlignment(Pos.CENTER);
        makeSettingsButtons(panelRoot);
        System.out.println(panelRoot.getPrefHeight());
        System.out.println(panelRoot.getPrefWidth());
        PANEL = panelRoot;
    }

    private void makeSettingsButtons(VBox settingsButtons) {
        try {
            Map<String, Image> settingsMap = PROP_READ.keyToImageMap(UPGRADE_BUTTON_FILEPATH, DEFAULT_SETTINGS_BUTTON_WIDTH, DEFAULT_SETTINGS_BUTTON_HEIGHT);
            for (String setting: settingsMap.keySet()) {
                Button settingsButton = UIFACTORY.makeImageButton("settingsButton", settingsMap.get(setting));
                settingsButton.setOnMouseClicked((arg0) -> GAME_SCREEN.settingsTriggered(setting));
                settingsButtons.getChildren().add(settingsButton);
            }
        }
        catch (MissingPropertiesException e) {

        }
    }


}
