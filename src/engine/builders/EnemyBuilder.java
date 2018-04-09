package engine.builders;


import engine.sprites.enemies.Enemy;
import engine.sprites.properties.DamageProperty;
import engine.sprites.properties.HealthProperty;
import engine.sprites.properties.ValueProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * 
 * 
 * @author Miles Todzo
 */
public class EnemyBuilder {
	public Enemy construct(String name, Image image, double speed, double initialHealth, double healthImpact,
			double killReward, double killUpgradeCost, double killUpgradeValue) {
		Enemy newEnemy = new Enemy(image, new HealthProperty(0, initialHealth, 0), new DamageProperty(0 , 0 , healthImpact), new ValueProperty(killReward));
		return newEnemy;
	}
}
