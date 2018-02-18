package myGame.gameObjects;

import myGame.engine.SoundManager;
import myGame.theVisuals.SpriteManager;

import java.awt.*;
import java.util.LinkedList;

public class ObjectHandler 
{
    public LinkedList<GameObject> object = new LinkedList<GameObject>();
    private GameObject tempObject;
    private SoundManager soundManager;
    private SpriteManager spriteManager;
    
    private GameObject portalRed;
    private GameObject portalBlue; 
    private int offset = 32;

    public ObjectHandler(SpriteManager spritemanager, SoundManager soundManager)
    {
        this.spriteManager = spritemanager;
        this.soundManager = soundManager;
        this.portalRed = new PortalRed(0, -200, ObjectId.PortalRed, this, offset);
        this.portalBlue = new PortalBlue(0, -200, ObjectId.PortalBlue, this, offset);
    }

    public void requestSound(String key)
    {
        soundManager.playMusic(key);
    }

    public Image requestSprite(String key)
    {
        return spriteManager.getImage(key);
    }

    public GameObject getPortalRed()
    {
        return this.portalRed;
    }
    
    public GameObject getPortalBlue()
    {
        return this.portalBlue;
    }
    
    /*
    * At each "tick" of the game, each game object's state is updated.
    * This function is called from the Game class.
    * @param    none
    * @return   none
    * */
    public void tick()
    {
        for(int i = 0; i< object.size();i++)
        {
            tempObject = object.get(i);
            tempObject.tick();
        }
    }

    /*
    * At each "tick" of the game, each game object is rendered.
    *
    * @param    none
    * @return   none
    * */
    public void render(Graphics g)
    {
        for(int i = 0; i< object.size();i++)
        {
            tempObject = object.get(i);
            tempObject.render(g);
        }
    }

    public int getSize()
    {
        return this.object.size();
    }

    public GameObject getObject(int index)
    {
        return this.object.get(index);
    }
    
    /*
    * Adds a game object to the Arraylist of existing objects in the game.
    * Ex: Players are added at the start of the game. Bullets are added when the fire key is pressed.
    *
    * @param    A game object coming into existance
    * @return   none
    * */
    public void addObject(GameObject object)
    {
        if(object.getId() == ObjectId.PortalRed)
            this.portalRed = object;
        if(object.getId() == ObjectId.PortalBlue)
            this.portalBlue = object;
        this.object.add(object);
    }

    /*
    * Removes a game object from the ArrayList of existing objects within the game
    * when the object "dies".
    * Ex: Players are removed on death. Bullets are removed upon collision.
    * */
    public void removeObject(GameObject object)
    {
        this.object.remove(object);
    }

}
