package main;

import engine.graphics.Mesh;
import engine.graphics.Vertex;
import engine.io.Input;
import engine.io.Window;
import engine.maths.Vector3f;
import engine.renderers.Renderer;

import static org.lwjgl.glfw.GLFW.*;

public class Main implements Runnable
{
    public static Main instance;
    public Thread game;
    public Window window;
    public Renderer renderer;

    public static final int WIDTH = 1920;
    public static final int HEIGHT = 1080;

    public Mesh mesh = new Mesh(new Vertex[] {
            new Vertex(new Vector3f(-0.5f,0.5f, 0.0f)),
            new Vertex(new Vector3f(0.5f,0.5f, 0.0f)),
            new Vertex(new Vector3f(0.5f,-0.5f, 0.0f)),
            new Vertex(new Vector3f(-0.5f,-0.5f, 0.0f))
            }, new int[] {
                0, 1, 2,
                0, 3, 2
            });

    public void start()
    {
        instance = this;
        this.renderer = new Renderer();
        game = new Thread(this, "game");
        game.start();
    }

    public void init()
    {
        System.out.println("Initializing game!");
        window = new Window(WIDTH, HEIGHT, "Voxel Engine");
        instance.getRenderer().setBackgroundColor(0.6f, 0.8f, 1f); //Sky Background color.
        window.create();
        mesh.create();
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
        if(Input.isKeyDown(GLFW_KEY_F11))
        {
            window.setFullscreen(!window.isFullscreen());
        }
    }

    private void render()
    {
        renderer.renderMesh(mesh);
        window.swapBuffers();
    }

    public static void main(String[] args)
    {
        new Main().start();
    }

    public static Main getInstance()
    {
        return instance;
    }

    public Renderer getRenderer()
    {
        return renderer;
    }
}
