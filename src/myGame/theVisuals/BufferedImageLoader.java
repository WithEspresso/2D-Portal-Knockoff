package myGame.theVisuals;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.File;


public class BufferedImageLoader 
{
    private BufferedImage image;
    
    public BufferedImage loadImage(String path)
    {
        try{
            image = ImageIO.read(new File(path));
        } catch (IOException e){
            e.printStackTrace();
        }
        return image;
    }
}
