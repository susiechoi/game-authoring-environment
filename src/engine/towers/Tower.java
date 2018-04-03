package engine.towers;

import engine.enemies.EnemyI;
import engine.properties.HealthProperty;
import engine.properties.ValueProperty;
import engine.towers.launcher.Launcher;
import javafx.scene.image.ImageView;

public class Tower implements TowerI{

    private Launcher myLauncher;
    private HealthProperty myHealth;
    private ImageView myImage;
    private ValueProperty myValue;

    public Tower(ImageView image, Launcher launcher, double health, double value) {
	myLauncher = launcher;
	myHealth = new HealthProperty(health);
	myImage = image;
	myValue = new ValueProperty(value);
    }

    @Override
    public void changeHealth(double h) {
	myHealth.change(h);
    }
    
    @Override
    public void getHitBy(EnemyI myEnemy) {
    }

    @Override
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

    @Override
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
	return balance;
	// TODO Auto-generated method stub
	
    }

}
