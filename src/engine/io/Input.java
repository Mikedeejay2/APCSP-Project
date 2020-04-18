package engine.io;

import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWScrollCallback;

import static org.lwjgl.glfw.GLFW.*;

public class Input
{
    private static boolean[] keys = new boolean[GLFW_KEY_LAST];
    private static boolean[] buttons = new boolean[GLFW_MOUSE_BUTTON_LAST];
    private static double mouseX, mouseY;
    private static double scrollX, scrollY;

    private GLFWKeyCallback keyboard;
    private GLFWCursorPosCallback mouseMove;
    private GLFWMouseButtonCallback mouseButtons;
    private GLFWScrollCallback mouseScroll;

    public Input()
    {
        keyboard = new GLFWKeyCallback()
        {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods)
            {
                if(key >= 0 && key < GLFW_KEY_LAST)
                    keys[key] = (action != GLFW_RELEASE);
            }
        };
        mouseMove = new GLFWCursorPosCallback()
        {
            @Override
            public void invoke(long window, double xpos, double ypos)
            {
                mouseX = xpos;
                mouseY = ypos;
            }
        };
        mouseButtons = new GLFWMouseButtonCallback()
        {
            @Override
            public void invoke(long window, int button, int action, int mods)
            {
                if(button >= 0 && button < GLFW_MOUSE_BUTTON_LAST)
                buttons[button] = (action != GLFW_RELEASE);
            }
        };

        mouseScroll = new GLFWScrollCallback()
        {
            @Override
            public void invoke(long window, double xoffset, double yoffset)
            {
                scrollX += xoffset;
                scrollY += yoffset;
            }
        };
    }

    public static boolean isKeyDown(int key)
    {
        return keys[key];
    }

    public static boolean isButtonDown(int button)
    {
        return buttons[button];
    }

    public void destroy()
    {
        keyboard.free();
        mouseMove.free();
        mouseButtons.free();
        mouseScroll.free();
    }

    public static double getMouseX()
    {
        return mouseX;
    }

    public static double getMouseY()
    {
        return mouseY;
    }

    public static double getScrollX()
    {
        return scrollX;
    }

    public static double getScrollY()
    {
        return scrollY;
    }

    public GLFWKeyCallback getKeyboardCallback()
    {
        return keyboard;
    }

    public GLFWCursorPosCallback getMouseMoveCallback()
    {
        return mouseMove;
    }

    public GLFWMouseButtonCallback getMouseButtonCallback()
    {
        return mouseButtons;
    }

    public GLFWScrollCallback getMouseScrollCallback()
    {
        return mouseScroll;
    }
}
