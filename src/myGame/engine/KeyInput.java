package myGame.engine;

import myGame.gameObjects.Player;
import myGame.gameObjects.GameObject;
import myGame.gameObjects.ObjectHandler;
import myGame.gameObjects.ObjectId;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class KeyInput extends KeyAdapter
{

    ObjectHandler handler;
    public KeyInput(ObjectHandler handler)
    {
        this.handler = handler;
    }
    @Override
    public void keyPressed(KeyEvent e)
    {
        int key = e.getKeyCode();
        for(int i=0; i<handler.object.size(); i++)
        {
            GameObject tempObject = handler.object.get(i);

            if(tempObject.getId() == ObjectId.Tank1)
            {
                if(key == KeyEvent.VK_D)
                {
                    tempObject.setdx(5);
                    ((Player)tempObject).setFacingLeft(false);
                }
                if(key == KeyEvent.VK_A)
                {
                    tempObject.setdx(-5);
                    ((Player)tempObject).setFacingLeft(true);
                }
                if(key == KeyEvent.VK_Q)
                {
                    
                    ((Player)tempObject).shootCheck("red");
                }
                if(key == KeyEvent.VK_E)
                {
                    ((Player)tempObject).shootCheck("blue");
                }
                if(key == KeyEvent.VK_SPACE && !tempObject.getJumping())
                {
                    //tempObject.setJumping(true);
                    tempObject.setdy(-20);
                }
                if(key == KeyEvent.VK_W)
                {
                    ((Player)(tempObject)).setAngle(-Math.PI/12);
                }
                if(key == KeyEvent.VK_S)
                {
                    ((Player)(tempObject)).setAngle(Math.PI/12);
                }
            }

        }    
        if(key == KeyEvent.VK_ESCAPE)
        {
            System.exit(1);
        }
    }
    
    @Override
    public void keyReleased(KeyEvent e)
    {
        int key = e.getKeyCode();
        for(int i=0; i<handler.object.size(); i++)
        {
            GameObject tempObject = handler.object.get(i);
            
            if(tempObject.getId() == ObjectId.Tank1)
            {
                if(key == KeyEvent.VK_D)
                {
                    tempObject.setdx(0);
                }
                if(key == KeyEvent.VK_A)
                {
                    tempObject.setdx(0);
                }
            }
        }

    }
    
}
