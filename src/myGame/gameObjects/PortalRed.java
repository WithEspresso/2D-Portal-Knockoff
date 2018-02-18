package myGame.gameObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class PortalRed extends GameObject
{
    private ObjectHandler handler;
    private String spriteKey = "portal_orange";
    private final int WIDTH = 32;
    private final int HEIGHT = 32;
    private int redX,redY;
    private int offset;

    public PortalRed(int x, int y, ObjectId id, ObjectHandler handler, int offset) 
    {
        super(x, y, id);
        this.handler = handler;
        this.setSize(WIDTH,HEIGHT);
        this.setRedX(x);
        this.setRedY(y);
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
        Color newRed = new Color(255,0,0,alpha);
        g.setColor(newRed);
        g.fillRect(x, y,WIDTH,HEIGHT);
        */
        g.drawImage(handler.requestSprite(spriteKey), this.getX(), this.getY(), null);
    }
    public void setRedX(int x)
    {
        this.redX = x;
    }
    public int getRedX()
    {
        return this.redX;
    }
    public void setRedY(int y)
    {
        this.redY = y;
    }
    public int getRedY()
    {
        return this.redY;
    }    
    @Override
    public void tick() 
    {
        checkCollision();
        System.out.println("X: " + this.x);
        System.out.println("Y: " + this.y);
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
    @Override
    public void takeDamage(int damage) 
    {
        
    }
}
