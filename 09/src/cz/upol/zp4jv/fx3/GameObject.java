package cz.upol.zp4jv.fx3;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Trida reprezentujici jakykoliv herni objekt (strela, hrac, nepritel)
 */
public class GameObject extends Rectangle {

    private boolean active = true;
    private String type;

    GameObject(int x, int y, int w, int h, String type) {
        super(w, h);

        this.type = type;
        setTranslateX(x);
        setTranslateY(y);
    }

    void moveLeft() {
        setTranslateX(getTranslateX() - 20);
    }

    void moveRight() {
        setTranslateX(getTranslateX() + 20);
    }

    void moveUp() {
        setTranslateY(getTranslateY() - 30);
    }

    void moveDown() {
        setTranslateY(getTranslateY() + 30);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
