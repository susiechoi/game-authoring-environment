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
import sound.ITRTSoundFactory;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;

import java.io.FileNotFoundException;
import java.util.Map;

public class SettingsPanel extends Panel {


    private final int DEFAULT_SETTINGS_BUTTON_SIZE = 50;
    private final int DEFAULT_SETTINGS_BUTTON_WIDTH = 50;

    private final String UPGRADE_BUTTON_FILEPATH = "images/settingsPanelImages/SettingsButtonNames.properties";
    private final GameScreen GAME_SCREEN;
    private final PropertiesReader PROP_READ;
    private final PromptReader PROMPTS;
    private final UIFactory UIFACTORY;
    private final ITRTSoundFactory SOUND_FACTORY;
    private Button helpButton;
    private Button instrButton;


    public SettingsPanel(GameScreen gameScreen, PromptReader promptReader) {
        GAME_SCREEN = gameScreen;
        PROMPTS =  promptReader;
        PROP_READ = new PropertiesReader();
        UIFACTORY = new UIFactory();
        SOUND_FACTORY = new ITRTSoundFactory();
    }

    private Button createHelp() {
        Image helpImage = new Image(getClass().getResourceAsStream("images/settingsPanelImages/help.png"));
        helpButton = new Button();
        helpButton.setGraphic(new ImageView(helpImage));
        //helpButton.setOnMouseClicked((args) -> );
        return helpButton;
    }

    private Button createInstrButton() {
        Image instrImage = new Image(getClass().getResourceAsStream("images/settingsPanelImages/instructions.png"));
        instrButton = new Button();
        instrButton.setGraphic(new ImageView(instrImage));
        //instrButton.setOnMouseClicked((args) -> );
        return instrButton;
    }
    @Override
    public void makePanel() {

        VBox panelRoot = new VBox();
        makeSettingsButtons(panelRoot);
        PANEL = panelRoot;
    }


    private void makeSettingsButtons(VBox settingsBox) {
        settingsBox.getChildren().add(SOUND_FACTORY.createVolumeSlider());
        try {
            Map<String,Image> settingsMap = PROP_READ.keyToImageMap(UPGRADE_BUTTON_FILEPATH, DEFAULT_SETTINGS_BUTTON_SIZE, DEFAULT_SETTINGS_BUTTON_SIZE);
            for (String setting: settingsMap.keySet()) {
                Button settingButton = UIFACTORY.makeImageButton("settingButton", settingsMap.get(setting));
                settingButton.setOnMouseClicked((arg0) -> GAME_SCREEN.settingsTriggered(setting));
                settingsBox.getChildren().add(settingButton);
            }
        }
        catch (MissingPropertiesException e) {

        }
    }



}
