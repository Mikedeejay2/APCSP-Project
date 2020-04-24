package com.mikedeejay2.voxel.game;

import com.mikedeejay2.voxel.engine.core.CoreEngine;
import com.mikedeejay2.voxel.engine.debug.DebugScreen;
import com.mikedeejay2.voxel.engine.graphics.font.TextMaster;
import com.mikedeejay2.voxel.engine.graphics.models.TexturedModel;
import com.mikedeejay2.voxel.engine.graphics.objects.Camera;
import com.mikedeejay2.voxel.engine.graphics.renderers.MasterRenderer;
import com.mikedeejay2.voxel.engine.io.Input;
import com.mikedeejay2.voxel.engine.io.Window;
import com.mikedeejay2.voxel.engine.loaders.Loader;
import com.mikedeejay2.voxel.engine.graphics.models.RawModel;
import com.mikedeejay2.voxel.engine.graphics.textures.ModelTexture;
import com.mikedeejay2.voxel.engine.graphics.objects.Entity;
import com.mikedeejay2.voxel.game.voxel.VoxelShape;
import com.mikedeejay2.voxel.game.voxel.VoxelTypes;
import com.mikedeejay2.voxel.game.world.World;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;

public class Main
{
    public static Main instance;
    public static Loader loader;
    public DebugScreen debugScreen;
    public CoreEngine coreEngine;
    public MasterRenderer renderer;

    Thread worldThread;

    public static final int WIDTH = 1920;
    public static final int HEIGHT = 1080;

    public RawModel voxelM;
    public ModelTexture voxelT;
    public TexturedModel voxelTM;
    public Entity voxel;

    public Camera camera;

    public World world;

    public void start()
    {
        instance = this;
        coreEngine = new CoreEngine(1920, 1080, 8000, this);
        coreEngine.start();
    }

    public void init()
    {
        System.out.println("Initializing game!");
        loader = new Loader();

        renderer = new MasterRenderer();

        camera = new Camera();

        voxelM = loader.loadToVAO(VoxelShape.getVertices(), VoxelShape.getTextureCoords(), VoxelShape.getIndices());
        voxelT = new ModelTexture(loader.loadTexture("block/diamond_block.png"));
        voxelTM = new TexturedModel(voxelM, voxelT);
        voxel = new Entity(VoxelTypes.dirt, new Vector3f(0, 0,-4));

        world = new World();

        this.worldThread = new Thread(world, "world");
        worldThread.start();

        TextMaster.init(loader);
        debugScreen = new DebugScreen();
        debugScreen.init();
    }

    public void update(float delta)
    {
        camera.update(delta);
    }

    public void input(float delta)
    {
        if(Input.getKeyDown(GLFW_KEY_F11)) coreEngine.getWindow().setFullscreen(!coreEngine.getWindow().isFullscreen());
        camera.input(delta);
        if(Input.getKeyDown(GLFW_KEY_F3)) debugScreen.toggle();
    }

    public void render()
    {
        renderer.processEntity(voxel);
        renderer.render(camera);
        TextMaster.render();
    }

    public void close()
    {
        worldThread.stop();
        renderer.cleanUp();
        TextMaster.cleanUp();
        loader.cleanUp();
    }

    public static void main(String[] args)
    {
        new Main().start();
    }

    public static Main getInstance()
    {
        return instance;
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

    public static Loader getLoader()
    {
        return loader;
    }
}
