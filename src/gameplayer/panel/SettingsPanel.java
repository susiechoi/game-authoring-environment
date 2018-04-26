package gameplayer.panel;

import authoring.frontend.exceptions.MissingPropertiesException;
import javafx.scene.control.Button;
import frontend.PromptReader;
import frontend.PropertiesReader;
import frontend.UIFactory;
import gameplayer.screen.GameScreen;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import sound.ITRTSoundFactory;
import javafx.scene.image.ImageView;

import java.util.Map;
import java.util.Map.Entry;

public class SettingsPanel extends Panel {


    private final int DEFAULT_SETTINGS_BUTTON_SIZE = 50;

    private final String UPGRADE_BUTTON_FILEPATH = "images/settingsPanelImages/SettingsButtonNames.properties";
    private final GameScreen GAME_SCREEN;
    private final PropertiesReader PROP_READ;
    private final UIFactory UIFACTORY;
    private ITRTSoundFactory SOUND_FACTORY;


    public SettingsPanel(GameScreen gameScreen) {
        GAME_SCREEN = gameScreen;
        PROP_READ = new PropertiesReader();
        UIFACTORY = new UIFactory();
        SOUND_FACTORY = GAME_SCREEN.getSoundFactory();
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
            for (Entry<String, Image> entry : settingsMap.entrySet()) {
                Button settingButton = UIFACTORY.makeImageButton("settingButton", entry.getValue());
                settingButton.setOnMouseClicked(arg0 -> GAME_SCREEN.settingsTriggered(entry.getKey()));
                settingsBox.getChildren().add(settingButton);
            }
        }
        catch (MissingPropertiesException e) {
            System.out.println("Settings button images missing");
        }
    }



}
