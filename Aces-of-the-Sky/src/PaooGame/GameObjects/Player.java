package PaooGame.GameObjects;

import PaooGame.Collision.CollisionDetector;
import PaooGame.GameWindow.GameWindow;
import PaooGame.GameManager.Key;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


/*! \class public class Player
    \brief Clasa pentru jucator ce include toate proprietatile acestuia
 */
public class Player {
    public static final int PLAYER_SIZE = 128;          /*!< Marimea in pixeli a imaginii*/
    private double x;                                   /*!< Coordonata x a pozitiei jucatorului*/
    private double y;                                   /*!< Coordonata y a pozitiei jucatorului*/
    private float angle;                                /*!< Unghiul la care este rotita imaginea jucatorului*/
    private float MAX_SPEED;                            /*!< Viteza maxima pe care o poate dezvolta jucatorul*/
    private float speed;                                /*!< Viteza curenta a jucatorului*/
    private final BufferedImage image;                  /*!< Imaginea(sprite-ul) jucatorului*/
    private int shotTime;                               /*!< Timpul scurs de cand a tras ultima data*/
    private int shotTimeCooldown;
    private double bulletSize;
    private float bulletSpeed;
    private ArrayList<Bullet> Bullets;                  /*!<Lista de gloante a jucatorului*/
    private Rectangle hitBox;                           /*!<Dreptunghi ce inconjoara jucatorul, folosit pentru coliziuni*/
    private int lives;                                  /*!<Numarul de vieti ale jucatorului*/
    private int score;
    private int respawnPointX;                          /*!<Coordonata X de spawn a jucatorului*/
    private int respawnPointY;                          /*!<Coordonata Y de spawn a jucatorului*/
    private float respawnAngle;                         /*!<Unghiul de spawn a jucatorului*/
    private boolean moved;                              /*!<Variabila ce stocheaza daca jucatorul s-a miscat de la start*/
    private int powerupOn;


    /*! \fn public Player
    \brief Constructor clasa pentru Player ce primeste ca parametrii pozitia de spawn(coordonatele x si y) si fereastra jocului
    */
    public Player(int x, int y, float angle, BufferedImage image) {
        this.x = x;
        this.y = y;
        this.angle = angle;

        /// Seteaza viteza maxima pe care o poate atinge jucatorul
        MAX_SPEED = 2f;
        speed = 0f;

        /// Incarca imaginea jucatorului
        this.image = image;
        shotTime = 0;

        /// Creeaza lista pentru stocarea gloantelor
        Bullets = new ArrayList<Bullet>();

        /// Creeaza dreptunghiul in jurul jucatorului pentru a fi folosit in cazul coliziunilor
        hitBox = new Rectangle(x, y, PLAYER_SIZE-32, PLAYER_SIZE-32);

        /// Se seteaza numarul de vieti ale jucatorului
        lives = 3;

        score = 0;

        /// Se salveaza datele de spawn ale jucatorului
        respawnPointX = x;
        respawnPointY = y;
        respawnAngle = angle;

        moved = false;

        powerupOn = 0;
    }

    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public float getAngle() {
        return angle;
    }
    public ArrayList<Bullet> getBullets() {return Bullets;}
    public Rectangle getHitBox() {return hitBox; }
    public int getLives() {return lives; }
    public float getMAX_SPEED() { return MAX_SPEED; }
    public int getPowerupOn() { return powerupOn; }
    public void setLives(int lives) {this.lives = lives; }
    public void setMAX_SPEED(float MAX_SPEED) {this.MAX_SPEED = MAX_SPEED; }
    public void setPowerupOn(int powerupOn) {this.powerupOn = powerupOn; }
    public void setScore (int score) { this.score = score; }
    public int getScore() { return score; }
    public void setShotTime(int shotTime) { this.shotTime = shotTime; }
    public void setShotTimeCooldown(int shotTimeCooldown) { this.shotTimeCooldown = shotTimeCooldown; }
    public void setbulletSize(double bulletSize) { this.bulletSize = bulletSize; }
    public void setBulletSpeed(float bulletSpeed) { this.bulletSpeed = bulletSpeed; }


    /*! \fn public void changeAngle
    \brief Metoda pentru a schimba unghiul jucatorului. Jucatorul se poate roti in jurul unui cerc de 360 de grade.
           Daca depaseste 359 de grade a ajuns la inceputul cerului adica 0 grade.
           Daca scade sub 0 grade a ajuns la finalul cercului adica 359 de grade.
    */
    public void changeAngle(float angle) {
        if(angle < 0) {
            angle = 359;
        }
        else if(angle > 359) {
            angle = 0;
        }
        this.angle = angle;
    }


    /*! \fn public void changeLocation
    \brief Metoda pentru a schimba locatia jucatorului.
    */
    public void changeLocation(double x, double y) {
        this.x = x;
        this.y = y;
    }


    /*! \fn public void draw(Graphics g)
    \brief Metoda pentru a desena jucatorul pe ecran
    */
    public void draw(Graphics g) {
        AffineTransform oldTransform = ((Graphics2D) g).getTransform();
        ((Graphics2D) g).translate(x, y);
        AffineTransform tran = new AffineTransform();
        tran.rotate(Math.toRadians(angle+90), PLAYER_SIZE / 2, PLAYER_SIZE / 2);
        ((Graphics2D) g).drawImage(image, tran, null);
        ((Graphics2D) g).setTransform(oldTransform);
    }

    /*! \fn public void update
    \brief Metoda pentru a actualiza pozitia pe ecran a jucatorului
    */
    public void update() {
        x+= Math.cos(Math.toRadians(angle)) * speed;
        y+= Math.sin(Math.toRadians(angle)) * speed;
        hitBox.x = (int)x;
        hitBox.y = (int)y;
        resetEffect();
    }


    /*! \fn public void speedUp / speedDown
    \brief Metode pentru a creste/scadea viteza de deplasare a jucatorului
    */
    public void speedUp() {
        if(speed > MAX_SPEED) {
            speed = MAX_SPEED;
        }
        else {
            speed+=0.03f;
        }
    }
    public void speedDown() {
        if(speed <= 0.5f) {
            if(moved == false) {
                speed = 0;
            }
            else {
                speed = 0.5f;
            }
        }
        else {
            speed-=0.005f;
        }
    }


    /*! \fn public void forcedSpeedDown
    \brief Metoda pentru a scadea viteza mai tare (se foloseste in momentul in care utlizator apasa tasta de franare a jucatorului (ArrowDown / S))
    */
    public void forcedSpeedDown() {
        if(speed <= 0) {
            speed = 0;
        }
        else {
            speed-= 0.005f;
        }
    }


    /*! \fn public void outOfBounds
    \brief Metoda care reseteaza starea jucatorului la starea initiala in cazul in care acesta iese in afara hartii
    */
    public void outOfBounds(int width, int height) {
        if(CollisionDetector.outOfBounds(this, width, height)) {
            this.changeLocation(respawnPointX, respawnPointY);
            this.speed = 0;
            this.angle = respawnAngle;
            lives--;
            moved = false;
        }
    }

    /*! \fn public void playerShot
    \brief Metoda care implementeaza ce se intampla in cazul in care jucatorul este lovit
    */
    public void playerShot(ArrayList<Bullet> bullets) {
        if(CollisionDetector.bulletHit(this, bullets)) {
            this.changeLocation(respawnPointX, respawnPointY);
            this.speed = 0;
            this.angle = respawnAngle;
            lives--;
            moved = false;
        }
    }

    /*! \fn public void playerShot
    \brief Metoda care implementeaza ce se intampla in cazul in care jucatorii intra unul in altul
    */
    public void playerCrash(Player other) {
        if(CollisionDetector.playerIntersects(this, other)) {
            this.changeLocation(respawnPointX, respawnPointY);
            this.speed = 0;
            this.angle = respawnAngle;
            lives--;
            moved = false;
        }
    }

    /*! \fn public void playerMovement
    \brief Metoda care realizeaza miscarea jucatorului si abilitatea de a trage cu gloante
    */
    public void playerMovement(Key key, ArrayList<Bullet> bullets, GameWindow wnd, boolean reverse) {
        float s = 1f;
        float angle = getAngle();
        /// Realizarea actiunilor pe baza tastelor apasate
        if(reverse == false) {
            if (key.isKey_left()) {
                angle -= s;
            }
            if (key.isKey_right()) {
                angle += s;
            }
        }
        if(reverse == true) {
            if (key.isKey_left()) {
                angle += s;
            }
            if (key.isKey_right()) {
                angle -= s;
            }
        }
        if(key.isKey_up()) {
            speedUp();
            moved = true;
        }
        else {
            speedDown();
        }
        if(key.isKey_down()) {
            forcedSpeedDown();
        }
        if(key.isKey_shoot()) {
            ///shotTime este folosit pentru a realizeaza un delay intre trageri
            if(shotTime == 0) {
                bullets.add(0, new Bullet(getX(), getY(), getAngle(), bulletSize, bulletSpeed));
            }
            shotTime++;
            if(shotTime > shotTimeCooldown) {
                shotTime = 0;
            }
        }
        else {
            if(shotTime != 0) {
                shotTime++;
            }
            if(shotTime > shotTimeCooldown) {
                shotTime = 0;
            }
        }
        /// Actualizarea gloantelor trase
        for(int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);
            if(bullet != null) {
                bullet.update();
                if(!bullet.check(wnd.GetWndWidth(), wnd.GetWndHeight())) {
                    bullets.remove(bullet);
                }
            }
            else {
                bullets.remove(bullet);
            }
        }
        /// Actualizeaza pozitia jucatorului
        update();
        /// Actualizeaza unghiul jucatorului
        changeAngle(angle);
    }


    /*! \fn public void bulletsDraw
    \brief Metoda care realizeaza desenarea gloantelor
    */
    public void bulletsDraw(Graphics g) {
        for(int i = 0; i < getBullets().size(); i++) {
            Bullet bullet = getBullets().get(i);
            if(bullet != null) {
                bullet.draw(g);
            }
        }
    }

    /*! \fn public void reset()
    \brief Metoda care reseteaza starea jucatorului la starea initiala
    */
    public void reset() {
        x = respawnPointX;
        y = respawnPointY;
        angle = respawnAngle;
        speed = 0f;
        shotTime = 0;
        lives = 3;
        moved = false;
        powerupOn = 0;
    }

    public void resetEffect() {
        if(powerupOn == 0) {
            MAX_SPEED = 2f;
        }
        else {
            powerupOn--;
        }
    }
}