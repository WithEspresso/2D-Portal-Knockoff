package myGame.theVisuals;

import myGame.engine.Game;
import myGame.gameObjects.ObjectHandler;
import myGame.gameObjects.Player;
import myGame.gameObjects.GameObject;
import myGame.gameObjects.ObjectId;

import java.awt.*;

public class HealthBars extends GameObject
{
    private int lives = 5;
    private String spriteKey = "life";

    private final int WIDTH = (int)(Game.WIDTH * 0.90);
    private final int HEIGHT = (int)(Game.HEIGHT * 0.10);

    private ObjectHandler objectHandler;

    public HealthBars(int x, int y, ObjectId id, ObjectHandler objectHandler)
    {
        super(x, y, id);
        this.objectHandler = objectHandler;
        this.setSize(WIDTH, HEIGHT);
    }

    @Override
    public void tick()
    {
        getHealthInfo();
    }

    private void getHealthInfo()
    {
        GameObject temp;

        for(int i = 0; i < objectHandler.getSize(); i++)
        {
            temp = objectHandler.getObject(i);
            if(temp.getId() == ObjectId.Tank1)
            {
                this.lives = ((Player)(temp)).getLives();
            }

        }
    }

    public void takeDamage(int damage)
    {
    }

    @Override
    public void render(Graphics g)
    {
        for(int i = 0; i < lives; i++)
        {
            int j = (i * 50);
            g.drawImage(objectHandler.requestSprite(spriteKey), this.getX() + j, this.getY(), null);
        }
    }
}