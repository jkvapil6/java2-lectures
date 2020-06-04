package cz.upol.zp4jv.swing3;

public class DoubleSwitch {

    private int state;

    public DoubleSwitch() {
        this.state = 0;
    }

    public int getState() {
        return state;
    }

    public void turnFirstOn() {
        this.state = state | 2;
    }

    public void turnFirstOff() {
        switch (state) {
            case 0: state = 0; break;
            case 1: state = 1; break;
            case 2: state = 0; break;
            case 3: state = 1; break;
        }
    }

    public void turnSecondOn() {
        state = state | 1;
    }

    public void turnSecondOff() {
        switch (state) {
            case 0: state = 0; break;
            case 1: state = 0; break;
            case 2: state = 2; break;
            case 3: state = 2; break;
        }
    }

    public void switchSecond() {
        switch (state) {
            case 0: state = 1; break;
            case 1: state = 0; break;
            case 2: state = 3; break;
            case 3: state = 2; break;
        }
    }

    public void switchFirst() {
        switch (state) {
            case 0: state = 2; break;
            case 1: state = 3; break;
            case 2: state = 0; break;
            case 3: state = 1; break;
        }
    }
}
