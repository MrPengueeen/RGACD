package mediaPage;



import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.ResourceBundle;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Screen;
import utils.Utils;

public class MediaPageController implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button button;

    @FXML
    private TextField categoryInput;

    @FXML
    private TextField rfidInput;
    
    @FXML
    private MediaView mv;
    
    @FXML
    private BorderPane mediaPageRoot;
    
    
    
    private String category, rfid, name;

	@Override
	public void initialize(URL location, ResourceBundle resource) {
		
		
		
		//mediaPageRoot.prefWidthProperty().bind();
		
		
		button.setOnAction( e -> {
			//category = categoryInput.getText();
			rfid = rfidInput.getText();
			System.out.println("Category: " + category);
			System.out.println("RFID: " + rfid);
			
			try {
				if(readCsv(rfid)) {
					System.out.println("Selected: " + name);
					playMedia();
				} else {
					System.out.println("No such RFID found!");
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
		});
	}
	
	public void setCategory(String cat) {
		this.category = cat;
	}
	
	public void setRfid(String rfid) {
		this.rfid = rfid;
	}
	
	public boolean readCsv(String rfid) throws Exception {
		
		boolean flag = false;
		
//		String configDirectory = System.getProperty("user:home" + "/rgacd/config.property");
//		File configFile = new File(configDirectory);
//		FileInputStream fileInput = new FileInputStream(configFile);
//		Properties property = new Properties();
//		property.load(fileInput);
//		
//		String mainDir = property.getProperty("Directory");
//		fileInput.close();
		String path = Utils.getMainDir() + "\\RGACD\\Categories\\" + category + "\\" + category + ".csv";
		Reader reader = Files.newBufferedReader(Paths.get(path));
		
		Iterable<CSVRecord> records = CSVFormat.DEFAULT.withHeader("RFID", "Name").parse(reader);
		for(CSVRecord record : records) {
			if(record.get("RFID").equals(rfid)) {
				name = record.get("Name");
				flag = true;
				break;
			}
			//System.out.println("RFID: " + record.get("RFID") + "  Name: " + record.get("Name"));
		}
		
		return flag;
	}
	
	public void playMedia() {
		

		try {
			
//			String configDirectory = System.getProperty("user:home" + "/rgacd/config.property");
//			File configFile = new File(configDirectory);
//			FileInputStream fileInput = new FileInputStream(configFile);
//			Properties property = new Properties();
//			property.load(fileInput);
//			
//			String mainDir = property.getProperty("Directory");
//			fileInput.close();
			String path = new File(Utils.getMainDir() + "\\RGACD\\Categories\\" + category + "\\" + rfid + "\\video.mp4").getAbsolutePath();
			Media media = new Media(new File(path).toURI().toString());
			MediaPlayer mediaPlayer = new MediaPlayer(media);
			mv.setMediaPlayer(mediaPlayer);
			mediaPlayer.setAutoPlay(true);
			DoubleProperty width = mv.fitWidthProperty();
			DoubleProperty height = mv.fitHeightProperty();
			width.bind(Bindings.selectDouble(mv.sceneProperty(), "width" ));
			height.bind(Bindings.selectDouble(mv.sceneProperty(), "height" ));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
   
}
