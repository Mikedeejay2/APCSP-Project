package com.mikedeejay2.voxel.game.player;

import com.mikedeejay2.voxel.game.Main;
import com.mikedeejay2.voxel.game.world.World;
import org.joml.Vector3d;

public class PlayerGravity
{
    Player player;

    private float currentVelocityY = 0;
    private float currentVelocityZ = 0;
    private float currentVelocityX = 0;

    private boolean isCollidingDown = false;
    private boolean isCollidingUp = false;
    private boolean isCollidingNorth = false;
    private boolean isCollidingSouth = false;
    private boolean isCollidingEast = false;
    private boolean isCollidingWest = false;

    private Main instance;
    private World currentWorld;

    public PlayerGravity(Player player)
    {
        this.player = player;
        instance = Main.getInstance();
        currentWorld = instance.getWorld();
    }

    public void update(float delta)
    {
        collisionCheck();
        updateVelocity();
        player.getPosition().add(currentVelocityX * delta, currentVelocityY *delta, currentVelocityZ * delta);
    }

    public void collisionCheck()
    {
        isCollidingDown = currentWorld.isVoxelAtCoordinate((int)Math.round(player.getPosition().x), (int)Math.round(player.getPosition().y-1.7f), (int)Math.round(player.getPosition().z));
        isCollidingUp = currentWorld.isVoxelAtCoordinate((int)Math.round(player.getPosition().x), (int)Math.round(player.getPosition().y+0.3f), (int)Math.round(player.getPosition().z));
        isCollidingWest = currentWorld.isVoxelAtCoordinate((int)Math.round(player.getPosition().x-0.1f), (int)Math.round(player.getPosition().y-1), (int)Math.round(player.getPosition().z));
        isCollidingEast = currentWorld.isVoxelAtCoordinate((int)Math.round(player.getPosition().x+0.1f), (int)Math.round(player.getPosition().y-1), (int)Math.round(player.getPosition().z));
        isCollidingNorth = currentWorld.isVoxelAtCoordinate((int)Math.round(player.getPosition().x), (int)Math.round(player.getPosition().y-1), (int)Math.round(player.getPosition().z+0.1f));
        isCollidingSouth = currentWorld.isVoxelAtCoordinate((int)Math.round(player.getPosition().x), (int)Math.round(player.getPosition().y-1), (int)Math.round(player.getPosition().z-0.1f));
    }

    public void updateVelocity()
    {
        if(isCollidingDown)
        {
            currentVelocityY = 0;
        }
        else
        {
            currentVelocityY -= 0.002f;
        }

        if(isCollidingUp)
        {
            currentVelocityY -= 0.05f;
        }
    }
}
