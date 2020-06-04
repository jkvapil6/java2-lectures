package cz.upol.zp4jv.fx1;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;


/**
 * JavaFX GUI Aplikace pro kodovani / dekodovani textu Caesarovou sifrou
 */
public class CaesarFX extends Application {

    private CodingInfo info;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        //////////////// POSITION-SHIFT SECTION /////////////////
        Text lbPos = new Text("Shift value:");
        TextField txtPosInput = new TextField();

        GridPane.setConstraints(lbPos,0,1);
        GridPane.setMargin(lbPos, new Insets(10, 10, 5, 10));

        GridPane.setConstraints(txtPosInput, 0,2);
        GridPane.setMargin(txtPosInput, new Insets(0, 10, 5, 10));


        //////////////////// ENCODE SECTION //////////////////////
        Text lbEncode = new Text("Encode input: ");
        TextArea txtEncode = new TextArea();
        txtEncode.setWrapText(true);

        GridPane.setConstraints(lbEncode,0,3);
        GridPane.setMargin(lbEncode, new Insets(10, 10, 5, 10));

        GridPane.setConstraints(txtEncode, 0, 4);
        GridPane.setMargin(txtEncode, new Insets(0, 10, 5, 10));


        //////////////////// DECODE SECTION //////////////////////
        Text lbDecode = new Text("Decode input: ");
        TextArea txtDecode = new TextArea();
        txtDecode.setWrapText(true);

        GridPane.setConstraints(lbDecode,0 ,5);
        GridPane.setMargin(lbDecode, new Insets(10, 10, 5, 10));

        GridPane.setConstraints(txtDecode, 0, 6);
        GridPane.setMargin(txtDecode, new Insets(0, 10, 5, 10));


        ///////////////////////  BUTTONS  ////////////////////////
        Button btnEncode = new Button("Encode");
        btnEncode.setOnAction(e -> {
            txtDecode.setText(encode(txtPosInput.getText(), txtEncode.getText(), true));
        });

        Button btnDecode = new Button("Decode");
        btnDecode.setOnAction(e -> {
            txtEncode.setText(encode(txtPosInput.getText(), txtDecode.getText(), false));
        });

        Button btnExplain = new Button("Explain");
        btnExplain.setOnAction(e -> explain(primaryStage));


        ///////////////////// BUTT0NS GRID //////////////////////
        GridPane paneBtns = new GridPane();

        GridPane.setConstraints(btnEncode, 0, 0);
        GridPane.setMargin(btnEncode, new Insets(5, 0, 0, 10));

        GridPane.setConstraints(btnDecode, 1, 0);
        GridPane.setMargin(btnDecode, new Insets(5, 0, 0, 5));

        GridPane.setConstraints(btnExplain, 2, 0);
        GridPane.setMargin(btnExplain, new Insets(5, 0, 0, 5));

        paneBtns.getChildren().addAll(btnEncode, btnDecode, btnExplain);
        GridPane.setConstraints(paneBtns, 0,7);


        ////////////////////////  MENU  /////////////////////////
        Menu menu = new Menu("_Program");
        menu.setMnemonicParsing(true); // pro menu defaultne true

        MenuItem mnuEn = new MenuItem("_Encode");
        mnuEn.setAccelerator(new KeyCodeCombination(KeyCode.E, KeyCombination.CONTROL_DOWN));
        mnuEn.setOnAction(e -> txtDecode.setText(encode(txtPosInput.getText(), txtEncode.getText(), true)));

        MenuItem mnuDe = new MenuItem("_Decode");
        mnuDe.setAccelerator(new KeyCodeCombination(KeyCode.D, KeyCombination.CONTROL_DOWN));
        mnuDe.setOnAction(e -> txtEncode.setText(encode(txtPosInput.getText(), txtDecode.getText(), false)));

        MenuItem mnuExit = new MenuItem("E_xit");
        mnuExit.setAccelerator(new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN));
        mnuExit.setOnAction(e -> primaryStage.close());

        menu.getItems().addAll(mnuEn, mnuDe, new SeparatorMenuItem(), mnuExit);
        MenuBar menuBar = new MenuBar(menu);
        GridPane.setConstraints(menuBar,0,0);


        ///////////////////// MAIN FORM GRID ///////////////////
        GridPane root = new GridPane();
        root.getChildren().addAll(menuBar, lbPos, txtPosInput,lbEncode, txtEncode, lbDecode, txtDecode, paneBtns);

        primaryStage.setTitle("Caesar's Cipher");
        primaryStage.setScene(new Scene(root, 400, 550));
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    /**
     * Vytvori vyskakovaci okno, vysvetlujici princip aplikace
     */
    private void explain(Stage primaryStage) {
        Stage stage = new Stage();
        stage.initOwner(primaryStage);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setTitle("Help");

        GridPane root = new GridPane();
        root.setVgap(10);
        root.setPadding(new Insets(10,20,10,20));

        stage.setScene(new Scene(root, 430, 120));

        Text lbExpl = new Text("Program shifts alphabet position of all characters in text by <shift value>.\nEncode shifts forward, decode backward.");
        GridPane.setConstraints(lbExpl,0,0);
        Text lbExplAll = new Text("");
        GridPane.setConstraints(lbExplAll,0,1);

        // pokud uzivatel uz neco dekodoval, zobrazi se i info o predchozim stavu
        if (info != null) {

            if (info.getEncoded().length() > 30) lbExpl.setText(lbExpl.getText() + "\n\nIt means if <shiftvalue> is " + info.getShift() +
                        "\n\nAnd input is: \n" + info.getEncoded().substring(0,30) + "..." +
                        "\n\nThen output is: \n" + info.getDecoded().substring(0,30) + "..." );
            else lbExpl.setText(lbExpl.getText() + "\n\nIt means if <shiftvalue> is " + info.getShift() +
                        "\n\nAnd input is: \n" + info.getEncoded() +
                        "\n\nThen output is: \n" + info.getDecoded());

            for (int i = 0; i < info.getEncoded().length() && i < 30; i++) {
                lbExplAll.setText(lbExplAll.getText()
                        .concat(String.valueOf(info.getEncoded().charAt(i)))
                        .concat(" -> ")
                        .concat(String.valueOf(info.getDecoded().charAt(i)))
                        .concat("\t"));

                if (i % 6 == 5) lbExplAll.setText(lbExplAll.getText().concat("\n"));
            }
            stage.setHeight(360);
        }

        Button btnOk = new Button("Understand");
        GridPane.setConstraints(btnOk,0,2);
        GridPane.setHalignment(btnOk, HPos.CENTER);
        btnOk.setCancelButton(true);
        btnOk.setOnAction(e -> stage.close());

        root.getChildren().addAll(lbExpl, lbExplAll, btnOk);

        stage.centerOnScreen();
        stage.show();
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
}
