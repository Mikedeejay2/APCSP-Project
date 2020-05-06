package com.mikedeejay2.voxel.engine.voxel.generators;

import com.mikedeejay2.voxel.game.voxel.VoxelShape;
import com.mikedeejay2.voxel.game.world.Chunk;
import com.mikedeejay2.voxel.game.world.World;
import org.joml.Vector3f;

public class OverworldGenerator
{
    World instanceWorld;

    public OverworldGenerator(World world)
    {
        instanceWorld = world;
    }

    public void populate(Chunk chunk)
    {
        PerlinNoiseGenerator generator = new PerlinNoiseGenerator();
        for (int x = 0; x < World.CHUNK_SIZE; x++)
        {
            for (int z = 0; z < World.CHUNK_SIZE; z++)
            {
                Vector3f position = new Vector3f((chunk.getChunkCoords().x/VoxelShape.VOXEL_SIZE) + x, (chunk.getChunkCoords().y/VoxelShape.VOXEL_SIZE), (chunk.getChunkCoords().z/VoxelShape.VOXEL_SIZE) + z);
                int height = (int)generator.generateHeight((int)position.x, (int)position.z);
                int chunkLevel = (int)Math.floor((float)height / (float)World.CHUNK_SIZE);
                if(chunk.getChunkLoc().y == chunkLevel)
                {
                    while(height > World.CHUNK_SIZE-1) height -= World.CHUNK_SIZE;
                    while(height < 0) height += World.CHUNK_SIZE;
                    for(int i = height; i >= 0; i--)
                    {
                        chunk.addVoxel(x, height - i, z, "dirt");
                    }

                }
                else if(chunk.getChunkLoc().y < chunkLevel)
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
