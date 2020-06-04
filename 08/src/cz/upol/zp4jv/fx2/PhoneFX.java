package cz.upol.zp4jv.fx2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PhoneFX extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(PhoneFX.class.getResource("javafx2/PhoneFXMainWindow.fxml"));
		Parent root = loader.load(); 
		
		// provazani controlleru a stage
		PhoneFXController controller = loader.getController();
		controller.setPrimaryStage(primaryStage);
		
		primaryStage.setTitle("Contacts");
		primaryStage.setScene(new Scene(root, 400, 300));
		primaryStage.show();
	}
}
