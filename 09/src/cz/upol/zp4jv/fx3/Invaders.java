package cz.upol.zp4jv.fx3;

import javafx.animation.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.List;
import java.util.stream.Collectors;

public class Invaders extends Application {

    Image img_inv = new Image("file:img/inv.png");
    Image img_shp = new Image("file:img/shp.png");

    /** stav hry .. todo: presunout do GameState */
    private boolean running = true;

    /** herni plocha */
	private Pane gamePane;

    private final int WIDTH = 520;
    private final int HEIGHT = 800;

    /** textovy popisek se ziskanymi body */
    private Text lbScore;

    /** objekt hrac */
    private GameObject player;

    /** pocitadlo pro generovani nepratelskych strel */
    private double shootCnt = 0;

    /** pocitadlo pro posun lodi */
    private double phaseCnt = 0;

    /** pocitadlo faze */
    private int phaseStep = 0;

    private GameState game = new GameState();

    /** zpozdeni mezi aktualizacemi herniho stavu */
    private static final int DELAY = 100; // ms

    /** zdroj casu pro aktualizaci hry */
    private Timeline timeline;


    private List<GameObject> getGameObjects() {
        return gamePane.getChildren().stream().map(n -> (GameObject)n).collect(Collectors.toList());
    }

    private void newWave() {
        for (int i = 0; i < 6; i++) {
            GameObject obj = new GameObject(i * 80, 20, 40, 40, "enemy");
            obj.setFill(new ImagePattern(img_inv));
            gamePane.getChildren().add(obj);
        }
    }

    private boolean update() {
        getGameObjects().forEach(obj -> {
            switch (obj.getType()) {
                case "enemybullet":
                     obj.moveDown();

                    if (obj.getTranslateY() >= HEIGHT) {
                        obj.setActive(false);
                        break;
                    }

                    if (obj.getBoundsInParent().intersects(player.getBoundsInParent())) {
                        player.setActive(false);
                        obj.setActive(false);
                    }
                    break;

                case "playerbullet":
                    obj.moveUp();

                    if (obj.getTranslateY() <= 0) {
                        obj.setActive(false);
                        break;
                    }

                    for (GameObject go : getGameObjects()) {
                        if (go.getType().equals("enemy") && go.getBoundsInParent().intersects(obj.getBoundsInParent())) {
                            go.setActive(false);
                            game.setScore(game.getScore() + 1);
                            obj.setActive(false);
                            break;
                        }
                    }
                    break;

                case "enemy":
                    if (obj.getTranslateY() > HEIGHT) player.setActive(false);
                    if (shootCnt >= 2 && Math.random() < 0.3) shoot(obj);
                    break;
            }
        });

        // smazani neaktivnich objektu
        gamePane.getChildren().removeIf(n -> {
            GameObject s = (GameObject) n;
            return !s.isActive();
        });

        printObjects();

        manageCounters();
        return player.isActive();
    }

    // TESTOVACI FUNKCE
    private void printObjects() {
        System.out.println("\nobjeky: ");
        getGameObjects().stream().forEach(n -> {
            System.out.print(n.getType().concat(" "));
        });
    }


    private void manageCounters() {
        shootCnt += 0.100;
        phaseCnt += 0.250;

        if (shootCnt > 2.1)  shootCnt = 0;
        if (phaseCnt > 2) {
            System.out.println("next phase!");
            nextPhase();
            phaseCnt = 0;
        }
    }


    private void nextPhase() {
        for (GameObject obj : getGameObjects()) {
            if (obj.getType().equals("enemy")) {
                if (phaseStep < 4) {
                    obj.moveRight();
                } else if (phaseStep == 4) {
                    obj.moveDown();
                } else if (phaseStep > 4 && phaseStep < 9) {
                    obj.moveLeft();
                } else if (phaseStep == 9) {
                    obj.moveDown();
                }
            }
        }

        phaseStep++;
        if (phaseStep > 9) {
            newWave();
            phaseStep = 0;
        }
    }


    private void shoot(GameObject obj) {
        int offset = 0;
        if (obj.getType().equals("enemy")) offset = 30;
        GameObject s = new GameObject((int)obj.getTranslateX() + 20, (int)obj.getTranslateY() + offset, 3, 20, obj.getType() + "bullet");
        s.setFill(Color.YELLOW);
        gamePane.getChildren().add(s);
    }


    private void pause() {
        if (running) {
            running = false;
            timeline.pause();
        } else {
            running = true;
            timeline.play();
        }
    }

    // todo..
    private void gameOver() {

        System.out.println("restart..?");
        timeline.stop();

        /*
        restart();
        pause();
        */
    }

    // todo..
    private void restart() {
        gamePane.getChildren().removeAll();

    }

    private void createGamePane() {
        gamePane = new Pane();
        gamePane.setPrefSize(WIDTH, HEIGHT);
        initGameObjects();
    }

    private void initGameObjects() {
        shootCnt = 0;
        phaseCnt = 0;
        phaseStep = 0;

        game.setScore(0);

        player = new GameObject(300 ,750, 40, 40, "player");
        player.setFill(new ImagePattern(img_shp));
        gamePane.getChildren().add(player);
        newWave();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        // inicializace herni plochy
        createGamePane();

        lbScore = new Text();
        lbScore.textProperty().bind(game.scoreProperty().asString());
        lbScore.setFill(Color.WHITE);
        lbScore.setFont(new Font("Fantasy", 32));

        BorderPane root = new BorderPane();

        StackPane gameStack = new StackPane();
        gameStack.getChildren().addAll(gamePane);

        HBox scorePane = new HBox(lbScore);

        root.setCenter(gameStack);
        root.setTop(scorePane);

        Scene scene = new Scene(root);
        scene.setFill(Color.BLACK);

        scene.setOnKeyPressed(this::dispatchKeyEvents);

        timeline = new Timeline();
        KeyFrame updates = new KeyFrame (

                // delay pro updatovani
                Duration.millis(DELAY),

                // check, jestli se ma pokracovat
                e -> { if (!update()) gameOver(); }
        );

        timeline.getKeyFrames().add(updates);

        // definuje nekonecne opakovani
        timeline.setCycleCount(Animation.INDEFINITE);

        // spusti hru hned po stupsteni programu
        timeline.play();

        primaryStage.setScene(scene);
        primaryStage.setTitle("Ivanders must die!");
        primaryStage.show();
        primaryStage.setResizable(false);
    }

    /**
     * Sleduje stisknete klavesy a pradava udalosti do stavu hry
     */
    private void dispatchKeyEvents(KeyEvent e) {
        switch (e.getCode()) {
            case LEFT:
                if (player.getTranslateX() > 0)
                    player.moveLeft();
                break;
            case RIGHT:
                if (player.getTranslateX() < ( WIDTH - player.widthProperty().intValue()))
                    player.moveRight();
                break;
            case SPACE:
                shoot(player);
                break;
            case P:
                pause();
                break;
            case R:
                restart();
                break;
        }
    }

    public static void main(String[] args) {
		launch(args);
	}
}
