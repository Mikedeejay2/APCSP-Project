package com.mikedeejay2.voxel.game.world;

import com.mikedeejay2.voxel.game.Main;
import com.mikedeejay2.voxel.game.voxel.VoxelShape;
import com.mikedeejay2.voxel.game.world.generators.OverworldGenerator;
import com.mikedeejay2.voxel.game.world.chunk.Chunk;
import com.mikedeejay2.voxel.game.world.chunk.ChunkConsumerThread;
import com.mikedeejay2.voxel.game.world.chunk.ChunkPC;
import com.mikedeejay2.voxel.game.world.chunk.ChunkProducerThread;
import org.joml.Vector3d;
import org.joml.Vector3f;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class World extends Thread
{
    public static final int CHUNK_SIZE = 32;
    public static final int CHUNKS_TO_PROCESS_PER_TICK = 10;
    public int chunksProcessedThisTick = 0;

    public int chunkUpdates = 0;

    public static int renderDistanceHorizontal = 16;
    public static int renderDistanceVertical = 6;

    public static World world;

    ChunkPC chunkPC;
    ChunkProducerThread chunkProducer;
    ChunkConsumerThread chunkConsumer;
    Thread chunkProducerThread;
    Thread chunkConsumerThread;

    Main instance = Main.getInstance();
    public Vector3d playerPosition;
    public Vector3f playerChunk;

    public OverworldGenerator overworldGenerator;

    public Map<Vector3f, Chunk> allChunks;

    public World()
    {
        playerPosition = new Vector3d(0, 0, 0);
        playerChunk = new Vector3f(0, 0, 0);
        allChunks = new ConcurrentHashMap<>();
        overworldGenerator = new OverworldGenerator(this);

        chunkPC = new ChunkPC();
        chunkProducer = new ChunkProducerThread(chunkPC, this);
        chunkConsumer = new ChunkConsumerThread(chunkPC, this);
        chunkProducerThread = new Thread(chunkProducer, "chunkProducer");
        chunkConsumerThread = new Thread(chunkConsumer, "chunkConsumer");
        chunkProducerThread.start();
        chunkConsumerThread.start();
    }

    @Override
    public void run()
    {
        while(true)
        {
            updatePlayerLoc();
            updateChunks();
            getRenderableChunks();
        }
    }

    private void getRenderableChunks()
    {
        ArrayList<Chunk> chunksToRender = new ArrayList<Chunk>();
        Vector3f playerChunk = getPlayerChunk();
        for (int x = (int) (playerChunk.x - renderDistanceHorizontal); x < playerChunk.x + renderDistanceHorizontal + 1; x++)
        {
            for (int y = (int) (playerChunk.y - renderDistanceVertical); y <  playerChunk.y + renderDistanceVertical + 1; y++)
            {
                for (int z = (int) (playerChunk.z - renderDistanceHorizontal); z < playerChunk.z + renderDistanceHorizontal + 1; z++)
                {
                    Chunk chunk = getChunkFromChunkLoc(new Vector3f(x, y, z));
                    if(chunk != null)
                        chunksToRender.add(chunk);
                    //world.renderChunk(x, y, z);
                }
            }
        }
        instance.chunksToRender = chunksToRender;
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
                    chunk.shouldRender = false;
                    chunk.containsVoxels = false;
                    chunk.hasLoaded = false;
                    chunk.entityShouldBeRemade = false;
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

    }

    public void renderChunk(float x, float y, float z)
    {
        Chunk chunk = getChunkFromChunkLoc(new Vector3f(x, y, z));
        if(chunk != null)
        chunk.render();
    }

    public void updatePlayerLoc()
    {
        playerPosition = instance.getCamera().getRealPos();
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
//        overworldGenerator.genTerrain(chunk);
        overworldGenerator.genFlat(chunk);
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

    public Vector3d getPlayerPosition()
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

    public boolean isVoxelAtCoordinate(int x, int y, int z)
    {
        Chunk chunk = getChunkFromCoordinates(new Vector3f(x, y ,z));
        if(chunk != null)
        {
            float tex = chunk.chunkCoords.x;
            float tey = chunk.chunkCoords.y;
            float tez = chunk.chunkCoords.z;
            if(!chunk.hasLoaded) return false;
            int newX = ((x)%CHUNK_SIZE);
            int newY = ((y)%CHUNK_SIZE);
            int newZ = ((z)%CHUNK_SIZE);
            if(newX < 0) newX += CHUNK_SIZE;
            if(newY < 0) newY += CHUNK_SIZE;
            if(newZ < 0) newZ += CHUNK_SIZE;
            return chunk.containsVoxelAtOffset(newX, newY, newZ);
        }
        return false;
    }

    public void cleanUp()
    {
        chunkConsumerThread.stop();
        chunkProducerThread.stop();
    }
}
