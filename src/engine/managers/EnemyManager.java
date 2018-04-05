package engine.managers;

import java.util.List;

import engine.sprites.enemies.Enemy;

/**
 * Enemy manager uses composite design pattern to handle updating all 
 * active Enemy objects in the game loop.
 * 
 * @author Katherine Van Dyk
 * @author Miles Todzo
 */
public class EnemyManager extends Manager {
    
	// this doesn't have its own lists like Tower manager does -bma
	
    /**
     * Constructor for Enemy manager
     */
    public EnemyManager() {
    		super();
    }
}
