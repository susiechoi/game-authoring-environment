package gameplayer;

import file.FileIO;
import gameplayer.screen.GameScreen;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.Group;
import javafx.scene.Parent;
import java.util.ResourceBundle;
import java.util.List;
import java.util.Map;


public class ScreenManager {

    public static final String FILE_ERROR_KEY = "FileErrorPrompt";
    public static final String SCREEN_ERROR_KEY = "ScreenErrorPrompt";

    private Stage PROGRAM_STAGE;
    private GameScreen CURRENT_SCREEN;
    private String GAME_TITLE;
    private double DEFAULT_HEIGHT;
    private double DEFAULT_WIDTH;
    //private final FileIO FILE_READER;

    public ScreenManager(Stage primaryStage) {
        PROGRAM_STAGE = primaryStage;
        //setup rest of values once file reader is finished
    }


}
