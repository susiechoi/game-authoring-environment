package authoring.frontend;

import javafx.scene.image.ImageView;

public interface PathImageInterface {

	public void getCoords(); //return bounds?
	
	public void setPathImage(String imageFilePath);
	
	public ImageView getPathImage();
	
	public void setPathSize(double size); //for composite only need get/set size and getCoords
	
	public double getPathSize();
}
