package engine;

import java.util.List;

import engine.enemies.Enemy;
import engine.managers.EnemyManager;
import engine.managers.ProjectileManager;
import engine.managers.TowerManager;
import engine.sprites.towers.Tower;
import engine.sprites.towers.TowerI;

/**
 * This class will hold all of the current Play information, such as the list of objects on the screen (enemies,
 * towers, projectiles, etc). Update method will be called every game loop to update the position of every object. 
 * @author ryanpond
 * @author Ben Hodgson 4/3/18
 * @author Miles Todzo
 *
 */
public class PlayState implements PlayStateI{
    
    private TowerManager myTowerManager;
    private EnemyManager myEnemyManager;
    private ProjectileManager myProjectileManager;
    private Level myLevel;
    
	public PlayState() {
		myTowerManager = new TowerManager();
		myEnemyManager = new EnemyManager();
	}

	@Override
	public void update() {
		List<Enemy> enemyList = myEnemyManager.unmodifiableActiveList();
		myTowerManager.checkForCollisions(enemyList);
		myProjectileManager.chechForCollisions(enemyList);
	}
	
	@Override
	public void setLevel(Level newLevel) {
		myLevel = level;
		myTowerManager.setTowers(myLevel.getTowers()); //maybe change so that it adds on to the List and doesn't overwrite old towers
		
	}

}
