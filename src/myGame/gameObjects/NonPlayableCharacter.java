package myGame.gameObjects;

import myGame.gameObjects.GameObject;
import myGame.gameObjects.ObjectId;

public abstract class NonPlayableCharacter extends GameObject
{
    public NonPlayableCharacter(int x, int y, ObjectId id)
    {
        super(x, y, id);
    }

    /*
    * To be implemented in the child classes.
    * Damagable objects will have their health subtracted,
    * healing objects will do negative damage,
    * invincible objects will have an empty function with no behavior
    * @param damage
    * @return none
    * */
    public abstract void takeDamage(int damage);
}
