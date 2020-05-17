package com.mikedeejay2.apcspfinal.player;

import com.mikedeejay2.apcspfinal.Main;
import com.mikedeejay2.apcspfinal.world.World;
import org.joml.Vector3d;
import org.joml.Vector3f;

public class PlayerCollision
{
    private static final float offset = 0.1f;

    private Player player;

    private Main instance;
    private World currentWorld;

    private float fallSpeed = 0;

    private Vector3f velocity;

    public PlayerCollision(Player player)
    {
        this.player = player;
        instance = Main.getInstance();
        currentWorld = instance.getWorld();
    }

    public void update(Vector3f velocity, float delta)
    {
        this.velocity = velocity;
        if(collidesDown() && velocity.y < 0) velocity.y = 0;
        if(collidesUp() && velocity.y > 0) velocity.y = 0;
        if(collidesWest() && velocity.x > 0) velocity.x = 0;
        if(collidesEast() && velocity.x < 0) velocity.x = 0;
        if(collidesNorth() && velocity.z > 0) velocity.z = 0;
        if(collidesSouth() && velocity.z < 0) velocity.z = 0;
        if(!collidesDown())
        {
            fallSpeed -= 0.005f;
            velocity.y += fallSpeed*delta;
        }
        else
        {
            fallSpeed = 0;
            player.jumping = false;
        }
    }

    public boolean collidesDown()
    {
        Vector3d pos = new Vector3d(player.getPosition());
        pos.add(velocity);
        return
        currentWorld.isCollidableVoxelAtCoordinate(
                (int) Math.round(pos.x + player.getAabb().x1 + offset),
                (int) Math.round(pos.y + player.getAabb().y1),
                (int) Math.round(pos.z + player.getAabb().z1 + offset)) ||
        currentWorld.isCollidableVoxelAtCoordinate(
                (int) Math.round(pos.x + player.getAabb().x2 - offset),
                (int) Math.round(pos.y + player.getAabb().y1),
                (int) Math.round(pos.z + player.getAabb().z2 - offset)) ||
        currentWorld.isCollidableVoxelAtCoordinate(
                (int) Math.round(pos.x + player.getAabb().x1 + offset),
                (int) Math.round(pos.y + player.getAabb().y1),
                (int) Math.round(pos.z + player.getAabb().z2 - offset)) ||
        currentWorld.isCollidableVoxelAtCoordinate(
                (int) Math.round(pos.x + player.getAabb().x2 - offset),
                (int) Math.round(pos.y + player.getAabb().y1),
                (int) Math.round(pos.z + player.getAabb().z1 + offset));
    }

    public boolean collidesUp()
    {
        Vector3d pos = new Vector3d(player.getPosition());
        pos.add(velocity);
        return
        currentWorld.isCollidableVoxelAtCoordinate(
                (int) Math.round(pos.x + player.getAabb().x1 + offset),
                (int) Math.round(pos.y + player.getAabb().y2),
                (int) Math.round(pos.z + player.getAabb().z1 + offset)) ||
        currentWorld.isCollidableVoxelAtCoordinate(
                (int) Math.round(pos.x + player.getAabb().x2 - offset),
                (int) Math.round(pos.y + player.getAabb().y2),
                (int) Math.round(pos.z + player.getAabb().z2 - offset)) ||
        currentWorld.isCollidableVoxelAtCoordinate(
                (int) Math.round(pos.x + player.getAabb().x1 + offset),
                (int) Math.round(pos.y + player.getAabb().y2),
                (int) Math.round(pos.z + player.getAabb().z2 - offset)) ||
        currentWorld.isCollidableVoxelAtCoordinate(
                (int) Math.round(pos.x + player.getAabb().x2 - offset),
                (int) Math.round(pos.y + player.getAabb().y2),
                (int) Math.round(pos.z + player.getAabb().z1 + offset));
    }

    public boolean collidesWest()
    {
        Vector3d pos = new Vector3d(player.getPosition());
        pos.add(velocity);
        return
        currentWorld.isCollidableVoxelAtCoordinate(
                (int) Math.round(pos.x + player.getAabb().x2),
                (int) Math.round(pos.y),
                (int) Math.round(pos.z + player.getAabb().z1 + offset)) ||
        currentWorld.isCollidableVoxelAtCoordinate(
                (int) Math.round(pos.x + player.getAabb().x2),
                (int) Math.round(pos.y - 1),
                (int) Math.round(pos.z + player.getAabb().z1 + offset)) ||
        currentWorld.isCollidableVoxelAtCoordinate(
                (int) Math.round(pos.x + player.getAabb().x2),
                (int) Math.round(pos.y),
                (int) Math.round(pos.z + player.getAabb().z2 - offset)) ||
        currentWorld.isCollidableVoxelAtCoordinate(
                (int) Math.round(pos.x + player.getAabb().x2),
                (int) Math.round(pos.y - 1),
                (int) Math.round(pos.z + player.getAabb().z2 - offset));
    }

    public boolean collidesEast()
    {
        Vector3d pos = new Vector3d(player.getPosition());
        pos.add(velocity);
        return
        currentWorld.isCollidableVoxelAtCoordinate(
                (int) Math.round(pos.x + player.getAabb().x1),
                (int) Math.round(pos.y),
                (int) Math.round(pos.z + player.getAabb().z1 + offset)) ||
        currentWorld.isCollidableVoxelAtCoordinate(
                (int) Math.round(pos.x + player.getAabb().x1),
                (int) Math.round(pos.y - 1),
                (int) Math.round(pos.z + player.getAabb().z1 + offset)) ||
        currentWorld.isCollidableVoxelAtCoordinate(
                (int) Math.round(pos.x + player.getAabb().x1),
                (int) Math.round(pos.y),
                (int) Math.round(pos.z + player.getAabb().z2 - offset)) ||
        currentWorld.isCollidableVoxelAtCoordinate(
                (int) Math.round(pos.x + player.getAabb().x1),
                (int) Math.round(pos.y - 1),
                (int) Math.round(pos.z + player.getAabb().z2 - offset));
    }

    public boolean collidesNorth()
    {
        Vector3d pos = new Vector3d(player.getPosition());
        pos.add(velocity);
        return
        currentWorld.isCollidableVoxelAtCoordinate(
                (int) Math.round(pos.x + player.getAabb().x1 + offset),
                (int) Math.round(pos.y),
                (int) Math.round(pos.z + player.getAabb().z2)) ||
        currentWorld.isCollidableVoxelAtCoordinate(
                (int) Math.round(pos.x + player.getAabb().x1 + offset),
                (int) Math.round(pos.y - 1),
                (int) Math.round(pos.z + player.getAabb().z2)) ||
        currentWorld.isCollidableVoxelAtCoordinate(
                (int) Math.round(pos.x + player.getAabb().x2 - offset),
                (int) Math.round(pos.y),
                (int) Math.round(pos.z + player.getAabb().z2)) ||
        currentWorld.isCollidableVoxelAtCoordinate(
                (int) Math.round(pos.x + player.getAabb().x2 - offset),
                (int) Math.round(pos.y - 1),
                (int) Math.round(pos.z + player.getAabb().z2));
    }

    public boolean collidesSouth()
    {
        Vector3d pos = new Vector3d(player.getPosition());
        pos.add(velocity);
        return
        currentWorld.isCollidableVoxelAtCoordinate(
                (int) Math.round(pos.x + player.getAabb().x1 + offset),
                (int) Math.round(pos.y),
                (int) Math.round(pos.z + player.getAabb().z1)) ||
        currentWorld.isCollidableVoxelAtCoordinate(
                (int) Math.round(pos.x + player.getAabb().x1 + offset),
                (int) Math.round(pos.y - 1),
                (int) Math.round(pos.z + player.getAabb().z1)) ||
        currentWorld.isCollidableVoxelAtCoordinate(
                (int) Math.round(pos.x + player.getAabb().x2 - offset),
                (int) Math.round(pos.y),
                (int) Math.round(pos.z + player.getAabb().z1)) ||
        currentWorld.isCollidableVoxelAtCoordinate(
                (int) Math.round(pos.x + player.getAabb().x2 - offset),
                (int) Math.round(pos.y - 1),
                (int) Math.round(pos.z + player.getAabb().z1));
    }
}
