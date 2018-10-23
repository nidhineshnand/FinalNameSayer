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
	ControllerConnecter _spine;
	protected ArrayList<String> _notFound;
	ArrayList<UserRecordingFile> _recordingList;
	protected ErrorSceneController _errorController;

	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		_listOfNames = new ArrayList<PractiseFile>();
		_spine = new ControllerConnecter();
		_notFound = new ArrayList<String>();
		_recordingList = new ArrayList<UserRecordingFile>();
	}
	
	/**
	 * loads ErrorScene.fxml
	 * @throws Exception 
	 */
	protected void openErrorScene(ArrayList<String> namesNotFound, String message) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ErrorScene.fxml"));
		Parent root = loader.load();
		_errorController = loader.getController();
		_errorController.setup(namesNotFound, message);
		Stage secondaryStage = new Stage();
		secondaryStage.initModality(Modality.WINDOW_MODAL);
		secondaryStage.initOwner(NameSayerStarter.primaryStage);
		secondaryStage.setTitle("Warning");
		secondaryStage.setScene(new Scene(root, 500, 300));
		secondaryStage.setResizable(false);
		secondaryStage.show();
	}
	
	/**
	 * getters
	 */
	public ControllerConnecter controllerConnecter() {
		return _spine;
	}
	
	public ArrayList<PractiseFile> listOfNames() {
		return _listOfNames;
	}
}
