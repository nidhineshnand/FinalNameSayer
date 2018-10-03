package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import sample.ControllerConnecter;

public abstract class Controller implements Initializable {
	
	// Fields
	ArrayList<String> _listOfNames;
	ControllerConnecter _spine;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		_listOfNames = new ArrayList<String>();
		_spine = new ControllerConnecter();
	}
	
	/**
	 * getters
	 */
	public ControllerConnecter controllerConnecter() {
		return _spine;
	}
	
	public ArrayList<String> listOfNames() {
		return _listOfNames;
	}
}
