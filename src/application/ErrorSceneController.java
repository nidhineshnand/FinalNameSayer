package application;

import java.awt.ScrollPane;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class ErrorSceneController extends Controller {

	
	@FXML
	public Label _warningMessage;
	@FXML
	public Pane _namePane;
	
	ArrayList<String> _namesNotFound;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);
		_namePane.setVisible(false);
	}
	
	void setup(ArrayList<String> namesNotFound, String errorMessage) {
		if (errorMessage.equals("NamesNotFound")) {
			_warningMessage.setText("The following names were not found in the database.");
			for (int i = 0; i < namesNotFound.size(); i++) {
				_namePane.getChildren().add(new Label(namesNotFound.get(0)));
			}
		}
	}
	
}
