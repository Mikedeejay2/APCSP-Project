package main;

import debug.DebugScreen;
import engine.CoreEngine;
import engine.font.TextMaster;
import engine.graphics.*;
import engine.graphics.shaders.StaticShader;
import engine.io.Input;
import engine.io.Window;
import engine.maths.Quaternion;
import engine.maths.Vector2f;
import engine.maths.Vector3f;
import engine.objects.Camera;
import engine.objects.GameObject;
import engine.renderers.Renderer;

import static org.lwjgl.glfw.GLFW.*;

public class Main
{
    public static Main instance;
    public Renderer renderer;
    public StaticShader shader;
    public Loader loader;
    public DebugScreen debugScreen;
    public CoreEngine coreEngine;

    public static final int WIDTH = 1920;
    public static final int HEIGHT = 1080;

    public Mesh blockMesh = new Mesh(new Vertex[] {
            new Vertex(new Vector3f(-0.5f,0.5f,-0.5f), new Vector2f(0, 0)),
            new Vertex(new Vector3f(-0.5f,-0.5f,-0.5f), new Vector2f(0, 1)),
            new Vertex(new Vector3f(0.5f,-0.5f,-0.5f), new Vector2f(1, 1)),
            new Vertex(new Vector3f(0.5f,0.5f,-0.5f), new Vector2f(1, 0)),

            new Vertex(new Vector3f(-0.5f,0.5f,0.5f), new Vector2f(0, 0)),
            new Vertex(new Vector3f(-0.5f,-0.5f,0.5f), new Vector2f(0, 1)),
            new Vertex(new Vector3f(0.5f,-0.5f,0.5f), new Vector2f(1, 1)),
            new Vertex(new Vector3f(0.5f,0.5f,0.5f), new Vector2f(1, 0)),

            new Vertex(new Vector3f(0.5f,0.5f,-0.5f), new Vector2f(0, 0)),
            new Vertex(new Vector3f(0.5f,-0.5f,-0.5f), new Vector2f(0, 1)),
            new Vertex(new Vector3f(0.5f,-0.5f,0.5f), new Vector2f(1, 1)),
            new Vertex(new Vector3f(0.5f,0.5f,0.5f), new Vector2f(1, 0)),

            new Vertex(new Vector3f(-0.5f,0.5f,-0.5f), new Vector2f(0, 0)),
            new Vertex(new Vector3f(-0.5f,-0.5f,-0.5f), new Vector2f(0, 1)),
            new Vertex(new Vector3f(-0.5f,-0.5f,0.5f), new Vector2f(1, 1)),
            new Vertex(new Vector3f(-0.5f,0.5f,0.5f), new Vector2f(1, 0)),

            new Vertex(new Vector3f(-0.5f,0.5f,0.5f), new Vector2f(0, 0)),
            new Vertex(new Vector3f(-0.5f,0.5f,-0.5f), new Vector2f(0, 1)),
            new Vertex(new Vector3f(0.5f,0.5f,-0.5f), new Vector2f(1, 1)),
            new Vertex(new Vector3f(0.5f,0.5f,0.5f), new Vector2f(1, 0)),

            new Vertex(new Vector3f(-0.5f,-0.5f,0.5f), new Vector2f(0, 0)),
            new Vertex(new Vector3f(-0.5f,-0.5f,-0.5f), new Vector2f(0, 1)),
            new Vertex(new Vector3f(0.5f,-0.5f,-0.5f), new Vector2f(1, 1)),
            new Vertex(new Vector3f(0.5f,-0.5f,0.5f), new Vector2f(1, 0))

    }, new int[] {
            3,1,0,
            2,1,3,
            4,5,7,
            7,5,6,
            11,9,8,
            10,9,11,
            12,13,15,
            15,13,14,
            19,17,16,
            18,17,19,
            20,21,23,
            23,21,22
            });

    Material material;

    public GameObject object;

    public Camera camera;

    public void start()
    {
        instance = this;
        coreEngine = new CoreEngine(1920, 1080, 240, this);
        coreEngine.start();
    }

    public void init()
    {
        System.out.println("Initializing game!");
        loader = new Loader();

        shader = new StaticShader();

        camera = new Camera(70f, (float)Window.getWidth() / Window.getHeight(), 0.01f, 1000, 0.3f, 10f);

        renderer = new Renderer(shader);

        instance.getRenderer().setBackgroundColor(0.6f, 0.8f, 1f); //Sky Background color.

        material = new Material("block/dirt.png");
        blockMesh.create();

        object = new GameObject(new Vector3f(0, 0, 2), new Quaternion(new Vector3f(0, 1, 0), (float) Math.toRadians(0f)), new Vector3f(1, 1, 1), blockMesh, material);
        object.create();

        TextMaster.init(loader);

        debugScreen = new DebugScreen();
        debugScreen.init();
    }

    public void update(float delta)
    {
        object.update(delta);
        camera.update(delta);
        debugScreen.update(delta);
    }

    public void input(float delta)
    {
        if(Input.getKeyDown(GLFW_KEY_F11)) coreEngine.getWindow().setFullscreen(!coreEngine.getWindow().isFullscreen());
        if(Input.getKeyDown(GLFW_KEY_F3)) debugScreen.toggle();
    }

    public void render()
    {
        renderer.renderObject(object);

        TextMaster.render();
    }

    public void close()
    {
        TextMaster.cleanUp();
        object.destroy();
        blockMesh.destroy();
        shader.destroy();
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

    public Camera getCamera()
    {
        return camera;
    }

    public Window getWindow()
    {
        return coreEngine.getWindow();
    }

    public DebugScreen getDebugScreen()
    {
        return debugScreen;
    }
}
