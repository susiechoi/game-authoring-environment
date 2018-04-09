package engine.builders;

import engine.sprites.properties.DamageProperty;
import engine.sprites.towers.projectiles.Projectile;
import javafx.scene.image.Image;

/**
 * Class that uses builder design pattern to construct a projectile
 * 
 * @author Katherine Van Dyk
 * @date 4/7/18
 *
 */
public class ProjectileBuilder {

	public Projectile construct(String name, Image image, double damage, double damageCost, double damageValue) {
		DamageProperty damageProperty = new DamageProperty(damageCost, damageValue, damage);
		return new Projectile(name, damageProperty, image);
	}

}
