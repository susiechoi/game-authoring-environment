package engine;

import java.util.Timer;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

/**
 * This class will handle all of the gameLoop interactions, and will also hold the 
 * current instance of the PlayState
 * @author ryanpond
 *
 */
public class GameEngine {

    private final int FRAMES_PER_SECOND = 60;
    private final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    private final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    private PlayState myPlayState;
    private Timer myTimer;
    private GameLoop myLoop;
    private Mediator myMediator;
    private Timeline ANIMATION;

    public GameEngine(Mediator mediator) {
	myPlayState = null;
	myMediator = mediator;
	myTimer = new Timer();
	myLoop = new GameLoop(this);
	setSpeed(MILLISECOND_DELAY);
	// attach "game loop" to time line to play it
        KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY),
                                      e -> loop(SECOND_DELAY));
        ANIMATION = new Timeline();
        ANIMATION.setCycleCount(Timeline.INDEFINITE);
        ANIMATION.getKeyFrames().add(frame);
    }

    public void setPlayState(PlayState p) {
	myPlayState = p;
    }

    public PlayState getPlayState() {
	return myPlayState;
    }

    /**
     * This is called every ____ number of seconds, according to the Timer
     * Called from the GameLoop class
     */
    public void update() {
	myPlayState.update();
    }

    /**
     * Pauses Game Loop animation so Game State stays constant
     */
    public void pause() {
	myTimer.cancel();
	ANIMATION.pause();

    }

    /**
     * Starts Game Loop animation, so Game State continuously loops
     */
    public void start() {
	ANIMATION.play();

    }

    /**
     * Sets Game Loop speed, to determine how fast level steps through.
     * 
     * @param speed: speed at which animation should iterate
     */
    public void setSpeed(Integer speed) {
	myTimer.schedule(myLoop, speed);

    }

    /**
     * Saves current Game State to File
     */
    public void savePlay() {

    }

    /**
     * Updates Game State to new Level as specified in XML File
     * 
     * @param l: integer denoting level to jump to
     */
    public void jumpLevel(int l) {

    }

    /**
     * Jumps to a certain level played in the Play
     * 
     * @param newLevel : The PlayState object with information about that level
     */
    public void setLevel(PlayState newLevel) {

    }

    /**
     * Restarts the current level
     */
    public void restartLevel() {

    }

    public void loop(double elapsedTime) {
	update();
    }

}
