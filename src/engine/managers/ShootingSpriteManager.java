package engine.managers;

import java.util.ArrayList;
import java.util.List;

import engine.sprites.ShootingSprites;
import engine.sprites.Sprite;
import engine.sprites.towers.projectiles.Projectile;
import javafx.collections.ObservableList;

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
    public void checkForCollisions(List<ShootingSprites> passedSprites) {
    		for (ShootingSprites activeSprite: this.getObservableListOfActive()) {
    			for (ShootingSprites passedActor: passedSprites) {
    				activeSprite.checkForCollision(passedActor, passedActor.getProjectiles());
    			}
    		}
    }
    
    public void shoot(List<ShootingSprites> passedSprites) {
    		for (ShootingSprites shootingSprite: this.getObservableListOfActive()) {
    			for (ShootingSprites passedSprite: passedSprites) {
    				if (shootingSprite.hasInRange(passedSprite) && shootingSprite.hasReloaded()) {
    					shootingSprite.addProjectile(shootingSprite.launch());
    				}
    			}
    		}
    }
    
	public void moveProjectiles() {
		for (ShootingSprites shootingSprite: this.getObservableListOfActive()) {
			if (shootingSprite.enemyWithinRange() && shootingSprite.hasReloaded()) {
				shootingSprite.shoot();
			}
			for (Projectile projectile: shootingSprite.getProjectiles()) {
//				projectile.move();
			}
		}
	}
   
}
