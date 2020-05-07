package com.mikedeejay2.voxel.game.world.chunk;

import com.mikedeejay2.voxel.game.Main;
import com.mikedeejay2.voxel.game.world.World;
import org.joml.Vector3f;

import java.util.LinkedList;

public class ChunkPC
{
    Main instance = Main.getInstance();

    World world = Main.getInstance().world;

    public static LinkedList<Chunk> chunksToBeProcessed = new LinkedList<Chunk>();
    public static int capacity = 2;

    int x;
    int y;
    int z;
    int rdh;
    int rdv;

    public void produce(World world) throws InterruptedException
    {
        boolean createdChunk = false;
        synchronized (this)
        {
            for (rdh = 0; rdh < World.renderDistanceHorizontal; rdh++)
            {
                for (rdv = 0; rdv < World.renderDistanceVertical; rdv++)
                {
                    for (x = (int) ((world.playerChunk.x) - rdh); x < (world.playerChunk.x) + rdh + 1; x++)
                    {
                        for (y = (int) ((world.playerChunk.y) - rdv); y < (world.playerChunk.y) + rdv + 1; y++)
                        {
                            for (z = (int) ((world.playerChunk.z) - rdh); z < (world.playerChunk.z) + rdh + 1; z++)
                            {
                                Vector3f currentChunkLoc = new Vector3f(x, y, z);
                                if (!world.chunkAtChunkLoc(currentChunkLoc))
                                {
                                    while (chunksToBeProcessed.size() == capacity)
                                        wait();
                                    Chunk chunk = world.generateChunk(currentChunkLoc);
                                    chunksToBeProcessed.add(chunk);
                                    notify();
                                    createdChunk = true;
                                } if(createdChunk) break;
                            } if(createdChunk) break;
                        } if(createdChunk) break;
                    } if(createdChunk) break;
                } if(createdChunk) break;
            }
        }
    }

    public void consume(World world) throws InterruptedException
    {
        synchronized (this)
        {
            while (chunksToBeProcessed.size() == 0) wait();
            Chunk chunk = chunksToBeProcessed.removeFirst();
            chunk.populate();
            world.chunksProcessedThisTick++;
            notify();
        }
    }
}
