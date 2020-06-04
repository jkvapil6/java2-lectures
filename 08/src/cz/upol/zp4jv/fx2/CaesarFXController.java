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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CaesarFXController implements Initializable {
	
	private Stage primaryStage;

	// navazeme xml objekty do controlleru
	@FXML private TextField txtShift;
    @FXML private TextArea taEncode;
    @FXML private TextArea taDecode;

    private CodingInfo info;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	@FXML public void explainAction() throws IOException {
		
		FXMLLoader loader = new FXMLLoader(PhoneFXController.class.getResource("javafx2/CaesarFXDialog.fxml"));
		Parent root = loader.load();
		
		Stage stage = new Stage();

        ExplainController controller = loader.getController();

        controller.setStage(stage, info);

		stage.initOwner(this.primaryStage);
		stage.initModality(Modality.WINDOW_MODAL);
		stage.setTitle("Help");
		stage.centerOnScreen();
		
		Scene scene = new Scene(root, 400, 250);
		scene.getStylesheets().add(PhoneFXController.class.getResource("javafx2/CaesarFX.css").toString());
		stage.setScene(scene);
		stage.show();
	}

	@FXML public void exitAction() {
		primaryStage.close();
	}

	@FXML public void encodeAction() {
        taDecode.setText(encode(txtShift.getText(), taEncode.getText(), true));
	}

	@FXML public void decodeAction() {
        taEncode.setText(encode(txtShift.getText(), taDecode.getText(), false));
	}

    /**
     * Zakoduje (encode == true) / dekoduje (encode == false) vstup txt o n pozic
     */
    private String encode(String n, String txt, boolean encode) {
        try {
            Integer num = Integer.parseInt(n);

            if (CaesarCipher.checkInput(txt)) {
                info = new CodingInfo(txt, CaesarCipher.caesarEncode(txt, num, encode), num);
                return CaesarCipher.caesarEncode(txt, num, encode);
            }
        } catch (NumberFormatException e) {
            System.out.println(e);
        }
        return null;
    }


	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public void setPrimaryStage(Stage stage) {
		this.primaryStage = stage;
	}
}
