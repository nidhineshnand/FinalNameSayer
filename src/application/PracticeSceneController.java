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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.TextAlignment;

import java.applet.AudioClip;
import sample.ControllerConnecter;
import sample.NameSayerFile;
import sample.PractiseFile;
import sample.UserRecordingFile;

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
	@FXML
	public Button _deleteButton;
	
	private int _counter = 0;
	private double _currentVolume;
	private UserRecordingFile _rFile;
	
	// Methods
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		super.initialize(location, resources);
		_currentVolume = _volumeSlider.getValue();
		_deleteButton.setVisible(false);
		_saveButton.setVisible(false);
		_playRecordingButton.setVisible(false);
	}
	
	/**
	 * To get names for the list
	 */
	void setNameList(ArrayList<PractiseFile> list) {
		_listOfNames = list;
		populateTable();
	}
	
	/**
	 * populates the TableView _recordingList
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void populateTable() {
		_recordingList = new TreeView();
		TreeItem<PractiseFile> item = new TreeItem<PractiseFile>();
		item.setExpanded(true);
		_recordingList.setRoot(item);
		for (PractiseFile name:_listOfNames) {
			TreeItem<PractiseFile> nameOfItem = new TreeItem<>(name);
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
		playItem(_listOfNames.get(_counter));
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
		if (_recordButton.getText().equals("Record")) {
			_rFile = _spine.createNewUserRecordingFile(_listOfNames.get(_counter));
			_rFile.startRecording();
			_recordButton.setText("Stop");
		} else {
			_rFile.stopRecording();
			_recordButton.setText("Record");
			_recordButton.setVisible(false);
			_playRecordingButton.setVisible(true);
			_saveButton.setVisible(true);
			_deleteButton.setVisible(true);
		}
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
		playItem(_rFile);
	}
	
	/**
	 * When _saveButton is clicked
	 */
	@FXML
	void saveClicked() {
		_spine.addUserRecording(_rFile);
		_recordButton.setVisible(true);
		_deleteButton.setVisible(false);
		_saveButton.setVisible(false);
		
	}
	
	/**
	 * When _deleteButton is clicked
	 */
	@FXML
	void deleteClicked() {
		_rFile.deleteFile();
		_recordButton.setVisible(true);
		_deleteButton.setVisible(false);
		_saveButton.setVisible(false);
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
		_currentName.setText(_listOfNames.get(_counter).get_displayName());
		_currentName.setTextAlignment(TextAlignment.CENTER);
	}

	/**
	 * When there is a file that needs playing
	 */
	private void playItem(NameSayerFile nameSayerFile) {
		File file = _spine.getPlayableFileFor(nameSayerFile);
		try {
			String source = file.toURI().toURL().toString();
			Media media = new Media(source);
			MediaPlayer player = new MediaPlayer(media);
			player.setVolume(_currentVolume/100);
			player.play();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
