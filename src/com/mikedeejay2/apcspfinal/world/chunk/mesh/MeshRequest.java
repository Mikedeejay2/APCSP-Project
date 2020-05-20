package com.mikedeejay2.apcspfinal.world.chunk.mesh;

import com.mikedeejay2.apcspfinal.world.World;
import com.mikedeejay2.apcspfinal.world.chunk.Chunk;

public class MeshRequest {

    public Chunk chunk;
    public World world;

    public boolean immediate;

    public MeshRequest(Chunk chunk, World world, boolean immediate) {
        this.chunk = chunk;
        this.world = world;
        this.immediate = immediate;
    }

    public Chunk getChunk() {
        return chunk;
    }

    public World getWorld() {
        return world;
    }

    public void setChunk(Chunk chunk) {
        this.chunk = chunk;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public boolean isImmediate()
    {
        return immediate;
    }
}
