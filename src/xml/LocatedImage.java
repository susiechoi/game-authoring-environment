package xml;

import javafx.scene.image.ImageView;

public class LocatedImage extends ImageView {

    private String url;
    
    public LocatedImage(String filepath) {
	super(filepath);
	url = filepath;
    }
    
    public String getUrl() {
	return url;
    }
    
}
