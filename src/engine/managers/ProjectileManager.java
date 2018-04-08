package engine.managers;

import java.util.List;

import engine.sprites.towers.projectiles.Projectile;

/**
 * Projectile manager uses composite design pattern to handle updating all 
 * active Projectile objects in the game loop.
 * 
 * @author Miles Todzo
 */
public class ProjectileManager extends Manager {
	
	/**
	 * Constructor from super class
	 */
	public ProjectileManager() {
		super();
	}
}
