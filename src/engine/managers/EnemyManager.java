package engine.managers;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import engine.path.Path;
import engine.sprites.ShootingSprites;
import engine.sprites.Sprite;
import engine.sprites.enemies.Enemy;

/**
 * Enemy manager uses composite design pattern to handle updating all 
 * active Enemy objects in the game loop.
 * 
 * @author Miles Todzo
 */
public class EnemyManager extends ShootingSpriteManager {

    int count = 0;

    private final Map<Path, List<Enemy>> myEnemies;


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
    public List<Sprite> moveEnemies(double elapsedTime) {
	List<Sprite> deadEnemies = new ArrayList<>();
	for (Path path : myEnemies.keySet()) {
	    for (Enemy enemy : myEnemies.get(path)) {
		if(path.checkKill(enemy.currentPosition())) {
		    deadEnemies.add(enemy);
		}
		else if(!isInRange(enemy.currentPosition(),enemy.targetPosition())) {
		    enemy.move(elapsedTime);
		}
		else {
		    Point newPosition = path.nextPosition(enemy.getIndex());
		    int pathIndex = path.getIndex(enemy.currentPosition(), enemy.getIndex());
		    enemy.setNewPosition(newPosition);
		    enemy.move(elapsedTime);
		    enemy.setIndex(pathIndex);
		}
	    }
	}
	return deadEnemies;

    }
    
    private boolean isInRange(Point curr, Point target) {
	return curr.distance(target)<10;
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
