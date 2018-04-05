package engine.managers;

import java.util.List;

import engine.sprites.enemies.Enemy;

/**
 * Enemy manager uses composite design pattern to handle updating all 
 * active Enemy objects in the game loop.
 * 
 * @author Katherine Van Dyk
 *
 */
public class EnemyManager extends Manager<Enemy> {
    
	// this doesn't have its own lists like Tower manager does -bma
	
    /**
     * Constructor for Enemy manager
     */
    public EnemyManager(List<Enemy> enemies) {
    		super(enemies);
    }
    
    
    
    

}
