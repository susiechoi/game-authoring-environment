package frontend;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Nice version of stage object which takes in a parent node and not a scene
 * @author andrewarnold
 *
 */
public class StageManager {
    
    private  Stage myStage;
    public static final int DEFAULT_WIDTH = 1280;
    public static final int DEFAULT_HEIGHT = 900;
    private final int stageWidth;
    private final int stageHeight;

    
    public StageManager(Stage stage) {
	this(stage, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }
    
    public StageManager(Stage stage, int width, int height) {
	myStage = stage;
	stageWidth = width;
	stageHeight = height;
	myStage.setWidth(stageWidth);
	myStage.setHeight(stageHeight);
    }
    
    public void switchScene(Scene scene) {
	myStage.setScene(scene);
    }
    public Scene getScene() {
	return myStage.getScene();
    }
    
    
    public void switchScreen(Parent topNode) {
	Scene programScene = new Scene(topNode);
	myStage.setScene(programScene);
	myStage.setWidth(stageWidth);
	myStage.setHeight(stageHeight);
	myStage.show();
    } 

}
