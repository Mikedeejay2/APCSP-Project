package com.mikedeejay2.voxel.game.world.chunk.mesh;

import com.mikedeejay2.voxel.game.world.World;
import com.mikedeejay2.voxel.game.world.chunk.Chunk;

public class MeshRequest {

    public Chunk chunk;
    public World world;
    public boolean shouldUpdateNeighbors;

    public MeshRequest(Chunk chunk, World world, boolean shouldUpdateNeighbors) {
        this.chunk = chunk;
        this.world = world;
        this.shouldUpdateNeighbors = shouldUpdateNeighbors;
    }

    public Chunk getChunk() {
        return chunk;
    }

    public World getWorld() {
        return world;
    }

    public boolean isShouldUpdateNeighbors() {
        return shouldUpdateNeighbors;
    }

    public void setChunk(Chunk chunk) {
        this.chunk = chunk;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public void setShouldUpdateNeighbors(boolean shouldUpdateNeighbors) {
        this.shouldUpdateNeighbors = shouldUpdateNeighbors;
    }
}
