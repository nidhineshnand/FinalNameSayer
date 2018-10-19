package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import sample.ControllerConnecter;

public class ShopSceneController extends Controller {
	
	@FXML
	public Button _defaultThemeButton;
	@FXML
	public Button _darkThemeButton;
	@FXML
	public Label _pointLabel;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);
	}
	
	
	/**
	 * When _dafaultThemeButton is clicked
	 */
	@FXML
	void defaultThemeClicked() {
		
	}
	
	/**
	 * When _darkThemeButton is clicked
	 */
	@FXML
	void darkThemeClicked() {
		
	}
	
	/**
	 * When this window is made this is called to set up the spine and the points
	 * @param spine
	 */
	void setup(ControllerConnecter spine) {
		_spine = spine;
		_pointLabel.setText(_spine.getPoints() + "");
	}
	
	void changeTheme(String themeURL) {
		
	}
}
