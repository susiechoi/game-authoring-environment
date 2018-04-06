package authoring.frontend;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.ImageView;

public class PathImageComposite implements PathImageInterface {
	
//	private Map<Integer, ImageView> pathImageMap = new HashMap<Integer, ImageView>();
	private List<ImageView> pathImages = new ArrayList<ImageView>();

	public void addPathImage(ImageView pathImage) {
		pathImages.add(pathImage);
	}

	@Override
	public void getCoords() {
		// return coordinates of all blocks
		//sort coordinates in a map?
	}

	@Override
	public void setPathImage(String imageFilePath) {
		// Don't need in composite
	}

	@Override
	public ImageView getPathImage() {
		// Don't need in composite
		return null;
	}

	@Override
	public void setPathSize(double size) {
		for (ImageView path: pathImages) {
			path.setFitWidth(size);
			path.setFitHeight(size);
		}
	}

	@Override
	public double getPathSize() {
		return pathImages.get(0).getFitHeight(); //all path images are same size
	}
	
	public List<ImageView> getPathImages() {
		return pathImages;
	}
}
