package authoring;

import java.awt.Point;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import authoring.frontend.exceptions.MissingPropertiesException;
import engine.builders.EnemyBuilder;
import engine.builders.LauncherBuilder;
import engine.builders.PathBuilder;
import engine.builders.ProjectileBuilder;
import engine.builders.TowerBuilder;
import engine.path.Path;
import engine.sprites.enemies.Enemy;
import engine.sprites.towers.Tower;
import engine.sprites.towers.launcher.Launcher;
import engine.sprites.towers.projectiles.Projectile;
import frontend.PropertiesReader;

/**
 * 
 * @author Ben Hodgson 4/8/18
 *
 * Class to generate generic objects to be used in the AuthoringModel
 */
public class GenericModel {

    public static String DEFAULT_ENEMY_IMAGES;
    public static String DEFAULT_ENEMY_IMAGE;
    public static String DEFAULT_TOWER_IMAGES;
    public static String DEFAULT_IMAGES_SUFFIX;
    public static String DEFAULT_PROJECTILE_IMAGES;
    public static String DEFAULT_TOWER_IMAGE;
    public static String DEFAULT_PROJECTILE_IMAGE;
    public static String DEFAULT_TOWER_FILEPATH;
    public static String DEFAULT_ENEMY_FILEPATH;
    public static String DEFAULT_PATH_START;
    public static String DEFAULT_PATH_MIDDLE;
    public static String DEFAULT_PATH_END;
    public static String DEFAULT_BACKGROUND_IMAGE;
    public static String DEFAULT_CONSTANT_FILEPATH;
    private final String mySettingsFile = "default_objects/Settings.properties";
    private PropertiesReader myPropertiesReader;
    private String myDefaultName;

    public GenericModel() {
	try {
	    populateInstanceVariables();
	} catch (MissingPropertiesException e) {
	    System.out.println("Could not instantiate instance variables in GenericModel Class!");
	    e.printStackTrace();
	}
    }

    public Path generateGenericPath() throws NumberFormatException, FileNotFoundException {
	List<Point> dummyPathPoints = new ArrayList<>();
	dummyPathPoints.add(new Point(2, 2));
	dummyPathPoints.add(new Point(2, 3));
	HashMap<String, List<Point>> pathImages = new HashMap<>();
	List<Point> dummyPathStartPoints = new ArrayList<>();
	dummyPathStartPoints.add(new Point(2, 2));
	List<Point> dummyPathEndPoints = new ArrayList<>();
	dummyPathEndPoints.add(new Point(2, 3));
	pathImages.put(DEFAULT_PATH_START, dummyPathStartPoints);
	pathImages.put(DEFAULT_PATH_END, dummyPathEndPoints);
	Path newPath = new PathBuilder().construct(dummyPathPoints, pathImages, DEFAULT_BACKGROUND_IMAGE, 60);
	return newPath;
    }

    /**
     * Reads information from GenericEnemy.properties file to create a default
     * Enemy object to be used to populate user input fields.
     * 
     * @return Enemy: a generic enemy with attribute read in from .properties file
     * @throws FileNotFoundException 
     * @throws NumberFormatException 
     */
    public Enemy generateGenericEnemy() throws NumberFormatException, FileNotFoundException {
	return generateGenericEnemy(myDefaultName);
    }
    
    /**
     * Reads information from GenericEnemy.properties file to create a default
     * Enemy object to be used to populate user input fields.
     * 
     * @param name: A string name to give to the Enemy object
     * @return Enemy: a generic enemy with attribute read in from .properties file
     * @throws FileNotFoundException 
     * @throws NumberFormatException 
     */
    public Enemy generateGenericEnemy(String name) throws NumberFormatException, FileNotFoundException {
	try {
	    Enemy newEnemy = new EnemyBuilder().construct(
		    name, 
		    myPropertiesReader.findVal(DEFAULT_ENEMY_FILEPATH, "enemyImage"), 
		    Double.parseDouble(myPropertiesReader.findVal(DEFAULT_ENEMY_FILEPATH,"enemySpeed")), 
		    Double.parseDouble(myPropertiesReader.findVal(DEFAULT_ENEMY_FILEPATH,"enemyHealth")), 
		    Double.parseDouble(myPropertiesReader.findVal(DEFAULT_ENEMY_FILEPATH,"enemyHealthImpact")), 
		    Double.parseDouble(myPropertiesReader.findVal(DEFAULT_ENEMY_FILEPATH,"enemyKillReward")), 
		    Double.parseDouble(myPropertiesReader.findVal(DEFAULT_ENEMY_FILEPATH,"enemyKillUpgradeCost")), 
		    Double.parseDouble(myPropertiesReader.findVal(DEFAULT_ENEMY_FILEPATH,"enemyKillUpgradeValue")));
	    return newEnemy;

	} catch (MissingPropertiesException e) {
	    // TODO Auto-generated catch block
	    System.out.println("Could not load GenericTower object!");
	}
	return null;
    }

    /**
     * Reads information from GenericTower.properties file to create a default
     * Tower object to be used to populate user input fields.
     * 
     * @return Tower: a generic tower with attribute read in from .properties file
     */
    public Tower generateGenericTower() throws NumberFormatException, FileNotFoundException {
	return generateGenericTower(myDefaultName);
    }

    /**
     * Reads information from GenericTower.properties file to create a default
     * Tower object to be used to populate user input fields.
     * 
     * @param name: A string name to give to the Tower object
     * @return Tower: a generic tower with attribute read in from .properties file
     */
    public Tower generateGenericTower(String name) throws NumberFormatException, FileNotFoundException {
	try {
	    double towerSize = Double.parseDouble(myPropertiesReader.findVal(DEFAULT_TOWER_FILEPATH, "towerSize"));
	    Projectile towerProjectile = generateGenericProjectile(name);
	    Launcher towerLauncher = generateGenericLauncher(towerProjectile);
	    Tower newTower = new TowerBuilder().construct(
		    myDefaultName, 
		    myPropertiesReader.findVal(DEFAULT_TOWER_FILEPATH, "towerImage"), 
		    towerSize, 
		    Double.parseDouble(myPropertiesReader.findVal(DEFAULT_TOWER_FILEPATH, "towerHealth")), 
		    Double.parseDouble(myPropertiesReader.findVal(DEFAULT_TOWER_FILEPATH, "towerHealthUpgradeValue")), 
		    Double.parseDouble(myPropertiesReader.findVal(DEFAULT_TOWER_FILEPATH, "towerHealthUpgradeCost")), 
		    towerLauncher,
		    Double.parseDouble(myPropertiesReader.findVal(DEFAULT_TOWER_FILEPATH, "towerValue")),
		    Double.parseDouble(myPropertiesReader.findVal(DEFAULT_TOWER_FILEPATH, "towerUpgradeCost")),
		    Double.parseDouble(myPropertiesReader.findVal(DEFAULT_TOWER_FILEPATH, "towerUpgradeValue"))); 
	    return newTower;
	} 
	catch (MissingPropertiesException e) {
	    // TODO Auto-generated catch block
	    System.out.println("Could not load GenericTower object!");
	}
	return null;
    }

    /**
     * Reads information from GenericTower.properties file to create a default
     * Launcher object to be used to manage a projectile in a Tower object.
     * 
     * @return Launcher: a generic projectile launcher with attribute read in from .properties file
     * @throws MissingPropertiesException 
     */
    private Launcher generateGenericLauncher(Projectile towerProjectile) throws NumberFormatException, FileNotFoundException, MissingPropertiesException {
	//	double projectileSize = Double.parseDouble(myPropertiesReader.findVal(DEFAULT_TOWER_FILEPATH, "projectileSize"));
	return new LauncherBuilder().construct(
		Double.parseDouble(myPropertiesReader.findVal(DEFAULT_TOWER_FILEPATH, "launcherSpeed")),  
		Double.parseDouble(myPropertiesReader.findVal(DEFAULT_TOWER_FILEPATH, "launcherRange")), 
		towerProjectile);  
    }

    /**
     * Reads information from GenericTower.properties file to create a default
     * Projectile object to be used to populate input fields for a Tower object.
     * 
     * @param name: A string name to give to the Projectile object
     * @return Projectile: a generic projectile with attribute read in from .properties file
     * @throws MissingPropertiesException 
     */
    private Projectile generateGenericProjectile(String name) throws NumberFormatException, FileNotFoundException, MissingPropertiesException {
	//	double projectileSize = Double.parseDouble(myPropertiesReader.findVal(DEFAULT_TOWER_FILEPATH, "projectileSize"));
	return new ProjectileBuilder().construct(
		name,  
		myPropertiesReader.findVal(DEFAULT_TOWER_FILEPATH, "projectileImage"), 
		// TODO add projectile speed !!!!
		Double.parseDouble(myPropertiesReader.findVal(DEFAULT_TOWER_FILEPATH, "projectileDamage")), 
		Double.parseDouble(myPropertiesReader.findVal(DEFAULT_TOWER_FILEPATH, "projectileSize")),
		Double.parseDouble(myPropertiesReader.findVal(DEFAULT_TOWER_FILEPATH, "projectileSpeed")));
    }

    private void populateInstanceVariables() throws MissingPropertiesException {
	myPropertiesReader = new PropertiesReader();
	myPropertiesReader = new PropertiesReader();
	DEFAULT_ENEMY_IMAGES = myPropertiesReader.findVal(mySettingsFile, "EnemyImages");
	DEFAULT_ENEMY_IMAGE = myPropertiesReader.findVal(mySettingsFile, "Enemy");
	DEFAULT_TOWER_IMAGES = myPropertiesReader.findVal(mySettingsFile, "TowerImages");
	DEFAULT_IMAGES_SUFFIX =  myPropertiesReader.findVal(mySettingsFile, "ImageSuffix");
	DEFAULT_PROJECTILE_IMAGES = myPropertiesReader.findVal(mySettingsFile, "ProjectileImages");
	DEFAULT_TOWER_IMAGE = myPropertiesReader.findVal(mySettingsFile, "Tower");
	DEFAULT_PROJECTILE_IMAGE = myPropertiesReader.findVal(mySettingsFile, "Projectile");
	DEFAULT_TOWER_FILEPATH = myPropertiesReader.findVal(mySettingsFile, "TowerFiles");
	DEFAULT_ENEMY_FILEPATH = myPropertiesReader.findVal(mySettingsFile, "EnemyFiles");
	DEFAULT_PATH_START = myPropertiesReader.findVal(mySettingsFile, "PathStart");
	DEFAULT_PATH_MIDDLE = myPropertiesReader.findVal(mySettingsFile, "PathMiddle");
	DEFAULT_PATH_END = myPropertiesReader.findVal(mySettingsFile, "PathEnd");
	DEFAULT_BACKGROUND_IMAGE = myPropertiesReader.findVal(mySettingsFile, "Background");
	DEFAULT_CONSTANT_FILEPATH = myPropertiesReader.findVal(mySettingsFile, "ConstantFiles");
	myDefaultName = myPropertiesReader.findVal(DEFAULT_CONSTANT_FILEPATH, "DefaultObjectName");
    }

}
