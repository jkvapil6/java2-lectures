package cz.upol.zp4jv.fx2;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class NewContactController {
	
	private Stage stage;
	private ObservableList<PhoneContact> phoneNums;
	
	@FXML private TextField txtName;
	@FXML private TextField txtPhone;
	@FXML private Text lbNotification;

	@FXML public void okAction() {
		if (txtName.getText().trim().equals("") || txtPhone.getText().trim().equals("")) {
			lbNotification.setText("Something is missing");
		} else {
			phoneNums.add(new PhoneContact(txtName.getText(), txtPhone.getText()));
			stage.close();
		}
	}

	@FXML public void cancelAction() {
		stage.close();
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public ObservableList<PhoneContact> getPhoneNums() {
		return phoneNums;
	}

	public void setPhoneNums(ObservableList<PhoneContact> phoneNums) {
		this.phoneNums = phoneNums;
	}
}
