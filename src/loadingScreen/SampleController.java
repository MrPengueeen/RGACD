package loadingScreen;

import java.net.URL;
import java.util.ResourceBundle;

import animatefx.animation.AnimationFX;
import animatefx.animation.Bounce;
import animatefx.animation.FadeIn;
import animatefx.animation.Tada;
import animatefx.animation.Wobble;
import animatefx.animation.ZoomIn;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class SampleController implements Initializable {
	
	@FXML
	Circle loadCircle1;
	@FXML
	Circle loadCircle2;
	@FXML
	Circle loadCircle3;
	@FXML
	Circle loadCircle4;
	@FXML
	VBox vbox;
	@FXML
	ImageView image;
	
	 @Override
	public void initialize(URL location, ResourceBundle resources) {
		 
		 ZoomIn zoom = new ZoomIn(image);
		 zoom.setSpeed(0.5);
		 zoom.setOnFinished(e -> new Tada(image).setSpeed(0.6).play());
		 zoom.play();
		 new Bounce(loadCircle1).setCycleCount(AnimationFX.INDEFINITE).setDelay(Duration.valueOf("100ms")).play();
		 new Bounce(loadCircle2).setCycleCount(AnimationFX.INDEFINITE).setDelay(Duration.valueOf("150ms")).play();
		 new Bounce(loadCircle3).setCycleCount(AnimationFX.INDEFINITE).setDelay(Duration.valueOf("200ms")).play();
		 new Bounce(loadCircle4).setCycleCount(AnimationFX.INDEFINITE).setDelay(Duration.valueOf("250ms")).play();
		 
		
	}
}
