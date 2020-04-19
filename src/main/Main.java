package main;

import engine.font.TextMaster;
import engine.font.creator.FontType;
import engine.font.creator.GUIText;
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
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

import java.io.File;

import static org.lwjgl.glfw.GLFW.*;

public class Main implements Runnable
{
    public static Main instance;
    public Thread game;
    public Window window;
    public Renderer renderer;
    public StaticShader shader;
    public TextMaster textMaster;
    public Loader loader;

    FontType font;
    GUIText text;

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
        game = new Thread(this, "game");
        game.start();
    }

    public void init()
    {
        System.out.println("Initializing game!");
        loader = new Loader();

        window = new Window(WIDTH, HEIGHT, "Voxel Engine");
        window.create();

        shader = new StaticShader();
        renderer = new Renderer(shader);

        camera = new Camera(70f, (float)window.getWidth() / window.getHeight(), 0.01f, 1000, 0.3f, 0.2f);

        instance.getRenderer().setBackgroundColor(0.6f, 0.8f, 1f); //Sky Background color.

        material = new Material("block/dirt.png");
        blockMesh.create();

        object = new GameObject(new Vector3f(0, 0, 2), new Quaternion(new Vector3f(0, 1, 0), (float) Math.toRadians(0f)), new Vector3f(1, 1, 1), blockMesh, material);
        object.create();
//
        TextMaster.init(loader);

        font = new FontType(new Texture("fonts/ascii.png").getId(), new File("res/fonts/ascii.fnt"));
        text = new GUIText("This is a test tefwefewfwefe fweoifj wefiojweofi ewjofi jewofiewj foiwej foiewj foiwjfoi ewjfoi wxt!", 1, font, new Vector2f(0, 0), 1f, true);
    }

    public void run()
    {
        init();
        while(!window.shouldClose())
        {
            update();
            render();
        }
        close();
    }

    private void update()
    {
        //System.out.println("Updating game!");
        if(Input.isKeyDown(GLFW_KEY_F11))
        {
            window.setFullscreen(!window.isFullscreen());
        }
        object.update();
        camera.update();
        GL11.glDisable(GL30.GL_CLIP_DISTANCE0);
        TextMaster.render();

        window.update();
    }

    private void render()
    {
        renderer.renderObject(object);

        window.swapBuffers();
    }

    private void close()
    {
        TextMaster.cleanUp();
        window.destroy();
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
        return window;
    }
}
