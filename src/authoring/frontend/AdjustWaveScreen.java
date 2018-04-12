package authoring.frontend;


public class AdjustWaveScreen extends PathScreen {
	private String myWaveNumber;


	protected AdjustWaveScreen(AuthoringView view, String waveNumber) {
		super(view);
		myWaveNumber = waveNumber;
	}
	@Override
	public void initializeGridSettings(CreatePathGrid grid) {
		setPathPanel(new WavePanel(getView(), grid.getMostRecentlyClicked(), myWaveNumber), new WaveToolBar(getView()));
		grid.setUpForWaves(e -> {setPathPanel(new WavePanel(getView(), grid.getMostRecentlyClicked(), myWaveNumber), new WaveToolBar(getView()));});//TODO: action here!!!);
	}
	@Override
	public void setSpecificUIComponents() {
	    // TODO Auto-generated method stub
	    
	}
}

