package PaooGame.GameManager;

import PaooGame.GameWindow.GameWindow;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


/*! \class public class Key
    \brief Ofera jucatorului control al tastaturii pentru a realiza actiuni in cadrul jocului
 */
public class Key {
            /*!< Starea butoanelor apasate*/
    private boolean key_right;
    private boolean key_left;
    private boolean key_up;
    private boolean key_down;
    private boolean key_shoot;
    private boolean key_esc;


    /*! \fn public void Capture()
    \brief Metoda pentru a captura input-ul de la tastatura
           Primeste ca argumente o instanta a clasei GameWindow si o lista de butoane care vor fi utilizate pentru a declansa actiuni
    */
    public void Capture(GameWindow wnd, int up, int down, int left, int right, int shoot, int esc) {
            /*!< Starea de inregistrare a butoanelor se pune pe canvas*/
        Canvas canvas = wnd.GetCanvas();
        canvas.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {

                if(e.getKeyCode() == up) {
                    setKey_up(true);
                }
                else if (e.getKeyCode() == down) {
                    setKey_down(true);
                }
                else if (e.getKeyCode() == left) {
                    setKey_left(true);
                }
                else if (e.getKeyCode() == right) {
                    setKey_right(true);
                }
                else if (e.getKeyCode() == shoot) {
                    setKey_shoot(true);
                }
                else if (e.getKeyCode() == esc) {
                    setKey_esc(true);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if(e.getKeyCode() == up) {
                    setKey_up(false);
                }
                else if (e.getKeyCode() == down) {
                    setKey_down(false);
                }
                else if (e.getKeyCode() == left) {
                    setKey_left(false);
                }
                else if (e.getKeyCode() == right) {
                    setKey_right(false);
                }
                else if (e.getKeyCode() == shoot) {
                    setKey_shoot(false);
                }
                else if (e.getKeyCode() == esc) {
                    setKey_esc(false);
                }
            }
        });
    }

    public boolean isKey_right() {
        return key_right;
    }
    public void setKey_right(boolean key_right) {
        this.key_right = key_right;
    }

    public boolean isKey_left() {
        return key_left;
    }
    public void setKey_left(boolean key_left) {
        this.key_left = key_left;
    }

    public boolean isKey_up() {
        return key_up;
    }
    public void setKey_up(boolean key_up) {
        this.key_up = key_up;
    }

    public boolean isKey_down() {
        return key_down;
    }
    public void setKey_down(boolean key_down) {
        this.key_down = key_down;
    }

    public boolean isKey_shoot() {
        return key_shoot;
    }
    public void setKey_shoot(boolean key_shoot) {
        this.key_shoot = key_shoot;
    }

    public boolean isKey_esc() { return key_esc; }
    public void setKey_esc(boolean key_esc) { this.key_esc = key_esc; }
    }
