package myGame.engine;

import javax.sound.sampled.*;
import java.io.File;
import java.util.HashMap;

public class SoundManager
{
    private static String workingDirectory = System.getProperty("user.dir");
    private static String MUSICPATH = workingDirectory + "\\PortalClone\\src\\sounds\\";
    private static HashMap soundPathMap;

    private static String[] keys = new String[]
            {"background_music",
                    "explosion",
                    "victory",
                    "tank_shot",
                    "booster_sound",
                    "drown",
                    "enter_portal_1",
                    "enter_portal_2",
                    "open_portal_1",
                    "open_portal_2",
                    "fire_blue",
                    "fire_red",
                    "taunt_01",
                    "taunt_02",
                    "taunt_03",
                    "taunt_04",
                    "taunt_05",
                    "welcome",
                    "game_over"
            };

    private static String[] paths = new String[]
            {"mp_coop_hallway_c4.wav",
                    "Explosion_large.wav",
                    "victory.wav",
                    "tank_shot.wav",
                    "boost_noise.wav",
                    "object_dissolve_in_goo_02.wav",
                    "portal_enter1.wav",
                    "portal_enter2.wav",
                    "portal_open_blue_01.wav",
                    "portal_open_red_01.wav",
                    "wpn_portal_gun_fire_blue_01.wav",
                    "wpn_portal_gun_fire_red_01.wav",
                    "taunt_01.wav",
                    "taunt_02.wav",
                    "taunt_03.wav",
                    "taunt_04.wav",
                    "taunt_05.wav",
                    "welcome.wav",
                    "game_over.wav"
            };
//C:\Users\Danielle Nunez\Documents\GitHub\secondgame-WithEspresso\PortalClone\src\sounds\gladosbattle_xfer06.wav
    static
    {
        System.out.println(workingDirectory);
        soundPathMap = new HashMap<String, String>();
        int size = keys.length;
        for(int i = 0; i < size; i++)
        {
            String fullPath = MUSICPATH + keys[i];
            soundPathMap.put(keys[i], paths[i]);
        }
    }

    /*
    * Given a key, will use that key to lookup a sound in the static HashMap
    * and play that sound. We're assuming that all keys exist in the hashmap
    * @param    The key corresponding to the desired sound
    * @reutrn   None, but a sound will be played on a new thread.
    * */
    public void playMusic(String key)
    {
        String fullPath = MUSICPATH + soundPathMap.get(key);
        new Thread(new Runnable()
        {
            public void run()
            {
                try
                {
                    Clip clip = AudioSystem.getClip();
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(fullPath));
                    clip.open(inputStream);
                    clip.start();
                } catch (Exception e)
                {
                    System.out.println("Error: Unable to play sound for: " + fullPath);
                }
            }
        }).start();
    }
}
