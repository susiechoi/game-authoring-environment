package engine.factories;

import java.util.Collection;

import engine.sprites.Sprite;
import engine.sprites.enemies.Enemy;

public class EnemyFactory extends Factory {
    
    public EnemyFactory(Collection<Sprite> data) {
	super(data);
    }
    
    public Enemy construct(String type) {
	return null;
    }

}
