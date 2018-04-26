package xml.serialization;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ImageWrapper implements ImageViewSerializer {

    private String myImagePath;
    private double height;
    private double width;
    private double x;
    private double y;
    private boolean visible;
    
    public ImageWrapper(String filepath) {
	myImagePath = filepath;
    }
    
    @Override
    public void fromImageView(ImageView image) {
	height = image.getFitHeight();
	width = image.getFitWidth();
	x = image.getX();
	y = image.getY();
	visible = image.isVisible();
    }

    @Override
    public ImageView toImageView() {
	ImageView image = new ImageView(new Image(getClass().getClassLoader().getResourceAsStream(myImagePath), 50, 50, true, true));
//	image.setFitHeight(height);
//	image.setFitWidth(width);
	image.setX(x);
	image.setY(y);
	image.setVisible(visible);
	return image;
    }

}
