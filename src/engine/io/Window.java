package engine.io;

import org.lwjgl.glfw.GLFWVidMode;

import static org.lwjgl.glfw.GLFW.*;

public class Window
{
    private int width, height;
    private String title;
    private long window;
    public static int frames;
    public static long time;
    public Input input;

    public Window(int width, int height, String title)
    {
        this.width = width;
        this.height = height;
        this.title = title;
        time = System.currentTimeMillis();
    }

    public void create()
    {
        if(!glfwInit())
        {
            System.err.println("ERROR: Could not initialize GLFW!");
            return;
        }

        input = new Input();
        window = glfwCreateWindow(width, height, title, 0, 0);

        if(window == 0)
        {
            System.err.println("ERROR: Window could not be created.");
            return;
        }

        GLFWVidMode videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(window, (videoMode.width() - width) / 2, (videoMode.height() - height) / 2);
        glfwMakeContextCurrent(window);

        glfwSetKeyCallback(window, input.getKeyboardCallback());
        glfwSetCursorPosCallback(window, input.getMouseMoveCallback());
        glfwSetMouseButtonCallback(window, input.getMouseButtonCallback());

        glfwShowWindow(window);

        glfwSwapInterval(1);
    }

    public void update()
    {
        glfwPollEvents();
        frames++;
        if(System.currentTimeMillis() > time + 1000)
        {
            glfwSetWindowTitle(window, title + " | FPS: " + frames);
            time = System.currentTimeMillis();
            frames = 0;
        }
    }

    public void swapBuffers()
    {
        glfwSwapBuffers(window);
    }

    public boolean shouldClose()
    {
        return glfwWindowShouldClose(window);
    }

    public void destroy()
    {
        input.destroy();
        glfwWindowShouldClose(window);
        glfwDestroyWindow(window);
        glfwTerminate();
    }
}
