package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
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
	public TextField _nameTextField;
	@FXML
	public Pane _recordingListPane;
	@FXML
	public Pane _practiceListPane;
	
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
	}
	
	/**
	 * gets the current name inserted into 
	 */
	private void getName() {
		if (_nameTextField.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "Please insert a Name");
		} else {
			_name = _nameTextField.getText();
			_listOfNames.add(_name);
			openPracticeScene();
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
        
        resetListOfNames();
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
			//_listOfNames.addAll(_practiceListPane.getChildren());
			openPracticeScene();
		}
	}
	
	/**
	 * When _addToListButton is clicked
	 */
	@FXML
	void addToListClicked() {
		staticSearch();
	}
	
	/**
	 * When _practiceButton is clicked
	 */
	@FXML
	void practiceClicked() {
		getName();
	}
	
	/**
	 * When _uploadButton is clicked
	 */
	@FXML
	void uploadClicked() {
		
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
		_practiceListPane.getChildren().setAll(_spine.populateUserRecordingFilesForMainScene());
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
			_spine.addPractiseFileToList(pFile);
			updateDatabaseListPane(pFile);
		}
		System.out.println("done");
	}
	
	/**
	 * updates database list pane
	 * @param pFile
	 */
	private void updateDatabaseListPane(PractiseFile pFile) {
		_practiceListPane.getChildren().add(_spine.populatePractiseFileForMainScene());
	}
}
