package cz.upol.zp4jv.fx2;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ExplainController {
	
	private Stage stage;
	@FXML private Label lbInfo;
    @FXML private Label lbInfoAll;

	@FXML public void cancelAction() {
		stage.close();
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage, CodingInfo info) {

        this.stage = stage;

        // pokud uzivatel uz neco dekodoval, zobrazi se i info o predchozim stavu
        if (info != null) {

            if (info.getEncoded().length() > 30)  lbInfo.setText(lbInfo.getText() + "\n\nIt means if <shiftvalue> is " + info.getShift() +
                    "\n\nAnd input is: \n" + info.getEncoded().substring(0,30) + "..." +
                    "\n\nThen output is: \n" + info.getDecoded().substring(0,30) + "..." );
            else  lbInfo.setText( lbInfo.getText() + "\n\nIt means if <shiftvalue> is " + info.getShift() +
                    "\n\nAnd input is: \n" + info.getEncoded() +
                    "\n\nThen output is: \n" + info.getDecoded());

            for (int i = 0; i < info.getEncoded().length() && i < 30; i++) {
                lbInfoAll.setText( lbInfoAll.getText()
                        .concat(String.valueOf(info.getEncoded().charAt(i)))
                        .concat(" -> ")
                        .concat(String.valueOf(info.getDecoded().charAt(i)))
                        .concat("\t"));

                if (i % 6 == 5)  lbInfoAll.setText( lbInfoAll.getText().concat("\n"));
            }
            stage.setHeight(360);
        }

        // lbInfo.setText("hello");
	}

}
