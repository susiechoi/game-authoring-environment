package authoring.factory;

import java.io.FileNotFoundException;

import authoring.GenericModel;
import authoring.frontend.exceptions.MissingPropertiesException;
import authoring.frontend.exceptions.NoDuplicateNamesException;
import authoring.frontend.exceptions.ObjectNotFoundException;
import engine.level.Level;
import engine.sprites.enemies.Enemy;
import engine.sprites.towers.Tower;

public class SpriteFactory {
    
    GenericModel myGeneric;
    
    public SpriteFactory(GenericModel generic) {
	myGeneric = generic;
    }
    
    public void makeSprite(String objectType, Level level, String name) throws NumberFormatException, FileNotFoundException, NoDuplicateNamesException, MissingPropertiesException, ObjectNotFoundException {
	if(objectType.equals("Tower")) {
	    makeTower(level, name);
	}
	else if(objectType.equals("Enemy")) {
	    makeEnemy(level, name);
	}
    }
    
    private void makeTower(Level level, String name) throws NoDuplicateNamesException, MissingPropertiesException, NumberFormatException, FileNotFoundException, ObjectNotFoundException {
	if (level.containsTower(name)) {
	    throw new NoDuplicateNamesException(name);
	}
	Tower newTower = myGeneric.generateGenericTower(name);
	level.addTower(name, newTower);
    }

    private void makeEnemy(Level level, String name) throws NoDuplicateNamesException, MissingPropertiesException, NumberFormatException, FileNotFoundException, ObjectNotFoundException {
	if (level.containsEnemy(name)) {
	    throw new NoDuplicateNamesException(name);
	}
	Enemy newEnemy = myGeneric.generateGenericEnemy(name);
	level.addEnemy(name, newEnemy);
	System.out.println(level+" "+name);
    }
    
    public void deleteSprite(String objectType, Level level, String name) throws ObjectNotFoundException {
	if (objectType.equals("Tower")) {
	    level.removeTower(name);
	}
	if (objectType.equals("Enemy")) {
	    level.removeEnemy(name);
	}
    }

}
