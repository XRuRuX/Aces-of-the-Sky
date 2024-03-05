package PaooGame.GameObjects.Powerups;
import java.util.Random;


/*! \public abstract class Powerup
    \brief Implementeaza notiunea abstracta de putere a jocului.

    Aceasta clasa are scopul de a implementa ideea de abilitati speciale in joc. Abilitatile vor putea fi 'culese' de pe jos
    in momentul in care un avion trece deasupra lor. Acestea vor aparea random pe harta si frecventa lor de spawn va
    depinde de mapa. Aceste abilitati speciale vor oferi jucatorilor avantaje in joc precum: viteza sporita, o viata in plus,
    rata mai mare de a trage cu gloante, etc
 */

import PaooGame.GameObjects.Player;
import PaooGame.States.State;

import java.awt.*;

public abstract class Powerup {
    private static Powerup currentPowerup; /*!< Referinta catre powerup-ul curent al jocului*/
    private static int freq = 700;

    public static void setPowerup(Powerup powerup) { currentPowerup = powerup; }
    public static Powerup getPowerup() { return currentPowerup; }

    /*! \fn public abstract void draw(Graphics g)
    \brief Metoda pentru a desena obiectul pe ecran
    */
    public abstract void draw(Graphics g);

    public abstract boolean delete();

    public abstract void effectOn(Player player);
    public static boolean powerupSpawn() {
        if(currentPowerup == null) {
            Random rand = new Random();
            if (rand.nextInt(freq) == 1) {
                freq = 700;
                return true;
            } else {
                freq--;
                return false;
            }
        }
        return false;
    }
    public Rectangle getHitBox() { return currentPowerup.getHitBox(); }
}
