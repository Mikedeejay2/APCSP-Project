package com.mikedeejay2.apcspfinal.world.generators;

import com.mikedeejay2.apcspfinal.world.noise.LayeredNoiseGenerator;
import com.mikedeejay2.apcspfinal.world.World;
import com.mikedeejay2.apcspfinal.world.chunk.Chunk;
import org.joml.SimplexNoise;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.Random;

public class OverworldGenerator
{
    public static Random random = new Random();
    public static final long seed = random.nextLong()*Long.MAX_VALUE;

    World instanceWorld;

    LayeredNoiseGenerator simplexNoiseGenerator = new LayeredNoiseGenerator(seed);

    public OverworldGenerator(World world)
    {
        instanceWorld = world;
    }

    public void genTerrain(Chunk chunk)
    {
        for (int x = 0; x < World.CHUNK_SIZE; x++)
        {
            for(int z = 0; z < World.CHUNK_SIZE; z++)
            {
                if(chunk.getChunkCoords() == null) return;
                Vector3f position = new Vector3f((chunk.getChunkCoords().x) + x, (chunk.getChunkCoords().y), (chunk.getChunkCoords().z) + z);
                int height = (int) (simplexNoiseGenerator.getLayeredNoise(position.x, position.z, 7));
                int chunkLevel = (int) Math.floor((float) height / (float) World.CHUNK_SIZE);

                if(chunk.getChunkLoc() != null)
                {
                    if(chunk.getChunkLoc().y == chunkLevel)
                    {
                        while(height > World.CHUNK_SIZE - 1) height -= World.CHUNK_SIZE;
                        while(height < 0) height += World.CHUNK_SIZE;

                        for(int i = height; i >= 0; i--) chunk.addVoxelWorldGen(x, height - i, z, "dirt");
                        if(chunk.getChunkLoc().y >= 0)
                        {
                            if(random.nextFloat() < 0.005f) generateTree(chunk, x, height + 1, z);
                            chunk.addVoxelWorldGen(x, height, z, "grass");
                        }
                        else chunk.addVoxelWorldGen(x, height, z, "sand");
                    }
                    else
                        if(chunk.getChunkLoc().y < chunkLevel)
                            for(int y = 0; y < World.CHUNK_SIZE; y++)
                                chunk.addVoxelWorldGen(x, y, z, "stone");
                    if(chunk.getChunkLoc().y < 0)
                        for(int y = 0; y < World.CHUNK_SIZE; y++)
                            if(!chunk.containsVoxelAtOffset(x, y, z)) chunk.addVoxelWorldGen(x, y, z, "water");
                }
            }
        }
    }

    public void genFlat(Chunk chunk)
    {
        for (int x = 0; x < World.CHUNK_SIZE; x++)
            for (int z = 0; z < World.CHUNK_SIZE; z++)
                if(chunk.getChunkLoc().y <= -1)
                    for(int y = 0; y < World.CHUNK_SIZE; y++)
                        chunk.addVoxelWorldGen(x, y, z, "grass");
    }

    public void generateTree(Chunk chunk, int x, int y, int z)
    {
        ArrayList<Vector3f> branchEnds = new ArrayList<Vector3f>();
        int worldX = (int)chunk.getChunkCoords().x+x, worldY = (int)chunk.getChunkCoords().y+y, worldZ = (int)chunk.getChunkCoords().z+z;
        int topHeight = (int)(random.nextFloat()*4)+3;
        int branchCount = (int)(random.nextFloat()*(topHeight*1.5)+2);
        int leafExtend = 2;

        branchEnds.add(new Vector3f(worldX, worldY+topHeight, worldZ));

        for(int height = 0; height < topHeight; height++)
            instanceWorld.addVoxelWorldGen(worldX, worldY + height, worldZ, "wood log");

        for(int i = 0; i < branchCount; i++)
        {
            int branchLength = (int)(random.nextFloat()*(topHeight+1));
            float directionX = (random.nextFloat()*2)-1, directionY = (random.nextFloat()*2)-1, directionZ = (random.nextFloat()*2)-1;

            for(int length = 0; length < branchLength; length++)
            {
                int bx = worldX + (int)(directionX * length), bz = worldZ + (int)(directionZ * length), by = worldY + (int)(directionY * length) + topHeight;
                instanceWorld.addVoxelWorldGen(bx, by, bz, "wood log");
                if(length == branchLength - 1) branchEnds.add(new Vector3f(bx, by, bz));
            }
        }

        for(Vector3f branchEnd : branchEnds)
            for(int leafX = (int) branchEnd.x - leafExtend; leafX < branchEnd.x + leafExtend; leafX++)
                for(int leafY = (int) branchEnd.y - leafExtend; leafY < branchEnd.y + leafExtend; leafY++)
                    for(int leafZ = (int) branchEnd.z - leafExtend; leafZ < branchEnd.z + leafExtend; leafZ++)
                        instanceWorld.addVoxelWorldGenNoOverride(leafX, leafY, leafZ, "leaves");
    }

    public static long getSeed()
    {
        return seed;
    }
}
