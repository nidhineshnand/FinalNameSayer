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
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SelectionSceneController extends Controller implements Initializable{
	
	// Fields
	@FXML
	public Button _selectedButton;
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
	
	private ArrayList<String> _listOfNames;
	private PracticeSceneController _practiceController;
	private String _name;
	
	// Methods
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		_listOfNames = new ArrayList<String>();
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
	}
	
	// Action listeners 
	/**
	 * When _practiceAllButton is clicked
	 */
	@FXML
	void practiceListClicked() {
		//openPracticeScene();
	}
	
	/**
	 * When _practiceSelectedButton is clicked
	 */
	@FXML
	void practiceSelectedClicked() {
		//openPracticeScene();
	}
	
	/**
	 * When _addToListButton is clicked
	 */
	@FXML
	void addToListClicked() {
		
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
	 * Getters
	 */
	ArrayList<String> getListOfNames() {
		System.out.println(_listOfNames);
		return _listOfNames;
	}
}
