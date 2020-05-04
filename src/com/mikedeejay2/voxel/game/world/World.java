package com.mikedeejay2.voxel.game.world;

import com.mikedeejay2.voxel.game.Main;
import com.mikedeejay2.voxel.game.voxel.VoxelShape;
import com.mikedeejay2.voxel.engine.voxel.generators.OverworldGenerator;
import org.joml.Vector3f;

import java.util.*;

public class World implements Runnable
{
    public static final int CHUNK_SIZE = 32;
    public static final int CHUNKS_TO_PROCESS_PER_TICK = 10;
    public int chunksProcessedThisTick = 0;

    public int chunkUpdates = 0;

    public static int renderDistanceHorizontal = 12;
    public static int renderDistanceVertical = 6;

    public static World world;

    Main instance = Main.getInstance();
    Vector3f playerPosition;
    Vector3f playerChunk;

    OverworldGenerator overworldGenerator;

    Map<Vector3f, Chunk> allChunks;

    public World()
    {
        playerPosition = new Vector3f(0, 0, 0);
        playerChunk = new Vector3f(0, 0, 0);
        allChunks = new HashMap<>();
        overworldGenerator = new OverworldGenerator(this);
    }

    @Override
    public void run()
    {
        while(true)
        {
            updatePlayerLoc();
            updateChunks();
            chunksProcessedThisTick = 0;
        }
    }

    public void unloadOldChunks()
    {
        List<Vector3f> locs = new ArrayList<>();
        try
        {
            locs = new ArrayList<>(allChunks.keySet());
        }
        catch (ConcurrentModificationException e) {}
        for(int i = 0; i < locs.size(); i++)
        {
            Vector3f loc = locs.get(i);
                if (playerChunk.x - loc.x > World.renderDistanceHorizontal || playerChunk.y - loc.y > World.renderDistanceVertical || playerChunk.z - loc.z > World.renderDistanceHorizontal ||
                        playerChunk.x - loc.x < -World.renderDistanceHorizontal || playerChunk.y - loc.y < -World.renderDistanceVertical || playerChunk.z - loc.z < -World.renderDistanceHorizontal)
                {
                    Chunk chunk = getChunkFromChunkLoc(loc);
                    chunk.destroy();
                    chunk.chunkCoords = null;
                    chunk.voxels = null;
                    chunk.chunkEntity = null;
                    allChunks.remove(loc);
                    loc = null;
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
        return new Vector3f((float)Math.floor(loc.x/CHUNK_SIZE* VoxelShape.VOXEL_SIZE), (float)Math.floor(loc.y/CHUNK_SIZE* VoxelShape.VOXEL_SIZE), (float)Math.floor(loc.z/CHUNK_SIZE* VoxelShape.VOXEL_SIZE));
    }

    public void updateChunks()
    {
        for (int rdh = 0; rdh < renderDistanceHorizontal; rdh++)
        {
            for (int rdv = 0; rdv < renderDistanceVertical; rdv++)
            {
                for (int x = (int) (playerChunk.x - rdh); x < playerChunk.x + rdh + 1; x++)
                {
                    for (int y = (int) (playerChunk.y - rdv); y < playerChunk.y + rdv + 1; y++)
                    {
                        for (int z = (int) (playerChunk.z - rdh); z < playerChunk.z + rdh + 1; z++)
                        {
                            if (chunksProcessedThisTick < CHUNKS_TO_PROCESS_PER_TICK)
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
        playerChunk.x = (float)Math.floor(playerPosition.x/CHUNK_SIZE/VoxelShape.VOXEL_SIZE);
        playerChunk.y = (float)Math.floor(playerPosition.y/CHUNK_SIZE/VoxelShape.VOXEL_SIZE);
        playerChunk.z = (float)Math.floor(playerPosition.z/CHUNK_SIZE/VoxelShape.VOXEL_SIZE);
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

    public static int getRenderDistanceHorizontal()
    {
        return renderDistanceHorizontal;
    }

    public static int getRenderDistanceVertical()
    {
        return renderDistanceVertical;
    }

    public Vector3f getPlayerPosition()
    {
        return playerPosition;
    }

    public Vector3f getPlayerChunk()
    {
        return playerChunk;
    }

    public Map<Vector3f, Chunk> getAllChunks()
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
        return chunkUpdates;
    }

    public void updateChunkUpdates()
    {
        chunkUpdates = chunksProcessedThisTick;
    }

    public int getAllChunksSize()
    {
        return allChunks.size();
    }

    public Chunk getChunk(Vector3f vector3f)
    {
        return allChunks.get(vector3f);
    }
}
