package myGame.gameObjects;

import myGame.theVisuals.SpriteManager;

import java.awt.*;

public class DeadTank extends GameObject
{
    private int animationCount = 0;
    private final int WIDTH = 256;
    private final int HEIGHT = 256;
    private ObjectHandler handler;

    public DeadTank(int x, int y, ObjectId id, ObjectHandler handler)
    {
        super(x, y, id);
        this.handler = handler;
        this.setSize(WIDTH, HEIGHT);
    }

    public void takeDamage(int damage)
    {
    }

    @Override
    public void tick()
    {
        //dead tanks don't move
    }

    @Override
    public void render(Graphics g)
    {
        if(animationCount < 10) {
            g.drawImage(handler.requestSprite("player_death_0"), getX(), getY(), null);
        }
        else
        {
            g.drawImage(handler.requestSprite("player_death_1"), getX(), getY(), null);
        }
        animationCount++;
        if(animationCount > 20)
            animationCount = 0;
    }
}
