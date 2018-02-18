package myGame.gameObjects;

import java.awt.*;

public class PortalBullet extends GameObject {

    private Boolean isAlive = true;
    private ObjectHandler handler;
    private String bulletColor;
    private String spriteKey;
    private ObjectId parentTankId;

    private final String OPEN_BLUE = "open_portal_1";
    private final String OPEN_RED = "open_portal_2";
    private String soundKey;


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
    public PortalBullet(int x, int y, ObjectId id, String bulletColor, double angle, ObjectHandler handler) {
        super(x, y, id);
        this.bulletColor = bulletColor;
        this.angle = angle;
        setID(id);
        this.setSecondaryID(SecondaryID.Bullet);
        if (id == ObjectId.Player1Bullet) {
            this.spriteKey = "red_tank_bullet";
            this.parentTankId = ObjectId.Tank1;
            this.soundKey = OPEN_RED;
        } else {
            this.spriteKey = "blue_tank_bullet";
            this.parentTankId = ObjectId.Tank2;
            this.soundKey = OPEN_BLUE;
        }
        this.dx = (int) (this.bulletSpeed * Math.cos(angle));
        this.dy = (int) (this.bulletSpeed * Math.sin(angle));
        this.handler = handler;
        this.setSize(width, height);
        this.setIsAlive(true);
    }

    /*
    *
    * */
    @Override
    public void tick() {
        this.x += dx;
        this.y += dy;
        checkCollision();
    }

    public void takeDamage(int damage) {
    }

    private void resetPortal(ObjectId portalKey, int offset) 
    {
        boolean alreadyExists = false;
//        int offset = 10;
//        if(isLeftIntersect)
//        {
//            offset = -10;
//        }
        for (int j = 0; j < handler.object.size(); j++) 
        {
            
            GameObject tempObject = handler.object.get(j);
            if (tempObject.getId() == portalKey) 
            {
                tempObject.setX(x);
                tempObject.setY(y);
                alreadyExists = true;
            }
            
        }
        if(!alreadyExists)
        {
            GameObject newPortal;
            if(portalKey == ObjectId.PortalRed)
            {
                newPortal = new PortalRed(this.getX(), this.getY(), portalKey, handler, offset);
                newPortal.setX(x);
                newPortal.setY(y);
            }
            else
            {
                newPortal = new PortalBlue(this.getX(), this.getY(), portalKey, handler, offset);                
                newPortal.setX(x);
                newPortal.setY(y);
            }
            handler.addObject(newPortal);
        }
        handler.requestSound(soundKey);
    }

    private void checkCollision()
    {
        for (int i = 0; i < handler.object.size(); i++) 
        {
            GameObject tempObject = handler.object.get(i);
            if(tempObject.getId() != parentTankId && tempObject.getId() != this.getId())
            {
                if (tempObject.getId() == ObjectId.Block)
                {
                    if (this.getBounds().intersects(((Block)(tempObject)).getBoundsLeft()))
                    {
                        int offset = -32;
                        
                        if("red".equals(this.bulletColor))
                        {
                            resetPortal(ObjectId.PortalRed, offset);
                        }
                        else
                        {
                            resetPortal(ObjectId.PortalBlue, offset);
                        }
                        handler.removeObject(this);
                    }
                    else if (this.getBounds().intersects(((Block)(tempObject)).getBoundsRight()))
                    {
                        int offset = 32;
                        
                        if("red".equals(this.bulletColor))
                        {
                            resetPortal(ObjectId.PortalRed, offset);
                        }
                        else
                        {
                            resetPortal(ObjectId.PortalBlue, offset);
                        }
                        
                        handler.removeObject(this);
                    }
                        
                }
                if (this.getBounds().intersects(tempObject.getBounds()))
                {
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
