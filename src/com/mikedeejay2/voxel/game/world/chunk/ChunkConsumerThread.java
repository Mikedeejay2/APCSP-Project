package com.mikedeejay2.voxel.game.world.chunk;

import com.mikedeejay2.voxel.game.world.World;

public class ChunkConsumerThread extends Thread
{

    private ChunkPC chunkPC;

    private World world;

    public ChunkConsumerThread(ChunkPC chunkPC, World world)
    {
        this.world = world;
        this.chunkPC = chunkPC;
    }

    @Override
    public void run()
    {
        try
        {
            while(true)
            {
                chunkPC.consume(world);
            }
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}
