package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.ControllerConnecter;
import sample.PractiseFile;
import sample.UserRecordingFile;

public abstract class Controller implements Initializable {
	
	// Fields
	ArrayList<PractiseFile> _listOfNames;
	protected ArrayList<String> _notFound;
	ArrayList<UserRecordingFile> _recordingList;
	protected ErrorSceneController _errorController;

	/**
	 * Method for when the controller is first opened
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		_listOfNames = new ArrayList<PractiseFile>();
		_notFound = new ArrayList<String>();
		_recordingList = new ArrayList<UserRecordingFile>();
	}
	
	/**
	 * loads ErrorScene.fxml
	 * @throws Exception 
	 */
	protected void openErrorScene(ArrayList<String> namesNotFound, String message) throws Exception {
		// loading the new scene
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ErrorScene.fxml"));
		Parent root = loader.load();
		_errorController = loader.getController();
		
		// setting up the controller
		_errorController.setup(namesNotFound, message);
		Stage secondaryStage = new Stage();
		secondaryStage.initModality(Modality.WINDOW_MODAL);
		secondaryStage.initOwner(Main.primaryStage);
		secondaryStage.setTitle("Warning");
		secondaryStage.setScene(new Scene(root, 500, 300));
		secondaryStage.setResizable(false);
		secondaryStage.show();
	}
	
	/**
	 * getters
	 */
	public ArrayList<PractiseFile> listOfNames() {
		return _listOfNames;
	}
}
