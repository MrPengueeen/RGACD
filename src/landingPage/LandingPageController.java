package landingPage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;

import animatefx.animation.SlideInLeft;
import animatefx.animation.SlideInRight;
import animatefx.animation.ZoomIn;
import animatefx.animation.ZoomInRight;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import loadingScreen.Main;

public class LandingPageController implements Initializable {
	
	@FXML 
	JFXDrawer drawer;
	@FXML
	VBox vbox;
	@FXML
	JFXHamburger hamburger;
	@FXML
	AnchorPane parentPane;
	@FXML 
	HBox modes, menubar;
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resource) {
		
		try {
			VBox box = FXMLLoader.load(getClass().getResource("/drawerContent/DrawerContent.fxml"));
			drawer.setSidePane(box);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		HamburgerBackArrowBasicTransition hamTrans = new HamburgerBackArrowBasicTransition(hamburger);
		hamTrans.setRate(-1);
		hamburger.addEventHandler(MouseEvent.MOUSE_CLICKED, (e)->{
			
			if(drawer.isClosed()) {
				drawer.open();
				new ZoomIn(modes).play();
				parentPane.setLeftAnchor(modes, 300.0);
				parentPane.setLeftAnchor(menubar, 300.0);
			} else {
				drawer.close();
				new ZoomIn(modes).play();
				parentPane.setLeftAnchor(modes, 0.0);
				parentPane.setLeftAnchor(menubar, 0.0);
			}
			hamTrans.setRate(hamTrans.getRate() * -1);
			hamTrans.play();
			
		});

		
		
	}
}
