package application;

import java.applet.Applet;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
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
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.TextAlignment;

import java.applet.AudioClip;
import sample.ControllerConnecter;

public class PracticeSceneController extends Controller {
	
	// Fields
	@FXML
	public Label _currentName;
	@FXML
	public Button _playAllButton;
	@FXML
	public Slider _volumeSlider;
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
	
	private int _counter = 0;
	private double _currentVolume;
	
	// Methods
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		super.initialize(location, resources);
		_currentVolume = _volumeSlider.getValue();
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
		item.setExpanded(true);
		_recordingList.setRoot(item);
		for (String name:_listOfNames) {
			TreeItem<String> nameOfItem = new TreeItem<>(name);
			item.getChildren().add(nameOfItem);
		}
		updateCurrentName();
		if (_listOfNames.size() <= 1) {
			_nextButton.setVisible(false);
			_prevButton.setVisible(false);
		}
	}
	
	/**
	 * When _playAllButton is clicked
	 */
	@FXML
	void playAllClicked() {
		try {
			playItem(_listOfNames.get(_counter));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * When _volumnSlider is dragged
	 */
	@FXML
	void volumeSliderDragged() {
		_currentVolume = _volumeSlider.getValue();
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
		String name = _currentName.getText();
		UserRecordingFile rFile = _spine.createNewUserRecordingFile(name);
	}

	/**
	 * When _prevButton is clicked
	 */
	@FXML
	void prevClicked() {
		_counter--;
		_counter = _counter % _listOfNames.size();
		updateCurrentName();
	}
	
	/**
	 * When _playRecordingButton is clicked
	 */
	@FXML
	void playRecordingClicked() {
		String name = _currentName.getText();
		File file = new File(name);
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
		_counter++;
		_counter = _counter % _listOfNames.size();
		updateCurrentName();
	}
	
	/**
	 * updating _currentName
	 */
	private void updateCurrentName() {
		_currentName.setText(_listOfNames.get(_counter));
		_currentName.setTextAlignment(TextAlignment.CENTER);
	}

	/**
	 * When there is a file that needs playing
	 */
	private void playItem(String name) throws InterruptedException {
		File audioFile = new File(name);
		URL url;
		try {
			url = audioFile.toURI().toURL();
			AudioClip ac = Applet.newAudioClip(url);
			ac.play();
			Thread.sleep(3000);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
}
