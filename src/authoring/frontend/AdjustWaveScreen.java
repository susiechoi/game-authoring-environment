package authoring.frontend;


public class AdjustWaveScreen extends PathScreen {
	private String myWaveNumber;


	protected AdjustWaveScreen(AuthoringView view, String waveNumber) {
		super(view);
		myWaveNumber = waveNumber;
	}
	@Override
	public void initializeGridSettings(CreatePathGrid grid) {
//		setPathPanel(new WavePanel(getView(), grid.getMostRecentlyClicked(), myWaveNumber));
//		grid.setUpForWaves(e -> {setPathPanel(new WavePanel(getView(), grid.getMostRecentlyClicked(), myWaveNumber));});//TODO: action here!!!);

	}
}

