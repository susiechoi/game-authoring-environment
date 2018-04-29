package authoring.frontend;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class StartBlock {

	private String myFilePath;
	private int myPathSize;

	/**
	 * Mover constructor
	 * @param x
	 * @param y
	 * @param imageFilePath
	 */
	public StartBlock(String imageFilePath, int pathSize) {
		setStartBlock(imageFilePath, pathSize);
		myPathSize = pathSize;
		myFilePath = imageFilePath;
	}



	public void setStartBlock(String imageFilePath, int pathSize) {
		ImageView image = new ImageView(new Image(imageFilePath));
		image.setFitWidth(pathSize);
		image.setFitHeight(pathSize);
	}
}
