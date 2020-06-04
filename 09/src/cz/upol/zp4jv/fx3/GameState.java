package cz.upol.zp4jv.fx3;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class GameState {

    /** ziskane body */
    private IntegerProperty score = new SimpleIntegerProperty(0);




    public final IntegerProperty scoreProperty() {
        return this.score;
    }

    public final int getScore() {
        return this.scoreProperty().get();
    }

    public final void setScore(final int score) {
        this.scoreProperty().set(score);
    }
}
