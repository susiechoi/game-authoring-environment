package engine;

import java.util.Timer;

/**
 * This class will handle all of the gameLoop interactions, and will also hold the 
 * current instance of the PlayState
 * @author ryanpond
 *
 */
public class GameEngine implements GameEngineInterface {

	
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

	@Override
	public void pause() {
		myTimer.cancel();
		
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSpeed(Integer speed) {
		myTimer.schedule(myLoop, speed);
		
	}

	@Override
	public void savePlay() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void jumpLevel(int l) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLevel(PlayState newLevel) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void restartLevel() {
		// TODO Auto-generated method stub
		
	}



}
