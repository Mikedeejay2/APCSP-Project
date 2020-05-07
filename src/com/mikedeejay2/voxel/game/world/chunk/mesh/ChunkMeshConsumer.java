package com.mikedeejay2.voxel.game.world.chunk.mesh;

public class ChunkMeshConsumer extends Thread
{
    ChunkMeshGenerator chunkMeshGenerator;

    public ChunkMeshConsumer(ChunkMeshGenerator chunkMeshGenerator)
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
