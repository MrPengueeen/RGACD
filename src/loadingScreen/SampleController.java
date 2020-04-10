package loadingScreen;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import animatefx.animation.AnimationFX;
import animatefx.animation.Bounce;
import animatefx.animation.FadeIn;
import animatefx.animation.Tada;
import animatefx.animation.Wobble;
import animatefx.animation.ZoomIn;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class SampleController implements Initializable {
	
	@FXML
	private Circle loadCircle1;
	@FXML
	private Circle loadCircle2;
	@FXML
	private Circle loadCircle3;
	@FXML
	private Circle loadCircle4;
	@FXML
	private VBox vbox;
	@FXML
	private ImageView image;
	@FXML
	AnchorPane parentPane;
	
	 @Override
	public void initialize(URL location, ResourceBundle resources) {
		 
		 ZoomIn zoom = new ZoomIn(image);
		 zoom.setSpeed(0.5);
		 zoom.setOnFinished(e -> new Tada(image).setSpeed(0.6).play());
		 zoom.play();
		 new Bounce(loadCircle1).setCycleCount(AnimationFX.INDEFINITE).setDelay(Duration.valueOf("100ms")).play();
		 new Bounce(loadCircle2).setCycleCount(AnimationFX.INDEFINITE).setDelay(Duration.valueOf("150ms")).play();
		 new Bounce(loadCircle3).setCycleCount(AnimationFX.INDEFINITE).setDelay(Duration.valueOf("200ms")).play();
		 //new Bounce(loadCircle4).setCycleCount(AnimationFX.INDEFINITE).setDelay(Duration.valueOf("250ms")).play();
		 Bounce bounce = new Bounce(loadCircle4);
		 
		 bounce.setCycleCount(4).setDelay(Duration.valueOf("250ms"));
		 bounce.setOnFinished(e -> {
			 try {
				AnchorPane landingPage = FXMLLoader.load(getClass().getResource("/landingPage/LandingPage.fxml"));
				//Scene scene = new Scene(root, 800, 800);
				
				
			
				parentPane.getChildren().setAll(landingPage);
				new FadeIn(landingPage).setSpeed(0.3).play();
				parentPane.setTopAnchor(landingPage, 0.0);
				parentPane.setRightAnchor(landingPage, 0.0);
				parentPane.setBottomAnchor(landingPage, 0.0);
				parentPane.setLeftAnchor(landingPage, 0.0);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		 });
		 bounce.play();
		
	}
}
