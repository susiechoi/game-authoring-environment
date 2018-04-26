package sound;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

/** 
 * A very basic, no-frills demonstration of release 1.0 of the SoundFactory utility
 *
 * @author benauriemma
 * 
 */
public class SoundTestDriver extends Application {  

    public static final int SIZE = 400;
    public static final Paint BACKGROUND = Color.AZURE;

    private SoundFactory mySoundFactory;
    private Group myRoot;

    /**
     * This very basic demonstration:
     * 		-instantiates an ITRTSoundFactory
     * 		-sets background music
     * 		-plays it
     * 		-adds a play button, pause button, and volume slider to the screen
     * 
     * This demonstration does not demonstrate use of the playSoundEffect(String soundName) method,
     * 		or any features past release 1.0
     * 
     * @param stage: Primary stage to attach all scenes
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
	
	// Set up the JavaFX Application
	primaryStage.setScene(setupScene());
	primaryStage.show();
	
	// Instantiate IfTrueReturnTrue's implemenatation of SoundFactory
	mySoundFactory = new ITRTSoundFactory();
	
	// Set the background music to the song referred to as "epic" in /sound/resources/soundFiles.properties
	mySoundFactory.setBackgroundMusic("epic");
	
	// Start playing the background music
	mySoundFactory.playBackgroundMusic();

	// create an un-styled PlayBackgroundMusicButton and add it to the scene
	Button playButton = mySoundFactory.createPlayBackgroundMusicButton();
	myRoot.getChildren().add(playButton);

	// create an un-styled PauseBackgroundMusicButton, add it to the scene, and move it down a little
	Button pauseButton = mySoundFactory.createPauseBackgroundMusicButton();
	myRoot.getChildren().add(pauseButton);
	pauseButton.setLayoutY(30);
	
	// create an un-styled VolumeSlider, add it to the scene, and move it down a little more
	Slider volumeSlider = mySoundFactory.createVolumeSlider();
	volumeSlider.setLayoutY(60);
	myRoot.getChildren().add(volumeSlider);
	
    }

    /**
     * Setup the scene
     * @return the scene
     */
    private Scene setupScene () {
	myRoot = new Group();
	Scene scene = new Scene(myRoot, SIZE, SIZE, BACKGROUND);
	return scene;
    }

    /**
     * Start the program
     */
    public static void main (String[] args) {
	launch(args);
    }
}
