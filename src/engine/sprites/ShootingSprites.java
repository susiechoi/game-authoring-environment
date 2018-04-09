package engine.sprites;

import java.util.List;

import engine.sprites.towers.launcher.Launcher;
import engine.sprites.towers.projectiles.Projectile;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * This class is a more specific Sprite that applies to just shooting objects (Enemy and Tower).
 * @author Miles Todzo
 * @author Katherine Van Dyk
 * @param image
 * @param projectileManager
 */

public class ShootingSprites extends Sprite{
	
	private Launcher myLauncher;
	private int hitCount;

	public ShootingSprites(String name, Image image, double size, Launcher launcher) {
		super(name, image);
		this.getImageView().setFitHeight(size);
		this.getImageView().setFitWidth(size);
		myLauncher = launcher;
	}
	
	public ObservableList<Projectile> getProjectiles(){
		return myLauncher.getObservableListOfActive();
	}
	
	public void increaseHitCount(int increaseAmount) {
		hitCount+=increaseAmount;
	}
	
	public void checkForCollision(ShootingSprites shooter, List<Projectile> projectiles) {
		this.checkTowerEnemyCollision(shooter);
		for (Projectile projectile: projectiles) {
			ImageView projectileImageView = projectile.getImageView();
			if(this.getImageView().intersects(projectileImageView.getX(), projectileImageView.getY(), projectileImageView.getFitWidth(), projectileImageView.getFitHeight())){
				this.handleCollision();
				projectile.handleCollision();
			}
		}
	}

	public void checkTowerEnemyCollision(ShootingSprites shooter) {
		if (this.getImageView().intersects(shooter.getImageView().getX(), shooter.getImageView().getY(), shooter.getImageView().getFitWidth(), shooter.getImageView().getFitHeight())) {
			this.handleCollision();
			shooter.handleCollision();
		}
	}
	
	//TODO should this be moved into launcher class? -- don't have access to x and y loc in launcher class
	public boolean hasInRange(ShootingSprites passedSprite) {
		double distanceBetween = Math.sqrt(Math.pow(passedSprite.getX()-this.getX(),2)+Math.pow(passedSprite.getY()-this.getY(), 2));
		return (distanceBetween <= myLauncher.getRange());
	}

	public boolean hasReloaded() {
		return myLauncher.hasReloaded();
	}
	
	public void launch() {
		myLauncher.launch();
	}

}
