package PaooGame.GameWindow;

import java.awt.*;
import java.awt.geom.Ellipse2D;



/*! \public class HUD
    \brief Implementeaza notiunea de interfata a jocului

    Aceasta clasa are rolul de a afisa informatii importante pe ecran de exemplu numarul de vieti ale jucatorilor,
    numarul de puncte ale fiecarui jucator etc.
 */
public class HUD {
    private String alliesText;      /*!< Textul afisat pe ecran in cazul jucatorului 1*/
    private String axisText;        /*!< Textul afisat pe ecran in cazul jucatorului 2*/
    private Font fontText;          /*!< Fontul textului afisat pe ecran*/
    private Font fontText2;
    private Color colorText;        /*!< Culoarea textului afisat pe ecran*/


    public HUD() {
        alliesText = "ALLIES: ";
        axisText = "AXIS: ";
        fontText = new Font("Arial", Font.BOLD, 30);
        fontText2 = new Font("Arial", Font.BOLD, 35);
        colorText = Color.WHITE;
    }

    /*! \fn public void draw()
    \brief Metoda pentru a desena HUD-ul pe ecran
    */
    public void draw(Graphics g, GameWindow wnd, int livesP1, int livesP2, int scoreP1, int scoreP2) {
        g.setFont(fontText);
        g.setColor(colorText);
        g.drawString(alliesText, 10, wnd.GetWndHeight() - 10);
        g.drawString(axisText, wnd.GetWndWidth()-190, 35);
        drawLives(g, wnd, livesP1, livesP2);
        drawScore(g, wnd, scoreP1, scoreP2);
    }

    /*! \fn private void drawLives()
    \brief Metoda pentru a desena vietile jucatorului pe ecran
           Metoda este privata pentru a fi apelata doar in metoda draw()
    */
    private void drawLives(Graphics g, GameWindow wnd, int livesP1, int livesP2) {
            /// Marimea cercului
        int circleSize = 20;
            /// Distanta dintre cercuri
        int spacing = 10;

            /// Se deseneaza cercurile
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(colorText);
        for (int i = 0; i < livesP1; i++) {
            int x = g.getFontMetrics(fontText).stringWidth(alliesText) + i * (circleSize + spacing) + 10;
            int y = wnd.GetWndHeight() - 10 - circleSize;
            g2d.fill(new Ellipse2D.Double(x, y, circleSize, circleSize));
        }
        for(int i = 0; i < livesP2; i++) {
            int x = wnd.GetWndWidth()-50 - (2-i) * (circleSize + spacing) + 10;
            int y = circleSize-5;
            g2d.fill(new Ellipse2D.Double(x, y, circleSize, circleSize));
        }
    }

/*! \fn private void drawScore()
    \brief Metoda pentru a desena scorul jucatorilor
     Metoda este privata pentru a fi apelata doar in metoda draw()
*/
    private void drawScore(Graphics g, GameWindow wnd, int scoreP1, int scoreP2) {
        String scoreText = "SCORE: " + scoreP1 + " - " + scoreP2;
        int x = (wnd.GetWndWidth() - g.getFontMetrics(fontText2).stringWidth(scoreText)) / 2;
        int y = 40;
        g.setFont(fontText2);
        g.drawString(scoreText, x, y);
    }
}
