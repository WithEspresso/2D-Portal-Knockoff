package myGame.gameObjects;

import myGame.theVisuals.SpriteManager;

import java.awt.*;

public class Background extends GameObject
{
    private ObjectHandler handler;
    private int animationCount = 0;
    private String prefix = "frame_";
    private String suffix = "00";
    private String spriteKey = prefix + suffix;

    public Background(int x, int y, ObjectHandler handler, ObjectId id)
    {
        super(x, y, id);
        this.handler = handler;
    }

    @Override
    public void tick()
    {
        animationCount++;
        setSuffix();

    }

    public void takeDamage(int damage)
    {
    }

    private void setSuffix()
    {
        String temp = "";
        if(animationCount > 47)
            animationCount = 0;
        if(animationCount < 10)
            temp = "0" + animationCount;
        else
            temp = "" + animationCount;
        this.suffix = new String(temp);
        //System.out.print(suffix);
    }

    @Override
    public void render(Graphics g)
    {
        g.drawImage(handler.requestSprite(spriteKey), getX(), getY(), null);
    }
}
