package myGame.gameObjects;

import java.awt.Color;
import java.awt.Image;

import myGame.engine.Game;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import static myGame.engine.Game.checkBounds;

public class Player extends GameObject
{
    private int width = 32;
    private int height = 32;

    private Boolean isFacingLeft = false;
    private boolean lastWasLeft = false;

    private int shoot_cooldown = 0;
    private int jump_cooldown = 0;
    private int STARTING_X = 160;
    private int STARTING_Y = 658;

    private int GRAVITY = 2;
    private final int MAX_SPEED = 5;
    private final int MAX_HEALTH = 5;

    //BEGIN SOUND KEYS
    private final String SHOOT_RED = "fire_red";
    private final String SHOOT_BLUE = "fire_blue";
    private final String DYING_NOISE = "drown";
    private final String BOOSTER_SOUND = "booster_sound";
    private final String TELEPORT_BLUE = "enter_portal_1";
    private final String TELEPORT_RED = "enter_portal_2";
    private final String VICTORY_SOUND = "victory";
    private final String RUNNING_RIGHT = "running_right_";
    private final String RUNNING_LEFT = "running_left_";
    private final String STANDING_LEFT = "standing_left_";
    private final String STANDING_RIGHT = "standing_right_";
    private final String GAME_OVER = "game_over";
    private String death_taunt = "taunt_01";
    private int standing_counter = 1;
    private int running_counter = 1;
    private int tauntIndex = 1;
    private int countDown = 10;
    private boolean beginCountdown = false;


    private String standing_image_key = STANDING_RIGHT + "01";
    private String running_image_key = RUNNING_RIGHT + "01";

    private int health = MAX_HEALTH;
    private double angle = 0;

    private ObjectId boosterId;
    private ObjectHandler handler;
    private ObjectId bulletId;
    private Color playerColor;
    private boolean portalsInitialized = false;
    private boolean redShot, blueShot = false;



    public Player(int x, int y, ObjectHandler handler, ObjectId id)
    {
        super(x, y, id);
        this.handler = handler;
        this.playerColor = Color.red;

        this.bulletId = ObjectId.Player1Bullet;
        this.boosterId = ObjectId.RedBooster;

        setSize(width, height);
        setSecondaryID(SecondaryID.Player);
    }

    /*
    * Changes the angle of the cannon. Up/down keys increment it by Pi/12.
    * @param    Angle in radians
    * @return   none
    * */
    public void setAngle(double dr)
    {
        angle = angle + dr;

    }


    /*
    * Checks to see if the bullet cooldown has expired. If the cooldown is done, allows the player to shoot a bullet
    * and resets the cooldowm.
    * @param    none
    * @return   none
    * */
    public void shootCheck(String color)
    {

        if(shoot_cooldown < 1)
        {
            shootBullet(color);
            if(color == "red")
            {
                handler.requestSound(SHOOT_RED);
                this.redShot = true;
            }
            else
            {
                handler.requestSound(SHOOT_BLUE);
                this.blueShot = true;
            }
            shoot_cooldown = 15;
        }
    }

    /*
    *   Checks to see if the player is currently facing left or right, then
    *   generates a bullet in front of the player. Adds a PI to the angle
    *   if the player is facing left and was previously facing right.
    *   @param  none
    *   @return none
    * */
    private void shootBullet(String color)
    {
        int rightShootOffset = 0;
        if (isFacingLeft && !lastWasLeft || !isFacingLeft && lastWasLeft)
        {
            adjustCannonAngleForTurningAround();
            lastWasLeft = !(lastWasLeft);
        }
        GameObject bullet = new PortalBullet(getX() + rightShootOffset, getY(), this.bulletId, color, angle, handler);
        handler.addObject(bullet);


    }

    private void adjustCannonAngleForTurningAround()
    {
        angle = Math.PI - angle;
    }

    /*
    *   Sets whether or not the tank is facing left or right.
    *   @param  A boolean value to indicate if the tank is facing left if true.
    *   @return none
    * */
    public void setFacingLeft(boolean isFacingLeft)
    {
        this.isFacingLeft = isFacingLeft;
    }

    /*
    *   Called whenever a collision with a bullet occurs. Subtracts
    *   the damage from the current health. If taking damage would result in death,
    *   calls the die function to remove this tank player from the game.
    *   @param  An integer value of damage to occur
    *   @return none
    * */

    /*
    *   Used by the health bars, Returns the current health of the tank as a percentage.
    *   @param  none
    *   @return the current health of the tank in integer
    * */
    public int getLives()
    {
        return this.health;
    }

    /*
    *   Removes this player object from the game, creates a new DeadTank object
    *   at this object's last coordinates. Plays a fanfare noise as well to
    *   celebrate the fact that life is short and you should cherish your loved ones.
    *   @param  none
    *   @return none
    * */
    private void die()
    {
        handler.requestSound(DYING_NOISE);
        if(health > 0)
        {
            this.setX(STARTING_X);
            this.setY(STARTING_Y);
            startCountdown();
            health = health -1;
        }
        else
        {
            handler.removeObject(this);
            handler.requestSound(GAME_OVER);
            handler.addObject(new VictoryScreen(Game.WIDTH/4,0,ObjectId.VictoryScreen, handler, getId() ));
        }
    }

    public void laughAtChell()
    {
        handler.requestSound(death_taunt);
        System.out.println("PLAYING TAUNT: " + tauntIndex);
        tauntIndex++;
        if(tauntIndex > 5)
            tauntIndex = 1;
        death_taunt = "taunt_" + "0" + tauntIndex;
        this.beginCountdown = false;
    }

    private void startCountdown()
    {
        this.beginCountdown = true;
        this.countDown = 30;
    }

    private void win()
    {
        handler.removeObject(this);
        handler.requestSound(VICTORY_SOUND);
        handler.addObject(new VictoryScreen(Game.WIDTH/4,0,ObjectId.VictoryScreen, handler, getId() ));
    }

    @Override
    public void tick()
    {
        //System.out.println("Player X: " + this.x);
        //System.out.println("Player Y: " + this.y);

        shoot_cooldown--;
        jump_cooldown--;
        countDown--;
        if(beginCountdown && countDown == 0)
            laughAtChell();

        if(dy == -20)
        {
            if(jump_cooldown < 0)
            {
                jump_cooldown = 20;
                handler.requestSound(BOOSTER_SOUND);
            }
            else
                setdy(0);
        }

        this.x = checkBounds((this.x + dx), 0, Game.RIGHTSIDE+32);
        this.y = checkBounds((this.y + dy), 0, Game.FLOOR);

        if(falling || jumping)
        {
            dy += GRAVITY;
            if(dy > MAX_SPEED)
            {
                dy = MAX_SPEED;
            }
        }
        if(redShot && blueShot)
        {
            portalsInitialized = true;
        }
        checkCollision();
    }

    public void setPortalsInitialized(boolean inPortal)
    {
        this.portalsInitialized = inPortal;
    }

    public boolean getPortalsInitialized()
    {
        return this.portalsInitialized;
    }


    /*
    * Iterates through all of the gameobjects in the handler.
    * If a block or destructable block is found, stops the tank from moving.
    * If a bullet is found, the tank takes damage and the bullet is removed from the
    * object handler.
    * @param    none
    * @return   none
    * */
    private void checkCollision()
    {
        int redX=0;
        int redY=0;
        int blueX=0;
        int blueY=0;

        redX = handler.getPortalRed().getX() + ((PortalRed)(handler.getPortalRed())).getOffset();
        redY = handler.getPortalRed().getY();
        blueX = handler.getPortalBlue().getX() + ((PortalBlue)(handler.getPortalBlue())).getOffset();
        blueY = handler.getPortalBlue().getY();

        for (int i = 0; i < handler.object.size(); i++)
        {
            GameObject tempObject = handler.object.get(i);

            if (tempObject.getId() == ObjectId.Block)
            {
                if (getBoundsTop().intersects(tempObject.getBounds())) {
                    y = tempObject.getY() + (height / 2) +16;
                    dy = 0;

                }
                if (getBounds().intersects(tempObject.getBounds())) {
                    y = tempObject.getY() - height;
                    dy = 0;

                    falling = false;
                    jumping = false;
                } else {
                    falling = true;
                }
                if (getBoundsRight().intersects(tempObject.getBounds())) {
                    x = tempObject.getX() - (width / 2) -16;

                }
                if (getBoundsLeft().intersects(tempObject.getBounds())) {
                    x = tempObject.getX() + (width / 2) +16;
                }
            }
            if (tempObject.getId() == ObjectId.Deadblock)
            {
                if (getBoundsTop().intersects(tempObject.getBounds()))
                {
                    this.die();
                }
                if (getBounds().intersects(tempObject.getBounds())) {
                    this.die();
                }
                if (getBoundsRight().intersects(tempObject.getBounds())) {
                    this.die();
                }
                if (getBoundsLeft().intersects(tempObject.getBounds())) {
                    this.die();
                }
            }
            if (tempObject.getId() == ObjectId.Goal)
            {
                if (getBoundsTop().intersects(tempObject.getBounds()))
                {
                    this.win();
                }
                if (getBounds().intersects(tempObject.getBounds())) {
                    this.win();
                }
                if (getBoundsRight().intersects(tempObject.getBounds())) {
                    this.win();
                }
                if (getBoundsLeft().intersects(tempObject.getBounds())) {
                    this.win();
                }
            }
            
            if (tempObject.getId() == ObjectId.PortalBlue && portalsInitialized)
            {
                if (getBoundsTop().intersects(tempObject.getBounds())
                        && getBounds().intersects(tempObject.getBounds())
                        && getBoundsRight().intersects(tempObject.getBounds())
                        && getBoundsLeft().intersects(tempObject.getBounds()))
                {
                    handler.requestSound(TELEPORT_BLUE);
                    this.x = redX ;
                    this.y = redY;
                }
            }
            if (tempObject.getId() == ObjectId.PortalRed && portalsInitialized)
            {
                if (getBoundsTop().intersects(tempObject.getBounds())
                        && getBounds().intersects(tempObject.getBounds())
                        && getBoundsRight().intersects(tempObject.getBounds())
                        && getBoundsLeft().intersects(tempObject.getBounds()))
                {
                    handler.requestSound(TELEPORT_RED);
                    this.x = blueX;
                    this.y = blueY;
                }
            }
        }
    }

    /*
    *   Determines the current state of the tank and draws the correct image accordingly at the
    *   current coordinates.
    *
    * @param    A Graphics object to draw with
    * @return   none
    * */
    @Override
    public void render(Graphics g)
    {

        Graphics2D g2d = (Graphics2D) g;

//        g2d.draw(getBounds());
//        g2d.draw(getBoundsRight());
//        g2d.draw(getBoundsTop());
//        g2d.draw(getBoundsLeft());

        if (this.getdx() == 0)
        {
            g2d.drawImage(getNextStandingImage(), this.getX(), getY(), null);
        }
        else
            g2d.drawImage(getNextRunningImage(), this.getX(), getY(), null);
    }

    private Image getNextStandingImage()
    {
        if(isFacingLeft)
            this.standing_image_key = STANDING_LEFT + getStandingCounter();
        else
            this.standing_image_key = STANDING_RIGHT + getStandingCounter();
        return handler.requestSprite(standing_image_key);
    }

    private Image getNextRunningImage()
    {
        if(isFacingLeft)
            this.running_image_key = RUNNING_LEFT + getRunningCounter();
        else
            this.running_image_key = RUNNING_RIGHT + getRunningCounter();
        return handler.requestSprite(running_image_key);
    }

    private String getStandingCounter()
    {
        return "0" + standing_counter;
    }

    private String getRunningCounter()
    {
        running_counter++;
        if(running_counter > 63)
        {
            running_counter = 1;
        }
        return "0" + running_counter / 9;
    }



    /*
    * Returns the objectId of this GameObject
    * @param    none
    * @return   The objectId of this GameObject
    * */
    public ObjectId getId()
    {
        return id;
    }

    @Override
    public void takeDamage(int damage) 
    {
        
    }

    @Override
    public Rectangle getBounds()
    {
        return new Rectangle((x+(width/2)-((width/2)/2)),y+(height/2),(width/2)+3,(height/2)+3);
    }
    public Rectangle getBoundsRight()
    {
        return new Rectangle(x+(width-5),y+7,10,height-13);
    }
    public Rectangle getBoundsLeft()
    {
        return new Rectangle(x,y+7,10,height-13);
    }
    public Rectangle getBoundsTop()
    {
        return new Rectangle((x+(width/2)-((width/2)/2)),y,(width/2)+3,height/2);
    }
}