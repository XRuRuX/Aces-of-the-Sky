package PaooGame.GameManager;

import java.util.Random;

public class EventManager {
    private int freqControls;
    private int revertControlsTime;
    private boolean revertControlsSignal;
    private int freqCamera;
    private int cameraBrokenTime;
    private boolean cameraBrokenSignal;

    public EventManager() {
        freqControls = 1000;
        revertControlsSignal = false;
        revertControlsTime = 200;

        freqCamera = 5000;
        cameraBrokenSignal = false;
        cameraBrokenTime = 100;
    }

    public boolean revertControls() {
        randomRevertControls();
        if(revertControlsSignal) {
            revertControlsTime--;
            return true;
        }
        else {
            return false;
        }
    }

    public boolean cameraBroken() {
        randomCameraBroken();
        if(cameraBrokenSignal) {
            cameraBrokenTime--;
            return true;
        }
        else {
            return false;
        }
    }

    private void randomRevertControls() {
        Random rand = new Random();
        if (rand.nextInt(freqControls) == 1) {
            freqControls = 1000;
            revertControlsTime = 200;
            revertControlsSignal = true;
        }
        if (revertControlsTime == 0) {
            revertControlsSignal = false;
        }
        else {
            freqControls--;
        }
    }

    private void randomCameraBroken() {
        Random rand = new Random();
        if (rand.nextInt(freqCamera) == 1) {
            freqCamera = 5000;
            cameraBrokenTime = 100;
            cameraBrokenSignal = true;
        }
        if (cameraBrokenTime == 0) {
            cameraBrokenSignal = false;
        }
        else {
            freqCamera--;
        }
    }
}

