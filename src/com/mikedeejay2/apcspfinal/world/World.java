package com.mikedeejay2.apcspfinal.world;

import com.mikedeejay2.apcspfinal.world.chunk.ChunkConsumerRunnable;
import com.mikedeejay2.apcspfinal.Main;
import com.mikedeejay2.apcspfinal.world.generators.OverworldGenerator;
import com.mikedeejay2.apcspfinal.world.chunk.Chunk;
import com.mikedeejay2.apcspfinal.world.chunk.ChunkPC;
import com.mikedeejay2.apcspfinal.world.chunk.ChunkProducerRunnable;
import org.joml.Vector3d;
import org.joml.Vector3f;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class World extends Thread
{
    public static final int CHUNK_SIZE = 32;

    public int chunksProcessedThisTick = 0;

    public int chunkUpdates = 0;

    public static int renderDistanceHorizontal = 16;
    public static int renderDistanceVertical = 8;

    ChunkPC chunkPC;

    ChunkProducerRunnable chunkProducer;
    Thread chunkProducerThread;

    ArrayList<Thread> chunkConsumerThreads;

    Main instance = Main.getInstance();
    public Vector3d playerPosition;
    public Vector3d playerPositionPrevious;
    public Vector3f playerChunk;
    public Vector3f playerChunkPrevious;

    public OverworldGenerator overworldGenerator;

    public Map<Vector3f, Chunk> allChunks;

    public World()
    {
        playerPosition = new Vector3d(0, 0, 0);
        playerChunk = new Vector3f(0, 0, 0);
        playerPositionPrevious = new Vector3d(0, 0, 0);
        playerChunkPrevious = new Vector3f(0, 0, 0);
        allChunks = new ConcurrentHashMap<>();
        overworldGenerator = new OverworldGenerator(this);

        chunkConsumerThreads = new ArrayList<Thread>();

        chunkPC = new ChunkPC();
        chunkProducer = new ChunkProducerRunnable(chunkPC, this);
        chunkProducerThread = new Thread(chunkProducer, "chunkProducer");
        chunkProducerThread.start();

        for(int i = 0; i < Runtime.getRuntime().availableProcessors(); i++)
        {
            ChunkConsumerRunnable chunkConsumerThread = new ChunkConsumerRunnable(chunkPC, this);
            Thread thread = new Thread(chunkConsumerThread, "chunkConsumer" + i);
            chunkConsumerThreads.add(thread);
            thread.start();
        }
    }

    @Override
    public void run()
    {
        while(true)
        {
            updatePlayerLoc();
            updateChunks();
            getRenderableChunks();
            try
            {
                Thread.sleep(50);
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }
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
                    {
                        if(chunk.containsVoxels && chunk.hasLoaded)
                            chunksToRender.add(chunk);
                    }
                }
            }
        }
        instance.chunksToRender = chunksToRender;
    }

    public void unloadOldChunks()
    {
        List<Vector3f> locs = new ArrayList<>(allChunks.keySet());
        for(Vector3f loc : locs)
        {
                if (playerChunk.x - loc.x > World.renderDistanceHorizontal || playerChunk.y - loc.y > World.renderDistanceVertical || playerChunk.z - loc.z > World.renderDistanceHorizontal ||
                        playerChunk.x - loc.x < -World.renderDistanceHorizontal || playerChunk.y - loc.y < -World.renderDistanceVertical || playerChunk.z - loc.z < -World.renderDistanceHorizontal)
                {
                    Chunk chunk = getChunkFromChunkLoc(loc);
                    chunk.shouldRender = false;
                    chunk.containsVoxels = false;
                    chunk.hasLoaded = false;
                    chunk.entityShouldBeRemade = false;
                    allChunks.remove(loc);
                    loc = null;
                    if(chunk.chunkEntity != null) chunk.destroy();
                    chunk.chunkEntity = null;
                    chunk = null;
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
        return new Vector3f((float)Math.floor(loc.x/(CHUNK_SIZE)), (float)Math.floor(loc.y/(CHUNK_SIZE)), (float)Math.floor(loc.z/(CHUNK_SIZE)));
    }

    public void updateChunks()
    {

    }

    public void updatePlayerLoc()
    {
        playerChunkPrevious = new Vector3f(playerChunk);
        playerPositionPrevious = new Vector3d(playerPosition);
        playerPosition = instance.getCamera().getRealPos();
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
//        overworldGenerator.genTerrain(chunk);
        overworldGenerator.genFlat(chunk);
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
            if(!chunk.hasLoaded) return false;
            int newX = ((x)%(CHUNK_SIZE));
            int newY = ((y)%(CHUNK_SIZE));
            int newZ = ((z)%(CHUNK_SIZE));
            if(newX < 0) newX += (CHUNK_SIZE);
            if(newY < 0) newY += (CHUNK_SIZE);
            if(newZ < 0) newZ += (CHUNK_SIZE);
            return chunk.containsVoxelAtOffset(newX, newY, newZ);
        }
        return false;
    }

    public void removeVoxel(int x, int y, int z)
    {
        Chunk chunk = getChunkFromCoordinates(new Vector3f(x, y ,z));
        if(chunk != null)
        {
            int newX = ((x)%(CHUNK_SIZE));
            int newY = ((y)%(CHUNK_SIZE));
            int newZ = ((z)%(CHUNK_SIZE));
            if(newX < 0) newX += (CHUNK_SIZE);
            if(newY < 0) newY += (CHUNK_SIZE);
            if(newZ < 0) newZ += (CHUNK_SIZE);
            chunk.removeVoxel(newX, newY, newZ);
        }
    }

    public void cleanUp()
    {
        for(Thread thread : chunkConsumerThreads)
        {
            thread.stop();
        }
        chunkProducerThread.stop();
    }
}
