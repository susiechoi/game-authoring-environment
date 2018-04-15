
package gameplayer;

import java.util.Scanner;

import engine.GameEngine;
import engine.Mediator;
import engine.sprites.towers.Tower;
import frontend.StageManager;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

public class EngineFrontEndLauncherForTesting extends Application {

    private static final String STARTING_LANGUAGE = "English";

    /**
     * Initialize the program and begin the animation loop 
     * 
     * @param stage: Primary stage to attach all scenes
     */
    @Override
    public void start(Stage primaryStage){

	StageManager stageManager = new StageManager(primaryStage);
//	Mediator mediator = new Mediator();

//	ScreenManager manager = new ScreenManager(stageManager, STARTING_LANGUAGE);

	//manager.loadInstructionScreen();
	ChangeListener<Number> listener = (observable, oldVal, newVal)-> {
//	    manager.loadInstructionScreen() ;
	};

	ListChangeListener<Tower> listListen = (e) -> {
//	    manager.loadInstructionScreen();
	};

//	mediator.addTowerListener(listListen);
//	ObservableList<Tower> placed = mediator.getPlaceTowers();
//	Tower example = new Tower(null, null,null,null);
//	placed.add(example);

	//mediator.addLevelListener(listener);

	//ReadOnlyObjectWrapper<Integer> level = mediator.getLevel();
	//level.set(1);

	//Integer test = mediator.getTest();
	//mediator.test();
	//test = test+1;
	//mediator.test();
	//test = test+1;
	//manager.triggerTestPlus();
    }

    /**
     * Start the program
     */
    public static void main (String[] args) {
	launch(args);
    }
}
