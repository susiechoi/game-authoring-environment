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

public class SettingsPanel extends Panel {



    private final GameScreen GAME_SCREEN;
    private final PropertiesReader PROP_READ;
    private final UIFactory UIFACTORY;
    private ITRTSoundFactory SOUND_FACTORY;
    private Map<String,String> GAMEPLAYER_PROPERTIES;


    public SettingsPanel(GameScreen gameScreen) {
        GAME_SCREEN = gameScreen;
        GAMEPLAYER_PROPERTIES = GAME_SCREEN.getGameplayerProperties();
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

        String SETTINGS_BUTTON_FILEPATH = GAMEPLAYER_PROPERTIES.get("settingsButtonFilepath");
        Integer DEFAULT_SETTINGS_BUTTON_SIZE = Integer.parseInt(GAMEPLAYER_PROPERTIES.get("settingsButtonSize"));

        settingsBox.getChildren().add(SOUND_FACTORY.createVolumeSlider());
        try {
            Map<String,Image> settingsMap = PROP_READ.keyToImageMap(SETTINGS_BUTTON_FILEPATH, DEFAULT_SETTINGS_BUTTON_SIZE, DEFAULT_SETTINGS_BUTTON_SIZE);
            for (String setting: settingsMap.keySet()) {
                Button settingButton = UIFACTORY.makeImageButton(GAMEPLAYER_PROPERTIES.get("settingsButtonID"), settingsMap.get(setting));
                settingButton.setOnMouseClicked((arg0) -> GAME_SCREEN.settingsTriggered(setting));
                settingsBox.getChildren().add(settingButton);
            }
        }
        catch (MissingPropertiesException e) {

        }
    }



}
