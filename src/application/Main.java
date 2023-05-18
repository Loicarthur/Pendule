
package application;
	
import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("Interface.fxml"));
			Scene scene = new Scene(root);
			scene.setFill(Color.LIGHTGRAY);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			//primaryStage.setFullScreen(true);
			primaryStage.centerOnScreen();
			primaryStage.setResizable(false);
			primaryStage.setTitle("Jeu du pendu");
			//Path P = Paths.get("application", "images", "3ilogo.png"); //import java.nio.file.Path
			primaryStage.getIcons().setAll(new Image(getClass().getResource("3ilogo.png").toExternalForm()));

			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
