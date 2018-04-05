package engine.managers;

import java.util.List;

import engine.sprites.Sprite;
import engine.sprites.towers.projectiles.Projectile;

/**
 * Projectile manager uses composite design pattern to handle updating all 
 * active Projectile objects in the game loop.
 * 
 * @author Katherine Van Dyk
 * @author Ryan Pond
 */
public class ProjectileManager extends Manager {

	// this doesn't have its own lists like Tower Manager does -bma
	
	/**
	 * Constructor from super class
	 */
	public ProjectileManager(List<Projectile> projectiles) {
		super();
	}
	
	

}
