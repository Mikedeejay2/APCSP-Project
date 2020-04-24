package com.mikedeejay2.voxel.game.world;

import com.mikedeejay2.voxel.game.Main;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;

public class World implements Runnable
{
    public static final int CHUNK_SIZE = 16;

    public static int renderDistance = 2;

    public static World world;

    Main instance = Main.getInstance();
    Vector3f playerPosition;
    Vector3f playerChunk;

    HashMap<Vector3f, Chunk> allChunks;
    HashMap<Vector3f, Chunk> chunksToRender;

    public World()
    {
        System.out.println("BOIU");
        playerPosition = new Vector3f(0, 0, 0);
        playerChunk = new Vector3f(0, 0, 0);
        allChunks = new HashMap<Vector3f, Chunk>();
        chunksToRender = new HashMap<Vector3f, Chunk>();
    }

    @Override
    public void run()
    {
        while(true)
        {
            updatePlayerLoc();
            updateChunks();
            try
            {
                Thread.sleep(50);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
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
        if(allChunks.containsKey(loc))
            return allChunks.get(loc);
        return null;
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
        for (int x = (int) (playerChunk.x - renderDistance); x <= playerChunk.x + renderDistance; x++)
        {
            for (int y = (int) (playerChunk.y - renderDistance); y <= playerChunk.y + renderDistance; y++)
            {
                for (int z = (int) (playerChunk.z - renderDistance); z <= playerChunk.z + renderDistance; z++)
                {
                    Vector3f currentChunkLoc = new Vector3f(x, y, z);
                    if (!chunkAtChunkLoc(currentChunkLoc))
                    {
                        Chunk chunk = generateChunk(currentChunkLoc);
                        chunk.populate();
                    }
                }
            }
        }
        chunksToRender = new HashMap<Vector3f, Chunk>();
        for (int x = (int) (playerChunk.x - renderDistance); x <= playerChunk.x + renderDistance; x++)
        {
            for (int y = (int) (playerChunk.y - renderDistance); y <= playerChunk.y + renderDistance; y++)
            {
                for (int z = (int) (playerChunk.z - renderDistance); z <= playerChunk.z + renderDistance; z++)
                {
                    Vector3f currentChunkLoc = new Vector3f(x, y, z);
                    if(allChunks.containsKey(currentChunkLoc))
                    {
                        chunksToRender.put(currentChunkLoc, allChunks.get(currentChunkLoc));
                    }
                }
            }
        }
    }

    public void render()
    {
        try
        {
            ArrayList<Chunk> chunksToProcess = new ArrayList<>(chunksToRender.values());
            for (Chunk chunk : chunksToProcess)
            {
                chunk.render();
            }
        }
        catch (ConcurrentModificationException e)
        {
            render();
        }
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
        Chunk chunk = new Chunk(currentChunkLoc);
        allChunks.put(currentChunkLoc, chunk);
        return chunk;
    }

    public static World getWorld()
    {
        return world;
    }
}
