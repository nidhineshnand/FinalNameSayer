package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;

public class ErrorSceneController extends Controller {

	
	@FXML
	public Label _warningMessage;
	@FXML
	public ScrollPane _nameScrollPane;
	@FXML
	public Pane _namePane;
	
	ArrayList<String> _namesNotFound;
	
	/**
	 * method that runs before the scene is opened
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);
		_nameScrollPane.setVisible(false);
	}
	
	/**
	 * method that will run straight after the scene is opened
	 * @param namesNotFound
	 * @param errorMessage
	 */
	void setup(ArrayList<String> namesNotFound, String errorMessage) {
		// when the user inputs a list but some are not found
		if (errorMessage.equals("NamesNotFound")) {
			_warningMessage.setText("The following names were not found in the database.");
			for (int i = 0; i < namesNotFound.size(); i++) {
				_namePane.getChildren().add(new Label(namesNotFound.get(0)));
			}
			_nameScrollPane.setVisible(true);
		} else if (errorMessage.equals("NoNameSelected")) { // when the user doesn't search up a name
			_warningMessage.setText("Please select a name.");
		}
	}
	
}
