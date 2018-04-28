package engine.sprites;

import java.util.ArrayList;
import java.util.List;

import engine.physics.ImageIntersecter;
import engine.sprites.properties.HealthProperty;
import engine.sprites.properties.Property;
import engine.sprites.properties.UpgradeProperty;
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
    public ShootingSprites(String name, String image, double size, Launcher launcher, List<Property> properties) {
	super(name, image, size, properties);
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
	List<Sprite> toBeRemoved = new ArrayList<>();
	List<Projectile> projectilesToBeDeactivated = new ArrayList<>();
	toBeRemoved.addAll(this.checkTowerEnemyCollision(target));
	for (Projectile projectile: this.getProjectiles()) {
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
	List<Sprite> deadSprites = new ArrayList<>();
	hitCount++;
	if(!target.handleCollision(collider)) {
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
	    if(this.handleCollision(shooter)) {
		toBeRemoved.add(this);
	    }
	}
	return toBeRemoved;
    }

    public boolean hasInRange(Sprite passedSprite) {
	double distanceBetween = Math.sqrt(Math.pow(passedSprite.getX()-this.getX(),2)+Math.pow(passedSprite.getY()-this.getY(), 2));
	return (distanceBetween <= myLauncher.getProperty("RangeProperty").getProperty());
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
    //TODO: GET RID OF MAGIC NAMES -> PROPERTIES FILE
    public double upgrade(String upgradeName, double balance) {
	//	System.out.println("gets here");
	if(upgradeName.equals("test4")) {
	    System.out.println("upgrade is working woo");
	    return upgradeLauncherProperty(balance, "FireRateProperty");
	}
	if(upgradeName == "test3") {
	    return upgradeProperty(balance, "HealthProperty");
	}
	if(upgradeName == "test2") {
	    return upgradeDamage(balance);
	}
	if(upgradeName == "test1") {
	    return upgradeLauncherProperty(balance, "RangeProperty");
	}
	return balance;

    }

    public double upgradeProperty(double balance, String propertyName) {
	UpgradeProperty propToUpgrade = (UpgradeProperty)this.getProperty(propertyName);
	return propToUpgrade.upgrade(balance);
    }

    public double upgradeLauncherProperty(double balance, String propertyName) {
	return this.getLauncher().upgradeProperty(balance, propertyName);
    }

    private double upgradeFireRate(double balance) {
	return this.getLauncher().upgradeProperty("FireProperty", balance);
    }

    private double upgradeHealth(double balance) {
	return balance;

    }

    private double upgradeDamage(double balance) {
	return this.getLauncher().upgradeDamage(balance);
    }

    private double upgradeRange(double balance) {
	return this.getLauncher().upgradeProperty("RangeProperty", balance);
    }

    protected void updateLauncher(Launcher launcher) {
	myLauncher = launcher; 
    }

    /**
     * Returns true if this ShootingSprite is still alive
     */
    @Override
    public boolean handleCollision(Sprite collider) {
	this.loseHealth(collider.getDamage());
	return this.isAlive();
    }

    public void loseHealth(double damage) {
	((HealthProperty) this.getProperty("HealthProperty")).loseHealth(damage);
    }

}
