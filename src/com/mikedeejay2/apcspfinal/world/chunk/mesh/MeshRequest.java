package com.mikedeejay2.apcspfinal.world.chunk.mesh;

import com.mikedeejay2.apcspfinal.world.World;
import com.mikedeejay2.apcspfinal.world.chunk.Chunk;

public class MeshRequest {

    public Chunk chunk;
    public World world;

    public MeshRequest(Chunk chunk, World world) {
        this.chunk = chunk;
        this.world = world;
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
}
