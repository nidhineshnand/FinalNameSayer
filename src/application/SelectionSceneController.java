package application;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.CustomTextField;
import org.controlsfx.control.textfield.TextFields;
import sample.ControllerConnecter;
import sample.PractiseFile;

public class SelectionSceneController extends Controller {

	// Fields
	@FXML
	public TreeView _practiceList;
	@FXML
	public Button _practiceListButton;
	@FXML
	public Button _addToListButton;
	@FXML
	public Button _practiceButton;
	@FXML
	public Button _uploadButton;
	@FXML
	public Pane _recordingListPane;
	@FXML
	public Pane _practiceListPane;
	public CustomTextField _nameTextField;


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
		populateDatabasePane();
		autoCompleteListBinding();
		_recordingListPane.getChildren().addAll(_spine.populateUserRecordingFilesForMainScene());
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
		Stage secondaryStage = new Stage();
		secondaryStage.initModality(Modality.WINDOW_MODAL);
		secondaryStage.initOwner(Main.primaryStage);
        secondaryStage.setTitle("NameSayer");
        secondaryStage.setScene(new Scene(root, 900, 600));
        secondaryStage.setResizable(false);
        secondaryStage.show();
        
        //resetListOfNames();
	}

	public void autoCompleteListBinding(){
		//Binding search results to textfieled
		TextFields.bindAutoCompletion(_nameTextField, t -> _spine.continousSearchResults(_nameTextField.getText()));
	}
	
	// Action listeners 
	/**
	 * When _practiceAllButton is clicked
	 */
	@FXML
	void practiceListClicked() {
		if (_practiceListPane.getChildren().isEmpty()) {
			JOptionPane.showMessageDialog(null, "There are no names in the practice list");
		} else {
			resetListOfNames();
			for (PractiseFile pFile: _spine.getSelectedPractiseFiles()) {
				_listOfNames.add(pFile);
			}
			System.out.println(_listOfNames.size());
			openPracticeScene();
		}
	}
	
	/**
	 * When _addToListButton is clicked
	 */
	@FXML
	void addToListClicked() {
		staticSearch();
		_practiceListPane.getChildren().addAll(_spine.populatePractiseFileForMainScene());
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
	 * When _uploadButton is clicked
	 */
	@FXML
	void uploadClicked() {
		//Launching file chooser
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Practise List");
		File uploadList = fileChooser.showOpenDialog(_practiceListPane.getScene().getWindow());

		//Checking if file is not null
		if(uploadList != null){
			_spine.loadPractiseFilesFromTextFile(uploadList.getPath());
		}

		//Refreshing view
		_practiceListPane.getChildren().addAll(_spine.populatePractiseFileForMainScene());
	}
	
	/**
	 * When the user is typing on _nameTextField
	 */
	@FXML
	void searching() {
		staticSearch();
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
	private void populateDatabasePane() {
		_recordingListPane.getChildren().setAll(_spine.populateUserRecordingFilesForMainScene());
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
			_practiceListPane.getChildren().add(_spine.populatePractiseFileForMainScene());
		}
	}
}
