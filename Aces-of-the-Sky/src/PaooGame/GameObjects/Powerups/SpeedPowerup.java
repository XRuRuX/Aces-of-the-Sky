package PaooGame.GameObjects.Powerups;


import PaooGame.GameObjects.Player;

import java.awt.*;
import java.util.Random;
import java.awt.image.BufferedImage;

/*! \class public class SpeedPowerup extends Powerup
    \brief Implementeaza abilitatea speciala ce mareste viteza jucatorului
 */
public class SpeedPowerup extends Powerup {
    private double x;               /*!< Coordonata x a pozitiei obiectului*/
    private double y;               /*!< Coordonata y a pozitiei obiectului*/
    private int time;
    BufferedImage image;            /*!< Imaginea power-up-ului*/
    private Rectangle hitBox;       /*!< Dreptunghi ce inconjoara obiectul pentru coliziuni*/

    /*! \fn public SpeedPowerup
    \brief Constructor clasa pentru SpeedPowerup ce primeste ca parametrii pozitia unde va aparea obiectul pe ecran
    si imaginea abilitatii
    */
    public SpeedPowerup(BufferedImage image) {
            /// Seteaza pozitia random intre 200 si 1500 pe x si 200 si 800 pe y
        Random rand = new Random();
        this.x = rand.nextInt(1301) + 200;
        this.y = rand.nextInt(601) + 200;
            /// Seteaza imaginea
        this.image = image;
            /// Creeaza hitbox-ul
        hitBox = new Rectangle((int) x, (int) y, 64, 64);
        time = 500;
    }

    /*! \fn public void draw(Graphics g)
    \brief Metoda pentru a desena obiectul pe ecran
    */
    @Override
    public void draw(Graphics g) {
        g.drawImage(image, (int) x, (int) y, 64, 64, null);
    }

    @Override
    public boolean delete() {
        if(time == 0) {
            return true;
        }
        else {
            time--;
            return false;
        }
    }

    @Override
    public void effectOn(Player player) {
        if(player.getPowerupOn() == 0) {
            player.setMAX_SPEED(player.getMAX_SPEED() + 1f);
            player.setPowerupOn(500);
        }
        else {
            player.setPowerupOn(500);
        }
        time = 0;
    }

    public Rectangle getHitBox() { return hitBox; }
}
