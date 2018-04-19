package engine;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import xml.PlaySaverWriter;
import xml.XMLFactory;

/**
 * This class will handle all of the gameLoop interactions, and will also hold the 
 * current instance of the PlayState
 * @author ryanpond
 *
 */
public class GameEngine {

    private final int FRAMES_PER_SECOND = 60;
    private final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    private final Integer DEFAULT_RELATIVE_SPEED = 5;
    private final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    private PlayState myPlayState;
    private Mediator myMediator;
    private Timeline ANIMATION;
    private double timeFactor;

    public GameEngine(Mediator mediator) {
	myPlayState = null;
	myMediator = mediator;
	timeFactor = 1;

	setSpeed(DEFAULT_RELATIVE_SPEED);
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
     * Calls the update function every loop
     * @param elapsedTime
     */
    public void loop(double elapsedTime) {
	myPlayState.update(elapsedTime*timeFactor);
    }


    /**
     * Pauses Game Loop animation so Game State stays constant
     * COMMENTED OUT BECAUSE THIS WOULD PAUSE THE ENTIRE GAMEENGINE NOT THE PLAYSTATE
     */
//    public void pause() {
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
    public void setSpeed(Integer relativeSpeed) {
	timeFactor = relativeSpeed;
    }

    /**
<<<<<<< HEAD
=======
     * Saves current Game State to File
     */
    public void savePlay(String filename) {
	PlaySaverWriter p = (PlaySaverWriter) XMLFactory.generateWriter("PlaySaverWriter");
	p.write(myPlayState, filename);
    }

    /**
>>>>>>> a3c8e958bf0a4ac0cf7da5e419420543043c2a3b
     * Updates Game State to new Level as specified in XML File
     * 
     * @param l: integer denoting level to jump to
     */
    public void jumpLevel(int l) {
	//TODO: 
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

   

}
