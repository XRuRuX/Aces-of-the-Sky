package PaooGame.States;

import PaooGame.Game;
import PaooGame.GameWindow.GameWindow;
import PaooGame.Graphics.Assets;
import PaooGame.GameManager.Key;

import java.awt.*;
import java.awt.event.KeyEvent;


/*! \class public class WinState extends State
    \brief Implementeaza starea de sfarsit a jocului in care se afiseaza care din cei 2 jucatori au castigat si se ofera posibilitatea reluarii jocului.
 */
public class WinState extends State{
    private static WinState instance;   /*!< Referinta catre instanta care va fi folosita pentru a implementa sablonul Singleton*/
    private GameWindow wnd;             /*!< Referinta catre fereastra jocului*/
    private Key Key;                    /*!< Referinta catre controale*/


/*! \fn private WinState(GameWindow wnd)
    \brief  Constructorul de initializare a clasei folosind sablonul Singleton(Constructor privat pentru a se creea o singura instanta)
*/
    private WinState(GameWindow wnd) {
            /// Se apeleaza constructorul clasei de baza
        super();
        this.wnd = wnd;
            /// Se creeaza obiectul Key pentru a putea fi folosita tastatura in acest State
        Key = new Key();
        Key.Capture(wnd, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_SPACE, KeyEvent.VK_ESCAPE);
    }


/*! \fn public static WinState getInstance(GameWindow wnd)
    \brief  Metoda publica este apelata in scopul de a returna singura instanta a clasei si de a implementa sablonul Singleton.
*/
    public static WinState getInstance(GameWindow wnd) {
        if(instance == null) {
            instance = new WinState(wnd);
        }
        return instance;
    }


/*! \fn public void Update()
    \brief Actualizeaza starea curenta a jocului.
*/
    @Override
    public void Update() {
    }


/*! \fn public void Draw(Graphics g)
    \brief Deseneaza (randeaza) pe ecran starea curenta a jocului.

    \param g Contextul grafic in care trebuie sa deseneze starea jocului pe ecran.
*/
    @Override
    public void Draw(Graphics g) {
            /// Verifica ce jucator a castigat si afiseaza pe ecran
        if(whoWon != 0) {
            if(whoWon == 1) {
                g.drawImage(Assets.EndScreen1, 0, 0, null);
            }
            else if(whoWon == 2) {
                g.drawImage(Assets.EndScreen2, 0, 0, null);
            }
        }
    }


/*! \fn public void nextState(Game game)
    \brief Metoda ce verifica daca trebuie sa treaca la urmatoarea stare
*/
    @Override
    public void nextState(Game game) {
            /// Daca se apasa tasta afisata pe ecran atunci se va relua jocul
        if(Key.isKey_shoot()) {
            currentState = game.getPlayState();
        }
    }
}