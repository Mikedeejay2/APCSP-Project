package com.mikedeejay2.voxel.engine.io;

import com.mikedeejay2.voxel.game.Main;
import org.joml.Vector2f;
import org.lwjgl.BufferUtils;

import java.nio.DoubleBuffer;

import static org.lwjgl.glfw.GLFW.*;

public class Input
{
    private static Main instance = Main.getInstance();

    private static boolean[] lastKeys = new boolean[GLFW_KEY_LAST];
    private static boolean[] lastMouse = new boolean[GLFW_MOUSE_BUTTON_LAST];

    private static double[] cursorX = new double[1];
    private static double[] cursorY = new double[1];

    public static void update(float delta)
    {
        for(int i = 0; i < GLFW_KEY_LAST; i++)
        {
            lastKeys[i] = getKey(i);
        }

        for(int i = 0; i < GLFW_MOUSE_BUTTON_LAST; i++)
        {
            lastMouse[i] = getMouse(i);
        }
    }


    public static boolean getKey(int keyCode)
    {
        if(keyCode > 31)
            if(glfwGetKey(instance.getWindow().getWindow(), keyCode) == 1)
                return true;
        return false;
    }

    public static boolean getKeyDown(int keyCode)
    {
        return getKey(keyCode) && !lastKeys[keyCode];
    }

    public static boolean getKeyUp(int keyCode)
    {
        return !getKey(keyCode) && lastKeys[keyCode];
    }

    public static boolean getMouse(int mouseButton)
    {
        if(glfwGetMouseButton(instance.getWindow().getWindow(), mouseButton) == 1)
            return true;
        return false;
    }

    public static boolean getMouseDown(int mouseButton)
    {
        return getMouse(mouseButton) && !lastMouse[mouseButton];
    }

    public static boolean getMouseUp(int mouseButton)
    {
        return !getMouse(mouseButton) && lastMouse[mouseButton];
    }

    public static Vector2f getMousePosition()
    {
        DoubleBuffer posX = BufferUtils.createDoubleBuffer(1);
        DoubleBuffer posY = BufferUtils.createDoubleBuffer(1);
        glfwGetCursorPos(instance.getWindow().getWindow(), posX, posY);
        return new Vector2f((float)posX.get(), (float)posY.get());
    }

    public static int getMousePositionX()
    {
        glfwGetCursorPos(instance.getWindow().getWindow(), cursorX, cursorY);
        return (int) cursorX[0];
    }

    public static int getMousePositionY()
    {
        glfwGetCursorPos(instance.getWindow().getWindow(), cursorX, cursorY);
        return (int) cursorY[0];
    }

    public static void setMousePosition(float x, float y)
    {
        glfwSetCursorPos(instance.getWindow().getWindow(), x, y);
    }

    public static void setCursor(boolean enabled)
    {
        if(enabled)
        {
            glfwSetInputMode(instance.getWindow().getWindow(), GLFW_CURSOR, GLFW_CURSOR_NORMAL);
        }
        else
            glfwSetInputMode(instance.getWindow().getWindow(), GLFW_CURSOR, GLFW_CURSOR_DISABLED);
    }
}
