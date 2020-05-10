package com.mikedeejay2.voxel.game.world.chunk;

import com.mikedeejay2.voxel.game.world.World;

public class ChunkProducerRunnable implements Runnable
{
    private ChunkPC chunkPC;

    private World world;

    public ChunkProducerRunnable(ChunkPC chunkPC, World world)
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
