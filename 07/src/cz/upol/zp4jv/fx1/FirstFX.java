package cz.upol.zp4jv.fx1;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class FirstFX extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		// vytvorit graficky prvek "popisek"
		Text lbInput = new Text("Input:");
		
		// graficky prvek "textovy vstup"
		TextField txtInput = new TextField("Hello");
		
		// graficky prvek tlacitko
		Button btnOk = new Button("Ok");
		
		// navazani udalosti
		btnOk.setOnAction(e -> {
			System.out.println(txtInput.getText());
		});
		
//		HBox root = new HBox(10); // sklada komponenty do vodorovne rady
//		VBox root = new VBox(10); // sklada komponenty do svisle rady
		FlowPane root = new FlowPane(10, 5); // evkivalent pro flow layout
		root.setAlignment(Pos.CENTER); // zarovnani komponent
		root.getChildren().addAll(lbInput, txtInput, btnOk);
		
		// definuje vlastnosti okna
		primaryStage.setTitle("Hello World");
		primaryStage.setScene(new Scene(root, 400, 150));
		primaryStage.show();
	}
}
