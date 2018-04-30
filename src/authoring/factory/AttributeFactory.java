package authoring.factory;

import authoring.AuthoredGame;
import authoring.frontend.exceptions.ObjectNotFoundException;
import engine.level.Level;
import engine.path.Path;
import engine.sprites.enemies.Enemy;
import engine.sprites.enemies.wave.Wave;
import engine.sprites.towers.Tower;

public class AttributeFactory {

    private AttributeFinder attributeFinder; 

    public AttributeFactory() {
	attributeFinder = new AttributeFinder();
    }

    public void setObjectAttribute(int level, String objectType, String name, String attribute, Object attributeValue, AuthoredGame game) throws ObjectNotFoundException, IllegalArgumentException, IllegalAccessException {
	if (objectType.equals("Enemy")) {
	    Level currentLevel = game.levelCheck(level);
	    if (currentLevel.containsEnemy(name)) {
		currentLevel.getEnemy(name).updateImage((String) attributeValue); 
	    }
	}
	else if(objectType.equals("Projectile")) {
	    Level currentLevel = game.levelCheck(level);
	    if (currentLevel.containsTower(name)) {
		currentLevel.getTower(name).setProjectileImage((String) attributeValue);
	    }
	}
	else if (objectType.equals("Tower")) {
	    Level currentLevel = game.levelCheck(level);
	    if (currentLevel.containsTower(name)) {
		currentLevel.getTower(name).updateImage((String) attributeValue); 
	    }
	}
	else if (objectType.equals("Settings")) {
	    attributeFinder.setFieldValue(attribute, game.getSettings(), attributeValue);
	}
    }

    public Object getObjectAttribute(int level, String objectType, String name, String attribute, AuthoredGame game) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, ObjectNotFoundException {
	Object attributeValue = null;
	AttributeFinder attributeFinder = new AttributeFinder(); 
	if (objectType.equals("Enemy")) {
	    Level currentLevel = game.levelCheck(level);
	    if (currentLevel.containsEnemy(name)) {
		Enemy enemy = currentLevel.getEnemy(name);
		attributeValue = attributeFinder.retrieveFieldValue(attribute, enemy);
	    }
	}
	else if (objectType.equals("Tower")) {
	    Level currentLevel = game.levelCheck(level);
	    if (currentLevel.containsTower(name)) {
		Tower tower = currentLevel.getTower(name);
		attributeValue = attributeFinder.retrieveFieldValue(attribute, tower);
	    }
	}
	else if (objectType.equals("Settings")) {
	    attributeValue = attributeFinder.retrieveFieldValue(attribute, game.getSettings());
	}
	else if (objectType.equals("Path")) {
	    Level currentLevel = game.levelCheck(level);
	    Path path = currentLevel.getPath();
	    System.out.println("PATH LEVEL LOADING FROM: " + level);
	    attributeValue = attributeFinder.retrieveFieldValue(attribute, path);
	}
	else if(objectType.equals("Wave")) {
	    Level currentLevel = game.levelCheck(level);
	    if (currentLevel.containsWaveNumber(Integer.parseInt(name))) {
		Wave wave = currentLevel.getWaves().get(Integer.parseInt(name));
		attributeValue = attributeFinder.retrieveFieldValue(attribute, wave);
	    }
	}
	if (attributeValue == null) {
	    throw new ObjectNotFoundException(name);
	}
	if (attributeValue.getClass() == Double.class) {
	    return Double.toString((double) attributeValue); 
	} 
	else return attributeValue; 
    }


}
