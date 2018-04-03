package engine.towers.projectiles;

import javafx.scene.image.ImageView;

public class Projectile implements ProjectileInterface{

	private int myDamage;
	private ImageView myImage;
	public Projectile(int damage, ImageView image) {
		myDamage = damage;
		myImage = image;
	}

	@Override
	public void getCurve(int speed, int xStart, int yStart) {
		
	}

	@Override
	public void launch() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hitsEnemy() {
		// TODO Auto-generated method stub
		
	}

}
