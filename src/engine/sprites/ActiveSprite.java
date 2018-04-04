package engine.sprites;

import javafx.scene.image.ImageView;

/**
 * This abtract class is for active sprites in the game. This includes Towers, Enemies, Projectiles, and anything else that may have
 * properties outside the scope of the Sprite class.
 * @author milestodzo
 *
 */
public class ActiveSprite extends Sprite{

	private double myDamage;
	public ActiveSprite(ImageView image, double damage) {
		super(image);
		myDamage = damage;
	}

	public double getDamge() {
		return myDamage;
	}
}
