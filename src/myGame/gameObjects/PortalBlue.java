package myGame.gameObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class PortalBlue extends GameObject
{
    private ObjectHandler handler;
    private String spriteKey = "portal_blue";
    private final int WIDTH = 32;
    private final int HEIGHT = 32;
    
    private int offset = 32;
    private int blueX,blueY;

    public PortalBlue(int x, int y, ObjectId id, ObjectHandler handler, int offset) 
    {
        super(x, y, id);
        this.handler = handler;
        this.setSize(WIDTH,HEIGHT);
        this.setBlueX(x);
        this.setBlueY(y);
        this.offset = offset; 
    }

    public int getOffset()
    {
        return this.offset;
    }

    @Override
    public void render(Graphics g) 
    {
        /*
        int alpha = 127;
        Color newBlue = new Color(0,0,255,alpha);
        g.setColor(newBlue);
        g.fillRect(x, y,WIDTH,HEIGHT);
        */
        g.drawImage(handler.requestSprite(spriteKey), this.getX(), this.getY(), null);
    }
    public void setBlueX(int x)
    {
        this.blueX = x;
    }
    public int getBlueX()
    {
        return this.blueX;
    }
    public void setBlueY(int y)
    {
        this.blueY = y;
    }
    public int getBlueY()
    {
        return this.blueY;
    }


    @Override
    public void tick() 
    {
        checkCollision();
    }
    private void checkCollision()
    {
        for(int i =0;i<handler.object.size();i++)
        {
            GameObject tempObject = handler.object.get(i);
            if(tempObject.getId() == ObjectId.Block)
            {
                if(this.getBoundsRight().intersects(tempObject.getBounds()))
                {
                    x = tempObject.getX() - (32);
                    this.offset = -32;
                }
                if(this.getBoundsLeft().intersects(tempObject.getBounds()))
                {
                    x = tempObject.getX()+32;
                    this.offset = 32;
                    
                }
            }
        }
    }
    @Override
    public void takeDamage(int damage) 
    {
        
    }
    public Rectangle getBounds()
    {
        return new Rectangle(x,y,32,32);
    }
    public Rectangle getBoundsRight() 
    {
        return new Rectangle(x+(32-5),y+7,10,32-13);
    }
    public Rectangle getBoundsLeft() 
    {
        return new Rectangle(x,y+7,10,32-13);
    }
    public Rectangle getBoundsTop() 
    {
        return new Rectangle((x+(32/2)-((32/2)/2)),y,(32/2)+3,32/2);
    }
}
