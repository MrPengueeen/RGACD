package arduinoCommunicationPage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.fazecast.jSerialComm.SerialPort;

import arduino.Arduino;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import mediaPage.MediaPageController;
import utils.Utils;

public class ArduinoPageController implements Initializable {
	
	private String category;
	private String rfidTag = "";
	
	
	public ArduinoPageController(String category) {
		this.category = category;
	}
	@FXML
	BorderPane arduinoPane;
	@FXML
	Text updateText;
	@FXML
	TextField textField;
	@FXML
	ImageView imageView;
	boolean threadRunner = true;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resource) {
//		final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
//	    executorService.scheduleAtFixedRate(this::myTask, 0, 1, TimeUnit.SECONDS);
		System.out.println("Initial category: " + category);
		updateText.setText("Recieved String from Arduino: " + rfidTag);
		
		
		
		Task<Void> task = new Task<Void>() {
			// Implement required call() method
			  @Override
			  protected Void call() throws Exception {
			    
			    
			    Platform.runLater(() -> arduinoCommunication(getCategory()));
			    
			    // We're not interested in the return value, so return null
			    return null;
			  }
			};
		new Thread(task).start();
		//arduinoCommunication(getCategory());
//		try {
//			loadMediaPage();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	    
	   //arduinoCommunication(getCategory());
		
	}
	
	public void setCategory(String category) {
		this.category = category;
		System.out.println("This method: " +  category);
	}
	
	public String getCategory() {
		return category;
	}
	
//	private void myTask() {
//		updateText.setText("The selected Category is: " + getCategory());
//		SerialPort[] portNames = SerialPort.getCommPorts();
//		System.out.println("All com ports: ");
//		for(SerialPort x : portNames) {
//			System.out.println(x.getSystemPortName());
//		}
//		
//		String rgacdPort = portNames[0].getSystemPortName();
//		
//		
//		Arduino arduino =  new Arduino();
//		arduino.setPortDescription(rgacdPort);
//		arduino.setBaudRate(9600);
//		
//		arduino.openConnection();
//		String rfidTag = "";
//
//		while(arduino.openConnection()) {
//			rfidTag = arduino.serialRead().replaceAll("[\\n\\t ]", "");
//			updateText.setText("Recieved String from Arduino: " + rfidTag);
//			
//			if(!rfidTag.equals("")) {
//				break;
//			}
//			
//			}
//		
//		
//		arduino.closeConnection();
//		System.out.println("Connection closed!");
//		
//	}
	
	private void arduinoCommunication(String category) {
		System.out.println("Arduino communication Started");
		SerialPort[] portNames = SerialPort.getCommPorts();
		System.out.println("All com ports: ");
		for(SerialPort x : portNames) {
			System.out.println(x.getSystemPortName());
		}
		
		String rgacdPort = portNames[0].getSystemPortName();
		
		
		Arduino arduino =  new Arduino();
		arduino.setPortDescription(rgacdPort);
		arduino.setBaudRate(9600);
		
		arduino.openConnection();

		while(arduino.openConnection()) {
			rfidTag = arduino.serialRead().replaceAll("[\\n\\t ]", "");
			System.out.println(rfidTag);
			updateText.setText("Recieved String from Arduino: " + rfidTag);
			
			if(rfidTag.length() == 5) {
				break;
			}
			
			}
		
		
		
		arduino.closeConnection();
		System.out.println("Connection closed!");
		System.out.println(this.getCategory());
		System.out.println(rfidTag);
		threadRunner = false;
		try {
			loadMediaPage();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private void loadMediaPage() throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/mediaPage/MediaPage.fxml"));
		MediaPageController mediaPageController = new MediaPageController(getCategory(), rfidTag);
		loader.setController(mediaPageController);
		BorderPane mediaPage =  loader.load();
//		MediaPageController mediaPageController = loader.getController();
//		mediaPageController.setCategory(getCategory());
//		mediaPageController.setRfid(rfidTag);
		
		
		mediaPage.prefHeightProperty().bind(arduinoPane.heightProperty());
		mediaPage.prefWidthProperty().bind(arduinoPane.widthProperty());
		arduinoPane.getChildren().setAll(mediaPage);
	}
	

}
