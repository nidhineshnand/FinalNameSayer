package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.ControllerConnecter;

public class Main extends Application {

	// Fields
	public static Stage primaryStage;
	
	// Method to open the initial selection scene
    @Override
    public void start(Stage primaryStage) throws Exception{
    	this.primaryStage = primaryStage;
    	// initialising the controller connector and setting up selection scene
    	ControllerConnecter _spine = new ControllerConnecter();
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SelectionScene.fxml"));
        Parent root = fxmlLoader.load();
        SelectionSceneController selectionSceneController = fxmlLoader.getController();
        selectionSceneController.setup(root, _spine);
        primaryStage.setTitle("NameSayer");
        primaryStage.setScene(new Scene(root, 1000, 800));
        primaryStage.setResizable(true);
        primaryStage.setMinWidth(970);
        primaryStage.setMinHeight(630);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
