package engine.physics;

import javafx.scene.image.ImageView;

public class Intersector implements IntersectInterface{

	ImageView myImage;
	public Intersector(ImageView image) {
		myImage = image;
	}

	@Override
	public boolean overlap(ImageView otherImage) {
		return otherImage.getBoundsInParent().intersects(myImage.getBoundsInParent());
	}

}
