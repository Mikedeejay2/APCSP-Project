package com.mikedeejay2.voxel.engine.io;

import com.mikedeejay2.voxel.game.Main;
import org.joml.Vector2f;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWScrollCallback;

import java.nio.DoubleBuffer;
import java.util.Arrays;

import static org.lwjgl.glfw.GLFW.*;

public class Input
{
    private static Main instance = Main.getInstance();

    private static int[] keysLast = new int[GLFW_KEY_LAST];
    private static int[] keys = new int[GLFW_KEY_LAST];
    private static int[] mouseLast = new int[GLFW_MOUSE_BUTTON_LAST];
    private static int[] mouse = new int[GLFW_MOUSE_BUTTON_LAST];
    private static double cursorX = 0;
    private static double cursorY = 0;
    private static double scroll = 0;

    private static long window = instance.getWindow().getWindow();

    public static void init()
    {
        glfwSetKeyCallback(window, new GLFWKeyCallback()
        {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods)
            {
                if(key >= 0)
                {
                    keysLast[key] = keys[key];
                    keys[key] = action;
                }
            }
        });
        glfwSetMouseButtonCallback(window, new GLFWMouseButtonCallback()
        {
            @Override
            public void invoke(long window, int button, int action, int mods)
            {
                mouseLast[button] = mouse[button];
                mouse[button] = action;
            }
        });
        glfwSetCursorPosCallback(window, new GLFWCursorPosCallback()
        {
            @Override
            public void invoke(long window, double xpos, double ypos)
            {
                cursorX = xpos;
                cursorY = ypos;
            }
        });
        glfwSetScrollCallback(window, new GLFWScrollCallback()
        {
            @Override
            public void invoke(long window, double xoffset, double yoffset)
            {
                scroll = yoffset;
            }
        });
    }

    public static void update(float delta)
    {
        keysLast = Arrays.copyOf(keys, keys.length);
        mouseLast = Arrays.copyOf(mouse, mouse.length);
    }


    public static boolean getKey(int keyCode)
    {
        if(keyCode > 31)
            if(keys[keyCode] == 1 || keys[keyCode] == 2)
                return true;
        return false;
    }

    public static boolean getLastKey(int keyCode)
    {
        if(keyCode > 31)
            if(keysLast[keyCode] == 1 || keysLast[keyCode] == 2)
                return true;
        return false;
    }

    public static boolean getKeyDown(int keyCode)
    {
        if(keys[keyCode] == 1 && keysLast[keyCode] == 0)
        {
            return true;
        }
        return false;
    }

    public static boolean getKeyUp(int keyCode)
    {
        if(keysLast[keyCode] == 2 && keys[keyCode] == 1)
        {
            return true;
        }
        return false;
    }

    public static boolean getMouse(int mouseButton)
    {
        if(mouse[mouseButton] == 1)
            return true;
        return false;
    }

    public static boolean getLastMouse(int mouseButton)
    {
        if(mouseLast[mouseButton] == 1)
            return true;
        return false;
    }

    public static boolean getMouseDown(int mouseButton)
    {
        if(mouse[mouseButton] == 1 && mouseLast[mouseButton] == 0)
        {
            return true;
        }
        return false;
    }

    public static boolean getMouseUp(int mouseButton)
    {
        if(mouseLast[mouseButton] == 1 && mouse[mouseButton] == 0)
        {
            return true;
        }
        return false;
    }

    public static Vector2f getMousePosition()
    {
        DoubleBuffer posX = BufferUtils.createDoubleBuffer(1);
        DoubleBuffer posY = BufferUtils.createDoubleBuffer(1);
        glfwGetCursorPos(instance.getWindow().getWindow(), posX, posY);
        return new Vector2f((float)posX.get(), (float)posY.get());
    }

    public static double getMousePositionX()
    {
        return cursorX;
    }

    public static double getMousePositionY()
    {
        return cursorY;
    }

    public static void setMousePosition(float x, float y)
    {
        glfwSetCursorPos(instance.getWindow().getWindow(), x, y);
        cursorX = x;
        cursorY = y;
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

    public static double getScroll()
    {
        double tempScroll = scroll;
        scroll = 0;
        return tempScroll;
    }
}
