package engine.managers;

import java.util.ArrayList;
import java.util.List;
import engine.sprites.ShootingSprites;
import engine.sprites.Sprite;
import engine.sprites.towers.Tower;
import engine.sprites.towers.projectiles.Projectile;

/**
 * 
 * @author Miles Todzo
 *
 */

public class ShootingSpriteManager extends Manager<ShootingSprites>{
    /**
     * Checks for collisions between between the list of active actors held by the Manager the method
     * was called on and the list of active actors passed as a parameter
     * @param passedSprites
     */
    public List<Sprite> checkForCollisions(List<ShootingSprites> passedSprites) {
	List<Sprite> spritesToBeRemoved = new ArrayList<>();
    		for (ShootingSprites activeSprite: this.getListOfActive()) {
    			for (ShootingSprites passedActor: passedSprites) {
    			    List<Sprite> deadSprites = activeSprite.checkForCollision(passedActor);
    			    spritesToBeRemoved.addAll(deadSprites);
    			}
    		}
    		return spritesToBeRemoved;
    }
    

    public List<Projectile> shoot(List<ShootingSprites> passedSprites) {
    		List<Projectile> newProjectiles = new ArrayList<>();
    		for (ShootingSprites shootingSprite: this.getListOfActive()) {
    			for (ShootingSprites passedSprite: passedSprites) {
    				if (shootingSprite.hasInRange(passedSprite) && shootingSprite.hasReloaded()) {
    					Projectile newProjectile = shootingSprite.launch(passedSprite, shootingSprite.getX(), shootingSprite.getY());
    					if (newProjectile != null) {
    						newProjectiles.add(newProjectile);
    					}
    				}
    			}
    		}
    		return newProjectiles;
    }
    
	public void moveProjectiles() {
		for (ShootingSprites shootingSprite: this.getListOfActive()) {
			for (Projectile projectile: shootingSprite.getProjectiles()) {
				projectile.move();
			}
		}
	}
   
}
