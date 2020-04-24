package com.mikedeejay2.voxel.engine.io;

import com.mikedeejay2.voxel.game.Main;
import org.lwjgl.glfw.GLFWVidMode;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL.*;
import static org.lwjgl.opengl.GL11.*;

public class Window
{
    private Main instance = Main.getInstance();
    private static int width, height;
    private String title;
    private long window;
    public static int frames;
    public static long time;
    private boolean isResized;
    private boolean isFullscreen;
    private int[] windowPosX = new int[1], windowPosY = new int[1];
    private static int FPS;
    private static int oldFPS;

    public Window(int width, int height, String title)
    {
        System.out.println("Window");
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

        window = glfwCreateWindow(width, height, title, isFullscreen ? glfwGetPrimaryMonitor() : 0, 0);

        if(window == 0)
        {
            System.err.println("ERROR: Window could not be created.");
            return;
        }

        GLFWVidMode videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        windowPosX[0] = (videoMode.width() - width) / 2;
        windowPosY[0] = (videoMode.height() - height) / 2;
        glfwSetWindowPos(window, (videoMode.width() - width) / 2, (videoMode.height() - height) / 2);
        glfwMakeContextCurrent(window);
        createCapabilities();
        glEnable(GL_DEPTH_TEST);

        glfwShowWindow(window);

        //glfwSwapInterval(2);
    }

    public void update()
    {
        if(isResized)
        {
            glViewport(0, 0, width, height);
            isResized = false;
        }

        instance.getRenderer().prepare();

        instance.render();
        glfwPollEvents();

        frames++;
        if(System.currentTimeMillis() > time + 1000)
        {
            glfwSetWindowTitle(window, title + " | FPS: " + frames);
            time = System.currentTimeMillis();
            FPS = frames;
            frames = 0;
        }
        swapBuffers();
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
        //instance.shader.destroy();
        glfwWindowShouldClose(window);
        glfwDestroyWindow(window);
        glfwTerminate();
    }

    public static int getWidth()
    {
        return width;
    }

    public void setWidth(int width)
    {
        this.width = width;
    }

    public static int getHeight()
    {
        return height;
    }

    public void setHeight(int height)
    {
        this.height = height;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public long getWindow()
    {
        return window;
    }

    public void setWindow(long window)
    {
        this.window = window;
    }

    public boolean isFullscreen()
    {
        return isFullscreen;
    }

    public void setFullscreen(boolean fullscreen)
    {
        isFullscreen = fullscreen;
        isResized = true;
        if(isFullscreen)
        {
            glfwGetWindowPos(window, windowPosX, windowPosY);
            glfwSetWindowMonitor(window, glfwGetPrimaryMonitor(), 0, 0, width, height, 0);
        }
        else
        {
            glfwSetWindowMonitor(window, 0, windowPosX[0], windowPosY[0], width, height,0 );
        }
    }

    public static int getFrames()
    {
        return frames;
    }

    public static int getFPS()
    {
        return FPS;
    }
}
