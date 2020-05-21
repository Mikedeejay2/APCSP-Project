package com.mikedeejay2.apcspfinal;

import com.mikedeejay2.apcspfinal.core.CoreEngine;
import com.mikedeejay2.apcspfinal.debug.DebugScreen;
import com.mikedeejay2.apcspfinal.graphics.font.TextMaster;
import com.mikedeejay2.apcspfinal.graphics.objects.Camera;
import com.mikedeejay2.apcspfinal.graphics.renderers.MasterRenderer;
import com.mikedeejay2.apcspfinal.graphics.textures.TextureAtlas;
import com.mikedeejay2.apcspfinal.io.Input;
import com.mikedeejay2.apcspfinal.io.Window;
import com.mikedeejay2.apcspfinal.loaders.Loader;
import com.mikedeejay2.apcspfinal.utils.Raycast;
import com.mikedeejay2.apcspfinal.world.World;
import com.mikedeejay2.apcspfinal.world.chunk.mesh.runnables.ChunkMeshProducerRunnable;
import com.mikedeejay2.apcspfinal.player.Player;
import com.mikedeejay2.apcspfinal.voxel.VoxelTypes;
import com.mikedeejay2.apcspfinal.world.chunk.Chunk;
import com.mikedeejay2.apcspfinal.world.chunk.mesh.runnables.ChunkMeshConsumerRunnable;
import com.mikedeejay2.apcspfinal.world.chunk.mesh.ChunkEntityCreator;

import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.*;

public class Main
{
    public static Main instance;
    public static Loader loader;
    public DebugScreen debugScreen;
    public CoreEngine coreEngine;
    public MasterRenderer renderer;

    public World world;
    ChunkEntityCreator entityCreator;
    Thread worldThread;

    public ChunkMeshProducerRunnable chunkMeshProducer;
    public ChunkMeshConsumerRunnable chunkMeshConsumer;
    Thread chunkMeshProducerThread;
    Thread chunkMeshConsumerThread;

    public static final int WIDTH = 1920;
    public static final int HEIGHT = 1080;

    public Player player;

    public Raycast mousePicker;


    public ArrayList<Chunk> chunksToRender;

    public void start()
    {
        Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
        instance = this;
        coreEngine = new CoreEngine(1920, 1080, 8000, this);
        coreEngine.start();
    }

    public void init()
    {
        System.out.println("Initializing game!");

        loader = new Loader();

        renderer = new MasterRenderer();

        Input.init();

        TextureAtlas loadData = VoxelTypes.getTextureAtlas();

        chunksToRender = new ArrayList<Chunk>();

        world = new World();

        player = new Player(new Camera());

        this.worldThread = new Thread(world, "world");
        worldThread.start();

        entityCreator = new ChunkEntityCreator();


        TextMaster.init(loader, this);
        debugScreen = new DebugScreen(this);
        debugScreen.init();

        mousePicker = new Raycast(player.getCamera(), renderer.getProjectionMatrix());
    }

    public void update(float delta)
    {
        entityCreator.update();
        player.update(delta);
    }

    public void update50ms(float delta)
    {
        world.updateChunkUpdates();
        debugScreen.update();
        world.unloadOldChunks();
        world.chunksProcessedThisTick = 0;
    }

    public void input(float delta)
    {
        player.input(delta);
        if(Input.getKeyDown(GLFW_KEY_F11)) coreEngine.getWindow().setFullscreen(!coreEngine.getWindow().isFullscreen());
        player.getCamera().input(delta);
        if(Input.getKeyDown(GLFW_KEY_F3)) debugScreen.toggle();
        if(Input.getMouseDown(GLFW_MOUSE_BUTTON_LEFT))
        {
            mousePicker.update();
            if(mousePicker.getCurrentPoint() != null)
            {
                player.removeVoxel(Math.round(mousePicker.getCurrentPoint().x), Math.round(mousePicker.getCurrentPoint().y), Math.round(mousePicker.getCurrentPoint().z));
//                    world.removeVoxel(Math.round(mousePicker.getCurrentPoint().x), Math.round(mousePicker.getCurrentPoint().y), Math.round(mousePicker.getCurrentPoint().z));
            }

        }
        if(Input.getMouseDown(GLFW_MOUSE_BUTTON_RIGHT))
        {
            mousePicker.update();
            if(mousePicker.getCurrentPoint() != null &&
            !player.getAabb().intersectBlockPoint(player.getPosition(), mousePicker.getCurrentPoint()))
            {
//                        world.addVoxelRelative("stone", mousePicker.getCurrentPoint());
                        player.addVoxel(mousePicker.getCurrentPoint());
            }
        }
    }

    public void render()
    {
        for(Chunk chunk : chunksToRender)
        {
            if(chunk != null)
            {
                chunk.render();
            }
        }
        renderer.render(player.getCamera());
        TextMaster.render();
    }

    public void close()
    {
        world.cleanUp();
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
        return player.getCamera();
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

    public ChunkMeshProducerRunnable getChunkMeshProducer()
    {
        return chunkMeshProducer;
    }

    public ChunkMeshConsumerRunnable getChunkMeshConsumer()
    {
        return chunkMeshConsumer;
    }

    public static int getWIDTH()
    {
        return WIDTH;
    }

    public static int getHEIGHT()
    {
        return HEIGHT;
    }

    public ArrayList<Chunk> getChunksToRender()
    {
        return chunksToRender;
    }

    public ChunkEntityCreator getEntityCreator()
    {
        return entityCreator;
    }
}
