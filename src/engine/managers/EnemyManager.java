package engine.managers;

import java.util.Map;

import engine.sprites.enemies.Enemy;

/**
 * Enemy manager uses composite design pattern to handle updating all 
 * active Enemy objects in the game loop.
 * 
 * @author Miles Todzo
 */
public class EnemyManager extends ShootingSpriteManager {
    
	// this doesn't have its own lists like Tower manager does -bma
	
    /**
     * Constructor for Enemy manager
     */
    public EnemyManager() {
    		super();
    }

    /**
     * Moves any projectiles that the enemies have shot on every step of the GameLoop
     */
    public void moveProjectiles() {
	// TODO Auto-generated method stub
	
    }

    /**
     * Moves all the enemies along the path on every step
     */
    public void moveEnemies() {
	// TODO Auto-generated method stub
	
    }

    public void setEnemies(Map<Enemy, Integer> enemies) {
	// TODO Auto-generated method stub
	
    }
}
