package com.mikedeejay2.apcspfinal.graphics.objects;

import com.mikedeejay2.apcspfinal.io.Input;
import com.mikedeejay2.apcspfinal.io.Window;
import org.joml.*;

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

    public Camera()
    {
        realPos = new Vector3d(-2540, 5, 6740);
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

        if (Input.getKey(GLFW_KEY_ESCAPE))
        {
            Input.setCursor(true);
            mouseLocked = false;
        }
        if (Input.getMouseDown(0))
        {
            Input.setMousePosition(Window.getWindowWidth() / 2.0f, Window.getWindowHeight() / 2.0f);
            Input.setCursor(false);
            mouseLocked = true;
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

    public void setPosition(Vector3f position)
    {
        this.position = position;
    }

    public void setRealPos(Vector3d realPos)
    {
        this.realPos = realPos;
    }

    public void setViewMatrix(Matrix4f viewMatrix)
    {
        this.viewMatrix = viewMatrix;
    }

    public void setPitch(float pitch)
    {
        this.pitch = pitch;
    }

    public void setYaw(float yaw)
    {
        this.yaw = yaw;
    }

    public void setRoll(float roll)
    {
        this.roll = roll;
    }

    public double getDeltaPosX()
    {
        return deltaPosX;
    }

    public void setDeltaPosX(double deltaPosX)
    {
        this.deltaPosX = deltaPosX;
    }

    public double getDeltaPosY()
    {
        return deltaPosY;
    }

    public void setDeltaPosY(double deltaPosY)
    {
        this.deltaPosY = deltaPosY;
    }

    public boolean isMouseLocked()
    {
        return mouseLocked;
    }

    public void setMouseLocked(boolean mouseLocked)
    {
        this.mouseLocked = mouseLocked;
    }

    public Vector3f getForward()
    {
        return forward;
    }

    public void setForward(Vector3f forward)
    {
        this.forward = forward;
    }

    public Vector3f getRight()
    {
        return right;
    }

    public void setRight(Vector3f right)
    {
        this.right = right;
    }
}
