package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.Initializable;
import sample.ControllerConnecter;
import sample.PractiseFile;
import sample.UserRecordingFile;

public abstract class Controller extends Application implements Initializable {
	
	// Fields
	ArrayList<PractiseFile> _listOfNames;
	ControllerConnecter _spine;
	protected ArrayList<String> _notFound;
	ArrayList<UserRecordingFile> _recordingList;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		_listOfNames = new ArrayList<PractiseFile>();
		_spine = new ControllerConnecter();
		_notFound = new ArrayList<String>();
		_recordingList = new ArrayList<UserRecordingFile>();
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
