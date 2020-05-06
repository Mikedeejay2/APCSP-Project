package com.mikedeejay2.voxel.game;

import com.mikedeejay2.voxel.engine.core.CoreEngine;
import com.mikedeejay2.voxel.engine.debug.DebugScreen;
import com.mikedeejay2.voxel.engine.graphics.font.TextMaster;
import com.mikedeejay2.voxel.engine.graphics.models.RawModel;
import com.mikedeejay2.voxel.engine.graphics.models.TexturedModel;
import com.mikedeejay2.voxel.engine.graphics.objects.Camera;
import com.mikedeejay2.voxel.engine.graphics.objects.Entity;
import com.mikedeejay2.voxel.engine.graphics.renderers.MasterRenderer;
import com.mikedeejay2.voxel.engine.graphics.renderers.Renderer;
import com.mikedeejay2.voxel.engine.io.Input;
import com.mikedeejay2.voxel.engine.io.Window;
import com.mikedeejay2.voxel.engine.loaders.Loader;
import com.mikedeejay2.voxel.engine.graphics.textures.ModelTexture;
import com.mikedeejay2.voxel.game.voxel.Voxel;
import com.mikedeejay2.voxel.game.voxel.VoxelShape;
import com.mikedeejay2.voxel.game.voxel.VoxelTypes;
import com.mikedeejay2.voxel.game.world.Chunk;
import com.mikedeejay2.voxel.game.world.World;
import org.joml.Vector3f;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import static com.mikedeejay2.voxel.game.world.World.renderDistanceHorizontal;
import static com.mikedeejay2.voxel.game.world.World.renderDistanceVertical;
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

    public Camera camera;

    public World world;

    public ArrayList<Chunk> chunksToRender;

    public CopyOnWriteArrayList<Vector3f> locsDebug = new CopyOnWriteArrayList<Vector3f>();

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
        ModelTexture loadData = VoxelTypes.getTexture("diamond_block");

        renderer = new MasterRenderer();

        Input.init();

        camera = new Camera();

        world = new World();

        chunksToRender = new ArrayList<Chunk>();

        this.worldThread = new Thread(world, "world");
        worldThread.start();
        System.out.println("At this point, world thread should have started");

        TextMaster.init(loader, this);
        debugScreen = new DebugScreen(this);
        debugScreen.init();
    }

    public void update(float delta)
    {
        camera.update(delta);
    }

    public void update50ms(float delta)
    {
        world.updateChunkUpdates();
        debugScreen.update();
        world.unloadOldChunks();
    }

    public void input(float delta)
    {
        if(Input.getKeyDown(GLFW_KEY_F11)) coreEngine.getWindow().setFullscreen(!coreEngine.getWindow().isFullscreen());
        camera.input(delta);
        if(Input.getKeyDown(GLFW_KEY_F3)) debugScreen.toggle();
    }

    public void render()
    {
        for(Chunk chunk : chunksToRender)
        {
            if(chunk != null)
            chunk.render();
        }
        for(Vector3f vector3f : locsDebug)
        {
            renderer.processEntity(new Entity(new TexturedModel(loader.loadToVAO(VoxelShape.getVertices(), VoxelShape.getTextureCoords(), VoxelShape.getIndices(), VoxelShape.getBrightness()), VoxelTypes.gold_block.getTexture()), vector3f));
            locsDebug.remove(vector3f);
        }
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

    public MasterRenderer getRenderer()
    {
        return renderer;
    }

    public World getWorld()
    {
        return world;
    }
}
