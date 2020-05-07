package com.mikedeejay2.voxel.game.world.chunk;

import com.mikedeejay2.voxel.game.world.World;

public class ChunkProducerThread extends Thread
{
    private ChunkPC chunkPC;

    private World world;

    public ChunkProducerThread(ChunkPC chunkPC, World world)
    {
        this.chunkPC = chunkPC;
        this.world = world;
    }

    @Override
    public void run()
    {
        try
        {
            while(true)
                chunkPC.produce(world);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}
