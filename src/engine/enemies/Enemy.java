package engine.enemies;

import engine.towers.projectiles.Projectile;
import javafx.scene.image.Image;

/**
 * This is used for the Enemy object in the game. It will use composition to implement moveable
 * and intersectable methods.
 * @author ryanpond
 *
 */
public class Enemy implements EnemyI{

	public Enemy() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void move(int newX, int newY) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void rotate(double angle) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean overlap(Image otherImage) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getHitBy(Projectile projectile) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void followPath() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

}
