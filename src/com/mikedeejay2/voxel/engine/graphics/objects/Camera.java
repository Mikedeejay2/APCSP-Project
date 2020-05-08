package com.mikedeejay2.voxel.engine.graphics.objects;

import com.mikedeejay2.voxel.engine.io.Input;
import com.mikedeejay2.voxel.engine.io.Window;
import org.joml.*;

import java.math.BigDecimal;

import static org.lwjgl.glfw.GLFW.*;

public class Camera
{
    private Vector3f position;

    private Vector3d realPos;

    private Matrix4f viewMatrix;
    private float pitch;
    private float yaw;
    private float roll;

    private double deltaPosX;
    private double deltaPosY;
    private boolean mouseLocked;

    Vector3f forward;
    Vector3f right;

    private final float REG_SPEED = 20;
    private final float REG_MULTIPLIER = 1.1f;

    private float multiplier = REG_MULTIPLIER;

    private float speed = REG_SPEED;
    private float sensitivity = 0.3f;

    public Camera()
    {
        realPos = new Vector3d(-2540, 50, 6740);
        position = new Vector3f(0, 0, 0);
        viewMatrix = new Matrix4f();
        this.forward = new Vector3f();
        this.right = new Vector3f();
        mouseLocked = false;
        pitch = 0;
        yaw = 0;
        roll = 0;
    }

    public void input(float delta)
    {
        viewMatrix.positiveZ(forward).negate().mul(speed * delta);
        viewMatrix.positiveX(right).mul(speed * delta);
        if(Input.getKey(GLFW_KEY_LEFT_CONTROL))
        {
            speed = REG_SPEED;
            multiplier = REG_MULTIPLIER;
        }
        if(Input.getKey(GLFW_KEY_W))
        {
            realPos.add(forward.x, 0, forward.z);
        }
        if(Input.getKey(GLFW_KEY_S))
        {
            realPos.sub(forward.x, 0, forward.z);
        }
        if(Input.getKey(GLFW_KEY_D))
        {
            realPos.add(right);
        }
        if(Input.getKey(GLFW_KEY_A))
        {
            realPos.sub(right);
        }
        if(Input.getKey(GLFW_KEY_SPACE))
        {
            realPos.y += (speed * delta);
        }
        if(Input.getKey(GLFW_KEY_LEFT_SHIFT))
        {
            realPos.y += -(speed * delta);
        }
        float oldSpeed = speed;
        speed += Input.getScroll()*multiplier;
        if(oldSpeed != speed)
        {
            multiplier *= REG_MULTIPLIER;
            if (speed < 0)
            {
                multiplier = REG_MULTIPLIER;
                speed = 0;
            }
        }

        if (Input.getKey(GLFW_KEY_ESCAPE))
        {
            Input.setCursor(true);
            mouseLocked = false;
        }
        if (Input.getMouseDown(0))
        {
            Input.setMousePosition(Window.getWidth() / 2.0f, Window.getHeight() / 2.0f);
            Input.setCursor(false);
            mouseLocked = true;
        }

        if (mouseLocked)
        {
            deltaPosX = Input.getMousePositionX() - (Window.getWidth() / 2.0f);
            deltaPosY = Input.getMousePositionY() - (Window.getHeight() / 2.0f);

            boolean rotY = deltaPosX != 0;
            boolean rotX = deltaPosY!= 0;

            if (rotY)
            {
                yaw += deltaPosX * sensitivity;
                if(yaw >= 360) yaw = 0;
                if(yaw < 0) yaw = 360;
            }
            if (rotX)
            {
                pitch += deltaPosY * sensitivity;
                if(pitch > 90) pitch = 90;
                if(pitch < -90) pitch = -90;
            }

            Input.setMousePosition(Window.getWidth() / 2.0f, Window.getHeight() / 2.0f);
        }
    }

    public Vector3f getPosition()
    {
        return position;
    }

    public float getPitch()
    {
        return pitch;
    }

    public float getYaw()
    {
        return yaw;
    }

    public float getRoll()
    {
        return roll;
    }

    public Matrix4f getViewMatrix()
    {
        return viewMatrix;
    }

    public Vector3d getRealPos()
    {
        return realPos;
    }
}
