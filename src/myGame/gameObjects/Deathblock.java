package myGame.gameObjects;

import myGame.theVisuals.SpriteManager;

import java.awt.*;

public class Deathblock extends GameObject
{
    private final int WIDTH = 32;
    private final int HEIGHT = 32;
    private final int maxHealth = 30;
    private int health;
    private ObjectHandler handler;
    private Color healthColor = Color.white;
    private String soundKey = "explosion";
    private String spriteKeyBase = "death_block_";
    private String spriteKey = "death_block_01";
    private int animationCounter = 0;
    private int counter = 1;
    public Deathblock(int x, int y, ObjectHandler handler,ObjectId id)
    {
        super(x, y, id);
        this.handler = handler;
        this.health = maxHealth;
        this.setSize(WIDTH, HEIGHT);
        this.setdy(1);
    }

    public void takeDamage(int damage)
    {
    }

    private void die()
    {
    }

    /*
    * Checks for intersection with bullets. If an intersection occurs, subtracts from health and removes bullet
    * from the object handler.
    * @param    none
    * @return   none
    * */
    public void tick()
    {
        this.setdy(0);
        animationCounter++;
        if(animationCounter == 15)
        {
            animationCounter = 0;
            this.setdy(this.getdy() + (2 * counter));
            counter = counter * -1;
        }
        this.setY(this.getY() + this.getdy());
    }



    public void render(Graphics g)
    {
        g.drawImage(handler.requestSprite(spriteKey), getX(), getY(), null);
    }

    @Override
    public Rectangle getBounds()
    {
        return new Rectangle((x+(WIDTH/2)-(WIDTH/4)),y+(HEIGHT/2),WIDTH/2, HEIGHT/2);
    }
    public Rectangle getBoundsRight()
    {
        return new Rectangle(x+WIDTH-5,y+7,5,HEIGHT-13);
    }
    public Rectangle getBoundsLeft()
    {
        return new Rectangle(x,y+7,5,HEIGHT-13);
    }
    public Rectangle getBoundsTop()
    {
        return new Rectangle((x+(WIDTH/2)-(WIDTH/4)),y,WIDTH/2,HEIGHT/2);
    }
}
