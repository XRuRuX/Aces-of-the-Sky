package PaooGame.GameObjects;


import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;


/*! \class public class Bullet
    \brief Clasa pentru fiecare glont pe care il trage jucatorul
 */
public class Bullet {
    private double x;               /*!< Coordonata x a pozitiei obiectului*/
    private double y;               /*!< Coordonata y a pozitiei obiectului*/
    private final Shape shape;      /*!< Forma obiectului*/
    private final Color color;      /*!< Culoarea obiectului*/
    private final float angle;      /*!< Unghiul obiectului*/
    private double size;            /*!< Marimea obiectului*/
    private float speed = 1f;       /*!< Viteza obiectului*/
    private Rectangle hitBox;       /*!< Dreptunghi ce inconjoara obiectul pentru coliziuni*/


    /*! \fn public Bullet
    \brief Constructor clasa pentru Bullet ce primeste ca parametrii pozitia, unghiul, marimea si viteza
    */
    public Bullet(double x, double y, float angle, double size, float speed) {
            /// Se seteaza pozitia in centrul jucatorului
        x+=Player.PLAYER_SIZE/2-(size/2);
        y+=Player.PLAYER_SIZE/2-(size/2);
        this.x = x;
        this.y = y;
            /// Seteaza culoarea, unghiul, marimea si viteza primite ca parametrii
        color = new Color(255, 255, 255);
        this.angle = angle;
        this.size = size;
        this.speed = speed;
            /// Creeaza forma
        shape = new Ellipse2D.Double(0, 0, size, size);
            /// Creeaza hitbox-ul
        hitBox = new Rectangle(0, 0, 10, 10);
    }


    /*! \fn public void update
    \brief Metoda pentru a actualiza pozitia pe ecran a obiectului
    */
    public void update() {
        x+=Math.cos(Math.toRadians(angle)) * speed;
        y+=Math.sin(Math.toRadians(angle)) * speed;
        hitBox.x = (int)x;
        hitBox.y = (int)y;

    }

    /*! \fn public boolean check(int width, int height)
    \brief Metoda pentru a verifica daca obiectul a iesit din ecran
    */
    public boolean check(int width, int height) {
        if((x <= -size) || (y < -size) || (x > width) || (y > height)) {
            return false;
        }
        else {
            return true;
        }
    }

    /*! \fn public void draw(Graphics g)
    \brief Metoda pentru a desena obiectul pe ecran
    */
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform oldTransform = g2d.getTransform();
        g2d.setColor(color);
        g2d.translate(x, y);
        g2d.fill(shape);
        g2d.setTransform(oldTransform);
    }

    public Rectangle getHitBox() {return hitBox; }
}
