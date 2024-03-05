package PaooGame.States;

import PaooGame.Game;
import PaooGame.GameWindow.GameWindow;
import PaooGame.Graphics.Assets;
import PaooGame.GameManager.Key;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;


/*! \class public class MenuState extends State
    \brief Implementeaza ideea de meniu a jocului cu mai multe optiuni precum: Play, Settings si Exit.
 */
public class MenuState extends State {
    private static MenuState instance;                  /*!< Referinta catre instanta care va fi folosita pentru a implementa sablonul Singleton*/
    private int xPositionButton;                        /*!< Pozitia pe axa X a unui buton*/
    private int yPositionButton;                        /*!< Pozitia pe axa Y a unui buton*/
    private int windowWidth;                             /*!< Latimea ferestrei jocului */
    private static BufferedImage playButton;            /*!< Butonul de Play */
    private static BufferedImage settingsButton;        /*!< Butonul de Settings */
    private static BufferedImage exitButton;            /*!< Butonul de Exit */
    private static BufferedImage settingsBackground;    /*!< Fundalul setarilor */
    private Key Key;                                    /*!< Referinta catre controalele de la tastatura*/
    private int activeOption;                           /*!< Optiunea activa in meniu*/
    private int delay;                                  /*!< Delay intre apasari ale butoanelor*/
    private boolean nextState;                          /*!< Verifica daca este necesar sa trecem la urmatoarea stare*/
    private boolean paused;                             /*!< Variabila ce stocheaza daca jocul este in pauza*/
    private boolean settings;                           /*!< Variabila ce stocheaza daca jocul este in setari*/
    private boolean home;                               /*!< Variabila ce stocheaza daca jocul este in home*/

        /*!< Stocheaza configuratiile jocului*/
    private int MAXSPEED;
    private int shotTime;
    private int size;
    private int speed;


/*! \fn private MenuState(GameWindow wnd)
    \brief  Constructorul de initializare a clasei folosind sablonul Singleton(Constructor privat pentru a se creea o singura instanta)
*/
    private MenuState(GameWindow wnd) {
            /// Pozitia butonului
        xPositionButton = (wnd.GetWndWidth()/2) - (Assets.menuPlayButton.getWidth()/2);
        yPositionButton = 200;
            /// Se incarca imaginile butoanelor din Assets
        playButton = Assets.menuPlayButtonActive;
        settingsButton = Assets.menuSettingsButton;
        exitButton = Assets.menuExitButton;
        settingsBackground = Assets.settingsBackgroundMaxSpeedActive;
            /// Tastatura pentru a putea fi folosita in cadrul meniului
        Key = new Key();
        Key.Capture(wnd, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_ENTER, KeyEvent.VK_ESCAPE);
            /// Configuratii meniu
        activeOption = 1;
        delay = 0;
        nextState = false;
        paused = false;
        settings = false;
        home = true;
            /// Latimea ferestrei jocului
        windowWidth = wnd.GetWndWidth();
    }

/*! \fn public static MenuState getInstance(GameWindow wnd)
    \brief  Metoda publica este apelata in scopul de a returna singura instanta a clasei si de a implementa sablonul Singleton.
*/
    public static MenuState getInstance(GameWindow wnd) {
        if(instance == null) {
            instance = new MenuState(wnd);
        }
        return instance;
    }

/*! \fn public void Update()
    \brief Actualizeaza starea curenta a jocului.
*/
    @Override
    public void Update() {
            /// Seteaza butoane in functie de optiunea activa
        if(home) {
            if (activeOption == 1) {
                playButton = Assets.menuPlayButtonActive;
                settingsButton = Assets.menuSettingsButton;
                exitButton = Assets.menuExitButton;
            } else if (activeOption == 2) {
                playButton = Assets.menuPlayButton;
                settingsButton = Assets.menuSettingsButtonActive;
                exitButton = Assets.menuExitButton;
            } else if (activeOption == 3) {
                playButton = Assets.menuPlayButton;
                settingsButton = Assets.menuSettingsButton;
                exitButton = Assets.menuExitButtonActive;
            } else if (activeOption == 4) {
                activeOption = 1;
            } else if (activeOption == 0) {
                activeOption = 3;
            } else {
                activeOption = 1;
            }
        }

        if(settings) {
            if (activeOption == 1) {
                settingsBackground = Assets.settingsBackgroundMaxSpeedActive;
            }
            else if(activeOption == 2) {
                settingsBackground = Assets.settingsBackgroundShotTimeActive;
            }
            else if(activeOption == 3) {
                settingsBackground = Assets.settingsBackgroundSizeActive;
            }
            else if(activeOption == 4) {
                settingsBackground = Assets.settingsBackgroundSpeedActive;
            }
            else if(activeOption == 5) {
                settingsBackground = Assets.settingsBackgroundRestoreActive;
            }
            else if(activeOption == 6) {
                activeOption = 1;
            }
            else if(activeOption == 0) {
                activeOption = 5;
            }
        }

            /// Incarca din baza de date configuratia jocului
        MAXSPEED = Float.valueOf(Database.getMAXSPEED()).intValue();
        shotTime = Database.getShotTime()/20;
        size = Double.valueOf(Database.getBulletSize()).intValue()/2;
        speed = Float.valueOf(Database.getBulletSpeed()).intValue()/2;

            /// Foloseste tastatura pentru a controla optiunile
        keyboardControl();
        keyboardAction();
    }

/*! \fn public void Draw(Graphics g)
    \brief Deseneaza (randeaza) pe ecran starea curenta a jocului.

    \param g Contextul grafic in care trebuie sa deseneze starea jocului pe ecran.
*/
    @Override
    public void Draw(Graphics g) {
            /// Desenaza imaginile in functie de optiunile jucatorului: Home, Settings, Paused
        if (home) {
            g.drawImage(Assets.menuImage, 0, 0, null);
            g.drawImage(playButton, xPositionButton, yPositionButton, null);
            g.drawImage(settingsButton, xPositionButton, yPositionButton + 200, null);
            g.drawImage(exitButton, xPositionButton, yPositionButton + 400, null);
        }
        if(paused) {
            Font fontText = new Font("Arial", Font.PLAIN, 100);
            Color colorText = Color.WHITE;
            g.setFont(fontText);
            g.setColor(colorText);
            g.drawString("PAUSE", windowWidth / 2 - 165, 120);
        }

        if(settings) {
            g.drawImage(settingsBackground, 0, 0, null);
            Font fontText2 = new Font("Calibri", Font.PLAIN, 60);
            g.setFont(fontText2);
            g.setColor(Color.WHITE);
            g.drawString(Integer.toString(MAXSPEED), 464, 165);
            g.drawString(Integer.toString(shotTime), 468, 307);
            g.drawString(Integer.toString(size), 466, 527);
            g.drawString(Integer.toString(speed), 466, 662);
        }

    }

/*! \fn public void nextState(Game game)
    \brief Metoda ce verifica daca trebuie sa treaca la urmatoarea stare
*/
    @Override
    public void nextState(Game game) {
        if(nextState == true) {
            nextState = false;
            paused = true;
            currentState = game.getPlayState();
        }
    }

/*! \fn public void keyboardControl()
    \brief Metoda ce implementeaza controlul jucatorului prin intermediul tastaturii
*/
    public void keyboardControl() {
        if (delay == 0) {
            if (Key.isKey_down()) {
                activeOption++;
                delay = 200;
            }
            if (Key.isKey_up()) {
                activeOption--;
                delay = 200;
            }
            if (settings) {
                handleKeyboardInput();
            }
        }

        for (int i = 0; i < delay; i++) {
            delay--;
        }
    }

/*! \fn public void keyboardAction()
    \brief Metoda ce implementeaza declansarea de actiuni pe baza alegerii facute de catre jucator
*/
    public void keyboardAction() {
        if (home) {
            if ((activeOption == 1) && (Key.isKey_shoot())) {
                nextState = true;
            }
            if ((activeOption == 2) && (Key.isKey_shoot())) {
                settings = true;
                home = false;
                activeOption = 1;
            }
            if ((activeOption == 3) && (Key.isKey_shoot())) {
                System.exit(0);
            }
        }
        if(settings) {
            if(Key.isKey_esc()) {
                settings = false;
                home = true;
                activeOption = 1;
            }
            if((activeOption == 5) && (Key.isKey_shoot())) {
                MAXSPEED = 2;
                shotTime = 5;
                size = 5;
                speed = 5;
                Database.update(MAXSPEED, shotTime*20, size*2, speed*2);
            }
        }
    }

/*! \fn private void handleKeyboardInput()
    \brief Metoda ce actualizeaza configuratia jocului
*/
    private void handleKeyboardInput() {
        if (activeOption == 1) {
            MAXSPEED = handleInput(MAXSPEED);
        }
        else if (activeOption == 2) {
            shotTime = handleInput(shotTime);
        }
        else if (activeOption == 3) {
            size = handleInput(size);
        }
        else if (activeOption == 4) {
            speed = handleInput(speed);
        }
        Database.update(MAXSPEED, shotTime*20, size*2, speed*2);
    }

/*! \fn private int handleInput()
    \brief Metoda ce actualizeaza valoarea variabilelor configuratiei jocului conform actiunii declansate
    de tastatura
*/
    private int handleInput(int value) {
        int returnValue = value;

        if(Key.isKey_right()) {
            returnValue++;
            if(returnValue > 9) {
                returnValue = 1;
            }
            delay = 200;
        }
        else if(Key.isKey_left()) {
            returnValue--;
            if(returnValue < 1) {
                returnValue = 9;
            }
            delay = 200;
        }

        return returnValue;
    }
}