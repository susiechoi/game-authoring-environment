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
		System.out.println("active list size " + this.getListOfActive().size());
		int i =1;
		for (ShootingSprites shootingSprite: this.getListOfActive()) {
		    for (ShootingSprites passedSprite: passedSprites) {
			System.out.println(" on number " + i);
			if (shootingSprite.hasReloaded()) {
			    System.out.println("in range for " + i);
			    Projectile newProjectile = shootingSprite.launch(passedSprite, shootingSprite.getX(), shootingSprite.getY());
			    if (newProjectile != null) {
				newProjectiles.add(newProjectile);
			    }
			}
		    }
		    i++;
		}
		System.out.println("Projectiles size " + newProjectiles.size());
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
