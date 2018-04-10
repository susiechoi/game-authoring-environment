package authoring.frontend;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;


public class AdjustWaveScreen extends PathScreen {
	private String myWaveNumber;

	protected AdjustWaveScreen(AuthoringView view, String waveNumber) {
		super(view);
		myWaveNumber = waveNumber;
	}
	@Override
	public void initializeGridSettings(CreatePathGrid grid) {
		grid.setUpForWaves(e -> {setPathPanel(new WavePanel(getView(), grid.getMostRecentlyClicked(), myWaveNumber));});//TODO: action here!!!);
	}
}
