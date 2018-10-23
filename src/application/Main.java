package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.ControllerConnecter;

public class Main extends Application {

	public static Stage primaryStage;
	
    @Override
    public void start(Stage primaryStage) throws Exception{
    	this.primaryStage = primaryStage;
    	ControllerConnecter _spine = new ControllerConnecter();
        Parent root = FXMLLoader.load(getClass().getResource("SelectionScene.fxml"));
        primaryStage.setTitle("NameSayer");
        primaryStage.setScene(new Scene(root, 1000, 800));
        primaryStage.setResizable(true);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
