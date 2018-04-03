package usecases;

import java.util.List;

import engine.enemies.EnemyI;
import engine.towers.projectiles.Projectile;

import java.util.ArrayList;

/**
 * Example code for Use Case 7: Enemy is hit by projectile. This code would be within the Game State
 * class in implementation, also under the update method.
 * 
 * @author Katherine Van Dyk 3/29/18
 *
 */
public class ProjectileEnemyExample {

    private List<EnemyI> enemies;
    private List<Projectile> projectiles;

    /**
     * Constructor that would be included within GameState class
     */
    public ProjectileEnemyExample() {
	enemies = new ArrayList<>();
	projectiles = new ArrayList<>();
    }
    
    /**
     * Update method called for every iteration of the game loop
     */
    public void update() {
	for(EnemyI enemy : enemies) {
	    for(Projectile projectile : projectiles) {
		if(enemy.getHitBy(projectile)) {
		    projectiles.remove(projectile);
		}
	    }
	}

    }


}
