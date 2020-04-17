import org.lwjgl.glfw.GLFWVidMode;

import static org.lwjgl.glfw.GLFW.*;
import org.lwjgl.opengl.GL;

public class Main
{
    public static final int WIDTH = 1920;
    public static final int HEIGHT = 1080;

    public static void main(String[] args)
    {

        if(!glfwInit())
        {
            throw new IllegalStateException("Failed to initialize GLFW!");
        }

        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        long window = glfwCreateWindow(WIDTH, HEIGHT, "APCSP Project", 0, 0);
        if(window == 0)
        {
            throw new IllegalStateException("Failed to create window.");
        }

        GLFWVidMode videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());

        glfwSetWindowPos(window, (videoMode.width() - WIDTH) / 2, (videoMode.height() - HEIGHT) / 2);

        glfwShowWindow(window);

        glfwMakeContextCurrent(window);

        GL.createCapabilities();

        while(!glfwWindowShouldClose(window))
        {
            glfwPollEvents();
        }

        glfwTerminate();
    }
}
