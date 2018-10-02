package application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TableView;

public class PracticeSceneController extends Controller {
	
	// Fields
	@FXML
	public Button _playAllButton;
	@FXML
	public Slider _volumnSlider;
	@FXML
	public Slider _micSensitivitySlider;
	@FXML
	public Button _backButton;
	@FXML
	public Button _recordButton;
	@FXML
	public TableView _recordingList;
	@FXML
	public Button _playRecordingButton;
	@FXML
	public Button _saveButton;
	
	// Methods
	
	/**
	 * When _playAllButton is clicked
	 */
	@FXML
	void playAllClicked() {
		
	}
	
	/**
	 * When _volumnSlider is dragged
	 */
	@FXML
	void volumnSliderDragged() {
		
	}
	
	/**
	 * When _micSensitivitySlider is dragged
	 */
	@FXML
	void micSensitivityDragged() {
		
	}
	
	/**
	 * When _backButton is clicked
	 */
	@FXML
	void backClicked() {
		//loadScene(_backButton, "SelectionScene");
	}
	
	/**
	 * When _recordButton is clicked
	 */
	@FXML
	void recordClicked() {
		
	}
	
	/**
	 * When _playRecordingButton is clicked
	 */
	@FXML
	void playRecordingClicked() {
		
	}
	
	/**
	 * When _saveButton is clicked
	 */
	@FXML
	void saveClicked() {
		
	}
}
