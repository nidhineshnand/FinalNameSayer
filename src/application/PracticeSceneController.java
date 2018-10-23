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
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.applet.AudioClip;

import javafx.util.Duration;
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
	public JFXButton _loop;
	public Spinner<Integer> _loopCount;
    public Label _pointsLabel;
	public JFXSpinner _jfxSpinner;
	private VBox _userRecordingList = new VBox();

	private int _counter = 0;
	private double _currentVolume;
	private UserRecordingFile _rFile;
	private RecordVoice _recordingProcess;
	private MediaPlayer _player;
	private ControllerConnecter _spine;
	private File _fileToPlay;
	private NameSayerFile _nameSayerFile;

	// Methods

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		super.initialize(location, resources);
		_currentVolume = _volumeSlider.getValue();
		_deleteButton.setVisible(false);
		_saveButton.setVisible(false);
		_playRecordingButton.setVisible(false);

		//Starting mic sound check
		_micSensitivityBar.setProgress(0);
		checkMicLevel();
		
		//Setting mnemonics for major keys
		_playAllButton.setMnemonicParsing(true);
		_playAllButton.setText("Play");
		_recordButton.setMnemonicParsing(true);
		_recordButton.setText("Record");
		_loop.setMnemonicParsing(true);
		_loop.setText("Loop");
	}


	/**
	 * To get names for the list
	 */
	void setNameList(ArrayList<PractiseFile> list, ControllerConnecter spine) {
		_listOfNames = list;
		_spine = spine;
		_userRecordingList = _spine.populateUserRecordingsForPractiseScene(_listOfNames.get(_counter));
		populateTable();
		updateRecordingPane();
		setSpinner();
		_volumeSlider.valueProperty().addListener(e -> {
			volumeSliderDragged();
		});

		_pointsLabel.setText(_spine.getPoints() + "");
	}

	//Sets the input value that the spinner will take
	private void setSpinner() {
		_loopCount.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10));
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
		playItem(_listOfNames.get(_counter), 1);
	}

	/**
	 * When _volumnSlider is dragged
	 */
	@FXML
	void volumeSliderDragged() {
		_currentVolume = _volumeSlider.getValue();
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
			_jfxSpinner.setVisible(true);
		} else {
			_recordingProcess.terminate();
			_recordButton.setText("Record");
			_jfxSpinner.setVisible(false);
			_recordButton.setVisible(false);
			_playRecordingButton.setVisible(true);
			_saveButton.setVisible(true);
			_deleteButton.setVisible(true);
			updateRecordingPane();
		}
	}


	//Class responsible for recording voice in a different thread
	class RecordVoice extends Task<Void> {
		private volatile boolean running = true;

		public void terminate() {
			_rFile.stopRecording();
		}

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
		playItem(_rFile, 1);
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
        _pointsLabel.setText(_spine.getPoints() + "");
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
        _pointsLabel.setText(_spine.getPoints() + "");
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
	 * When _playSelectedButton is clicked then the selected recordings are played
	 */
	@FXML
	void playSelectedClicked() {
		updateRecordingPane();
		ArrayList<UserRecordingFile> selectedRecordings = _spine.getSelectedLocalRecordingFilesFromPractiseScene();
		if(!selectedRecordings.isEmpty()) {
			CollectionsFile collectionsFile = new CollectionsFile(null, selectedRecordings);
			playItem(collectionsFile, 1);
		}
	}

	/**Loops the selected files the given number of times*/
	@FXML
	void loopFiles(){
	    //File is only looped if a recording file is selected and the loop button is not at a stop
		if(_spine.getSelectedLocalRecordingFilesFromPractiseScene() != null) {
			ArrayList<PractiseFile> practiseFiles = new ArrayList<>();
			practiseFiles.add(_listOfNames.get(_counter));
			CollectionsFile collectionsFile = new CollectionsFile(practiseFiles, _spine.getSelectedLocalRecordingFilesFromPractiseScene());
			playItem(collectionsFile, _loopCount.getValue());
        }


	}

	/**
	 * updating _currentName
	 */
	private void updateCurrentName() {
		_currentName.setText(_listOfNames.get(_counter).get_displayName());
		_currentName.setTextAlignment(TextAlignment.CENTER);
	}

	private void updateRecordingPane() {
		_userRecordingList = _spine.populateUserRecordingsForPractiseScene(_listOfNames.get(_counter));
		_recordingList.setContent(_userRecordingList);
		_userRecordingList.prefHeight(200);
	}

	/**
	 * When there is a file that needs playing
	 */
	private void playItem(NameSayerFile nameSayerFile, int loop) {
		_nameSayerFile = nameSayerFile;
		GeneratePlayableFile generatePlayableFile = new GeneratePlayableFile();
		new Thread(generatePlayableFile).start();
		generatePlayableFile.setOnSucceeded(event ->{
	try {
			if (_player != null){
				_player.stop();
				_player.dispose();
			}
			String source = _fileToPlay.toURI().toURL().toString();
			Media media = new Media(source);
			_player = new MediaPlayer(media);
			_player.setCycleCount(loop);
			_player.setVolume(_currentVolume/100);
			_player.seek(Duration.millis(0));
			_player.play();
		} catch (MalformedURLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		});

	}
	//Class responsible for recording voice in a different thread
	class GeneratePlayableFile extends Task<Void> {

		@Override
		protected Void call() throws Exception {
			_fileToPlay = _spine.getPlayableFileFor(_nameSayerFile);
			return null;

		}
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
