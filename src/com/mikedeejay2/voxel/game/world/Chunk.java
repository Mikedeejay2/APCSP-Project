package com.mikedeejay2.voxel.game.world;

import com.mikedeejay2.voxel.game.voxel.Voxel;
import org.joml.Vector3f;

import java.util.HashMap;

public class Chunk
{
    HashMap<Vector3f, Voxel> voxels = new HashMap<Vector3f, Voxel>();

    Vector3f chunkLoc;
    Vector3f chunkCoords;

    public Chunk(Vector3f chunkLoc)
    {
        this.chunkLoc = chunkLoc;
        this.chunkCoords = new Vector3f(chunkLoc.x * 16, chunkLoc.y * 16, chunkLoc.z * 16);
    }

    public void populate()
    {
        for(int x = 0; x < World.CHUNK_SIZE; x++)
        {
            for(int z = 0; z < World.CHUNK_SIZE; z++)
            {
                Vector3f position = new Vector3f(chunkCoords.x + x, 0, chunkCoords.z + z);
                voxels.put(position, new Voxel("stone", position));
            }
        }
    }

    public void render()
    {
        for(int x = 0; x < World.CHUNK_SIZE; x++)
        {
            for(int y = 0; y < World.CHUNK_SIZE; y++)
            {
                for(int z = 0; z < World.CHUNK_SIZE; z++)
                {
                    Vector3f position = new Vector3f(chunkCoords.x + x, chunkCoords.y + y, chunkCoords.z + z);
                    if(containsVoxelAtOffset(position))
                    {
                        getVoxelAtOffset(position).render();
                    }
                    position = null;
                }
            }
        }
    }

    public boolean containsVoxelAtOffset(Vector3f location)
    {
        return voxels.containsKey(location);
    }

    public Voxel getVoxelAtOffset(Vector3f location)
    {
        return voxels.get(location);
    }

    public Vector3f getChunkLoc()
    {
        return chunkLoc;
    }

    public Vector3f getChunkCoords()
    {
        return chunkCoords;
    }
}
