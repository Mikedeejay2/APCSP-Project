package com.mikedeejay2.voxel.game.world;

import com.mikedeejay2.voxel.game.Main;
import com.mikedeejay2.voxel.game.TimeLoop;
import org.joml.Vector3f;

public class World implements Runnable
{

    Main instance = Main.getInstance();
    Vector3f playerPosition;

    public World()
    {

    }

    @Override
    public void run()
    {
        while(true)
        {
            if(TimeLoop.shouldTick())
            {
                playerPosition = instance.getCamera().getPosition();
            }
        }
    }
}
