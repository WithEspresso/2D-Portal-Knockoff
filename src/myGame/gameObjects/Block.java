package myGame.gameObjects;

import myGame.theVisuals.SpriteManager;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

public class Block extends GameObject
{
    private ObjectHandler handler;
    private String spriteKey = "block";
    private final int WIDTH = 32;
    private final int HEIGHT = 32;

    public Block(int x, int y, ObjectId id, ObjectHandler handler)
    {
        super(x, y, id);
        this.handler = handler;
        this.setSize(WIDTH, HEIGHT);
    }

    @Override
    public void tick()
    {
        //checkCollision();
    }

    private void checkCollision()
    {
        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);
            if (tempObject.getSecondaryId() == SecondaryID.Bullet)
            {
                if (this.getBounds().intersects(tempObject.getBounds()))
                {
                    handler.removeObject(tempObject);
                }
            }
        }
    }

    public void takeDamage(int damage)
    {
    }

    @Override
    public void render(Graphics g) 
    {
        g.drawImage(handler.requestSprite(spriteKey), getX(), getY(), null);
    }

    public ObjectId getId() 
    {
        return id;
    }

    @Override
    public Rectangle getBounds() 
    {
        return new Rectangle(x,y,32,32);
    }

    public Rectangle getBoundsRight() 
    {
        return new Rectangle(x+WIDTH-5,y+7,5,HEIGHT-13);
    }

    public Rectangle getBoundsLeft() 
    {
        return new Rectangle(x,y+7,5,HEIGHT-13);
    }
}
