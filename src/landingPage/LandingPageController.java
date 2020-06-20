package landingPage;

import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;


import animatefx.animation.SlideInLeft;
import animatefx.animation.SlideInRight;
import animatefx.animation.ZoomIn;
import animatefx.animation.ZoomInRight;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import loadingScreen.Main;
import mediaPage.MediaPageController;
import utils.Utils;

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
	@FXML
	JFXButton learnButton;
	@FXML
	AnchorPane landingPane;
	@FXML
	JFXComboBox<String> category;
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resource) {
		
		checkForMainDirectory();
		String categoryCsvPath = new File(Utils.getMainDir() + "\\Categories\\Categories.csv").getAbsolutePath();
		
		try {
			loadCategoriesToMenuButton(categoryCsvPath);
		} catch (FileNotFoundException e2) {
			e2.printStackTrace();
		}
		
		
		
		try {
			VBox box = FXMLLoader.load(getClass().getResource("/drawerContent/DrawerContent.fxml"));
			drawer.setSidePane(box);
		} catch (IOException e1) {
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
		
		learnButton.setOnAction( e -> {
			try {
				loadMediaPage();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			
		});

		
		
	}
	
	@FXML
	public void loadMediaPage() throws IOException {
		String categorySelected = category.getSelectionModel().getSelectedItem().toString();
		//BorderPane mediaPage =  FXMLLoader.load(getClass().getResource("/mediaPage/MediaPage.fxml"));
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/mediaPage/MediaPage.fxml"));
		BorderPane mediaPage =  loader.load();
		MediaPageController mediaPageController = loader.getController();
		mediaPageController.setCategory(categorySelected);
		
		
		mediaPage.prefHeightProperty().bind(landingPane.heightProperty());
		mediaPage.prefWidthProperty().bind(landingPane.widthProperty());
		landingPane.getChildren().setAll(mediaPage);
		System.out.println("Button Pressed!");
		System.out.println("Category Selected: " + categorySelected);
		
	}
	
	
	public void loadCategoriesToMenuButton(String pathToCsv) throws FileNotFoundException {
		String row;
	
		
		BufferedReader csvReader = new BufferedReader(new FileReader(new File(pathToCsv)));
		
		try {
			while((row = csvReader.readLine()) != null) {
				String[] elements = row.split("\\s*[,]\\s*");
				List<String> categoryList = Arrays.asList(elements);
				ObservableList<String> categoriesObsList = FXCollections.observableArrayList(categoryList);
				category.setItems(categoriesObsList);
				System.out.println(categoryList);
			}
			csvReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		
		
	}
	
	public void checkForMainDirectory() {
		String propertyPath = System.getProperty("user.home") + "/rgacd/config.property";
		File configFile = new File(propertyPath);
		
		if(configFile.exists()) {
			System.out.println("Exists!");
		} else {
			System.out.println("Doesn't exist! Generating config file");
			Properties propertyFile = new Properties();
			propertyFile.setProperty("Directory","F:\\RGACD Test Directory");
			try {
				configFile.getParentFile().mkdirs();
				FileOutputStream fileOut = new FileOutputStream(configFile);
				propertyFile.store(fileOut, "RGACD APPLICATION CONFIG FILE");
				fileOut.close();
				System.out.println("Config file generation complete!");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
}
