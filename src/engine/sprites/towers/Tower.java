package engine.sprites.towers;

import engine.sprites.Sprite;
import engine.sprites.enemies.EnemyI;
import engine.sprites.properties.*;
import engine.sprites.towers.launcher.Launcher;
import javafx.scene.image.ImageView;

public class Tower extends Sprite implements TowerI {

    private Launcher myLauncher;
    private HealthProperty myHealth;
    private ValueProperty myValue;

    public Tower(ImageView image, Launcher launcher, HealthProperty health, ValueProperty value) {
	super(image);
	myLauncher = launcher;
	myHealth = health;
	myValue = value;
    }

    @Override
    public void changeHealth(double h) {
	myHealth.change(h);
    }
    
    @Override
    public void getHitBy(EnemyI myEnemy) {
    }

    @Override
    public double sell() {
	return myValue.getProperty();
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

    @Override
    public double upgrade(double balance) {
	// TODO Auto-generated method stub
	return 0;
    }

}
