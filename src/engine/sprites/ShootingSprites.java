package engine.sprites;

import java.util.List;

import engine.managers.ProjectileManager;
import engine.sprites.towers.projectiles.Projectile;
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

	public ShootingSprites(ImageView image, ProjectileManager projectileManager) {
		super(image);
		myProjectileManager = projectileManager;
	}
	
	public ObservableList<Sprite> getProjectiles(){
		return myProjectileManager.getObservableListOfActive();
	}

}
