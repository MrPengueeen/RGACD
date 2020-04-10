package loadingScreen;
	
import animatefx.animation.FadeIn;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;



public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
			primaryStage.setMaximized(true);
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("Sample.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("loadingScreen.css").toExternalForm());
			primaryStage.setScene(scene);
			//new FadeIn(root).setSpeed(0.2).play();
			primaryStage.show();
			
		} 
		
	
	
	public static void main(String[] args) {
		launch(args);
	}
}
