package com.mikedeejay2.apcspfinal.world.generators;

import com.mikedeejay2.apcspfinal.noise.LayeredNoiseGenerator;
import com.mikedeejay2.apcspfinal.world.World;
import com.mikedeejay2.apcspfinal.world.chunk.Chunk;
import org.joml.SimplexNoise;
import org.joml.Vector3f;

public class OverworldGenerator
{
    World instanceWorld;

    SimplexNoise huh;
    LayeredNoiseGenerator simplexNoiseGenerator = new LayeredNoiseGenerator(12093);

    public OverworldGenerator(World world)
    {
        instanceWorld = world;
    }

    public void genTerrain(Chunk chunk)
    {
        for (int x = 0; x < World.CHUNK_SIZE; x++)
        {
            for (int z = 0; z < World.CHUNK_SIZE; z++)
            {
                if(chunk.getChunkCoords() == null) return;
                if(chunk.getChunkLoc().y >= 0)
                {
                    Vector3f position = new Vector3f((chunk.getChunkCoords().x) + x, (chunk.getChunkCoords().y), (chunk.getChunkCoords().z) + z);
                    int height = (int)(simplexNoiseGenerator.getLayeredNoise(position.x, position.z, 7));
                    int chunkLevel = (int)Math.floor((float)height / (float)World.CHUNK_SIZE);
                    if(chunk.getChunkLoc() != null && chunk.getChunkLoc().y == chunkLevel)
                    {
                        while(height > World.CHUNK_SIZE - 1) height -= World.CHUNK_SIZE;
                        while(height < 0) height += World.CHUNK_SIZE;
                        for(int i = height; i >= 0; i--)
                        {
                            chunk.addVoxel(x, height - i, z, "dirt");
                        }
                    }
                    else
                    {
                        if(chunk.getChunkLoc() != null && chunk.getChunkLoc().y < chunkLevel || chunk.getChunkLoc().y == -1)
                        {
                            for(int y = 0; y < World.CHUNK_SIZE; y++)
                            {
                                chunk.addVoxel(x, y, z, "stone");
                            }
                        }
                    }
                }
                else
                {
                    for(int y = 0; y < World.CHUNK_SIZE; y++)
                    {
                        chunk.addVoxel(x, y, z, "stone");
                    }
                }
            }
        }
    }

    public void genFlat(Chunk chunk)
    {
        for (int x = 0; x < World.CHUNK_SIZE; x++)
        {
            for (int z = 0; z < World.CHUNK_SIZE; z++)
            {
                if(chunk.getChunkLoc().y <= -1)
                {
                    for(int y = 0; y < World.CHUNK_SIZE; y++)
                    {
                        chunk.addVoxel(x, y, z, "stone");
                    }
                }
            }
        }
    }

}