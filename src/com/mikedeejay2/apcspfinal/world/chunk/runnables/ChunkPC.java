package com.mikedeejay2.apcspfinal.world.chunk.runnables;

import com.mikedeejay2.apcspfinal.Main;
import com.mikedeejay2.apcspfinal.world.World;
import com.mikedeejay2.apcspfinal.world.chunk.Chunk;
import org.joml.Vector3f;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ChunkPC
{
    Main instance = Main.getInstance();

    World world = Main.getInstance().world;

    public static BlockingQueue<Chunk> chunksToBeProcessed = new LinkedBlockingQueue<>();
    public static int capacity = Runtime.getRuntime().availableProcessors()*2;

    private Vector3f playerChunk;

    public void produce(World world) throws InterruptedException
    {
        if(playerChunk == null) playerChunk = new Vector3f(world.getPlayerChunk().x, world.getPlayerChunk().y, world.getPlayerChunk().z);
        synchronized (this)
        {
            boolean breakout = false;
            for (int rdh = 0; rdh < World.renderDistanceHorizontal; rdh++)
            {
                for (int rdv = 0; rdv < World.renderDistanceVertical; rdv++)
                {
                    for (int x = (int) ((world.playerChunk.x) - rdh); x < (world.playerChunk.x) + rdh + 1; x++)
                    {
                        for (int y = (int) ((world.playerChunk.y) - rdv); y < (world.playerChunk.y) + rdv + 1; y++)
                        {
                            for (int z = (int) ((world.playerChunk.z) - rdh); z < (world.playerChunk.z) + rdh + 2; z++)
                            {
                                if(!playerChunk.equals(world.getPlayerChunk()))
                                {
                                    breakout = true;
                                    playerChunk = new Vector3f(world.getPlayerChunk().x, world.getPlayerChunk().y, world.getPlayerChunk().z);
                                    break;
                                }
                                Vector3f currentChunkLoc = new Vector3f(x, y, z);
                                if (!world.chunkAtChunkLoc(currentChunkLoc))
                                {
                                    while (chunksToBeProcessed.size() == capacity) Thread.sleep(1);
                                    Chunk chunk = world.generateChunk(currentChunkLoc);
                                    chunksToBeProcessed.add(chunk);
                                }
                                if(breakout) break;
                            }
                            if(breakout) break;
                        }
                        if(breakout) break;
                    }
                    if(breakout) break;
                }
                if(breakout) break;
            }
        }
    }

    public void consume(World world) throws InterruptedException
    {
            while (chunksToBeProcessed.size() == 0) Thread.sleep(1);
            try
            {
                Chunk chunk = chunksToBeProcessed.take();
                chunk.populate();
                world.chunksProcessedThisTick++;
            }
            catch(IllegalMonitorStateException e) {}
    }
}
