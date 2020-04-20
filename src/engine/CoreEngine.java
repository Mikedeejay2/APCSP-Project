package engine;

import engine.io.Input;
import engine.io.Window;
import main.Main;

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
        this.game = new Thread(this, "game");
        isRunning = false;
        this.main = game;
        this.width = width;
        this.height = height;
        this.frameTime = 1.0 / framerate;
        window = new Window(width, height, "Mikedeejay2 Voxel Engine");
        window.create();
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

                if (frameCounter >= 1.0)
                {
                    frames = 0;
                    frameCounter = 0;
                }
            }
            if(render)
            {
                main.input((float) frameTime);

                main.update((float) frameTime);

                Input.update((float)frameTime);
                render = false;
                main.render();
                window.update();
                frames++;
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
