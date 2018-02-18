/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myGame.gameObjects;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Rectangle;
import myGame.theVisuals.SpriteManager;

/**
 *
 * @author Methionine
 */
public class DestructableBlock extends Block
{
    private final int WIDTH = 32;
    private final int HEIGHT = 32;
    private final int maxHealth = 30;
    private int health;
    private ObjectHandler handler;
    private Color healthColor = Color.white;
    private String soundKey = "explosion";
    private String spriteKey = "destructable_block_1";

    public DestructableBlock(int x, int y, ObjectHandler handler,ObjectId id)
    {
        super(x, y, id, handler);
        this.handler = handler;
        this.health = maxHealth;
        this.setSize(WIDTH, HEIGHT);
    }
    
    public void takeDamage(int damage)
    {
        if(this.health - damage <= 0)
            die();
        else
            this.health -= damage;
        if(this.health < (int)(0.40 * maxHealth))
            this.spriteKey = "destructable_block_3";
        else if(this.health < (int)(0.70 * maxHealth))
            this.spriteKey = "destructable_block_2";
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
    }



    public void render(Graphics g)
    {
        g.setColor(this.healthColor);
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
