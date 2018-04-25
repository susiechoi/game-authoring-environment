package engine.builders;

import engine.sprites.properties.DamageProperty;
import engine.sprites.towers.projectiles.Projectile;

/**
 * Class that uses builder design pattern to construct a projectile
 * 
 * @author Katherine Van Dyk
 * @date 4/7/18
 *
 */
public class ProjectileBuilder {

    public Projectile construct(String name, String imagepath, double damage, double size, double speed) {
	DamageProperty damageProperty = new DamageProperty(0, 0, damage);
	return new Projectile(name, damageProperty, speed, imagepath, speed);
    }


}
