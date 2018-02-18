package myGame.theVisuals;

import myGame.engine.Game;
import myGame.gameObjects.DestructableBlock;
import myGame.gameObjects.ObjectHandler;
import myGame.gameObjects.GameObject;
import myGame.gameObjects.ObjectId;

import java.util.ArrayList;

import java.awt.*;

public class MiniMap extends GameObject
{
    ObjectHandler objectHandler;
    private int WIDTH = (int)(Game.WIDTH * 0.20);
    private int HEIGHT = (int)(Game.HEIGHT * 0.20);
    private String spriteKey = "minimap_overlay";

    public MiniMap(int x, int y, ObjectId id, ObjectHandler objectHandler)
    {
        super(x, y, id);
        this.objectHandler = objectHandler;
        this.setSize(WIDTH, HEIGHT);
        blocks = new ArrayList<DestructableBlock>();
    }

    private int tank1x;
    private int tank1y;
    private int tank2x;
    private int tank2y;

    private ArrayList blocks;


    @Override
    public void tick()
    {
        populateMiniMap();
    }

    private void populateMiniMap()
    {
        blocks.clear();
        GameObject temp;
        for(int i = 0; i < objectHandler.getSize(); i++)
        {
            temp = objectHandler.getObject(i);
            if(temp.getId() == ObjectId.Tank1)
            {
                this.tank1x = getX() + (int)(temp.getX()*0.20);
                this.tank1y = getY() + (int)(temp.getY()*0.20);
            }
            if(temp.getId() == ObjectId.Tank2)
            {
                this.tank2x = getX() + (int)(temp.getX()*0.20);
                this.tank2y = getY() + (int)(temp.getY()*0.20);
            }
            if(temp.getId() == ObjectId.DestructableBlock)
            {
                blocks.add(temp);
            }
        }
    }
    public void takeDamage(int damage)
    {
    }

    @Override
    public void render(Graphics g)
    {
        g.setColor(new Color(255, 255, 255, 80));
        g.fillRect(getX(), getY(), (int)(WIDTH), (int)(HEIGHT));
        g.drawImage(objectHandler.requestSprite(spriteKey), getX(), getY(), null);
        g.setColor(Color.RED);
        g.fillRect(tank1x, tank1y + 20, 10, 10);
        g.setColor(Color.blue);
        g.fillRect(tank2x, tank2y + 20, 10, 10);

        g.setColor(Color.cyan);
        GameObject temp;
        for(int i = 0; i < blocks.size(); i++)
        {
            temp = (GameObject)blocks.get(i);
            g.fillRect(this.getX() + (int)(temp.getX() * 0.20), this.getY() + (int)(temp.getY() * 0.20), 7, 7);
        }
    }
}
