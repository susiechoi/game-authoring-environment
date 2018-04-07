package engine.sprites;

import engine.managers.ProjectileManager;
import javafx.collections.ObservableList;
import javafx.scene.image.ImageView;

/**
 * This class is a more specific Sprite that applies to just shooting objects (Enemy and Tower).
 * @author Miles Todzo
 * @param image
 * @param projectileManager
 */

public class ShootingSprites extends Sprite{
	
	private ProjectileManager myProjectileManager;
	private int hitCount;

	public ShootingSprites(ImageView image) {
		super(image);
		myProjectileManager = new ProjectileManager();
	}
	
	public ObservableList<Sprite> getProjectiles(){
		return myProjectileManager.getObservableListOfActive();
	}
	
	public void increaseHitCount(int increaseAmount) {
		hitCount+=increaseAmount;
	}
//	
//	@Override
//	public void checkForCollision(ShootingSprites shooter, List<Sprite> sprites) {
//		this.checkTowerEnemyCollision(shooter);
//		for (Sprite sprite: sprites) {
//			ImageView spriteImageView = sprite.getImage();
//			if(this.getImage().intersects(spriteImageView.getX(), spriteImageView.getY(), spriteImageView.getFitWidth(), spriteImageView.getFitHeight())){
//				this.handleCollision();
//				sprite.handleCollision();
//			}
//		}
//	}

	public void checkTowerEnemyCollision(ShootingSprites shooter) {
		if (this.getImage().intersects(shooter.getImage().getX(), shooter.getImage().getY(), shooter.getImage().getFitWidth(), shooter.getImage().getFitHeight())) {
			this.handleCollision();
		}
	}

}
