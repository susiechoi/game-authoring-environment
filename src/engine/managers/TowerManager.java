package engine.managers;

import engine.towers.Tower;

/**
 * Tower manager uses composite design pattern to handle updating all 
 * active Tower objects in the game loop.
 * 
 * @author Katherine Van Dyk
 *
 */
public class TowerManager extends Manager<Tower> {
    
    /**
     * Constructor for super class
     */
    public TowerManager() {
	super();
    }

}
