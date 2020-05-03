package com.mikedeejay2.voxel.game.world.generators;

import com.mikedeejay2.voxel.engine.utils.PerlinNoiseGenerator;
import com.mikedeejay2.voxel.game.voxel.Voxel;
import com.mikedeejay2.voxel.game.world.Chunk;
import com.mikedeejay2.voxel.game.world.World;
import org.joml.Vector3f;

import java.util.Random;

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
                Vector3f position = new Vector3f(chunk.getChunkCoords().x + x, chunk.getChunkCoords().y, chunk.getChunkCoords().z + z);
                int height = (int)generator.generateHeight((int)position.x, (int)position.z);
                int chunkLevel = (int)Math.floor((float)height / (float)World.CHUNK_SIZE);
                while(height > 15) height -= 16;
                while(height < 0) height += 16;
                Vector3f pos = new Vector3f(position.x, height, position.z);
                if(chunk.getChunkLoc().y == chunkLevel)
                {
                    for(int i = height; i >= 0; i--)
                        chunk.voxels[x][height-i][z] = new Voxel("gold_block", pos);

                }
                else if(chunk.getChunkLoc().y < chunkLevel)
                {
                    for(int y = 0; y < World.CHUNK_SIZE; y++) chunk.voxels[x][y][z] = new Voxel("gold_block", pos);
                }
            }
        }
    }

}
