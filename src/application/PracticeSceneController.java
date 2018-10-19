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

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.applet.AudioClip;

import sample.*;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
import javax.swing.JOptionPane;

public class PracticeSceneController extends Controller {
	
	// Fields
	@FXML
	public Label _currentName;
	@FXML
	public Button _playAllButton;
	@FXML
	public Slider _volumeSlider;
	@FXML
	public ProgressBar _micSensitivityBar;
	@FXML
	public Button _recordButton;
	@FXML
	public ScrollPane _recordingList = new ScrollPane();
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
	@FXML
	public Button _playSelectedButton;
	@FXML
	public Button _deleteSelectedButton;
	public JFXComboBox<UserRecordingFile> _userRecordingDropdown;
	public JFXButton _loop;
	public Spinner<Integer> _loopCount;
	public Label _currentName1;

	private int _counter = 0;
	private double _currentVolume;
	private UserRecordingFile _rFile;
	private RecordVoice _recordingProcess;
	private MediaPlayer _player;

	// Methods
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		super.initialize(location, resources);
		_currentVolume = _volumeSlider.getValue();
		_deleteButton.setVisible(false);
		_saveButton.setVisible(false);
		_playRecordingButton.setVisible(false);

		//Setting mic
		//Starting mic sound check
		_micSensitivityBar.setProgress(0);
		checkMicLevel();


		_volumeSlider.valueProperty().addListener(e -> {
			volumeSliderDragged();
		});

	}

	/**Populates the loop dropdown with the observable list of UserRecordingFiles.*/
	public void populateDropdown(){
		_userRecordingDropdown.getItems().addAll(_spine.getAssociatedUserRecordingFilesForLoop(_listOfNames.get(_counter)));
	}


	/**
	 * To get names for the list
	 */
	void setNameList(ArrayList<PractiseFile> list) {
		_listOfNames = list;
		populateTable();
		populateDropdown();
	}
	
	/**
	 * populates the TableView _recordingList
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void populateTable() {
		updateCurrentName();
		if (_listOfNames.size() <= 1) {
			_nextButton.setVisible(false);
			_prevButton.setVisible(false);
		}
		updateRecordingPane();
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
		if (_player != null){
			_player.setVolume(_currentVolume/100);
		}
	}
	
	/**
	 * When _recordButton is clicked
	 */
	@FXML
	void recordClicked() {
		if (_recordButton.getText().equals("Record")) {
			//Starting recording process in the background
			_rFile = _spine.createNewUserRecordingFile(_listOfNames.get(_counter));
			_recordingProcess = new RecordVoice();
			new Thread(_recordingProcess).start();
			_recordButton.setText("Stop");
		} else {
			_recordingProcess.cancel();
			_recordButton.setText("Record");
			_recordButton.setVisible(false);
			_playRecordingButton.setVisible(true);
			_saveButton.setVisible(true);
			_deleteButton.setVisible(true);
			updateRecordingPane();
		}
	}

	//Class responsible for recording voice in a different thread
	class RecordVoice extends Task<Void> {

		@Override
		protected Void call() throws Exception {
			_rFile.startRecording();
			return null;
		}
	}

	/**
	 * When _prevButton is clicked
	 */
	@FXML
	void prevClicked() {
		_counter--;
		if (_counter < 0) {
			_counter = _listOfNames.size() - 1;
		}
		updateCurrentName();
		updateRecordingPane();
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
		_playRecordingButton.setVisible(false);
		updateRecordingPane();
		_spine.changePoints(3);
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
		_playRecordingButton.setVisible(false);
		updateRecordingPane();
		_spine.changePoints(-1);
	}
	
	/**
	 * When _nextButton is clicked
	 */
	@FXML
	void nextClicked() {
		_counter++;
		_counter = _counter % _listOfNames.size();
		updateCurrentName();
		updateRecordingPane();
	}
	
	/**
	 * When _deleteSelectedButton is clicked
	 */
	@FXML
	void deleteSelectedClicked() {
		_spine.deleteSelectedUserRecordingFiles();
		updateRecordingPane();
	}
	
	/**
	 * When _playSelectedButton is clicked 
	 */
	@FXML
	void playSelectedClicked() {
		ArrayList<UserRecordingFile> toDelete = _spine.getSelectedLocalRecordingFilesFromPractiseScene();
		if (toDelete.size() == 1) {
			_rFile = toDelete.get(0);
			playItem(_rFile);
		} else {
			JOptionPane.showMessageDialog(null, "Please select only one to play");
		}
	}

	/**Loops the selected files the given number of times*/
	@FXML
	void loopFiles(){
		CollectionsFile collectionsFile = new CollectionsFile(_listOfNames.get(_counter), _userRecordingDropdown.getValue());
		for(int i = 0; i < _loopCount.getValue(); i++){
			playItem(collectionsFile);
		}
	}
	
	/**
	 * updating _currentName
	 */
	private void updateCurrentName() {
		_currentName.setText(_listOfNames.get(_counter).get_displayName());
		_currentName.setTextAlignment(TextAlignment.CENTER);
		_currentName1.setText(_listOfNames.get(_counter).get_displayName());
		_currentName1.setTextAlignment(TextAlignment.CENTER);
	}

	private void updateRecordingPane() {
		_recordingList.setContent(_spine.populateUserRecordingsForPractiseScene(_listOfNames.get(_counter)));
	}

	/**
	 * When there is a file that needs playing
	 */
	private void playItem(NameSayerFile nameSayerFile) {
		File file = _spine.getPlayableFileFor(nameSayerFile);
		try {
			String source = file.toURI().toURL().toString();
			Media media = new Media(source);
			_player = new MediaPlayer(media);
			_player.play();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * gets the parent Controller
	 */
	void getSelectionSceneController(SelectionSceneController controller) {
		_spine = controller.getSpine();
	}

	/**
	 * checks mic level
	 */
	public void checkMicLevel() {
		CheckMic mictask = new CheckMic();
		new Thread(mictask).start();
		_micSensitivityBar.progressProperty().unbind();
		_micSensitivityBar.progressProperty().bind(mictask.progressProperty());
	}

	//Class responsible for recording voice in a different thread
	class CheckMic extends Task<Void> {

		@Override
		protected Void call() throws Exception {


			//Setting audio format
			AudioFormat format = new AudioFormat(44100, 16, 2, true, true);
			final int bufferByteSize = 2048;

			//Adding and opening line for audio
			TargetDataLine line;
			try{
				line = AudioSystem.getTargetDataLine(format);
				line.open(format, bufferByteSize);
			} catch (LineUnavailableException e){
				e.getStackTrace();
				return null;
			}

			//Setting buffer
			byte[] buffer = new byte[bufferByteSize];
			float[] samples = new float[bufferByteSize/2];

			float lastPeak = 0f;

			//Getting values form mic input
			line.start();
			for (int s; (s = line.read(buffer, 0, buffer.length)) > -1F;){

				//Converting bytes to samples
				for (int i = 0, j = 0; i < s;){
					int sample = 0;

					//Reversing lines
					sample |= buffer[i++] & 0xFF;
					sample |= buffer[i++] << 8;   //  if the format is big endian)

					//normalize to range of +-1.0f
					samples[j++] = sample/32768f;

				}

				//Obtaining the rms value that will be used to symbolize the peaks in the graphic
				float rms = 0f;
				float peak = 0f;

				for (float sample : samples){

					float abs = Math.abs(sample);
					if (abs > peak){
						peak = abs;
					}

					rms += sample * sample;
				}

				rms = (float)Math.sqrt(rms/ samples.length);

				//Comparing peaks for gradual decent
				if (lastPeak > peak) {
					peak = lastPeak * 0.875f;
				}

				lastPeak = peak;

				updateProgress(rms, 1);
			}

			return null;
		}
	}
}
