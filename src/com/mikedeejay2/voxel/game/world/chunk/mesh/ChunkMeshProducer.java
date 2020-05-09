package com.mikedeejay2.voxel.game.world.chunk.mesh;

import java.util.concurrent.ConcurrentLinkedQueue;

public class ChunkMeshProducer extends  Thread
{
    ChunkMeshGenerator chunkMeshGenerator;

    private ConcurrentLinkedQueue<MeshRequest> latestQueue;
    private ConcurrentLinkedQueue<MeshRequest> queue;

    private final int capacity = 5;

    public ChunkMeshProducer(ChunkMeshGenerator chunkMeshGenerator)
    {
        this.latestQueue = new ConcurrentLinkedQueue<MeshRequest>();
        this.queue = new ConcurrentLinkedQueue<MeshRequest>();
        this.chunkMeshGenerator = chunkMeshGenerator;
    }

    @Override
    public void run()
    {
        while(true)
        {
            try
            {
                while(latestQueue.size() < capacity) if(queue.size() != 0) latestQueue.add(queue.remove());
                chunkMeshGenerator.produce(latestQueue);
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }

    public void addRequest(MeshRequest meshRequest)
    {
        if(meshRequest.chunk == null && meshRequest.world == null) return;
        if(!meshRequest.chunk.containsVoxels) return;
        if(!meshRequest.chunk.hasLoaded) return;
        latestQueue.add(meshRequest);
    }
}
