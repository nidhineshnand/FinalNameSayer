package application;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTabPane;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import org.controlsfx.control.textfield.CustomTextField;
import org.controlsfx.control.textfield.TextFields;
import sample.ControllerConnecter;
import sample.PractiseFile;

public class SelectionSceneController extends Controller {

	// Fields
	@FXML
	public Button _practiceListButton;
	@FXML
	public Button _addToListButton;
	@FXML
	public Button _practiceButton;
	@FXML
	public Button _uploadButton;
	@FXML
	public ScrollPane _recordingListPane = new ScrollPane();
	@FXML
	public ScrollPane _practiceListPane = new ScrollPane();
	public CustomTextField _nameTextField;
	public JFXCheckBox _practiseFileSelectAllPractiseFileCheckBox;
	public Label _pointsLabel;
    public JFXCheckBox _userRecordingCheckBox;
	public JFXButton _deleteUserRecording;
	public Pane _mainContainer;

	private VBox _practiseList = new VBox();
	private VBox _userRecordingList;
	private PracticeSceneController _practiceController;
	private String _name;
	private VBox _practiceFileList;
	private PractiseFile _pFile;
	
	// Methods
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		//_practiceFileList = super.controllerConnecter().populatePractiseFileForMainScene();
		super.initialize(location, resources);
		autoCompleteListBinding();
		//Populates the User Recording files tab
		populatePanes();
		_pointsLabel.setText(_spine.getPoints() + "");
		_mainContainer.heightProperty().addListener((observable, oldValue, newValue) -> {

			_practiceFileList.setPrefHeight(newValue.doubleValue() - 110);
			_userRecordingList.setPrefHeight(newValue.doubleValue() - 110);
			System.out.println(newValue);
		});
	}

	/**
	 * gets the current name inserted into 
	 */
	private void getName() {
		if (_nameTextField.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "Please insert a Name");
		} else {
			String name = _nameTextField.getText();
			_pFile = _spine.searchButtonPressed(name, _notFound);
			if (_pFile != null) {
				_listOfNames.add(_pFile);
				openPracticeScene();
			} else {
				JOptionPane.showMessageDialog(null, "This name is not in the database");
			}
			
		}
	}
	
	/**
	 * opens PracticeScene
	 */
	private void openPracticeScene() {
		try {
			start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * loads PracticeScene
	 * @throws Exception
	 */
	public void start() throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("PracticeScene.fxml"));
		Parent root = loader.load();
		_practiceController = loader.getController();
		_practiceController.setNameList(_listOfNames);
		_practiceController.getSelectionSceneController(this);
		Stage secondaryStage = new Stage();
		secondaryStage.initModality(Modality.WINDOW_MODAL);
		secondaryStage.initOwner(NameSayerStarter.primaryStage);
        secondaryStage.setTitle("NameSayer");
        secondaryStage.setScene(new Scene(root, 900, 600));
        secondaryStage.setResizable(false);
        secondaryStage.show();
        secondaryStage.setOnHiding(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent arg0) {
				populatePanes();
				_pointsLabel.setText(_spine.getPoints() + "");
				System.out.println(_spine.getPoints());
				System.out.println("stopping");
				//Resizing scene
				_practiceFileList.setPrefHeight(_mainContainer.getHeight() - 110);
				_userRecordingList.setPrefHeight(_mainContainer.getHeight() - 110);
			}
        });
	}


	//Method that is responsible for selecting all user recordings

	public void selectAllUserRecordings(){
		//If check box is selected then the label is changed to deselect all and all files are selected
		if(_userRecordingCheckBox.isSelected()){
			_userRecordingCheckBox.setText("Deselect All");
			_spine.setUserRecordingFileListCheckBox(true);

			//If checkbox is not selected then the label is changed to select all and all the files are deselected
		} else {
			_userRecordingCheckBox.setText("Select All");
			_spine.setUserRecordingFileListCheckBox(false);
		}

		populatePanes();
	}
	private void autoCompleteListBinding(){
		//Binding search results to textfieled
		TextFields.bindAutoCompletion(_nameTextField, t -> _spine.continousSearchResults(_nameTextField.getText()));
	}


	//Sets the select all functionality to the practise file list
	public void selectAllPractiseFileCheckBox(){
		//If check box is selected then the label is changed to deselect all and all files are selected
		if(_practiseFileSelectAllPractiseFileCheckBox.isSelected()){
			_practiseFileSelectAllPractiseFileCheckBox.setText("Deselect All");
			_spine.setPractiseFileListCheckBox(true);

			//If checkbox is not selected then the label is changed to select all and all the files are deselected
		} else {
			_practiseFileSelectAllPractiseFileCheckBox.setText("Select All");
			_spine.setPractiseFileListCheckBox(false);
		}
		populatePanes();

	}

	// Action listeners 
	/**
	 * When _practiceAllButton is clicked
	 */
	@FXML
	void practiceListClicked() {
		resetListOfNames();
		for (PractiseFile pFile: _spine.getSelectedPractiseFiles()) {
			_listOfNames.add(pFile);
		}
		if (_listOfNames.isEmpty()) {
			JOptionPane.showMessageDialog(null, "There are no names in the practice list");
		} else {
			System.out.println(_listOfNames.size());
			openPracticeScene();
		}
	}

	//This method delete selected user recordings
	public void deleteSelectedUserRecordings(){
		_spine.deleteSelectedUserRecordingFiles();
		populatePanes();
	}
	
	/**
	 * When _addToListButton is clicked
	 */
	@FXML
	void addToListClicked() {
		staticSearch();
		populatePanes();
	}
	
	/**
	 * When _practiceButton is clicked
	 */
	@FXML
	void practiceClicked() {
		resetListOfNames();
		getName();
	}
	
	/**
	 * When _shopButton is clicked
	 */
	@FXML
	void shopClicked() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("ShopScene.fxml"));
			Parent root = loader.load();
			Stage secondaryStage = new Stage();
			secondaryStage.initModality(Modality.WINDOW_MODAL);
			secondaryStage.initOwner(NameSayerStarter.primaryStage);
	        secondaryStage.setTitle("Shop");
	        secondaryStage.setScene(new Scene(root, 900, 600));
	        secondaryStage.setResizable(false);
	        secondaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * When _uploadButton is clicked
	 */
	@FXML
	void uploadClicked() {
		//Launching file chooser
		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
		fileChooser.getExtensionFilters().add(extFilter);
		fileChooser.setTitle("Open Practise List");
		File uploadList = fileChooser.showOpenDialog(_practiceListPane.getScene().getWindow());

		//Checking if file is not null
		if(uploadList != null){
			_spine.loadPractiseFilesFromTextFile(uploadList.getPath());
		}

		//Refreshing view
		populatePanes();
		_practiceFileList.setPrefHeight(_mainContainer.getHeight() - 110);
		_userRecordingList.setPrefHeight(_mainContainer.getHeight() - 110);
	}
	
	/**
	 * When the user is typing on _nameTextField
	 */
	@FXML
	void searching() {
		staticSearch();
	}
	
	@FXML
	void removeClicked() {
		_spine.deleteSelectedPractiseFiles();
		populatePanes();
	}
	
	/**
	 * resets the list of names in _listOfNames
	 */
	private void resetListOfNames() {
		_listOfNames.clear();
	}

	/**
	 * populates database pane for the startup
	 */
	protected void populatePanes() {
		_userRecordingList = _spine.populateUserRecordingFilesForMainScene();
		_recordingListPane.setContent(_userRecordingList);
		_practiceFileList = _spine.populatePractiseFileForMainScene();
		_practiceListPane.setContent(_practiceFileList);
	}
	
	/**
	 * when the user enters a name into _nameTextField statically
	 */
	private void staticSearch() {
		ArrayList<String> notFound = new ArrayList<String>();
		String str = _nameTextField.getText();
		PractiseFile pFile = _spine.searchButtonPressed(str, notFound);
		if (pFile == null) {
			JOptionPane.showMessageDialog(null, "This name is not in the data base");
		} else {
			//_spine.addPractiseFileToList(pFile);
			System.out.println(pFile.get_displayName());
			populatePanes();
		}
	}
	
	/**
	 * getter for the controller connecter
	 */
	ControllerConnecter getSpine() {
		return _spine;
	}
}
