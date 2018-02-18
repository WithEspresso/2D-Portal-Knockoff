package myGame.gameObjects;

import myGame.engine.Game;

import java.awt.*;

public class VictoryScreen extends GameObject
{
    private ObjectHandler handler;
    private String spriteKey;
    private final int WIDTH = 478;
    private final int HEIGHT = 342;
    private int animationCounter = 0;
    private boolean beginFloating = false;

    public VictoryScreen(int x, int y, ObjectId id, ObjectHandler handler, ObjectId losingPlayer)
    {
        super(x, y, id);
        this.handler = handler;
        if(losingPlayer == ObjectId.Tank1)
            spriteKey = "game_over";
        else
            spriteKey = "win_screen";
        this.setdy(2);
        this.setSize(0, 0);
    }

    @Override
    public void tick()
    {
        animationCounter++;
        this.setY(this.getY() + this.getdy());
        if(!beginFloating)
        {
            if (this.getY() > Game.HEIGHT / 4)
            {
                this.setdy(-1);
                beginFloating = true;
            }
        }
        else if(beginFloating)
        {
            if(animationCounter >= 30)
                animationCounter = 0;
            if(animationCounter >= 15)
                this.setdy(-1);
            else
                this.setdy(1);
        }
    }

    @Override
    public void render(Graphics g)
    {
        g.drawImage(handler.requestSprite(spriteKey), getX(), getY(), null);
    }

    @Override
    public void takeDamage(int damage) {

    }
}
