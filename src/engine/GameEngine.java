package engine;

import java.util.Timer;

/**
 * This class will handle all of the gameLoop interactions, and will also hold the 
 * current instance of the PlayState
 * @author ryanpond
 *
 */
public class GameEngine {

	
	private PlayState myPlayState;
	private Timer myTimer;
	private GameLoop myLoop;
	
	public GameEngine() {
		int delayMS = 50;
		myTimer = new Timer();
		myLoop = new GameLoop(this);
		setSpeed(delayMS);
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
		
	}

	 /**
     * Starts Game Loop animation, so Game State continuously loops
     */
	public void start() {
		// TODO Auto-generated method stub
		
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



}
