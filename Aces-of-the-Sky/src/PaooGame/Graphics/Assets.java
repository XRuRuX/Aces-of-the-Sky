package PaooGame.Graphics;

import java.awt.image.BufferedImage;



/*! \class public class Assets
    \brief Clasa incarca fiecare element grafic necesar jocului.

    Game assets include tot ce este folosit intr-un joc: imagini, sunete, harti etc.
 */
public class Assets
{
    public static BufferedImage menuImage;      /*!< Referinta catre imaginea ce contine fundalul meniului*/
    public static BufferedImage menuPlayButton;
    public static BufferedImage menuPlayButtonActive;
    public static BufferedImage menuSettingsButton;
    public static BufferedImage menuSettingsButtonActive;
    public static BufferedImage menuExitButton;
    public static BufferedImage menuExitButtonActive;
    public static BufferedImage settingsBackgroundMaxSpeedActive;
    public static BufferedImage settingsBackgroundShotTimeActive;
    public static BufferedImage settingsBackgroundSizeActive;
    public static BufferedImage settingsBackgroundSpeedActive;
    public static BufferedImage settingsBackgroundRestoreActive;
    public static BufferedImage mapLvl1;            /*!< Referinta catre imaginea ce contine harta jocului*/
    public static BufferedImage mapLvl2;            /*!< Referinta catre imaginea ce contine harta jocului*/
    public static BufferedImage mapLvl3;            /*!< Referinta catre imaginea ce contine harta jocului*/
    public static BufferedImage cameraBroken;
    public static BufferedImage Plane1;         /*!< Referinta catre sprite-ul ce contine imaginea avionului 1*/
    public static BufferedImage Plane2;         /*!< Referinta catre sprite-ul ce contine imaginea avionului 2*/
    public static BufferedImage speedPowerup;  /*!< Referinta catre imaginea ce contine icon-ul abilitatii de viteza*/
    public static BufferedImage EndScreen1;     /*!< Referinta catre imaginea ce contine sfarsitul jocului 1*/
    public static BufferedImage EndScreen2;     /*!< Referinta catre imaginea ce contine sfarsitul jocului 2*/


    /*! \fn public static void Init()
        \brief Functia initializaza referintele catre elementele grafice utilizate.

        Aceasta functie poate fi rescrisa astfel incat elementele grafice incarcate/utilizate
        sa fie parametrizate. Din acest motiv referintele nu sunt finale.
     */
    public static void Init()
    {
            /// Se initializeaza un obiect BufferedImage care incarca imaginea fundalului meniului
        menuImage = ImageLoader.LoadImage("/textures/MenuBackground.png");
        menuPlayButton = ImageLoader.LoadImage("/textures/MenuButtons/PlayButton.png");
        menuPlayButtonActive = ImageLoader.LoadImage("/textures/MenuButtons/PlayButtonActive.png");
        menuSettingsButton = ImageLoader.LoadImage("/textures/MenuButtons/SettingsButton.png");
        menuSettingsButtonActive = ImageLoader.LoadImage("/textures/MenuButtons/SettingsButtonActive.png");
        menuExitButtonActive = ImageLoader.LoadImage("/textures/MenuButtons/ExitButtonActive.png");
        menuExitButton = ImageLoader.LoadImage("/textures/MenuButtons/ExitButton.png");
        settingsBackgroundMaxSpeedActive = ImageLoader.LoadImage("/textures/Settings/SettingsBackgroundMaxSpeedActive.png");
        settingsBackgroundShotTimeActive = ImageLoader.LoadImage("/textures/Settings/SettingsBackgroundShotTimeActive.png");
        settingsBackgroundSizeActive = ImageLoader.LoadImage("/textures/Settings/SettingsBackgroundSizeActive.png");
        settingsBackgroundSpeedActive = ImageLoader.LoadImage("/textures/Settings/SettingsBackgroundSpeedActive.png");
        settingsBackgroundRestoreActive = ImageLoader.LoadImage("/textures/Settings/SettingsBackgroundRestoreToDefault.png");
            /// Se initializeaza un obiect BufferedImage care incarca toata harta jocului
        mapLvl1 = ImageLoader.LoadImage("/textures/MapLvl1.png");
        mapLvl2 = ImageLoader.LoadImage("/textures/MapLvl2.png");
        mapLvl3 = ImageLoader.LoadImage("/textures/MapLvl3.png");
        cameraBroken = ImageLoader.LoadImage("/textures/NoCamera.png");

            /// Se initializeaza un obiect BufferedImage care incarca imaginea avionului 1
        Plane1 = ImageLoader.LoadImage("/textures/Plane1.png");

            /// Se initializeaza un obiect BufferedImage care incarca imaginea avionului 1
        Plane2 = ImageLoader.LoadImage("/textures/Plane2.png");

            /// Se initializeaza un obiect BufferedImage care incarca imaginea abilitatii de viteza
        speedPowerup = ImageLoader.LoadImage("/textures/SpeedPowerup.png");

            /// Se initializeaza un obiect BufferedImage care incarca imaginea sfarsitului de joc in cazul in care jucatorul 1 a castigat
        EndScreen1 = ImageLoader.LoadImage("/textures/EndScreen1.png");

            /// Se initializeaza un obiect BufferedImage care incarca imaginea sfarsitului de joc in cazul in care jucatorul 1 a castigat
        EndScreen2 = ImageLoader.LoadImage("/textures/EndScreen2.png");
    }
}
