
package myGame.theVisuals;

import myGame.engine.Game;

import java.awt.Dimension;
import javax.swing.JFrame;

public class GameScreen 
{

    /*
    * Constructor for the game screen. Initializes the game's window setup, then calls
    * game.start to initialize the game.
    * @param Width of the window as an integer,
    *       height of the window as an integer,
    *       title of the window as a string,
    *       game to display and run
    * @return an instance of the GameScreen.
    *
    * */
    public GameScreen(int width, int height, String title, Game game)
    {
        game.setPreferredSize(new Dimension(width,height));
        game.setMaximumSize(new Dimension(width,height));
        game.setMinimumSize(new Dimension(width,height));
        JFrame frame = new JFrame(title);
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        game.start();
    }
}
