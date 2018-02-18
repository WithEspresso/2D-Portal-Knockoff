package myGame.gameObjects;

import java.awt.*;

public class Glados extends GameObject
{
    private final String TAUNT_BASE = "taunt_";
    private String soundKey = TAUNT_BASE + "01";

    private final String WELCOME = "welcome";

    private ObjectHandler handler;
    private int tauntCounter = 0;
    private int tauntIndex = 1;

    public Glados(int x, int y, ObjectId id, ObjectHandler handler)
    {
        super(x, y, id);
        setSize(0, 0);
        this.handler = handler;
        handler.requestSound(WELCOME);
    }

    @Override
    public void tick()
    {

    }

    @Override
    public void render(Graphics g) {

    }

    @Override
    public void takeDamage(int damage) {

    }
}
