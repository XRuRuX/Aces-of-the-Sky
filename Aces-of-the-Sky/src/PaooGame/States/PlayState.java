package PaooGame.States;

import PaooGame.Collision.CollisionDetector;
import PaooGame.Game;
import PaooGame.GameManager.EventManager;
import PaooGame.GameObjects.Player;
import PaooGame.GameObjects.Powerups.Powerup;
import PaooGame.GameObjects.Powerups.SpeedPowerup;
import PaooGame.GameWindow.GameWindow;
import PaooGame.GameWindow.HUD;
import PaooGame.Graphics.Assets;
import PaooGame.GameManager.Key;
import PaooGame.GameManager.Level;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;


/*! \class public class PlayState extends State
    \brief Implementeaza/controleaza jocul. Clasa contine referinte catre diferite obiecte si
    componente necesare desfasurarii jocului.
 */
public class PlayState extends State {
    private static PlayState instance;  /*!< Referinta catre instanta care va fi folosita pentru a implementa sablonul Singleton*/
    private GameWindow wnd;             /*!< Referinta catre fereastra jocului*/
    private BufferedImage map;          /*!< Referinta catre harta jocului*/
    private Player Player1;             /*!< Referinta catre jucatorul 1*/
    private Player Player2;             /*!< Referinta catre jucatorul 2*/
    private Key Key1;                   /*!< Referinta catre controalele de la tastatura ale jucatorului 1*/
    private Key Key2;                   /*!< Referinta catre controalele de la tastatura ale jucatorului 2*/
    private EventManager Event;         /*!< Referinta catre clasa ce gestioneaza evenimentele produse in joc*/
    private int currentLevel;           /*!< Nivelul curent*/
    private Level Level;                /*!< Clasa ce gestioneaza nivelele si modul in care acestea sunt accesate*/
    private HUD HUD;                    /*!< Referinta catre interfara grafica ce afiseaza informatii pe ecran*/
    protected Powerup speedPowerup;     /*!< Referinta catre abilitatile speciale pe care le poate accesa jucatorul*/
    private boolean revertControls;     /*!< Verifica daca este necesara actiunea de a inversa controalele*/
    private boolean cameraBroken;       /*!< Verifica daca este necesara actiunea de a orbi jucatorii*/


/*! \fn public PlayState(GameWindow wnd)
    \brief  Constructorul de initializare a clasei
*/
    private PlayState(GameWindow wnd) {
            /// Apel al constructorului clasei de baza
        super();
            /// Seteaza GameWindow-ul
        this.wnd = wnd;
            /// Construieste obiectele de tip Player
        Player1 = new Player(0, 15, 0, Assets.Plane1);
        Player2 = new Player(wnd.GetWndWidth() - 180, wnd.GetWndHeight() - 180, 180f, Assets.Plane2);
            /// Apeleaza metode de initializare a tastaturii
        initKeyboard();
            /// Construieste interfata grafica pentru a afisa informatii pe ecran
        HUD = new HUD();
            /// Seteaza harta
        map = Assets.mapLvl1;
            /// Construieste contextul pentru a gestiona evenimentele
        Event = new EventManager();
            /// Construieste contextul pentru a gestiona nivelele
        Level = new Level();
            /// Context pentru a genera powerup-uri
        Powerup.setPowerup(null);
            /// Seteaza nivelul curent la 1
        currentLevel = 1;
            /// Nu este inca nevoie de inversarea controalelor
        revertControls = false;
            /// Nu este inca nevoie de a orbi jucatorii
        cameraBroken = false;

    }

/*! \fn public static PlayState getInstance(GameWindow wnd)
    \brief  Metoda publica este apelata in scopul de a returna singura instanta a clasei si de a implementa sablonul Singleton.
*/
    public static PlayState getInstance(GameWindow wnd) {
        if(instance == null) {
            instance = new PlayState(wnd);
        }
        return instance;
    }

/*! \fn public void Update()
    \brief Actualizeaza starea curenta a jocului.
*/
    @Override
    public void Update() {
            /// Miscarea jucatorilor
        Player1.playerMovement(Key1, Player1.getBullets(), wnd, revertControls);
        Player2.playerMovement(Key2, Player2.getBullets(), wnd, revertControls);

            /// Verifica coliziunile
        Collisions();

            /// Verifica daca trebuie generat powerup
        if (currentLevel != 3) {
            if (Powerup.powerupSpawn()) {
                speedPowerup = new SpeedPowerup(Assets.speedPowerup);
                Powerup.setPowerup(speedPowerup);
            }
        }

            /// Verifica daca powerup trebuie sters
        if (Powerup.getPowerup() != null) {
            if (Powerup.getPowerup().delete()) {
                speedPowerup = null;
                Powerup.setPowerup(null);
            }
        }

            /// Verifica daca nivelul este 3 in acest caz nu pot exista powerup-uri iar evenimentele de tip cameraBroken
            /// si revertControls sunt active pentru a avea nivelul o dificultate mai mare
        if(Level.getCurrentLevel() == 3) {
            revertControls = Event.revertControls();
            speedPowerup = null;
            Powerup.setPowerup(null);
        }
            /// Verifica daca nivelul este 2 sau 3 in acest caz pot exista powerup-uri iar doar evenimentul de tip
            /// cameraBroken exista pentru a face o diferenta intre nivelurile de dificultate
        if(Level.getCurrentLevel() >= 2) {
            cameraBroken = Event.cameraBroken();
        }

            /// Se verifica daca trebuie trecut la nivelul urmator
        if(Level.Update(checkLives(), map, Player1, Player2)) {
                /// Se modifica mapa in cazul in care se schimba nivelul
            map = Level.getMap();
                /// Se modifica jucatorii in cazul in care se schimba nivelul
            Player1 = Level.getPlayer1();
            Player2 = Level.getPlayer2();
        }

            /// In cazul in care se modifica setarile jocului acestea vor fi actualizate imediat in joc
        updatefromDatabase();
    }

/*! \fn public void Draw(Graphics g)
    \brief Deseneaza (randeaza) pe ecran starea curenta a jocului.

    \param g Contextul grafic in care trebuie sa deseneze starea jocului pe ecran.
*/
    @Override
    public void Draw(Graphics g) {
            /// Se deseneaza harta
        g.drawImage(map, 0, 0, null);
            /// Se deseneaza jucatorii
        Player1.draw(g);
        Player2.draw(g);
            /// Se deseneaza gloantele
        Player1.bulletsDraw(g);
        Player2.bulletsDraw(g);
            /// Se deseneaza powerup
        if (Powerup.getPowerup() != null) {
            Powerup.getPowerup().draw(g);
        }
            /// Se deseneaza HUD-ul
        HUD.draw(g, wnd, Player1.getLives(), Player2.getLives(), Player1.getScore(), Player2.getScore());

            /// In cazul in care evenimentul de tip cameraBroken este activ se va desena pe ecran o imagine
        if(cameraBroken) {
            g.drawImage(Assets.cameraBroken, 0, 0, null);
        }
    }

/*! \fn public void nextState(Game game)
    \brief Verifica daca trebuie sa trecem la urmatoarea stare si daca conditiile sunt adevarata
           trece la urmatoarea stare
*/
    @Override
    public void nextState(Game game) {
            /// Se verifica scorul jucatorilor iar in asa fel se verifica daca a castigat cineva
        if ((Player1.getScore() == 2) || (Player2.getScore() == 2)) {
            if (Player1.getScore() == 2) {
                whoWon = 1;
            } else if (Player2.getScore() == 2) {
                whoWon = 2;
            }
                /// In cazul in care un jucator a castigat se schimba starea si se reseteaza scorul si jucatorii
            currentState = game.getWinState();
            Player1.reset();
            Player2.reset();
            Player1.setScore(0);
            Player2.setScore(0);
        }
            /// Daca se apasa tasta esc se pune pauza la joc si se intra in meniu
        if (Key1.isKey_esc()) {
            currentState = game.getMenuState();
        }
    }

/*! \fn private void initKeyboard()
    \brief Citeste tastele apasate pentru a putea controla jucatorul
    Metoda este declarata privat deoarece trebuie apelata doar in constructorul clasei
 */
    private void initKeyboard() {
        Key1 = new Key();
        Key2 = new Key();
        Key1.Capture(wnd, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_R, KeyEvent.VK_ESCAPE);
        Key2.Capture(wnd, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_P, KeyEvent.VK_ESCAPE);
    }

/*! \fn private void initKeyboard()
    \brief Citeste tastele apasate pentru a putea controla avionul
    Metoda este declarata privat deoarece trebuie apelata doar in metoda Update()
*/
    private void Collisions() {
        /// Metoda verifica daca jucatorii au iesit din fereastra jocului
        Player1.outOfBounds(wnd.GetWndWidth(), wnd.GetWndHeight());
        Player2.outOfBounds(wnd.GetWndWidth(), wnd.GetWndHeight());
        /// Metoda verifica daca unul din jucatori a fost impuscat
        Player1.playerShot(Player2.getBullets());
        Player2.playerShot(Player1.getBullets());
        /// Metoda verifica daca jucatorii au intrat unul in altul
        Player1.playerCrash(Player2);
        Player2.playerCrash(Player1);
        /// Metoda verifica daca unul dintre jucatori a luat powerup
        if (CollisionDetector.playerPowerup(Player1)) {
            Powerup.getPowerup().effectOn(Player1);
        }
        if (CollisionDetector.playerPowerup(Player2)) {
            Powerup.getPowerup().effectOn(Player2);
        }
    }

/*! \fn private boolean checkLives()
    \brief Se verifica daca unul din jucatori a ramas fara vieti
*/
    private boolean checkLives() {
            /// Verifica daca numarul de vieti este egal cu 0. In cazul in care un jucator ramane fara vieti
            /// scorul celuilalt jucator creste cu un punct
        if ((Player1.getLives() == 0) || (Player2.getLives() == 0)) {
            if (Player1.getLives() == 0) {
                Player2.setScore(Player2.getScore() + 1);
            } else if (Player2.getLives() == 0) {
                Player1.setScore(Player1.getScore() + 1);
            }
            Player1.reset();
            Player2.reset();
            return true;
        }
        return false;
    }

/*! \fn private void updateFromDatabase()
    \brief Se extrage din baza de date configuratia jocului
*/
    private void updatefromDatabase() {
        Player1.setMAX_SPEED(Database.getMAXSPEED());
        Player2.setMAX_SPEED(Database.getMAXSPEED());
        Player1.setShotTimeCooldown(Database.getShotTime());
        Player2.setShotTimeCooldown(Database.getShotTime());
        Player1.setbulletSize(Database.getBulletSize());
        Player2.setbulletSize(Database.getBulletSize());
        Player2.setbulletSize(Database.getBulletSize());
        Player1.setBulletSpeed(Database.getBulletSpeed());
        Player2.setBulletSpeed(Database.getBulletSpeed());
    }
}