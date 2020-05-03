package com.mikedeejay2.voxel.game.world;

import com.mikedeejay2.voxel.game.Main;
import com.mikedeejay2.voxel.game.world.generators.OverworldGenerator;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class World implements Runnable
{
    public static final int CHUNK_SIZE = 32;
    public static final int CHUNKS_TO_PROCESS_PER_TICK = 10;
    public int chunksProcessedThisTick = 0;

    public int chunkUpdates = 0;
    public int chunkUpdateCount = 0;

    public static int renderDistance = 4;

    public static World world;

    Main instance = Main.getInstance();
    Vector3f playerPosition;
    Vector3f playerChunk;

    OverworldGenerator overworldGenerator;

    HashMap<Vector3f, Chunk> allChunks;

    public World()
    {
        playerPosition = new Vector3f(0, 0, 0);
        playerChunk = new Vector3f(0, 0, 0);
        allChunks = new HashMap<Vector3f, Chunk>();
        overworldGenerator = new OverworldGenerator(this);
    }

    @Override
    public void run()
    {
        while(true)
        {
            updatePlayerLoc();
            updateChunks();
            unloadOldChunks();
            try
            {
                Thread.sleep(50);
                chunkUpdates += chunksProcessedThisTick;
                chunksProcessedThisTick = 0;
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void unloadOldChunks()
    {
        ArrayList<Vector3f> locs = new ArrayList<>(allChunks.keySet());
        for(int i = 0; i < locs.size(); i++)
        {
            Vector3f loc = locs.get(i);
            if (chunksProcessedThisTick < CHUNKS_TO_PROCESS_PER_TICK)
            {
                if (playerChunk.x - loc.x > World.renderDistance || playerChunk.y - loc.y > World.renderDistance || playerChunk.z - loc.z > World.renderDistance ||
                        playerChunk.x - loc.x < -World.renderDistance || playerChunk.y - loc.y < -World.renderDistance || playerChunk.z - loc.z < -World.renderDistance)
                {
                    Chunk chunk = getChunkFromChunkLoc(loc);
                    chunk.chunkCoords = null;
                    chunk.voxels = null;
                    chunk.chunkEntity = null;
                    allChunks.remove(loc);
                    loc = null;
                    chunksProcessedThisTick++;
                }
            }
        }
        locs.clear();
        locs = null;
    }

    public boolean chunkAtChunkLoc(Vector3f loc)
    {
        if(allChunks.containsKey(loc))
            return true;
        return false;
    }

    public boolean chunkAtCoords(Vector3f loc)
    {
        Vector3f locChunk = coordsToChunkLoc(loc);
        if(allChunks.containsKey(locChunk))
            return true;
        return false;
    }

    public Chunk getChunkFromChunkLoc(Vector3f loc)
    {
        return allChunks.get(loc);
    }

    public Chunk getChunkFromCoordinates(Vector3f loc)
    {
        Vector3f locChunk = coordsToChunkLoc(loc);
        if(allChunks.containsKey(locChunk))
            return allChunks.get(locChunk);
        return null;
    }

    public Vector3f coordsToChunkLoc(Vector3f loc)
    {
        return new Vector3f((float)Math.floor(loc.x/CHUNK_SIZE), (float)Math.floor(loc.y/CHUNK_SIZE), (float)Math.floor(loc.z/CHUNK_SIZE));
    }

    public void updateChunks()
    {
        for (int x = (int) (playerChunk.x - renderDistance); x < playerChunk.x + renderDistance + 1; x++)
        {
            for (int y = (int) (playerChunk.y - renderDistance); y < playerChunk.y + renderDistance + 1; y++)
            {
                for (int z = (int) (playerChunk.z - renderDistance); z < playerChunk.z + renderDistance + 1; z++)
                {
                    if(chunksProcessedThisTick < CHUNKS_TO_PROCESS_PER_TICK)
                    {
                        Vector3f currentChunkLoc = new Vector3f(x, y, z);
                        if (!chunkAtChunkLoc(currentChunkLoc))
                        {
                            Chunk chunk = generateChunk(currentChunkLoc);
                            chunk.populate();
                            chunksProcessedThisTick++;
                        }
                    }
                }
            }
        }
    }

    public void renderChunk(float x, float y, float z)
    {
        Chunk chunk = getChunkFromChunkLoc(new Vector3f(x, y, z));
        if(chunk != null)
        chunk.render();
    }

    public void updatePlayerLoc()
    {
        playerPosition = instance.getCamera().getPosition();
        playerChunk.x = (float)Math.floor(playerPosition.x/CHUNK_SIZE);
        playerChunk.y = (float)Math.floor(playerPosition.y/CHUNK_SIZE);
        playerChunk.z = (float)Math.floor(playerPosition.z/CHUNK_SIZE);
    }

    public Chunk generateChunk(Vector3f currentChunkLoc)
    {
        Chunk chunk = new Chunk(currentChunkLoc, this);
        allChunks.put(currentChunkLoc, chunk);
        return chunk;
    }

    public void populateChunk(Chunk chunk)
    {
        overworldGenerator.populate(chunk);
    }

    public static World getWorld()
    {
        return world;
    }

    public static int getChunkSize()
    {
        return CHUNK_SIZE;
    }

    public static int getRenderDistance()
    {
        return renderDistance;
    }

    public Vector3f getPlayerPosition()
    {
        return playerPosition;
    }

    public Vector3f getPlayerChunk()
    {
        return playerChunk;
    }

    public HashMap<Vector3f, Chunk> getAllChunks()
    {
        return allChunks;
    }

    public static int getChunksToProcessPerTick()
    {
        return CHUNKS_TO_PROCESS_PER_TICK;
    }

    public int getChunksProcessedThisTick()
    {
        return chunksProcessedThisTick;
    }

    public int getChunkUpdates()
    {
        return chunkUpdateCount;
    }

    public void resetChunkUpdateCount()
    {
        chunkUpdateCount = chunkUpdates;
        chunkUpdates = 0;
    }
}
