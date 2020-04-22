package com.mikedeejay2.voxel.game;

import com.mikedeejay2.voxel.engine.core.CoreEngine;
import com.mikedeejay2.voxel.engine.graphics.models.TexturedModel;
import com.mikedeejay2.voxel.engine.graphics.renderers.Renderer;
import com.mikedeejay2.voxel.engine.graphics.shaders.StaticShader;
import com.mikedeejay2.voxel.engine.io.Input;
import com.mikedeejay2.voxel.engine.io.Window;
import com.mikedeejay2.voxel.engine.loaders.Loader;
import com.mikedeejay2.voxel.engine.graphics.models.RawModel;
import com.mikedeejay2.voxel.engine.graphics.textures.ModelTexture;
import com.mikedeejay2.voxel.engine.graphics.objects.Entity;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;

public class Main
{
    public static Main instance;
    public Renderer renderer;
    public Loader loader;
    //public DebugScreen debugScreen;
    public CoreEngine coreEngine;

    public StaticShader staticShader;

    public static final int WIDTH = 1920;
    public static final int HEIGHT = 1080;

    float[] vertices = {
            -0.5f,0.5f,0,	//V0
            -0.5f,-0.5f,0,	//V1
            0.5f,-0.5f,0,	//V2
            0.5f,0.5f,0		//V3
    };

    int[] indices = {
            0,1,3,	//Top left triangle (V0,V1,V3)
            3,1,2	//Bottom right triangle (V3,V1,V2)
    };

    float[] textureCoords = {
            0, 0,
            0, 1,
            1, 1,
            1, 0
    };

    public RawModel voxelM;
    public ModelTexture voxelT;
    public TexturedModel voxelTM;
    public Entity voxel;

    public RawModel testingM;
    public ModelTexture testingT;
    public TexturedModel testingTM;
    public Entity testing;

    //public Camera camera;

    public void start()
    {
        instance = this;
        coreEngine = new CoreEngine(1920, 1080, 8000, this);
        coreEngine.start();
    }

    public void init()
    {
        System.out.println("Initializing game!");
        //shader = new StaticShader();
        //camera = new Camera(70f, (float)Window.getWidth() / Window.getHeight(), 0.01f, 1000, 0.3f, 10f);

        loader = new Loader();
        renderer = new Renderer();
        staticShader = new StaticShader();

        voxelM = loader.loadToVAO(VoxelShape.getVertices(), VoxelShape.getTextureCoords(), VoxelShape.getIndices());
        voxelT = new ModelTexture(loader.loadTexture("block/diamond_block.png"));
        voxelTM = new TexturedModel(voxelM, voxelT);
        voxel = new Entity(voxelTM, new Vector3f(0, 0,0), 0, 0, 0, 1);

        testingM = loader.loadToVAO(vertices, textureCoords,indices);
        testingT = new ModelTexture(loader.loadTexture("block/stone.png"));
        testingTM = new TexturedModel(testingM, testingT);
        testing = new Entity(testingTM, new Vector3f(0, 0,0), 0, 0, 0, 1);

//        material = new Material("block/dirt.png");
//        blockMesh.create();
//        object = new GameObject(new Vector3f(0, 0, 2), new Quaternion(new Vector3f(0, 1, 0), (float) Math.toRadians(0f)), new Vector3f(1, 1, 1), blockMesh, material);
//        object.create();
        //TextMaster.init(loader);
//        debugScreen = new DebugScreen();
//        debugScreen.init();
    }

    public void update(float delta)
    {
        voxel.increasePosition(0, 0, 0, delta);
        voxel.increaseRotation(100, 100, 100, delta);
        //object.update(delta);
        //camera.update(delta);
//        debugScreen.update(delta);
    }

    public void input(float delta)
    {
        if(Input.getKeyDown(GLFW_KEY_F11)) coreEngine.getWindow().setFullscreen(!coreEngine.getWindow().isFullscreen());
//        if(Input.getKeyDown(GLFW_KEY_F3)) debugScreen.toggle();
    }

    public void render()
    {
        renderer.prepare();
        staticShader.start();
        renderer.render(voxel, staticShader);
        staticShader.stop();
        //TextMaster.render();
    }

    public void close()
    {
        //TextMaster.cleanUp();
        //object.destroy();
        //blockMesh.destroy();
        //shader.destroy();
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

//    public Camera getCamera()
//    {
//        return camera;
//    }

    public Window getWindow()
    {
        return coreEngine.getWindow();
    }

//    public DebugScreen getDebugScreen()
//    {
//        return debugScreen;
//    }
}