package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;

public class PracticeSceneController extends Controller implements Initializable {
	
	// Fields
	@FXML
	public Label _currentName;
	@FXML
	public Button _playAllButton;
	@FXML
	public Slider _volumnSlider;
	@FXML
	public Slider _micSensitivitySlider;
	@FXML
	public ProgressBar _micSensitivityBar;
	@FXML
	public Button _recordButton;
	@FXML
	public TreeView _recordingList;
	@FXML
	public Button _prevButton;
	@FXML
	public Button _playRecordingButton;
	@FXML
	public Button _saveButton;
	@FXML
	public Button _nextButton;
	
	private ArrayList<String> _listOfNames;
	
	// Methods
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
	}
	
	/**
	 * To get names for the list
	 */
	void setNameList(ArrayList<String> list) {
		_listOfNames = list;
		populateTable();
	}
	
	/**
	 * populates the TableView _recordingList
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void populateTable() {
		_recordingList = new TreeView();
		TreeItem<String> item = new TreeItem<>("Names");
		_recordingList.setRoot(item);
		for (String name:_listOfNames) {
			TreeItem<String> nameOfItem = new TreeItem<>(name);
			System.out.println(name);
			item.getChildren().add(nameOfItem);
		}
	}
	
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
	 * When _recordButton is clicked
	 */
	@FXML
	void recordClicked() {
		
	}

	/**
	 * When _prevButton is clicked
	 */
	@FXML
	void prevClicked() {
		
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
	
	/**
	 * When _nextButton is clicked
	 */
	@FXML
	void nextClicked() {
		
	}

	
}
