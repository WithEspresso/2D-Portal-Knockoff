package myGame.gameObjects;

import java.awt.*;

public class Bullet extends GameObject
{
    private Boolean isAlive = true;
    private ObjectHandler handler;
    private Color bulletColor;
    private String spriteKey;
    private ObjectId parentTankId;


    private double angle;
    private double bulletLife = 0.2;
    private int dx = 10;
    private int dy = 0;
    private int width = 16;
    private int height = 16;

    private int bulletSpeed = 10;
    private final int damage = 10;

    /* Constructor.
    * Takes an angle in radians to determine the velocity of the bullet's x/y components.
    * */
    public Bullet(int x, int y, ObjectId id, Color bulletColor, double angle, ObjectHandler handler)
    {
        super(x, y, id);
        this.bulletColor = bulletColor;
        this.angle = angle;
        setID(id);
        this.setSecondaryID(SecondaryID.Bullet);
        if(id == ObjectId.Player1Bullet)
        {
            this.spriteKey = "red_tank_bullet";
            this.parentTankId = ObjectId.Tank1;
        }
        else {
            this.spriteKey = "blue_tank_bullet";
            this.parentTankId = ObjectId.Tank2;
        }
        this.dx = (int)(this.bulletSpeed * Math.cos(angle));
        this.dy = (int)(this.bulletSpeed * Math.sin(angle));
        this.handler = handler;
        this.setSize(width, height);
        this.setIsAlive(true);
    }

    /*
    *
    * */
    @Override
    public void tick()
    {
        this.x += dx;
        this.y += dy;
        checkCollision();
    }

    public void takeDamage(int damage)
    {
    }

    private void checkCollision()
    {
        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);
            if(tempObject.getId() != parentTankId && tempObject.getId() != this.getId())
            {
                if (tempObject.getId() == ObjectId.DestructableBlock)
                {
                    if (this.getBounds().intersects(((DestructableBlock)(tempObject)).getBoundsRight())
                            || this.getBounds().intersects(((DestructableBlock)(tempObject)).getBoundsLeft()))
                    {
                        tempObject.takeDamage(this.getDamage());
                        handler.removeObject(this);
                    }
                }
                if (this.getBounds().intersects(tempObject.getBounds()))
                {
                    System.out.println("tempObject = " + tempObject.getId());
                    System.out.println("bullet collision occured");
                    tempObject.takeDamage(this.getDamage());
                    handler.removeObject(this);
                }
            }
        }
    }

    @Override
    public void render(Graphics g)
    {
        if(isAlive)
        {
            g.drawImage(handler.requestSprite(spriteKey), getX(), getY(), null);
        }
    }


    public int getDamage()
    {
        return this.damage;
    }
}
