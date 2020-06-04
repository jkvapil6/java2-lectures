package cz.upol.zp4jv.fx2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CaesarFX extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(PhoneFX.class.getResource("javafx2/CaesarFXMainWindow.fxml"));
		Parent root = loader.load(); 
		
		// provazani controlleru a stage
		CaesarFXController controller = loader.getController();
		controller.setPrimaryStage(primaryStage);
		
		primaryStage.setTitle("Caesar's Cipher");

		Scene scene = new Scene(root, 400, 400);
		scene.getStylesheets().add(PhoneFXController.class.getResource("javafx2/CaesarFX.css").toString());

		primaryStage.setScene(scene);

		primaryStage.show();
	}
}
