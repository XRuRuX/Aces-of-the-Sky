package PaooGame.States;

import PaooGame.Game;
import PaooGame.GameManager.DatabaseManager;

import java.awt.*;


/*! \public abstract class State
    \brief Implementeaza notiunea abstracta de stare a jocului/programului.

    Un joc odata ce este lansat in executie nu trebuie "sa arunce jucatorul direct in lupta", este nevoie de
    un meniu care sa contine optiuni: New Game, Load Game, Settings, About etc. Toate aceste optiuni nu sunt altceva
    decat stari ale programului (jocului) ce trebuiesc incarcate si afisate functie de starea curenta.
 */
public abstract class State {
    protected static State currentState;            /*!< Referinta catre starea curenta a jocului: game, meniu, settings, about etc.*/
    protected static DatabaseManager Database;      /*!< Referinta catre baza de date ce va fi folosita in toate state-urile.*/
    protected static int whoWon;                    /*!< Referinta catre variabila ce salveaza cine a castigat.*/


/*! \fn private State()
    \brief  Constructorul clasei
*/
    public State() {
            /// Seteaza starea curenta ca null
        currentState = null;
            /// Se incarca baza de date
        Database = new DatabaseManager();
        Database.connect();
            /// Nu exista inca jucator care a castigat
        whoWon = 0;
    }


        /// Metoda abstracta destinata actualizarii starii curente
    public abstract void Update();

        /// Metoda abstracta destinata actualizarii starii curente
    public abstract void Draw(Graphics g);

        /// Metoda abstracta pentru a trece la urmatoarea stare
    public abstract void nextState(Game game);

    public static void SetState(State state) {
        currentState = state;
    }

    public static State GetState()
    {
        return currentState;
    }
}
