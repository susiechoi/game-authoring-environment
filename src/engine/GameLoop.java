package engine;

import java.util.TimerTask;

/**
 * Class utilizes the TimerTask superclass, to run an update every specified number of seconds
 * @author ryanpond
 *
 */
public class GameLoop extends TimerTask {

	private GameEngine myController;
	
	public GameLoop(GameEngine controller) {
		myController = controller;
	}

	@Override
	public void run() {
		myController.update();
	}

}
