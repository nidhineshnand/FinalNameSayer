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
	public Button _sunsetThemeButton;
	@FXML
	public Button _chocoThemeButton;
	@FXML
	public Label _pointLabel;
	
	private SelectionSceneController _parent;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);
	}
	
	
	/**
	 * When _dafaultThemeButton is clicked
	 */
	@FXML
	void defaultThemeClicked() {
		_parent.setCssName("");
	}
	
	/**
	 * When _rainbowThemeButton is clicked
	 */
	@FXML
	void sunsetThemeClicked() {
		if (_spine.getPoints() < 5) {
			try {
				openErrorScene(null, "NotEnoughPoints");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			_parent.setCssName("Sunset");
		}
	}
	
	/**
	 * When _darkThemeButton is clicked
	 */
	@FXML
	void darkThemeClicked() {
		if (_spine.getPoints() < 15) {
			try {
				openErrorScene(null, "NotEnoughPoints");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			_parent.setCssName("Dark");
		}
	}
	
	/**
	 * When _chocoThemeButton is clicked
	 */
	@FXML
	void chocoThemeClicked() {
		if (_spine.getPoints() < 10) {
			try {
				openErrorScene(null, "NotEnoughPoints");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			_parent.setCssName("Choco");
		}
	}
	
	/**
	 * When this window is made this is called to set up the spine and the points
	 * @param spine
	 */
	void setup(SelectionSceneController parent) {
		_parent = parent;
		_spine = _parent._spine;
		_pointLabel.setText(_spine.getPoints() + "");
	}
}
