package PaooGame;


/*! \class public class Main
    \brief Punctul de intrare al jocului ce contine metoda principala Main
 */
public class Main
{
    public static void main(String[] args)
    {
        Game game = new Game("Aces of the Sky", 1728, 960);
        game.StartGame();
    }
}
