package application;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import sample.ControllerConnecter;

public class ShopSceneController extends Controller {
	
	// FXML fields
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

	// Fields
	private ControllerConnecter _spine;
	private Parent _selectionSceneParent;
	private Parent _marketSceneParent;
	private SelectionSceneController _selectionController;
	
	// Method that is ran on startup
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);
	}

	// called when the controller is to be setup immediately after startup, instantiates the necessary private fields
	public void setup(ControllerConnecter spine, Parent practiseSceneParent, Parent marketSceneParent, SelectionSceneController selectionController){
		_spine = spine;
		_selectionSceneParent = practiseSceneParent;
        _pointLabel.setText(_spine.getPoints() + "");
        _marketSceneParent = marketSceneParent;
        _selectionController = selectionController;
	}
	
	/**
	 * When _dafaultThemeButton is clicked
	 */
	@FXML
	void defaultThemeClicked() {
		changeThemeTo(null);
		_selectionController._shopButton.setVisible(true);
		_selectionController._invertedShopButton.setVisible(false);
	}
	
	/**
	 * When _rainbowThemeButton is clicked
	 */
	@FXML
	void sunsetThemeClicked() {
		if (_spine.getPoints() < 5) { // opens error scene for lack of points
			try {
				openErrorScene(null, "NotEnoughPoints");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else { // changes theme to sunset
			changeThemeTo("Sunset");
			_selectionController._shopButton.setVisible(false);
			_selectionController._invertedShopButton.setVisible(true);
		}
	}
	
	/**
	 * When _darkThemeButton is clicked
	 */
	@FXML
	void darkThemeClicked() {
		if (_spine.getPoints() < 15) { // opens error scene for lack of points
			try {
				openErrorScene(null, "NotEnoughPoints");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else { // changes theme to dark
			changeThemeTo("Dark");
			_selectionController._shopButton.setVisible(false);
			_selectionController._invertedShopButton.setVisible(true);
		}
	}
	
	/**
	 * When _chocoThemeButton is clicked
	 */
	@FXML
	void chocoThemeClicked() {
		if (_spine.getPoints() < 10) { // opens error scene for lack of points
			try {
				openErrorScene(null, "NotEnoughPoints");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else { // changes theme to choco
			changeThemeTo("Choco");
	        _selectionController._shopButton.setVisible(false);
			_selectionController._invertedShopButton.setVisible(true);
		}
	}

	/**This method changes the theme of the marketplace and selection scene during runtime*/
	private void changeThemeTo(String theme){
		_spine.setCurrentTheme(theme);
		_selectionSceneParent.getStylesheets().clear();
		_selectionSceneParent.getStylesheets().add("themes/"+_spine.getCurrentTheme() +"SelectionSceneStyleSheet.css");
		_marketSceneParent.getStylesheets().clear();
		_marketSceneParent.getStylesheets().add("themes/"+_spine.getCurrentTheme() +"ShopSceneStyleSheet.css");
	}

	/**This method resets the score to 0*/
	public void resetButtonClicked() {
		//Launching warning scene
		//	openErrorScene(null, "Are you sure ");
		_spine.clearScore();
		_pointLabel.setText(_spine.getPoints() + "");
	}
}
