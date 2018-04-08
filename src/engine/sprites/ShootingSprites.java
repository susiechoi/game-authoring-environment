package engine.sprites;

import engine.managers.ProjectileManager;
import javafx.collections.ObservableList;
import javafx.scene.image.ImageView;

public class ShootingSprites extends Sprite{
	
	ProjectileManager myProjectileManager;
	
	/**
	 * This class is a more specific Sprite that applies to just shooting objects (Enemy and Tower).
	 * @author Miles Todzo
	 * @param image
	 * @param projectileManager
	 */

	public ShootingSprites(ImageView image) {
		super(image);
		myProjectileManager = new ProjectileManager();
	}
	
	public ObservableList<Sprite> getProjectiles(){
		return myProjectileManager.getObservableListOfActive();
	}

}
