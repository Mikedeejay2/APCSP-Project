package com.mikedeejay2.voxel.engine.graphics.objects;

import com.mikedeejay2.voxel.engine.io.Input;
import com.mikedeejay2.voxel.engine.io.Window;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import static org.lwjgl.glfw.GLFW.*;

public class Camera
{
    private Vector3f position;
    private Matrix4f viewMatrix;
    private float pitch;
    private float yaw;
    private float roll;

    private double deltaPosX;
    private double deltaPosY;

    private boolean mouseLocked;

    Vector3f forward;
    Vector3f right;

    private final float SPEED_SPRINT_MULTIPLIER = 2;

    private float speed = 20;
    private float sensitivity = 0.3f;

    public Camera()
    {
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
        if(Input.getKey(GLFW_KEY_LEFT_CONTROL)) speed = 200;
        if(Input.getKey(GLFW_KEY_W)) position.add(forward.x, 0, forward.z);
        if(Input.getKey(GLFW_KEY_S)) position.sub(forward.x, 0, forward.z);
        if(Input.getKey(GLFW_KEY_D)) position.add(right);
        if(Input.getKey(GLFW_KEY_A)) position.sub(right);
        if(Input.getKey(GLFW_KEY_SPACE)) position.y += (speed * delta);
        if(Input.getKey(GLFW_KEY_LEFT_SHIFT)) position.y += -(speed * delta);

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

    public void update(float delta)
    {

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
}
