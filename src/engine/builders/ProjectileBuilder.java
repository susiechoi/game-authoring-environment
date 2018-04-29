package engine.builders;

import java.util.ArrayList;
import java.util.List;

import engine.sprites.properties.BoomerangProperty;
import engine.sprites.properties.ConstantSpeedProperty;
import engine.sprites.properties.DamageCollisionProperty;
import engine.sprites.properties.DamageProperty;
import engine.sprites.properties.FreezeProperty;
import engine.sprites.properties.HeatSeekingProperty;
import engine.sprites.properties.Property;
import engine.sprites.properties.RandomShotProperty;
import engine.sprites.towers.projectiles.Projectile;

/**
 * Class that uses builder design pattern to construct a projectile
 * 
 * @author Katherine Van Dyk
 * @date 4/7/18
 *
 */
public class ProjectileBuilder {

    public Projectile construct(String name, String imagepath, double damage, double size, double constantSpeedProperty) {
	List<Property> properties = new ArrayList<>();
	properties.add(new DamageProperty(0, 0, damage));
	properties.add(new ConstantSpeedProperty(constantSpeedProperty));
	System.out.println("out here");
	properties.add(new BoomerangProperty(100));
	properties.add(new DamageCollisionProperty(100));
	return new Projectile(name, size, imagepath, properties);
    }


}
