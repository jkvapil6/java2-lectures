package cz.upol.zp4jv.fx1;


import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PhoneFX extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}
	
	private ObservableList<PhoneContact> phoneNums = FXCollections.observableArrayList(
			new PhoneContact("Emergency", "112"),
			new PhoneContact("New emergency", "0118 999 881 999 119 7253"),
			new PhoneContact("Mr. Plow", "KL5-3226"));

	@Override
	public void start(Stage primaryStage) throws Exception {
		// --- Menu File
		Menu menuFile = new Menu("_File");
		menuFile.setMnemonicParsing(true); // pro menu defaultne true
		
		MenuItem mnuNew = new MenuItem("_New");
		mnuNew.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));
		mnuNew.setOnAction(e -> newItem(primaryStage));
		
		MenuItem mnuExit = new MenuItem("E_xit");
		mnuExit.setAccelerator(new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN));
		mnuExit.setOnAction(e -> primaryStage.close());
		
		menuFile.getItems().addAll(mnuNew, new SeparatorMenuItem(), mnuExit);

		MenuBar menuBar = new MenuBar(menuFile);
		
		
		// seznam umisteny v hlavni casti okna
		ListView<PhoneContact> listPhoneNums = new ListView<PhoneContact>();
		listPhoneNums.setItems(phoneNums);
		listPhoneNums.setOnMouseClicked(e -> {
			if (e.getClickCount() > 1)
				System.out.println(listPhoneNums.getSelectionModel().getSelectedItem());
		});
		
		
		BorderPane root = new BorderPane();
		root.setTop(menuBar);
		root.setCenter(listPhoneNums);
		
		// vlastnosti okna
		primaryStage.setTitle("Contacts");
		primaryStage.setScene(new Scene(root, 500, 400));
		primaryStage.show();
		
	}
	
	private void newItem(Stage primaryStage) {
		// vytvori nove modalni okno
		Stage stage = new Stage();
		stage.initOwner(primaryStage);
		stage.initModality(Modality.WINDOW_MODAL);
		stage.setTitle("Input dialog");

		GridPane root = new GridPane();
		root.setAlignment(Pos.CENTER);
		root.setHgap(20);
		root.setVgap(10);
		//root.setGridLinesVisible(true);
		
		Text lbCaption = new Text("New contact");
		lbCaption.setFont(Font.font(20));
		
		root.add(lbCaption, 0, 0, 2, 1); // roztazeni pres dva sloupce
		
		TextField txtName = new TextField();
		TextField txtPhone = new TextField();
		
		root.addRow(1, new Text("Name"), txtName);
		root.addRow(2, new Text("Phone"), txtPhone);
		
		// text, ktery se zobrazi v pripade chyby
		Text lbNotification = new Text();
		lbNotification.setFill(Color.RED);
		root.add(lbNotification, 0, 3, 2, 1);
		
		// tlacitka
		Button btnOk = new Button("Ok");
		btnOk.setDefaultButton(true); // stisknuti enter odpovida stisknuti tlacitka
		btnOk.setOnAction(e -> {
			String name = txtName.getText().trim();
			String phone = txtPhone.getText().trim();
			if (name.equals("") || phone.equals("")) {
				lbNotification.setText("Something is missing");
			} else {
				phoneNums.add(new PhoneContact(name, phone));
				stage.close();
			}
		});
		
		Button btnCancel = new Button("Cancel");
		btnCancel.setCancelButton(true); // stisknuti escape odpovida stisknuti tlacitka
		btnCancel.setOnAction(e -> stage.close());
		
		// vytvori blok tlacitek
		HBox buttons = new HBox(10);
		buttons.getChildren().addAll(btnOk, btnCancel);
		buttons.setAlignment(Pos.CENTER_RIGHT);
		root.add(buttons, 0, 4, 2, 1);
		
		
		stage.setScene(new Scene(root, 400, 300));
		stage.centerOnScreen();
		stage.show();
	}
}
