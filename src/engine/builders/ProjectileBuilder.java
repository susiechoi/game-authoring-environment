package engine.builders;

import java.util.ArrayList;
import java.util.List;

import engine.sprites.properties.ConstantSpeedProperty;
import engine.sprites.properties.DamageProperty;
import engine.sprites.properties.Property;
import engine.sprites.towers.projectiles.Projectile;

/**
 * Class that uses builder design pattern to construct a projectile
 * 
 * @author Katherine Van Dyk
 * @date 4/7/18
 *
 */
public class ProjectileBuilder {

    @SuppressWarnings("unchecked")
    public Projectile construct(String name, String imagepath, double damage, double size, double constantSpeedProperty) {
	List<Property<Object>> properties = new ArrayList<>();
	properties.add(new DamageProperty(0, 0, damage));
	properties.add(new ConstantSpeedProperty(constantSpeedProperty));
	return new Projectile(name, size, imagepath, properties);
    }


}
