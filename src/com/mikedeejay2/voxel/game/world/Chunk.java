package com.mikedeejay2.voxel.game.world;

import com.mikedeejay2.voxel.engine.graphics.models.RawModel;
import com.mikedeejay2.voxel.game.Main;
import com.mikedeejay2.voxel.game.voxel.Voxel;
import com.mikedeejay2.voxel.game.voxel.VoxelShape;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Chunk
{
    public Voxel[][][] voxels;

    public Vector3f chunkLoc;
    public Vector3f chunkCoords;

    public Chunk(Vector3f chunkLoc)
    {
        this.chunkLoc = chunkLoc;
        this.chunkCoords = new Vector3f(chunkLoc.x *  World.CHUNK_SIZE, chunkLoc.y * World.CHUNK_SIZE, chunkLoc.z * World.CHUNK_SIZE);
        this.voxels = new Voxel[World.CHUNK_SIZE][World.CHUNK_SIZE][World.CHUNK_SIZE];
    }

    public void populate()
    {
        for(int x = 0; x < World.CHUNK_SIZE; x++)
        {
            for(int z = 0; z < World.CHUNK_SIZE; z++)
            {
                Vector3f position = new Vector3f(chunkCoords.x + x, 0, chunkCoords.z + z);
                if(Math.abs(chunkLoc.x) % 2 == 0 && Math.abs(chunkLoc.z) % 2 == 0)
                    voxels[x][0][z] = new Voxel("diamond_block", position);
                if(Math.abs(chunkLoc.x) % 2 == 0 && Math.abs(chunkLoc.z) % 2 == 1)
                    voxels[x][0][z] = new Voxel("gold_block", position);
                if(Math.abs(chunkLoc.x) % 2 == 1 && Math.abs(chunkLoc.z) % 2 == 1)
                    voxels[x][0][z] = new Voxel("diamond_block", position);
                if(Math.abs(chunkLoc.x) % 2 == 1 && Math.abs(chunkLoc.z) % 2 == 0)
                    voxels[x][0][z] = new Voxel("gold_block", position);
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
                    if(containsVoxelAtOffset(x, y, z))
                    {
                        getVoxelAtOffset(x, y, z).render();
                    }
                    position = null;
                }
            }
        }
    }

    public void createVertices()
    {
        List<Float> verticesList = new ArrayList<Float>();
        List<Float> textureCoordsList = new ArrayList<Float>();
        List<Float> IndicesList = new ArrayList<Float>();
        for(int x = 0; x < World.CHUNK_SIZE; x++)
        {
            for (int y = 0; y < World.CHUNK_SIZE; y++)
            {
                for (int z = 0; z < World.CHUNK_SIZE; z++)
                {
                    if(containsVoxelAtOffset(x, y, z))
                    {
                        if(!containsVoxelAtOffset(x+1, y, z))
                            Collections.addAll(verticesList, VoxelShape.);
                        if(!containsVoxelAtOffset(x+1, y, z))
                            //DO SOMETHING
                        if(!containsVoxelAtOffset(x+1, y, z))
                            //DO SOMETHING
                        if(!containsVoxelAtOffset(x+1, y, z))
                            //DO SOMETHING
                        if(!containsVoxelAtOffset(x+1, y, z))
                            //DO SOMETHING
                        if(!containsVoxelAtOffset(x+1, y, z))
                            //DO SOMETHING
                    }
                }
            }
        }
        RawModel chunkModel = Main.getLoader().loadToVAO();
    }

    public boolean containsVoxelAtOffset(int x, int y, int z)
    {
            return voxels[x][y][z] != null;
    }

    public Voxel getVoxelAtOffset(int x, int y, int z)
    {
        return voxels[x][y][z];
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
