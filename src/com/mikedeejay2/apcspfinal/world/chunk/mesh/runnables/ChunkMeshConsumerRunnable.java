package com.mikedeejay2.apcspfinal.world.chunk.mesh.runnables;

public class ChunkMeshConsumerRunnable implements Runnable
{
    ChunkMeshGenerator chunkMeshGenerator;

    public ChunkMeshConsumerRunnable(ChunkMeshGenerator chunkMeshGenerator)
    {
        this.chunkMeshGenerator = chunkMeshGenerator;
    }

    @Override
    public void run()
    {
        while(true)
        {
            try
            {
                chunkMeshGenerator.consume();
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }
}
