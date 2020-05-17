package com.mikedeejay2.apcspfinal.world.chunk.mesh;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ChunkMeshProducerRunnable implements Runnable
{
    ChunkMeshGenerator chunkMeshGenerator;

    private ConcurrentLinkedQueue<MeshRequest> latestQueue;
    private ConcurrentLinkedQueue<MeshRequest> queue;

    private final int capacity = Runtime.getRuntime().availableProcessors()*2;

    public ChunkMeshProducerRunnable(ChunkMeshGenerator chunkMeshGenerator)
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
                while(latestQueue.size() < capacity && queue.size() != 0) latestQueue.add(queue.remove());
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
        else queue.add(meshRequest);
    }

    public void addRequestImmediate(MeshRequest meshRequest)
    {
        if(!meshRequest.chunk.containsVoxels) return;
        if(!meshRequest.chunk.hasLoaded) return;
        ArrayList<MeshRequest> requests = new ArrayList<>(latestQueue);
        latestQueue.clear();
        latestQueue.add(meshRequest);
        latestQueue.addAll(requests);
    }
}
