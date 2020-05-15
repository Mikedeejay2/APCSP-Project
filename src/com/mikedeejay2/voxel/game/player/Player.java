package com.mikedeejay2.voxel.game.player;

import com.mikedeejay2.voxel.engine.graphics.objects.Camera;
import org.joml.Vector3d;
import org.joml.Vector3f;

public class Player
{
    private Camera camera;

    public float walkSpeed = 20;

    private PlayerGravity playerGravity;

    public Player(Camera camera)
    {
        this.camera = camera;
        this.playerGravity = new PlayerGravity(this);
    }

    public void update(float delta)
    {
        playerGravity.update(delta);
    }

    public Vector3d getPosition()
    {
        return camera.getRealPos();
    }

    public Camera getCamera()
    {
        return camera;
    }

    public float getYaw()
    {
        return camera.getYaw();
    }

    public float getPitch()
    {
        return camera.getPitch();
    }
}
