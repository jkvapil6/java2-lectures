package cz.upol.zp4jv.fx2;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PhoneFXController implements Initializable {
	
	private Stage primaryStage;
	
	private ObservableList<PhoneContact> phoneNums = FXCollections.observableArrayList(
		new PhoneContact("Emergency", "112"),
		new PhoneContact("New emergency", "0118 999 881 999 119 7253"),
		new PhoneContact("Mr. Plow", "KL5-3226"));
	
	@FXML private ListView<PhoneContact> listPhones;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		listPhones.setItems(phoneNums);
	}

	@FXML public void newAction() throws IOException {
		
		FXMLLoader loader = new FXMLLoader(PhoneFXController.class.getResource("javafx2/PhoneFXDialog.fxml"));
		Parent root = loader.load();
		
		Stage stage = new Stage();
		NewContactController controller = loader.getController();
		controller.setStage(stage);
		controller.setPhoneNums(phoneNums);
		
		stage.initOwner(this.primaryStage);
		stage.initModality(Modality.WINDOW_MODAL);
		stage.setTitle("Input dialog");
		stage.centerOnScreen();
		
		Scene scene = new Scene(root, 400, 250);
		scene.getStylesheets().add(PhoneFXController.class.getResource("javafx2/PhoneFXDialog.css").toString());
		stage.setScene(scene);
		stage.show();
		
	}

	@FXML public void exitAction() {
		primaryStage.close();
	}
	
	@FXML public void listClickAction(MouseEvent e) {
		if (e.getClickCount() > 1)
			System.out.println(listPhones.getSelectionModel().getSelectedItem());
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public void setPrimaryStage(Stage stage) {
		this.primaryStage = stage;
	}
}
