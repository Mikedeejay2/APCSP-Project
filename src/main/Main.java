package main;

import engine.io.Input;
import engine.io.Window;

import static org.lwjgl.glfw.GLFW.*;

public class Main implements Runnable
{
    public Thread game;
    public Window window;
    public static final int WIDTH = 1920;
    public static final int HEIGHT = 1080;

    public void start()
    {
        game = new Thread(this, "game");
        game.start();
    }

    public void init()
    {
        System.out.println("Initializing game!");
        window = new Window(WIDTH, HEIGHT, "Voxel Engine");
        window.create();
    }

    public void run()
    {
        init();
        while(!window.shouldClose())
        {
            update();
            render();
        }
        window.destroy();
    }

    private void update()
    {
        //System.out.println("Updating game!");
        window.update();
        if(Input.isButtonDown(GLFW_MOUSE_BUTTON_LEFT)) System.out.println("X: " + Input.getMouseX() + ", Y: " + Input.getMouseY());
    }

    private void render()
    {
        //System.out.println("Rendering game!");
        window.swapBuffers();
    }

    public static void main(String[] args)
    {
        new Main().start();
    }
}
