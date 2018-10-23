package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
	private ControllerConnecter _spine;
	private Parent _selectionSceneParent;
	private Parent _marketSceneParent;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);
	}

	public void setup(ControllerConnecter spine, Parent practiseSceneParent, Parent marketSceneParent){
		_spine = spine;
		_selectionSceneParent = practiseSceneParent;
        _pointLabel.setText(_spine.getPoints() + "");
        _marketSceneParent = marketSceneParent;
	}
	
	/**
	 * When _dafaultThemeButton is clicked
	 */
	@FXML
	void defaultThemeClicked() {
		changeThemeTo(null);
	}
	
	/**
	 * When _rainbowThemeButton is clicked
	 */
	@FXML
	void sunsetThemeClicked() {
		/**
		if (_spine.getPoints() < 5) {
			try {
				openErrorScene(null, "NotEnoughPoints");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			_parent.setCssName("Sunset");
			try {
				openErrorScene(null, "SaveAndRestart");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}*/
		changeThemeTo("Sunset");
	}
	
	/**
	 * When _darkThemeButton is clicked
	 */
	@FXML
	void darkThemeClicked() {
		/**
		if (_spine.getPoints() < 15) {
			try {
				openErrorScene(null, "NotEnoughPoints");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			_parent.setCssName("Dark");
			try {
				openErrorScene(null, "SaveAndRestart");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}*/
		changeThemeTo("Dark");
	}
	
	/**
	 * When _chocoThemeButton is clicked
	 */
	@FXML
	void chocoThemeClicked() {
		/**
		if (_spine.getPoints() < 10) {
			try {
				openErrorScene(null, "NotEnoughPoints");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			_parent.setCssName("Choco");
			try {
				openErrorScene(null, "SaveAndRestart");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}*/
        changeThemeTo("Choco");

	}

	/**This method changes the theme of the marketplace and selection scene during runtime*/
	private void changeThemeTo(String theme){
		_spine.setCurrentTheme(theme);
		_selectionSceneParent.getStylesheets().clear();
		_selectionSceneParent.getStylesheets().add("themes/"+_spine.getCurrentTheme() +"SelectionSceneStyleSheet.css");
		_marketSceneParent.getStylesheets().clear();
		_marketSceneParent.getStylesheets().add("themes/"+_spine.getCurrentTheme() +"SelectionSceneStyleSheet.css");
	}

}
