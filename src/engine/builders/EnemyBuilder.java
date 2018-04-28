package engine.builders;

import engine.sprites.enemies.Enemy;
import engine.sprites.properties.DamageProperty;
import engine.sprites.properties.HealthProperty;
import engine.sprites.properties.ValueProperty;
import engine.sprites.towers.launcher.Launcher;

/**
 * 
 * 
 * @author Miles Todzo
 */
public class EnemyBuilder {

    public Enemy construct(String name, String image, double speed, double initialHealth, double healthImpact,
	    double killReward, double killUpgradeCost, double killUpgradeValue, Launcher launcher) {
	Enemy newEnemy = new Enemy(name, image, speed, 50, launcher, new HealthProperty(0, 0, initialHealth), new DamageProperty(0 , 0 , healthImpact), new ValueProperty(killReward));
	return newEnemy;
    }

}
