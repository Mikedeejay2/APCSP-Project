package com.mikedeejay2.voxel.game.world.generators;

import com.mikedeejay2.voxel.engine.voxel.generators.PerlinNoiseGenerator;
import com.mikedeejay2.voxel.game.voxel.VoxelShape;
import com.mikedeejay2.voxel.game.world.chunk.Chunk;
import com.mikedeejay2.voxel.game.world.World;
import org.joml.Vector3f;

import java.util.Arrays;

public class OverworldGenerator
{
    World instanceWorld;

    public OverworldGenerator(World world)
    {
        instanceWorld = world;
    }

    public void genTerrain(Chunk chunk)
    {
        PerlinNoiseGenerator generator = new PerlinNoiseGenerator();
        for (int x = 0; x < World.CHUNK_SIZE; x++)
        {
            for (int z = 0; z < World.CHUNK_SIZE; z++)
            {
                if(chunk.getChunkCoords() == null) return;
                Vector3f position = new Vector3f((chunk.getChunkCoords().x) + x, (chunk.getChunkCoords().y), (chunk.getChunkCoords().z) + z);
                int height = (int)generator.generateHeight((int)position.x, (int)position.z);
                int chunkLevel = (int)Math.floor((float)height / (float)World.CHUNK_SIZE);
                if(chunk.getChunkLoc() != null && chunk.getChunkLoc().y == chunkLevel && chunkLevel >= 0)
                {
                    while(height > World.CHUNK_SIZE-1) height -= World.CHUNK_SIZE;
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
