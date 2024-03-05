package PaooGame.GameManager;

import PaooGame.GameObjects.Player;
import PaooGame.GameObjects.Powerups.Powerup;
import PaooGame.Graphics.Assets;

import java.awt.image.BufferedImage;

public class Level {
    private int currentLevel;
    BufferedImage map;
    Player Player1;
    Player Player2;

    public Level() {
        currentLevel = 1;
        map = null;
        Player1 = null;
        Player2 = null;
    }

    public boolean Update(boolean checkLives, BufferedImage mapC, Player Player1C, Player Player2C) {
        map = mapC;
        Player1 = Player1C;
        Player2 = Player2C;

        if ((Player1.getScore() == 2) || (Player2.getScore() == 2)) {
            currentLevel = 1;
            changeToLevel1();
            return true;
        }

        if (checkLives) {
            if (currentLevel == 1) {
                changeToLevel2();
                currentLevel++;
                return true;
            }
            if (currentLevel == 2) {
                changeToLevel3();
                currentLevel++;
                return true;
            }
        }
        return false;
    }

    private void changeToLevel1() {
        map = Assets.mapLvl1;
        Player1.reset();
        Player2.reset();
    }

    private void changeToLevel2() {
        map = Assets.mapLvl2;
        Player1.reset();
        Player2.reset();
    }

    private void changeToLevel3() {
        map = Assets.mapLvl3;
        Player1.reset();
        Player2.reset();
    }

    public BufferedImage getMap() { return map; }
    public Player getPlayer1() { return Player1; }
    public Player getPlayer2() { return Player2; }
    public int getCurrentLevel() { return currentLevel; }
}
