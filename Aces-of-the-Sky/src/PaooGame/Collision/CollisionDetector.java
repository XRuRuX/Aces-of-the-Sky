package PaooGame.Collision;

import PaooGame.GameObjects.Bullet;
import PaooGame.GameObjects.Player;
import PaooGame.GameObjects.Powerups.Powerup;

import java.util.ArrayList;


/*! \class public class CollisionDetector
    \brief Clasa pentru a verifica coliziunile
 */
public class CollisionDetector {

    /*! \fn public outOfBounds(Player player, int width, int height)
    \brief Metoda ce verifica daca jucatorul a depasit limitele ecranului
    */
    public static boolean outOfBounds(Player player, int width, int height) {
        if((player.getX() > width-130) || (player.getX() < 0)) {
            return true;
        }
        else if((player.getY() > height-130) || (player.getY() < 0)) {
            return true;
        }
        return false;
    }

    /*! \fn public bulletHit(Player player, ArrayList<Bullet> Bullets)
    \brief Metoda ce verifica daca jucatorul a fost lovit de un glont
    */
    public static boolean bulletHit(Player player, ArrayList<Bullet> Bullets) {
        for(int i=0; i < Bullets.size(); i++) {
            Bullet bullet = Bullets.get(i);
            if(player.getHitBox().getBounds().intersects(bullet.getHitBox().getBounds())) {
                Bullets.remove(bullet);
                return true;
            }
        }
        return false;
    }

    /*! \fn public playerIntersects(Player player1, Player player2)
    \brief Metoda ce verifica daca jucatorii au intrat unul in altul
    */
    public static boolean playerIntersects(Player player1, Player player2) {
        if(player1.getHitBox().getBounds().intersects(player2.getHitBox().getBounds())) {
            return true;
        }
        return false;
    }

    public static boolean playerPowerup(Player player) {
        if(Powerup.getPowerup() != null) {
            if (player.getHitBox().getBounds().intersects(Powerup.getPowerup().getHitBox())) {
                return true;
            }
        }
        return false;
    }
}
