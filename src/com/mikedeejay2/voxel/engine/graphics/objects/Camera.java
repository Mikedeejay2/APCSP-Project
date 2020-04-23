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

    private int deltaPosX;
    private int deltaPosY;

    private boolean mouseLocked;

    Vector3f forward;
    Vector3f right;

    private float speed = 10f;

    public Camera()
    {
        position = new Vector3f(0, 0, 0);
        viewMatrix = new Matrix4f();
        this.forward = new Vector3f();
        this.right = new Vector3f();
        mouseLocked = false;
    }

    public void input(float delta)
    {
        if(Input.getKey(GLFW_KEY_W)) position.z += -(speed * delta);
        if(Input.getKey(GLFW_KEY_S)) position.z += (speed * delta);
        if(Input.getKey(GLFW_KEY_D)) position.x += (speed * delta);
        if(Input.getKey(GLFW_KEY_A)) position.x += -(speed * delta);
        if(Input.getKey(GLFW_KEY_SPACE)) position.y += (speed * delta);
        if(Input.getKey(GLFW_KEY_LEFT_SHIFT)) position.y += -(speed * delta);

        if (Input.getKey(GLFW_KEY_ESCAPE))
        {
            Input.setCursor(true);
            mouseLocked = false;
        }
        if (Input.getMouseDown(0))
        {
            Input.setMousePosition(Window.getWidth() / 2, Window.getHeight() / 2);
            Input.setCursor(false);
            mouseLocked = true;
        }

        if (mouseLocked)
        {
            deltaPosX = Input.getMousePositionX().sub(Window.getWidth() / 2, Window.getHeight() / 2);
            deltaPosY = Input.getMousePosition().sub(Window.getWidth() / 2, Window.getHeight() / 2);

            boolean rotY = deltaPos.getX() != 0;
            boolean rotX = deltaPos.getY() != 0;

            if (rotY)
                getTransform().rotate(yAxis, (float) Math.toRadians(deltaPos.getX() * sensitivity));
            if (rotX)
                getTransform().rotate(getTransform().getRot().getRight(), (float) Math.toRadians(deltaPos.getY() * sensitivity));

            if (rotY || rotX)
                Input.setMousePosition(new Vector2f(Window.getWidth() / 2, Window.getHeight() / 2));
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
