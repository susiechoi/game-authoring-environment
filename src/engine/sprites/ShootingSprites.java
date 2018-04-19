package engine.sprites;

import java.util.ArrayList;
import java.util.List;

import engine.physics.ImageIntersecter;
import engine.sprites.properties.HealthProperty;
import engine.sprites.towers.launcher.Launcher;
import engine.sprites.towers.projectiles.Projectile;

/**
 * This class is a more specific Sprite that applies to just shooting objects (Enemy and Tower).
 * @author Miles Todzo
 * @author Katherine Van Dyk
 * @author Ryan Pond 4/9
 * @param image
 * @param projectileManager
 */
public abstract class ShootingSprites extends Sprite{

    private Launcher myLauncher;
    private int hitCount;
    private int deadCount;
    private ImageIntersecter intersector;
    //   private List<Sprite> targetsBeingShotAt;
    /**
     * Shooting sprite that is holds a launcher and is able to shoot at other sprites
     * on the screen
     * 
     * @param name: Name of the sprite
     * @param image: String denoting image path of sprite
     * @param size: Size parameter of the image
     * @param launcher: Launcher object specific to shooting sprite
     */
    public ShootingSprites(String name, String image, double size, Launcher launcher) {
	super(name, image, size);
	hitCount=0;
	deadCount = 0;
	intersector = new ImageIntersecter(this);
	//	this.getImageView().setFitHeight(size);
	//	this.getImageView().setFitWidth(size);
	myLauncher = launcher;
	//	targetsBeingShotAt = new ArrayList<>();
    }

    /**
     * @return List of all active projectiles
     */
    public List<Projectile> getProjectiles(){
	return myLauncher.getListOfActive();
    }

    /**
     * Increases the hit count of the enemy
     * 
     * @param increaseAmount
     */
    public void increaseHitCount(int increaseAmount) {
	hitCount+=increaseAmount;
    }

    /**
     * This checks for collisions between the shooter's projectiles and this ShootingSprite
     * @param shooter : Input shooter that is shooting projectiles
     * @return : a list of all sprites to be removed from screen (dead)
     */
    public List<Sprite> checkForCollision(ShootingSprites target) {
//    	System.out.println("CHECK FOR COLLISION");
	List<Sprite> toBeRemoved = new ArrayList<>();
	List<Projectile> projectilesToBeDeactivated = new ArrayList<>();
	List<Projectile> projectiles = this.getProjectiles();
	toBeRemoved.addAll(this.checkTowerEnemyCollision(target)); //TODO add any dead tower/enemy to toBeRemoved list
	//System.out.println("PROJECTILES SIZE " + projectiles.size());
	for (Projectile projectile: projectiles) {
	//	System.out.println("WEEEWOOWOWOWO");
//		System.out.println(target.intersects(projectile) + " " + !(projectile.hasHit(target)));
	    if(target.intersects(projectile) && !(projectile.hasHit(target))){
		toBeRemoved.addAll(objectCollision(target, projectile)); //checks collisions between projectiles and enemy/tower
		if (projectile.handleCollision(target)) {
		    toBeRemoved.add(projectile);
		    projectilesToBeDeactivated.add(projectile);
		}
	    }
	}
	for (Projectile deactivatedProjectile: projectilesToBeDeactivated) { //TODO implement method in shootingSprite (deactivateProjectile) that does this
	    this.getLauncher().removeFromActiveList(deactivatedProjectile);
	}
	return toBeRemoved;
    }

    private List<Sprite> objectCollision(Sprite target, Sprite collider) {
   // 	System.out.println("IN OBJECT COLLISION");
  //  	System.out.println(target.getImageView().intersects(collider.getImageView().getBoundsInLocal()));
	List<Sprite> deadSprites = new ArrayList<>();
	hitCount++;
	if(!target.handleCollision(collider)) {
//		System.out.println("COLLISION WITH COLLIDER IN OBJECT COLLISION");
	    deadCount++;
	    deadSprites.add(target);
	}
	return deadSprites;
    }

    /**
     * Checks to see if the shooter itself overlaps with this ShootingSprite object
     * @param shooter
     * @return
     */
    public List<Sprite> checkTowerEnemyCollision(ShootingSprites shooter) {
	List<Sprite> toBeRemoved = new ArrayList<>();
	if (intersector.overlaps(shooter.getImageView())) { 
	    this.handleCollision(shooter); //TODO - handle these
	    shooter.handleCollision(this);
	}
	return toBeRemoved;
    }

    public boolean hasInRange(ShootingSprites passedSprite) {
	double distanceBetween = Math.sqrt(Math.pow(passedSprite.getX()-this.getX(),2)+Math.pow(passedSprite.getY()-this.getY(), 2));
	return (distanceBetween <= myLauncher.getRange());
    }

    public boolean hasReloaded(double elapsedTime) {
	return myLauncher.hasReloaded(elapsedTime);
    }

    public Projectile launch(ShootingSprites target, double shooterX, double shooterY) {
	return myLauncher.launch(target, shooterX, shooterY);
    }

    /**
     * Checks if there is an intersection between a projectile (fired from tower) and this enemy
     * @param projectile
     * @return intersect or not
     */
    public boolean intersects(Projectile projectile) {
	return this.getImageView().getBoundsInLocal().intersects(projectile.getImageView().getBoundsInLocal());
	//return intersector.overlaps(projectile.getImageView());
    }

    public Launcher getLauncher() {
	return myLauncher;
    }
    
    @Override
    public double getDamage() {
	return myLauncher.getProjectileDamage();
    }
    
    protected int getHitCount() {
	return hitCount;
    }
    
    protected double getDeadCount() {
	return deadCount;
    }
    
    public boolean isAlive() {
    	return (this.getHealthProp().getProperty() > 0);
    }

	protected HealthProperty getHealthProp() {
		// TODO Auto-generated method stub
		return new HealthProperty(0,0,0);
	}

    /**
     * Method that will upgrade the Sprite
     * @param upgradeName : Property to be upgraded
     */
    public double upgrade(String upgradeName, double balance) {
	if(upgradeName == "FireRate") {
	    return upgradeFireRate(balance);
	}
	if(upgradeName == "Health") {
	    return upgradeHealth(balance);
	}
	if(upgradeName == "Damage") {
	    return upgradeDamage(balance);
	}
	if(upgradeName == "Range") {
	    return upgradeRange(balance);
	}
	return balance;
	
    }

    private double upgradeFireRate(double balance) {
	return this.getLauncher().upgradeFireRate(balance);
    }

    private double upgradeHealth(double balance) {
	return balance;
	
    }

    private double upgradeDamage(double balance) {
	return this.getLauncher().upgradeDamage(balance);
    }

    private double upgradeRange(double balance) {
	return this.getLauncher().upgradeRange(balance);
    }


}
