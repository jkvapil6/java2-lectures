package cz.upol.zp4jv.fx2;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FirstFX extends Application {
	
	// provazani s konkretnim uzivatelskym prvkem
	@FXML
	private TextField inputField;
	
	// provazani s konkretni akci
	@FXML
	public void submitAction() {
		System.out.println(inputField.getText());
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(FirstFX.class.getResource("javafx2/FirstFX.fxml"));
		
		primaryStage.setTitle("Hello world");
		primaryStage.setScene(new Scene(root, 400, 200));
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
