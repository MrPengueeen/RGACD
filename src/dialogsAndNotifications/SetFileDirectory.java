package dialogsAndNotifications;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class SetFileDirectory implements Initializable {
	
	String selectedDirectoryPath, textFieldPath;
	
	
	@FXML
	Button browseButton, saveButton;
	@FXML
	TextField directoryField;
	
	
	public void initialize(URL location, ResourceBundle resource) {
		
	}
	
	@FXML
	private void chooseDirectory() {
		System.out.println("ButtonPressed");
		Stage dirStage = new Stage();
		DirectoryChooser dirChooser = new DirectoryChooser();
		File selectedDirectory = dirChooser.showDialog(dirStage);
		selectedDirectoryPath = selectedDirectory.getAbsolutePath();
		directoryField.setText(selectedDirectoryPath);
	}
	
	@FXML
	private void saveDirectory() {
		System.out.println(selectedDirectoryPath);
		textFieldPath = directoryField.getText();
		String propertyPath = System.getProperty("user.home") + "/rgacd/config.property";
		File configFile = new File(propertyPath);
		File mainDirectory =  new File(textFieldPath + "/RGACD/Categories/Categories.csv");
		Properties propertyFile = new Properties();
		propertyFile.setProperty("Directory", textFieldPath);
		try {
			configFile.getParentFile().mkdirs();
			mainDirectory.getParentFile().mkdirs();
			mainDirectory.createNewFile();
			FileOutputStream fileOut = new FileOutputStream(configFile);
			propertyFile.store(fileOut, "RGACD APPLICATION CONFIG FILE");
			fileOut.close();
			System.out.println("Config file generation complete!");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Stage stage = (Stage)saveButton.getScene().getWindow();
		stage.close();
	}
	
	public String getDirectoryPath() {
		return textFieldPath;
	}
}
