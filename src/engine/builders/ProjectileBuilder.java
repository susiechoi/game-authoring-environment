package engine.builders;

import engine.sprites.properties.ConstantSpeedProperty;
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

    public Projectile construct(String name, String imagepath, DamageProperty damage, double size, ConstantSpeedProperty constantSpeedProperty) {
	return new Projectile(name, damage, size, imagepath, constantSpeedProperty);
    }


}
