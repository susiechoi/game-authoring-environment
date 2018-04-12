package engine.managers;

import java.util.ArrayList;
import java.util.List;
import engine.sprites.ShootingSprites;
import engine.sprites.Sprite;
import engine.sprites.towers.projectiles.Projectile;

/**
 * 
 * @author Miles Todzo
 *
 */

public class ShootingSpriteManager extends Manager<ShootingSprites>{

    private int myRoundScore;
    private List<ShootingSprites> targetsBeingShotAt = new ArrayList<>();
    
    /**
     * Checks for collisions between between the list of active actors held by the Manager the method
     * was called on and the list of active actors passed as a parameter
     * @param passedSprites
     */
    public List<Sprite> checkForCollisions(List<ShootingSprites> passedSprites) {
	myRoundScore = 0;
	List<Sprite> spritesToBeRemoved = new ArrayList<>();
	for (ShootingSprites activeSprite: this.getListOfActive()) {
	    for (ShootingSprites passedActor: passedSprites) {
		List<Sprite> deadSprites = activeSprite.checkForCollision(passedActor);
		spritesToBeRemoved.addAll(deadSprites);
	    }
	    myRoundScore += activeSprite.getRoundScore();
	}
	return spritesToBeRemoved;
    }


    /**
     * The Manager shoots from it's Launcher at the passedSprites
     * @param passedSprites : target being shot at
     * @return Projectiles to add to the front end view
     */
    public List<Projectile> shoot(List<ShootingSprites> passedSprites) {
		List<Projectile> newProjectiles = new ArrayList<>();
		for (ShootingSprites shootingSprite: this.getListOfActive()) {
		    for (ShootingSprites passedSprite: passedSprites) {
			if (shootingSprite.hasReloaded() && shootingSprite.hasInRange(passedSprite)&& passedSprite!=null) {// && !targetsBeingShotAt.contains(passedSprite)) { //TODO add back range check
			    Projectile newProjectile = shootingSprite.launch(passedSprite, shootingSprite.getX(), shootingSprite.getY());
			    //targetsBeingShotAt.add(passedSprite); //Need to remove the target when projectile misses or dies or whatever
			    if (newProjectile != null) {
				newProjectiles.add(newProjectile);
			    }
			}
		    }
		}
		return newProjectiles;
    }

    /**
     * Moves the projectiles. Goes through the Manager and gets the list of projectiles, and moves them
     */
    public void moveProjectiles(double elapsedTime) {
	for (ShootingSprites shootingSprite: this.getListOfActive()) {
	    for (Projectile projectile: shootingSprite.getProjectiles()) {
		projectile.move(elapsedTime);
	    }
	}
    }
    public int getRoundScore() {
	return myRoundScore;
    }
}
