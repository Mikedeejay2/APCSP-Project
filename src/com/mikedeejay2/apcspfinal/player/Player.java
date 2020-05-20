package com.mikedeejay2.apcspfinal.player;

import com.mikedeejay2.apcspfinal.Main;
import com.mikedeejay2.apcspfinal.collision.AxisAlignedBB;
import com.mikedeejay2.apcspfinal.graphics.objects.Camera;
import com.mikedeejay2.apcspfinal.io.Input;
import com.mikedeejay2.apcspfinal.io.Window;
import org.joml.Vector3d;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;

public class Player
{
    private Camera camera;

    private Vector3f velocity;

    private final float REG_SPEED = 10;
    private final float JUMP_SPEED = 9.5f;

    private float speed = REG_SPEED;
    private float sensitivity = 0.3f;

    private boolean falling = false;

    public boolean jumping = false;

    private PlayerCollision playerGravity;

    private AxisAlignedBB aabb;

    String currentBlock = "stone";

    public Player(Camera camera)
    {
        this.camera = camera;
        this.velocity = new Vector3f(0, 0, 0);
        this.aabb = new AxisAlignedBB(-0.4f, -1.7f, -0.4f, 0.4f, 0.3f, 0.4f);
        this.playerGravity = new PlayerCollision(this);
    }

    public void update(float delta)
    {
        playerGravity.update(velocity, delta);
        camera.getRealPos().add(velocity);
        velocity.set(0, 0, 0);
    }

    public void input(float delta)
    {
//        if(Input.getScroll() != 0)
//        {
//            speed += Input.getScroll()*2000;
//            System.out.println(Input.getScroll());
//        }
//        if(Input.getKey(GLFW_KEY_LEFT_CONTROL))
//        {
//            speed = REG_SPEED;
//        }

        camera.getViewMatrix().positiveZ(camera.getForward()).negate().mul(speed * delta);
        camera.getViewMatrix().positiveX(camera.getRight()).mul(speed * delta);

        if(Input.getKey(GLFW_KEY_W))
        {
            velocity.add(camera.getForward().x, 0, camera.getForward().z);
        }
        if(Input.getKey(GLFW_KEY_S))
        {
            velocity.sub(camera.getForward().x, 0, camera.getForward().z);
        }
        if(Input.getKey(GLFW_KEY_D))
        {
            velocity.add(camera.getRight());
        }
        if(Input.getKey(GLFW_KEY_A))
        {
            velocity.sub(camera.getRight());
        }
        if(Input.getKey(GLFW_KEY_SPACE) && !jumping)
        {
            jumping = true;
        }
        if(jumping) velocity.y += (JUMP_SPEED * delta);















        if (camera.isMouseLocked())
        {
            camera.setDeltaPosX(Input.getMousePositionX() - (Window.getWindowWidth() / 2.0f));
            camera.setDeltaPosY(Input.getMousePositionY() - (Window.getWindowHeight() / 2.0f));

            boolean rotY = camera.getDeltaPosX() != 0;
            boolean rotX = camera.getDeltaPosY()!= 0;

            if (rotY)
            {
                camera.setYaw((float) (camera.getYaw() + camera.getDeltaPosX() * sensitivity));
                if(camera.getYaw() >= 360) camera.setYaw(0);
                if(camera.getYaw() < 0) camera.setYaw(360);
            }
            if (rotX)
            {
                camera.setPitch((float) (camera.getPitch() + camera.getDeltaPosY() * sensitivity));
                if(camera.getPitch() > 90) camera.setPitch(90);
                if(camera.getPitch() < -90) camera.setPitch(-90);
            }

            Input.setMousePosition(Window.getWindowWidth() / 2.0f, Window.getWindowHeight() / 2.0f);
        }
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

    public Vector3f getVelocity()
    {
        return velocity;
    }

    public float getREG_SPEED()
    {
        return REG_SPEED;
    }

    public float getJUMP_SPEED()
    {
        return JUMP_SPEED;
    }

    public float getSpeed()
    {
        return speed;
    }

    public float getSensitivity()
    {
        return sensitivity;
    }

    public boolean isFalling()
    {
        return falling;
    }

    public PlayerCollision getPlayerGravity()
    {
        return playerGravity;
    }

    public AxisAlignedBB getAabb()
    {
        return aabb;
    }

    public void removeVoxel(int x, int y, int z)
    {
        Main.getInstance().getWorld().removeVoxel(x, y, z);
    }

    public void addVoxel(Vector3f location)
    {
        Main.getInstance().getWorld().addVoxelRelative(currentBlock, location);
    }
}
