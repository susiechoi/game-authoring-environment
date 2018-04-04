package engine.sprites.towers;

import engine.sprites.Sprite;
import engine.sprites.enemies.EnemyI;
import engine.sprites.properties.HealthProperty;
import engine.sprites.properties.ValueProperty;
import engine.sprites.towers.launcher.Launcher;
import javafx.scene.image.ImageView;

public class Tower extends Sprite implements TowerI {

    private Launcher myLauncher;
    private HealthProperty myHealth;
    private ValueProperty myValue;

    public Tower(ImageView image, Launcher launcher, double health, double value) {
	super(image);
	myLauncher = launcher;
	myHealth = new HealthProperty(health);
	myValue = new ValueProperty(value);
    }

    @Override
    public void changeHealth(double h) {
	myHealth.change(h);
    }
    
    @Override
    public void getHitBy(EnemyI myEnemy) {
    }

    public void move(double newX, double newY) {
	myImage.setX(newX);
	myImage.setY(newY);
    }

    @Override
    public double sell() {
	return myValue.getProperty();
    }

    @Override
    public double upgradeGeneral(double balance) {
	//TODO balance
	return balance;
    }

    public double upgradeHealth(double balance) {
	return myHealth.upgrade(balance);
    }

    @Override
    public double upgradeRateOfFire(double balance) {
	return balance;
	// TODO Auto-generated method stub
	
    }

    @Override
    public double upgradeDamage(double balance) {
	return myLauncher.upgrade(balance);
    }

}
