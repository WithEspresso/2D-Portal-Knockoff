
package myGame.gameObjects;
import myGame.geometry.Vector;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;


public abstract class GameObject 
{
    private Image sprite;

    protected int x;
    protected int y;
    protected int dx;
    protected int dy;
    protected boolean falling = true;
    protected boolean jumping = false;

    protected ObjectId id;
    protected SecondaryID secondaryID;
    private Boolean isAlive = true;
    Vector size;

    public GameObject(int x, int y, ObjectId id)
    {
        setX(x);
        setY(y);
        setID(id);
    }

    public abstract void tick();
    public abstract void render(Graphics g);

    public void setFalling(Boolean falling)
    {
        this.falling = true;
    }
    public boolean getFalling()
    {
        return this.falling;
    }
    public void setJumping(Boolean jumping)
    {
        this.jumping = true;
    }
    public boolean getJumping()
    {
        return this.jumping;
    }

    /*
    *   For simplicity's sake, everything in this game is a rectangle.
    *   This function is used when checking for collision.
    *   @param  none
    *   @return A swing Rectangle created using the object's current XY coordinates, height, and width.
    * */
    public Rectangle getBounds()
    {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }

    /*
    * @param none
    * @return the integer value of the object's y coordinates
    * */
    public int getX()
    {
        return this.x;
    }
    public int getdx()
    {
        return this.dx;
    }
    /*
    * @param the integer value of the object's x coordinates
    * @return none
    * */
    public void setX(int x)     
    {
        this.x = x;
    }
    /*
    * @param the integer value of the object's x new velocity
    * @return none
    * */
    public void setdx(int dx)
    {
        this.dx = dx;
    }
    /*
    * @param the integer value of the object's y coordinates
    * @return none
    * */
    public void setY(int y)
    {
        this.y = y;
    }
    /*
    * @param the integer value of the object's new y velocity
    * @return none
    * */
    public void setdy(int dy)
    {
        this.dy = dy;
    }
    /*
    * @param none
    * @return the integer value of the object's y coordinates
    * */
    public int getY()
    {
        return this.y;
    }
    public int getdy()
    {
        return this.dy;
    }

    /*
    * @param    none
    * @return   the enumerated value of the gameobject id.
    * */
    public ObjectId getId()
    {
        return this.id;
    }
    /*
    * @param    the enumerated value of the gameobject id.
    * @return   none
    * */
    public void setID(ObjectId id)
    {
        this.id = id;
    }

    /*
* @param    none
* @return   the enumerated value of the gameobject's secondaryid.
* */
    public SecondaryID getSecondaryId()
    {
        return this.secondaryID;
    }
    /*
    * @param    the enumerated value of the gameobject' secondaryid.
    * @return   none
    * */
    public void setSecondaryID(SecondaryID secondaryID)
    {
        this.secondaryID = secondaryID;
    }

    /*
    * @param    none
    * @return   the enumerated value of the gameobject id.
    * */
    public int getWidth()
    {
        return this.size.getX();
    }

    /*
    * @param    none
    * @return   the enumerated value of the gameobject id.
    * */
    public int getHeight()
    {
        return this.size.getY();
    }

    /*
    * Since the size never changes, this should be called at the constructor of
    * every concrete class that extends from GameObject.
    * @param width of the gameobject as an integer, height of the gameobject as an integer.
    * @return none
    * */
    public void setSize(int x, int y)
    {
        this.size = new Vector(x, y);
    }

    public void setSize(Vector size)
    {
        this.size = size;
    }

    public void setIsAlive(boolean deadOrAlive)
    {
        this.isAlive = deadOrAlive;
    }

    public Boolean getIsAlive()
    {
        return isAlive;
    }

    /*
    * Returns the size vector of this gameobject. Note that the size vector
    * is actually the half width of the gameobject's width and height.
    * Used for creating an aabb for collision detection.
    * @param none
    * @return The half size of this object in vector form.
    *   Example: 800x600 image will have a size vector of x=400, y = 300.
    *
    * */
    public Vector getSize()
    {
        return this.size;
    }

    /*
    * Returns the instantiated image of the gameobject.
    * @param none
    * @return the instantiated image of the gameobject.
    * */
    public Image getImage()
    {
        return this.sprite;
    }

    /*
    * Sets the image of the gameobject to a new image by using the SpriteManager class
    * as a sprite manager.
    * @param String key of the image name to retrieve from the
    * @return none
    * */
    /*
    public void setImage(String imageName)
    {
        this.sprite = handler.requestSprite(imageName);
    }
    */

    
    
    
    
    
    public abstract void takeDamage(int damage);

    @Override
    public String toString()
    {
        String gameObjectStatus = "";
        gameObjectStatus += ("Current size: " + this.getWidth() + ", " + this.getHeight() + "/n");
        gameObjectStatus += ("Current coordinates: " + this.getX() + ", " + this.getY() + "/n");
        return gameObjectStatus;
    }
}
