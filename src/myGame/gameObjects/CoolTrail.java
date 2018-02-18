package myGame.gameObjects;

import java.awt.*;

public class CoolTrail extends GameObject
{
    private ObjectHandler handler;
    private float trail_fade = 1;
    private Color trailColor;

    private int width;
    private int height;
    private double life = 0.1;


    public CoolTrail(int x, int y, ObjectId id, ObjectHandler handler, Color trailColor, int width, int height, double life)
    {
        super(x, y + 8, id);
        this.handler = handler;
        this.trailColor = trailColor;
        this.width = width;
        this.height = height;
    }

    @Override
    public void tick()
    {
        if (trail_fade > life)
            trail_fade -= life - 0.05;
        else
            handler.removeObject(this);
    }

    @Override
    public void render(Graphics g)
    {
        Graphics2D graphics2d = (Graphics2D) g;
        graphics2d.setComposite(disappear(trail_fade));

        graphics2d.setColor(this.trailColor);
        graphics2d.fillRect(getX(), getY(), width,height);

        graphics2d.setComposite(disappear(1));
    }

    public void takeDamage(int damage)
    {
    }

    private AlphaComposite disappear(double alpha)
    {
        int type = AlphaComposite.SRC_OVER;
        return (AlphaComposite.getInstance(type, (float)alpha));
    }
}
