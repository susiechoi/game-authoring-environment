package engine.factories;

import java.util.Collection;
import java.util.Map;

import engine.sprites.enemies.Enemy;

public class EnemyFactory extends Factory {
    
    public EnemyFactory(Map<String, Collection<Object>> data) {
	super(data);
    }
    
    public Enemy construct(String type) {
	return null;
    }

}
