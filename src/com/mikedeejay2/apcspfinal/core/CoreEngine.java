package com.mikedeejay2.apcspfinal.core;

import com.mikedeejay2.apcspfinal.io.Input;
import com.mikedeejay2.apcspfinal.io.Window;
import com.mikedeejay2.apcspfinal.Main;

/*
 * This class is loosely organized off of BennyBox's
 * LWJGL game engine series, but was created by me without
 * rewatching any tutorials.
 */
public class CoreEngine implements Runnable
{
    private boolean isRunning;
    private Main main;
    private Window window;
    private int width;
    private int height;
    private double frameTime;

    public Thread game;

    public CoreEngine(int width, int height, double framerate, Main game)
    {
        System.out.println("CoreEngine");
        window = new Window(width, height, "Mikedeejay2 Voxel Engine");
        window.create();
        this.game = new Thread(this, "game");
        isRunning = false;
        this.main = game;
        this.width = Window.getWindowWidth();
        this.height = Window.getWindowHeight();
        this.frameTime = 1.0 / framerate;
    }

    public void start()
    {
        if (isRunning)
            return;

        run();
    }

    public void stop()
    {
        if (!isRunning)
            return;

        isRunning = false;
    }

    public void run()
    {
        boolean render = false;

        double time = System.currentTimeMillis();

        isRunning = true;

        int frames = 0;
        double frameCounter = 0;

        main.init();

        double lastTime = Time.getTime();
        double unprocessedTime = 0;

        while (isRunning)
        {
            double startTime = Time.getTime();
            double passedTime = startTime - lastTime;
            lastTime = startTime;

            unprocessedTime += passedTime;
            frameCounter += passedTime;


            while (unprocessedTime > frameTime)
            {
                render = true;

                unprocessedTime -= frameTime;

                if (window.shouldClose())
                    stop();

                main.input((float) frameTime);
                Input.update((float)frameTime);

                main.update((float) frameTime);

                if (frameCounter >= 1.0)
                {
                    frames = 0;
                    frameCounter = 0;
                }
            }
            if(render)
            {
                render = false;
                main.render();
                window.update();
                frames++;
            }
            if(System.currentTimeMillis() >= time + 50)
            {
                main.update50ms((float)frameTime);
                time = System.currentTimeMillis();
            }
        }

        cleanUp();
    }

    private void cleanUp()
    {
        main.close();
        window.destroy();
    }

    public Window getWindow()
    {
        return window;
    }
}
