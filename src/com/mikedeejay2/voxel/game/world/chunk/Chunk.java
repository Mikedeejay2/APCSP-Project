package com.mikedeejay2.voxel.game.world.chunk;

import com.mikedeejay2.voxel.engine.graphics.objects.Entity;
import com.mikedeejay2.voxel.game.Main;
import com.mikedeejay2.voxel.game.voxel.Voxel;
import com.mikedeejay2.voxel.game.voxel.VoxelShape;
import com.mikedeejay2.voxel.game.voxel.VoxelTypes;
import com.mikedeejay2.voxel.game.world.chunk.mesh.ChunkMeshGenerator;
import com.mikedeejay2.voxel.game.world.World;
import com.mikedeejay2.voxel.game.world.chunk.mesh.MeshRequest;
import org.joml.Vector3f;

public class Chunk
{
    public short[][][] voxels;

    public Entity chunkEntity;

    public Vector3f chunkLoc;
    public Vector3f chunkCoords;

    public float[] verticesTemp;
    public float[] textureCoordsTemp;
    public int[] indicesTemp;
    public float[] brightnessTemp;

    World instanceWorld;

    Main main;

    public boolean hasLoaded;
    public boolean containsVoxels;
    public boolean shouldUpdateNeighbors;
    public boolean entityShouldBeRemade;
    public boolean shouldRender;
    public boolean isAlreadyBeingCalculated;

    public Chunk(Vector3f chunkLoc, World world)
    {
        instanceWorld = world;
        main = Main.getInstance();
        this.chunkLoc = chunkLoc;
        this.chunkCoords = new Vector3f(chunkLoc.x *  World.CHUNK_SIZE, chunkLoc.y * World.CHUNK_SIZE, chunkLoc.z * World.CHUNK_SIZE);
        this.voxels = new short[World.CHUNK_SIZE][World.CHUNK_SIZE][World.CHUNK_SIZE];
        hasLoaded = false;
        containsVoxels = false;
        shouldRender = false;
    }

    public void populate()
    {
        try
        {
            instanceWorld.populateChunk(this);
            rebuildChunkMesh(true, false);
//            updateNeighbors();
            hasLoaded = true;
        } catch(NullPointerException e) {}
    }

    public void update()
    {

    }

    public void updateNeighbors(boolean immediate)
    {
        Vector3f nextChunkLoc;
        try
        {
            nextChunkLoc = new Vector3f(chunkLoc.x + 1, chunkLoc.y, chunkLoc.z);
            if(instanceWorld.chunkAtChunkLoc(nextChunkLoc))
                instanceWorld.getChunk(nextChunkLoc).rebuildChunkMesh(false, immediate);

            nextChunkLoc = new Vector3f(chunkLoc.x - 1, chunkLoc.y, chunkLoc.z);
            if(instanceWorld.chunkAtChunkLoc(nextChunkLoc))
                instanceWorld.getChunk(nextChunkLoc).rebuildChunkMesh(false, immediate);

            nextChunkLoc = new Vector3f(chunkLoc.x, chunkLoc.y + 1, chunkLoc.z);
            if(instanceWorld.chunkAtChunkLoc(nextChunkLoc))
                instanceWorld.getChunk(nextChunkLoc).rebuildChunkMesh(false, immediate);

            nextChunkLoc = new Vector3f(chunkLoc.x, chunkLoc.y - 1, chunkLoc.z);
            if(instanceWorld.chunkAtChunkLoc(nextChunkLoc))
                instanceWorld.getChunk(nextChunkLoc).rebuildChunkMesh(false, immediate);

            nextChunkLoc = new Vector3f(chunkLoc.x, chunkLoc.y, chunkLoc.z + 1);
            if(instanceWorld.chunkAtChunkLoc(nextChunkLoc))
                instanceWorld.getChunk(nextChunkLoc).rebuildChunkMesh(false, immediate);

            nextChunkLoc = new Vector3f(chunkLoc.x, chunkLoc.y, chunkLoc.z - 1);
            if(instanceWorld.chunkAtChunkLoc(nextChunkLoc))
                instanceWorld.getChunk(nextChunkLoc).rebuildChunkMesh(false, immediate);

            shouldUpdateNeighbors = false;
        } catch(NullPointerException e) {System.out.println("bruh");}

    }

    public void updateNeighborsSmart(boolean immediate, float x, float y, float z)
    {
        try
        {
            if(x == World.CHUNK_SIZE-1)
            {
                rebuildFromChunkLoc(chunkLoc.x + 1, chunkLoc.y, chunkLoc.z, immediate);
            }

            if(x == 0)
            {
                rebuildFromChunkLoc(chunkLoc.x - 1, chunkLoc.y, chunkLoc.z, immediate);
            }

            if(y == World.CHUNK_SIZE-1)
            {
                rebuildFromChunkLoc(chunkLoc.x, chunkLoc.y + 1, chunkLoc.z, immediate);
            }

            if(y == 0)
            {
                rebuildFromChunkLoc(chunkLoc.x, chunkLoc.y - 1, chunkLoc.z, immediate);
            }

            if(z == World.CHUNK_SIZE-1)
            {
                rebuildFromChunkLoc(chunkLoc.x, chunkLoc.y, chunkLoc.z + 1, immediate);
            }

            if(z == 0)
            {
                rebuildFromChunkLoc(chunkLoc.x, chunkLoc.y, chunkLoc.z - 1, immediate);
            }

            if(x == World.CHUNK_SIZE-1 && y == World.CHUNK_SIZE-1)
            {
                rebuildFromChunkLoc(chunkLoc.x + 1, chunkLoc.y + 1, chunkLoc.z, immediate);
            }


            if(x == 0 && y == 0)
            {
                rebuildFromChunkLoc(chunkLoc.x - 1, chunkLoc.y - 1, chunkLoc.z, immediate);
            }

            if(x == 0 && y == World.CHUNK_SIZE-1)
            {
                rebuildFromChunkLoc(chunkLoc.x - 1, chunkLoc.y + 1, chunkLoc.z, immediate);
            }

            if(x == World.CHUNK_SIZE-1 && y == 0)
            {
                rebuildFromChunkLoc(chunkLoc.x + 1, chunkLoc.y - 1, chunkLoc.z, immediate);
            }

            if(z == World.CHUNK_SIZE-1 && y == World.CHUNK_SIZE-1)
            {
                rebuildFromChunkLoc(chunkLoc.x, chunkLoc.y + 1, chunkLoc.z + 1, immediate);
            }

            if(z == 0 && y == 0)
            {
                rebuildFromChunkLoc(chunkLoc.x, chunkLoc.y - 1, chunkLoc.z - 1, immediate);
            }

            if(z == 0 && y == World.CHUNK_SIZE-1)
            {
                rebuildFromChunkLoc(chunkLoc.x, chunkLoc.y + 1, chunkLoc.z - 1, immediate);
            }

            if(z == World.CHUNK_SIZE-1 && y == 0)
            {
                rebuildFromChunkLoc(chunkLoc.x, chunkLoc.y - 1, chunkLoc.z + 1, immediate);
            }

            if(x == World.CHUNK_SIZE-1 && z == World.CHUNK_SIZE-1)
            {
                rebuildFromChunkLoc(chunkLoc.x + 1, chunkLoc.y, chunkLoc.z + 1, immediate);
            }

            if(x == 0 && z == 0)
            {
                rebuildFromChunkLoc(chunkLoc.x - 1, chunkLoc.y, chunkLoc.z - 1, immediate);
            }

            if(x == 0 && z == World.CHUNK_SIZE-1)
            {
                rebuildFromChunkLoc(chunkLoc.x - 1, chunkLoc.y, chunkLoc.z + 1, immediate);
            }

            if(x == World.CHUNK_SIZE-1 && z == 0)
            {
                rebuildFromChunkLoc(chunkLoc.x + 1, chunkLoc.y, chunkLoc.z - 1, immediate);
            }

            if(x == 0 && y == 0 && z == 0)
            {
                rebuildFromChunkLoc(chunkLoc.x - 1, chunkLoc.y - 1, chunkLoc.z - 1, immediate);
            }

            if(x == World.CHUNK_SIZE-1 && y == World.CHUNK_SIZE-1 && z == World.CHUNK_SIZE-1)
            {
                rebuildFromChunkLoc(chunkLoc.x + 1, chunkLoc.y + 1, chunkLoc.z + 1, immediate);
            }

            if(x == World.CHUNK_SIZE-1 && y == 0 && z == 0)
            {
                rebuildFromChunkLoc(chunkLoc.x + 1, chunkLoc.y - 1, chunkLoc.z - 1, immediate);
            }

            if(x == 0 && y == World.CHUNK_SIZE-1 && z == 0)
            {
                rebuildFromChunkLoc(chunkLoc.x - 1, chunkLoc.y + 1, chunkLoc.z - 1, immediate);
            }

            if(x == 0 && y == 0 && z == World.CHUNK_SIZE-1)
            {
                rebuildFromChunkLoc(chunkLoc.x - 1, chunkLoc.y - 1, chunkLoc.z + 1, immediate);
            }

            if(x == World.CHUNK_SIZE-1 && y == World.CHUNK_SIZE-1 && z == 0)
            {
                rebuildFromChunkLoc(chunkLoc.x + 1, chunkLoc.y + 1, chunkLoc.z - 1, immediate);
            }

            if(x == 0 && y == World.CHUNK_SIZE-1 && z == World.CHUNK_SIZE-1)
            {
                rebuildFromChunkLoc(chunkLoc.x - 1, chunkLoc.y + 1, chunkLoc.z + 1, immediate);
            }

            if(x == World.CHUNK_SIZE-1 && y == 0 && z == World.CHUNK_SIZE-1)
            {
                rebuildFromChunkLoc(chunkLoc.x + 1, chunkLoc.y - 1, chunkLoc.z + 1, immediate);
            }

            shouldUpdateNeighbors = false;
        } catch(NullPointerException e) {System.out.println("bruh");}

    }

    public void rebuildFromChunkLoc(float x, float y, float z, boolean immediate)
    {
        Vector3f chunkLoc = new Vector3f(x, y, z);
        if(instanceWorld.chunkAtChunkLoc(chunkLoc))
            instanceWorld.getChunk(chunkLoc).rebuildChunkMeshSmartNeighbor(false, immediate, x, y, z);
    }

    public void rebuildChunkMesh(boolean shouldUpdateNeighbors, boolean immediate)
    {
        if(!immediate) Main.getInstance().getRenderer().genMesh(this, instanceWorld);
        else Main.getInstance().getRenderer().genMeshImmediate(this, instanceWorld);
        if(shouldUpdateNeighbors) updateNeighbors(immediate);
        shouldUpdateNeighbors = false;
    }

    public void rebuildChunkMeshSmartNeighbor(boolean shouldUpdateNeighbors, boolean immediate, float x, float y, float z)
    {
        if(!immediate) Main.getInstance().getRenderer().genMesh(this, instanceWorld);
        else Main.getInstance().getRenderer().genMeshImmediate(this, instanceWorld);
        if(shouldUpdateNeighbors) updateNeighborsSmart(immediate, x, y, z);
        shouldUpdateNeighbors = false;
    }

    public int getCloseness()
    {
        int closeness = 0;
        closeness += Math.abs(chunkLoc.x - instanceWorld.playerChunk.x);
        closeness += Math.abs(chunkLoc.y - instanceWorld.playerChunk.y);
        closeness += Math.abs(chunkLoc.z - instanceWorld.playerChunk.z);
        return closeness;
    }

    public void render()
    {
        if(!hasLoaded) return;
        if(entityShouldBeRemade) ChunkMeshGenerator.createChunkEntity(this);
        if(chunkEntity == null) return;
        if(shouldRender)
        {
            chunkEntity.setPosition(new Vector3f((float) (chunkCoords.x - instanceWorld.playerPosition.x), (float) (chunkCoords.y - instanceWorld.playerPosition.y), (float) (chunkCoords.z - instanceWorld.playerPosition.z)));
            Main.getInstance().getRenderer().processEntity(chunkEntity);
        }
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
        voxels[x][y][z] = (short) VoxelTypes.getIDFromName(name);
        setContainsVoxels(true);
    }

    public boolean containsVoxelAtOffset(int x, int y, int z)
    {
        if(x < 0 || y < 0 || z < 0 || x > World.CHUNK_SIZE-1 || y > World.CHUNK_SIZE-1 || z > World.CHUNK_SIZE-1)
            return false;
        return voxels[x][y][z] != 0;
    }

    public Voxel getVoxelAtOffset(int x, int y, int z)
    {
        return new Voxel(voxels[x][y][z], new Vector3f(chunkCoords.x + x, chunkCoords.y + y, chunkCoords.z + z));
    }

    public int getVoxelIDAtOffset(int x, int y, int z)
    {
        return voxels[x][y][z];
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
        chunkEntity.destroy();
        chunkEntity = null;
//        chunkLoc = null;
//        chunkCoords = null;
        verticesTemp = null;
        textureCoordsTemp = null;
        indicesTemp = null;
        instanceWorld = null;
        hasLoaded = false;
        containsVoxels = false;
    }

    public void removeVoxel(int x, int y, int z)
    {
        voxels[x][y][z] = 0;
        if(!edgeCheck(x, y, z)) rebuildChunkMeshSmartNeighbor(false, true, x, y, z);
        else rebuildChunkMeshSmartNeighbor(true, true, x, y, z);
    }

    private boolean edgeCheck(float x, float y, float z)
    {
        return x == 0 || y == 0 || z == 0 || x == World.CHUNK_SIZE-1 || y == World.CHUNK_SIZE-1 || z == World.CHUNK_SIZE-1;
    }

    private boolean edgeCheckSingle(float x)
    {
        return x == 0 || x == World.CHUNK_SIZE-1;
    }




}
