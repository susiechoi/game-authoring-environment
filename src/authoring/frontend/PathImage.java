package authoring.frontend;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class PathImage implements PathImageInterface {
	
	private ImageView pathImage;
	
	public PathImage(String imageFilePath) {
		setPathImage(imageFilePath);
	}


	@Override
	public void getCoords() {
		//return bounds of just one block (in map?)
		// TODO Auto-generated method stub
	}

	@Override
	public void setPathImage(String imageFilePath) {
		Image pathImg = new Image(imageFilePath);
		pathImage = new ImageView();
		pathImage.setImage(pathImg);
		pathImage.setFitHeight(CreatePathPanel.PANEL_PATH_SIZE);
		pathImage.setFitWidth(CreatePathPanel.PANEL_PATH_SIZE);
	}

	@Override
	public ImageView getPathImage() {
		return pathImage;
	}

	@Override
	public void setPathSize(double size) {
		pathImage.setFitHeight(size);
		pathImage.setFitWidth(size);
	}

	@Override
	public double getPathSize() {
		return pathImage.getFitHeight();
	}
}
