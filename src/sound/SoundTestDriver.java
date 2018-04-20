package sound;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;

/** 
 * Use the driver JavaFX library to start the application.
 *
 * @author benauriemma
 * 
 */
public class SoundTestDriver extends Application {  
    
    private final int FRAMES_PER_SECOND = 60;
    private final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    private final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    public static final int SIZE = 400;
    public static final Paint BACKGROUND = Color.AZURE;
    //public static final int SLIDER_MIN = 1;
    //public static final int SLIDER_MAX = 399;
    public static final int IMAGE_HEIGHT = 300;
    public static final int IMAGE_WIDTH = 300;

    private Timeline myAnimation;
    //private ImageView myImage;
    //private Slider mySlider;
    //private Integer myPrincipleComponents;
    private String myFileName;
    //private Scene myScene;
    private SoundFactory mySoundFactory;
    private Group myRoot;

    /**
     * Initialize the program and begin the animation loop 
     * 
     * 
     * @param stage: Primary stage to attach all scenes
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
	primaryStage.setScene(setupScene());
        KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY),
                                      e -> update(SECOND_DELAY));
        myAnimation = new Timeline();
        myAnimation.setCycleCount(Timeline.INDEFINITE);
        myAnimation.getKeyFrames().add(frame);
        primaryStage.show();
	mySoundFactory = new ITRTSoundFactory();
        mySoundFactory.setBackgroundMusic("epic");
        Button b = mySoundFactory.createPlayBackgroundMusicButton();
        myRoot.getChildren().add(b);
        Slider v = mySoundFactory.createVolumeSlider();
        myRoot.getChildren().add(v);
       // mySoundFactory.playBackgroundMusic();
        /*
        long start = System.currentTimeMillis();
        while(System.currentTimeMillis()<start + 10000) {
            
        }
        // THIS TESTING IS CURRENTLY INCOMPLETE
        System.out.println("Out of loop");
        mySoundFactory.setVolume(10);
        */
        //myAnimation.play();
    }
    
    private Scene setupScene () {
	myRoot = new Group();
	Scene scene = new Scene(myRoot, SIZE, SIZE, BACKGROUND);
	return scene;
}

    /**
     * Calls the update function every loop
     * @param elapsedTime
     */
    public void update(double elapsedTime) {
	
    }

    /**
     * Start the program
     */
    public static void main (String[] args) {
	launch(args);
    }
}
