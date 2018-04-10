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
     * COMMENTED OUT BECAUSE THIS WOULD PAUSE THE ENTIRE GAMEENGINE NOT THE PLAYSTATE
     */
//    public void pause() {
//	myTimer.cancel();
//	ANIMATION.pause();
//
//    }

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
     * (relative speed of program, 1 being slowest, 5 being normal, 10 being fastest)
     */
    public void setSpeed(Integer speed) {
	myTimer.schedule(myLoop, speed);
	System.out.println("Incomplete setSpeed method, make sure to check this functionality!");
	//TODO myTimer.schedule may need bugfixing for scaling speed
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

    /**
     * Calls the update function every loop
     * @param elapsedTime
     */
    public void loop(double elapsedTime) {
	update();
    }
    

}
