package com.mikedeejay2.apcspfinal.io;

import com.mikedeejay2.apcspfinal.graphics.font.TextMaster;
import com.mikedeejay2.apcspfinal.Main;
import com.mikedeejay2.apcspfinal.loaders.IconLoader;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL.*;
import static org.lwjgl.opengl.GL11.*;

/*
 * Based off the GLFW wiki
 */
public class Window
{
    private Main instance = Main.getInstance();
    private static int windowWidth, windowHeight;
    private String title;
    private long window;
    public static int frames;
    public static long time;
    private boolean isResized;
    private boolean isFullscreen;
    private int[] windowPosX = new int[1], windowPosY = new int[1];
    private static int FPS;
    private static int oldFPS;

    private final IconLoader resource_01 = IconLoader.loadImage("res/textures/icon/icon.png");

    private static int monitorWidth, monitorHeight;

    GLFWWindowSizeCallback glfwWindowSizeCallback;

    public Window(int width, int height, String title)
    {
        System.out.println("Window");
        this.windowWidth = width;
        this.windowHeight = height;
        this.title = title;
        time = System.currentTimeMillis();
        glfwWindowSizeCallback = new GLFWWindowSizeCallback()
        {
            @Override
            public void invoke(long window, int width, int height)
            {
                glViewport(0, 0, width, height);
                windowWidth = width;
                windowHeight = height;
                instance.getRenderer().windowHasBeenResized();
                TextMaster.windowHasBeenResized();
            }
        };
    }

    public void create()
    {
        if(!glfwInit())
        {
            System.err.println("ERROR: Could not initialize GLFW!");
            return;
        }

        window = glfwCreateWindow(windowWidth, windowHeight, title, isFullscreen ? glfwGetPrimaryMonitor() : 0, 0);

        if(window == 0)
        {
            System.err.println("ERROR: Window could not be created.");
            return;
        }

        GLFWVidMode videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        windowPosX[0] = (videoMode.width() - windowWidth) / 2;
        windowPosY[0] = (videoMode.height() - windowHeight) / 2;
        monitorHeight = videoMode.height();
        monitorWidth = videoMode.width();
        glfwSetWindowPos(window, (videoMode.width() - windowWidth) / 2, (videoMode.height() - windowHeight) / 2);
        glfwMakeContextCurrent(window);
        createCapabilities();

        GLFWImage image = GLFWImage.malloc(); GLFWImage.Buffer imagebf = GLFWImage.malloc(1);
        image.set(
                resource_01.getWidth(),
                resource_01.getHeight(),
                resource_01.getImage());
        imagebf.put(0, image);
        glfwSetWindowIcon(window, imagebf);

        //Callback debugProc = GLUtil.setupDebugMessageCallback(); // may return null if the debug mode is not available

        glEnable(GL_DEPTH_TEST);

        glfwShowWindow(window);

        //glfwSwapInterval(2);
        glfwWindowSizeCallback.set(window);
    }

    public void update()
    {
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

    public static int getWindowWidth()
    {
        return windowWidth;
    }

    public void setWidth(int width)
    {
        this.windowWidth = width;
    }

    public static int getWindowHeight()
    {
        return windowHeight;
    }

    public void setHeight(int height)
    {
        this.windowHeight = height;
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
            glfwSetWindowMonitor(window, glfwGetPrimaryMonitor(), 0, 0, monitorWidth, monitorHeight, 2000);
        }
        else
        {
            glfwSetWindowMonitor(window, 0, windowPosX[0], windowPosY[0], windowWidth, windowHeight,2000);
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
