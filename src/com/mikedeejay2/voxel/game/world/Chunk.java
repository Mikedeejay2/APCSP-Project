package com.mikedeejay2.voxel.game.world;

import com.mikedeejay2.voxel.engine.graphics.objects.Entity;
import com.mikedeejay2.voxel.game.Main;
import com.mikedeejay2.voxel.game.voxel.Voxel;
import com.mikedeejay2.voxel.game.voxel.VoxelShape;
import com.mikedeejay2.voxel.game.voxel.VoxelTypes;
import com.mikedeejay2.voxel.engine.voxel.generators.ChunkMeshGenerator;
import org.joml.Vector3f;

public class Chunk
{
    public int[][][] voxels;

    public Entity chunkEntity;

    public Vector3f chunkLoc;
    public Vector3f chunkCoords;

    public float[] verticesTemp;
    public float[] textureCoordsTemp;
    public int[] indicesTemp;
    public float[] brightnessTemp;

    World instanceWorld;

    boolean hasLoaded;
    boolean containsVoxels;

    public Chunk(Vector3f chunkLoc, World world)
    {
        this.chunkLoc = chunkLoc;
        this.chunkCoords = new Vector3f(chunkLoc.x *  World.CHUNK_SIZE* VoxelShape.VOXEL_SIZE, chunkLoc.y * World.CHUNK_SIZE* VoxelShape.VOXEL_SIZE, chunkLoc.z * World.CHUNK_SIZE* VoxelShape.VOXEL_SIZE);
        this.voxels = new int[World.CHUNK_SIZE][World.CHUNK_SIZE][World.CHUNK_SIZE];
        instanceWorld = world;
        hasLoaded = false;
        containsVoxels = false;
    }

    public void populate()
    {
        instanceWorld.populateChunk(this);
        verticesTemp = ChunkMeshGenerator.createVertices(this);
        textureCoordsTemp = ChunkMeshGenerator.createTextureCoords(this);
        indicesTemp = ChunkMeshGenerator.createIndices(this);
        brightnessTemp = ChunkMeshGenerator.createBrightness(this);
        hasLoaded = true;
    }

    public void render()
    {
        if(!hasLoaded) return;
        if(chunkEntity == null) ChunkMeshGenerator.createChunkEntity(this);
        if(containsVoxels)
        Main.getInstance().getRenderer().processEntity(chunkEntity);
//        for(int x = 0; x < World.CHUNK_SIZE; x++)
//        {
//            for(int y = 0; y < World.CHUNK_SIZE; y++)
//            {
//                for(int z = 0; z < World.CHUNK_SIZE; z++)
//                {
//                    Vector3f position = new Vector3f(chunkCoords.x + x, chunkCoords.y + y, chunkCoords.z + z);
//                    if(containsVoxelAtOffset(x, y, z))
//                    {
//                        getVoxelAtOffset(x, y, z).render();
//                    }
//                    position = null;
//                }
//            }
//        }
    }

    public void addVoxel(int x, int y, int z, String name)
    {
        voxels[x][y][z] = VoxelTypes.getIDFromName(name);
        setContainsVoxels(true);
    }

    public boolean containsVoxelAtOffset(int x, int y, int z)
    {
        if(x < 0 || y < 0 || z < 0 || x > World.CHUNK_SIZE-1 || y > World.CHUNK_SIZE-1 || z > World.CHUNK_SIZE-1) return false;
            return voxels[x][y][z] != 0;
    }

    public Voxel getVoxelAtOffset(int x, int y, int z)
    {
        return new Voxel(voxels[x][y][z], new Vector3f(chunkCoords.x + x, chunkCoords.y + y, chunkCoords.z + z));
    }

    public String getVoxelNameAtOffset(int x, int y, int z)
    {
        return VoxelTypes.getNameFromID(voxels[x][y][z]);
    }

    public Vector3f getChunkLoc()
    {
        return chunkLoc;
    }

    public Vector3f getChunkCoords()
    {
        return chunkCoords;
    }

    public Chunk getChunkDown()
    {
        return instanceWorld.getChunkFromChunkLoc(new Vector3f(chunkLoc.x, chunkLoc.y-1, chunkLoc.z));
    }

    public Chunk getChunkUp()
    {
        return instanceWorld.getChunkFromChunkLoc(new Vector3f(chunkLoc.x, chunkLoc.y+1, chunkLoc.z));
    }

    public void setContainsVoxels(boolean containsVoxels)
    {
        this.containsVoxels = containsVoxels;
    }

    public void destroy()
    {
        voxels = null;
        chunkEntity.destroy();
        chunkLoc = null;
        chunkCoords = null;
        verticesTemp = null;
        textureCoordsTemp = null;
        indicesTemp = null;
        instanceWorld = null;
        hasLoaded = false;
        containsVoxels = false;
    }
}
