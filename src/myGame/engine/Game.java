package myGame.engine;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import myGame.gameObjects.*;
import myGame.theVisuals.*;

public class Game extends Canvas implements Runnable
{
    private boolean running = false;
    private Thread thread;
    public static int WIDTH = 1200;
    public static int HEIGHT = 900;
    public static int FLOOR = 690;
    public static int LEFTSIDE = 0;
    public static int RIGHTSIDE = 1050;
    private ObjectHandler handler;
    
    private static String workingDirectory = System.getProperty("user.dir");
    private final String MUSICPATH = workingDirectory + "\\PortalClone\\src\\myGame.audio\\terran1.wav";
    private final String BUFFEREDIMAGEPATH = workingDirectory + "\\PortalClone\\src\\images\\level.png";
    private BufferedImage level1 = null;
    private BufferedImage background =null;
    private Image dimg=null;
    private String soundKey = "background_music";

    /*
    * Starts the game if it isn't already running.
    * The main game runs on one thread.
    * */
    public synchronized void start()
    {
        if(running)
        {
            return;
        }
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    /*
    * Starts the game loop.
    * @param    none
    * @return   none
    * */
    @Override
    public void run() 
    {
        initialize();
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int updates = 0;
        int frames = 0;
        while (running) 
        {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) 
            {
                tick();
                updates++;
                delta--;
            }
            render();
            frames++;

            if (System.currentTimeMillis() - timer > 1000) 
            {
                timer += 1000;
                System.out.println("FPS:" + frames + " Ticks: " + updates);
                frames = 0;
                updates = 0;
            }
        }
    }

    /*
    * Initializes the components in the game, creates the handler, adds
    * the player objects to the handler, adds the keylistener to this game.
    * Used as a helper function in  Game.run();
    * @param    none
    * @return   none
    * */
    private void initialize()
    {
        WIDTH = getWidth();
        HEIGHT = getHeight();
        SoundManager soundManager = new SoundManager();
        SpriteManager spriteManager = new SpriteManager();
        BufferedImageLoader loader = new BufferedImageLoader();
        level1 = loader.loadImage(workingDirectory + "\\PortalClone\\src\\images\\level.png");
        background = loader.loadImage(workingDirectory + "\\PortalClone\\src\\images\\Background.png");

        soundManager.playMusic(soundKey);

        dimg = background.getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH);
        
        handler = new ObjectHandler(spriteManager, soundManager);
        //handler.addObject(new Player(300,600,handler, ObjectId.Tank1));
        handler.addObject(new HealthBars(0, 0, ObjectId.HealthBar, handler));
        loadImageLevel(level1);

        this.addKeyListener(new KeyInput(handler));
    }


    /*
    * TODO: ANDREW EXPLAIN PLS
    * */
    private void loadImageLevel(BufferedImage bi)
    {
        int w = bi.getWidth();
        int h = bi.getHeight();//3 offset from health bars

        GameObject glados = new Glados(0, 0, ObjectId.MiniMap, handler);
        handler.addObject(glados);
        
        //Maps pixels in png to objects in that corresponding space in the game
        for(int i = 0; i < h; i++)
        {
            for(int j = 0; j< w; j++)
            {
                int pixel = bi.getRGB(i,j);
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = (pixel) & 0xff;
                
                //invinciblewall white
                if(red == 255 && green == 255 && blue == 255)
                {
                    handler.addObject(new Block(i*32,(j*32)+50, ObjectId.Block, handler));
                }
                if(red == 0 && green == 0 && blue == 255)
                {
                    handler.addObject(new Player(i*32,j*32,handler, ObjectId.Tank1));
                }
                if(red == 0 && green == 255 && blue ==0)
                {
                    handler.addObject(new Deathblock(i*32,(j*32)+50, handler, ObjectId.Deadblock));
                }
                if(red == 255 && green == 255 && blue ==0)
                {
                    handler.addObject(new Goal(i*32,(j*32)+50, handler, ObjectId.Goal));
                }
            }
        }
        
    }

    /*
    *   Tick in the handler requires all objects existing in the game to update their position,
    *   current actions, etc. Render is called after to draw the tank.
    *   Ex: tick in the Player will require the object to update their position so it is within the
    *   bounds of the game, then render() will be call subsequenctly to draw the tank.
    *   @param      none
    *   @return     none
    * */
    private void tick()
    {
        handler.tick();
    }

    /*
    * Initializes the buffer strategy. Uses three buffers to render the game with.
    * @param    none
    * @return   none
    * */
    private void render()
    {
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null)
        {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        
        
        g.setColor(Color.black);
        g.fillRect(0,0,getWidth(),getHeight());
        g.drawImage(dimg, 0, 0, null);
        handler.render(g);
        g.dispose();
        bs.show();
    }

    /*
    *   Checks to see if an object is performing a legal movement.
    *   @param the current x/y coordinate of a moving object, their maximum bounds and their minimum bounds
    *   @return an x or y coordinate within the minimum or maximum bounds of the game
    * */
    public static int checkBounds(int position, int minimum, int maximum)
    {
        if(position >= maximum)
            return maximum;
        else if(position <= minimum)
            return minimum;
        else
            return position;
    }


    /*
    * Main function starts up the game.
    * @param    none
    * @return   Endless hours of enjoyment for the whole family
    * */
    public static void main(String args[])
    {
        new GameScreen(1200,900,"Tank Unknown Battle Grounds", new Game());
    }
}
