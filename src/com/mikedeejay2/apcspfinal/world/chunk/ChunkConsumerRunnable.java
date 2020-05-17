package com.mikedeejay2.apcspfinal.world.chunk;

import com.mikedeejay2.apcspfinal.world.World;

public class ChunkConsumerRunnable implements Runnable
{

    private ChunkPC chunkPC;

    private World world;

    public ChunkConsumerRunnable(ChunkPC chunkPC, World world)
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
