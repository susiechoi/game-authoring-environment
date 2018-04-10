package engine.managers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import engine.path.Path;
import engine.sprites.enemies.Enemy;
import engine.sprites.towers.Tower;

/**
 * Enemy manager uses composite design pattern to handle updating all 
 * active Enemy objects in the game loop.
 * 
 * @author Miles Todzo
 */
public class EnemyManager extends ShootingSpriteManager {
    
    private final Map<Path, List<Enemy>> myEnemies;
    
	// this doesn't have its own lists like Tower manager does -bma
	
    /**
     * Constructor for Enemy manager
     */
    public EnemyManager() {
    		super();
    		myEnemies = new HashMap<Path, List<Enemy>>();
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
	for (Path path : myEnemies.keySet()) {
	    for (Enemy enemy : myEnemies.get(path)) {
		enemy.move(path);
	    }
	}
	
    }

    public void setEnemies(Collection<Enemy> enemies) {

    }
    
    /**
     * Adds an enemy to the manager that's mapped to a specific path
     * 
     * @param path: the path that the enemy follows
     * @param enemy: the enemy object to be added to the manager
     */
    public void addEnemy(Path path, Enemy enemy) {
	if (myEnemies.containsKey(path)) {
	    List<Enemy> pathEnemies = myEnemies.get(path);
	    pathEnemies.add(enemy);
	    myEnemies.put(path, pathEnemies);
	}
	else {
	    List<Enemy> pathEnemies = new ArrayList<Enemy>();
	    pathEnemies.add(enemy);
	    myEnemies.put(path, pathEnemies);
	}
    }
    
}
