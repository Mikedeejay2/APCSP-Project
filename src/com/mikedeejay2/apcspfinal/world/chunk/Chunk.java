package com.mikedeejay2.apcspfinal.world.chunk;

import com.mikedeejay2.apcspfinal.Main;
import com.mikedeejay2.apcspfinal.graphics.objects.Entity;
import com.mikedeejay2.apcspfinal.utils.DirectionEnum;
import com.mikedeejay2.apcspfinal.voxel.Voxel;
import com.mikedeejay2.apcspfinal.world.World;
import com.mikedeejay2.apcspfinal.voxel.VoxelTypes;
import org.joml.Vector3f;

import static com.mikedeejay2.apcspfinal.world.World.CHUNK_SIZE;

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
        this.chunkCoords = new Vector3f(chunkLoc.x *  CHUNK_SIZE, chunkLoc.y * CHUNK_SIZE, chunkLoc.z * CHUNK_SIZE);
        hasLoaded = false;
        containsVoxels = false;
        shouldRender = false;
    }

    public void initVoxelArray()
    {
        this.voxels = new short[CHUNK_SIZE][CHUNK_SIZE][CHUNK_SIZE];
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

    }

    public void updateNeighborsSmart(boolean immediate, float x, float y, float z)
    {
        if(x == CHUNK_SIZE-1                                          ) rebuildFromChunkLoc((chunkLoc.x + 1), (chunkLoc.y    ), (chunkLoc.z    ), immediate);
        if(x == 0                                                     ) rebuildFromChunkLoc((chunkLoc.x - 1), (chunkLoc.y    ), (chunkLoc.z    ), immediate);
        if(                     y == CHUNK_SIZE-1                     ) rebuildFromChunkLoc((chunkLoc.x    ), (chunkLoc.y + 1), (chunkLoc.z    ), immediate);
        if(                     y == 0                                ) rebuildFromChunkLoc((chunkLoc.x    ), (chunkLoc.y - 1), (chunkLoc.z    ), immediate);
        if(                                          z == CHUNK_SIZE-1) rebuildFromChunkLoc((chunkLoc.x    ), (chunkLoc.y    ), (chunkLoc.z + 1), immediate);
        if(                                          z == 0           ) rebuildFromChunkLoc((chunkLoc.x    ), (chunkLoc.y    ), (chunkLoc.z - 1), immediate);
        if(x == CHUNK_SIZE-1 && y == CHUNK_SIZE-1                     ) rebuildFromChunkLoc((chunkLoc.x + 1), (chunkLoc.y + 1), (chunkLoc.z    ), immediate);
        if(x == 0            && y == 0                                ) rebuildFromChunkLoc((chunkLoc.x - 1), (chunkLoc.y - 1), (chunkLoc.z    ), immediate);
        if(x == 0            && y == CHUNK_SIZE-1                     ) rebuildFromChunkLoc((chunkLoc.x - 1), (chunkLoc.y + 1), (chunkLoc.z    ), immediate);
        if(x == CHUNK_SIZE-1 && y == 0                                ) rebuildFromChunkLoc((chunkLoc.x + 1), (chunkLoc.y - 1), (chunkLoc.z    ), immediate);
        if(z == CHUNK_SIZE-1 && y == CHUNK_SIZE-1                     ) rebuildFromChunkLoc((chunkLoc.x    ), (chunkLoc.y + 1), (chunkLoc.z + 1), immediate);
        if(z == 0            && y == 0                                ) rebuildFromChunkLoc((chunkLoc.x    ), (chunkLoc.y - 1), (chunkLoc.z - 1), immediate);
        if(z == 0            && y == CHUNK_SIZE-1                     ) rebuildFromChunkLoc((chunkLoc.x    ), (chunkLoc.y + 1), (chunkLoc.z - 1), immediate);
        if(z == CHUNK_SIZE-1 && y == 0                                ) rebuildFromChunkLoc((chunkLoc.x    ), (chunkLoc.y - 1), (chunkLoc.z + 1), immediate);
        if(x == CHUNK_SIZE-1                      && z == CHUNK_SIZE-1) rebuildFromChunkLoc((chunkLoc.x + 1), (chunkLoc.y    ), (chunkLoc.z + 1), immediate);
        if(x == 0                                 && z == 0           ) rebuildFromChunkLoc((chunkLoc.x - 1), (chunkLoc.y    ), (chunkLoc.z - 1), immediate);
        if(x == 0                                 && z == CHUNK_SIZE-1) rebuildFromChunkLoc((chunkLoc.x - 1), (chunkLoc.y    ), (chunkLoc.z + 1), immediate);
        if(x == CHUNK_SIZE-1                      && z == 0           ) rebuildFromChunkLoc((chunkLoc.x + 1), (chunkLoc.y    ), (chunkLoc.z - 1), immediate);
        if(x == 0            && y == 0            && z == 0           ) rebuildFromChunkLoc((chunkLoc.x - 1), (chunkLoc.y - 1), (chunkLoc.z - 1), immediate);
        if(x == CHUNK_SIZE-1 && y == 0            && z == 0           ) rebuildFromChunkLoc((chunkLoc.x + 1), (chunkLoc.y - 1), (chunkLoc.z - 1), immediate);
        if(x == 0            && y == CHUNK_SIZE-1 && z == 0           ) rebuildFromChunkLoc((chunkLoc.x - 1), (chunkLoc.y + 1), (chunkLoc.z - 1), immediate);
        if(x == 0            && y == 0            && z == CHUNK_SIZE-1) rebuildFromChunkLoc((chunkLoc.x - 1), (chunkLoc.y - 1), (chunkLoc.z + 1), immediate);
        if(x == CHUNK_SIZE-1 && y == 0            && z == CHUNK_SIZE-1) rebuildFromChunkLoc((chunkLoc.x + 1), (chunkLoc.y - 1), (chunkLoc.z + 1), immediate);
        if(x == CHUNK_SIZE-1 && y == CHUNK_SIZE-1 && z == 0           ) rebuildFromChunkLoc((chunkLoc.x + 1), (chunkLoc.y + 1), (chunkLoc.z - 1), immediate);
        if(x == 0            && y == CHUNK_SIZE-1 && z == CHUNK_SIZE-1) rebuildFromChunkLoc((chunkLoc.x - 1), (chunkLoc.y + 1), (chunkLoc.z + 1), immediate);
        if(x == CHUNK_SIZE-1 && y == CHUNK_SIZE-1 && z == CHUNK_SIZE-1) rebuildFromChunkLoc((chunkLoc.x + 1), (chunkLoc.y + 1), (chunkLoc.z + 1), immediate);
        shouldUpdateNeighbors = false;
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
        if(entityShouldBeRemade && containsVoxels)
        {
            main.getEntityCreator().addChunk(this);
        }
        if(chunkEntity == null) return;
        if(shouldRender)
        {
            chunkEntity.setPosition(new Vector3f((float) (chunkCoords.x - instanceWorld.playerPosition.x), (float) (chunkCoords.y - instanceWorld.playerPosition.y), (float) (chunkCoords.z - instanceWorld.playerPosition.z)));
            Main.getInstance().getRenderer().processEntity(chunkEntity);
        }
    }

    public void addVoxelWorldGen(int x, int y, int z, String name)
    {
        if(!containsVoxels) initVoxelArray();
        voxels[x][y][z] = (byte) VoxelTypes.getIDFromName(name);
        setContainsVoxels(true);
    }

    public void addVoxelNeighbor(DirectionEnum direction, String voxelName, int x, int y, int z)
    {
        Chunk neighborChunk = null;
        switch(direction)
        {
            case UP: neighborChunk = instanceWorld.getChunkFromChunkLoc(new Vector3f(chunkLoc.x, chunkLoc.y + 1, chunkLoc.z)); break;
            case DOWN: neighborChunk = instanceWorld.getChunkFromChunkLoc(new Vector3f(chunkLoc.x, chunkLoc.y - 1, chunkLoc.z)); break;
            case WEST: neighborChunk = instanceWorld.getChunkFromChunkLoc(new Vector3f(chunkLoc.x + 1, chunkLoc.y, chunkLoc.z)); break;
            case EAST: neighborChunk = instanceWorld.getChunkFromChunkLoc(new Vector3f(chunkLoc.x - 1, chunkLoc.y, chunkLoc.z)); break;
            case NORTH: neighborChunk = instanceWorld.getChunkFromChunkLoc(new Vector3f(chunkLoc.x, chunkLoc.y, chunkLoc.z+1)); break;
            case SOUTH: neighborChunk = instanceWorld.getChunkFromChunkLoc(new Vector3f(chunkLoc.x, chunkLoc.y, chunkLoc.z-1)); break;
        }

        if(neighborChunk == null) return;
        if(x < 0) x += CHUNK_SIZE; if(x >= CHUNK_SIZE) x -= CHUNK_SIZE;
        if(y < 0) y += CHUNK_SIZE; if(y >= CHUNK_SIZE) y -= CHUNK_SIZE;
        if(z < 0) z += CHUNK_SIZE; if(z >= CHUNK_SIZE) z -= CHUNK_SIZE;

        neighborChunk.addVoxelWorldGen(x, y, z, voxelName);
    }

    public boolean containsVoxelAtOffset(int x, int y, int z)
    {
        if(invalidCheck(x, y, z)) return false;
        return voxels[x][y][z] != 0;
    }

    public boolean containsVoxelAtOffset(int x, int y, int z, boolean ignoreLiquid)
    {
        if(invalidCheck(x, y, z)) return false;
        int id = voxels[x][y][z];
        if(id == 0) return false;
        Voxel voxel = VoxelTypes.getFromID(id);
        if(voxel == null) return true;
        if(ignoreLiquid) return !voxel.isLiquid();
        return voxel.isLiquid();
    }

    public boolean containsVoxelAtOffsetSolid(int x, int y, int z, boolean solid)
    {
        if(invalidCheck(x, y, z)) return false;
        int id = voxels[x][y][z];
        if(id == 0) return false;
        Voxel voxel = VoxelTypes.getFromID(id);
        if(voxel == null) return true;
        if(!solid) return !voxel.isSolid();
        return voxel.isSolid();
    }

    public boolean invalidCheck(int x, int y, int z)
    {
        return x < 0 || y < 0 || z < 0 || x > CHUNK_SIZE-1 || y > CHUNK_SIZE-1 || z > CHUNK_SIZE-1 || !containsVoxels;
    }

    public Voxel getVoxelAtOffset(int x, int y, int z)
    {
        if(!containsVoxels || voxels[x][y][z] == 0) return null;
        return VoxelTypes.getFromID(voxels[x][y][z]);
    }

    public int getVoxelIDAtOffset(int x, int y, int z)
    {
        if(!containsVoxels) return 0;
        return voxels[x][y][z];
    }

    public String getVoxelNameAtOffset(int x, int y, int z)
    {
        if(!containsVoxels) return null;
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
        verticesTemp = null;
        textureCoordsTemp = null;
        indicesTemp = null;
        instanceWorld = null;
        hasLoaded = false;
        containsVoxels = false;
    }

    public void removeVoxel(int x, int y, int z)
    {
        if(!containsVoxels) return;
        voxels[x][y][z] = 0;
        if(!edgeCheck(x, y, z)) rebuildChunkMeshSmartNeighbor(false, true, x, y, z);
        else rebuildChunkMeshSmartNeighbor(true, true, x, y, z);
    }

    public void addVoxel(int x, int y, int z, String name)
    {
        if(!containsVoxels) initVoxelArray();
        voxels[x][y][z] = (byte) VoxelTypes.getIDFromName(name);
        setContainsVoxels(true);
        if(!edgeCheck(x, y, z)) rebuildChunkMeshSmartNeighbor(false, true, x, y, z);
        else rebuildChunkMeshSmartNeighbor(true, true, x, y, z);
    }

    private boolean edgeCheck(float x, float y, float z)
    {
        return x == 0 || y == 0 || z == 0 || x == CHUNK_SIZE-1 || y == CHUNK_SIZE-1 || z == CHUNK_SIZE-1;
    }

    private boolean edgeCheckSingle(float x)
    {
        return x == 0 || x == CHUNK_SIZE-1;
    }

    public boolean inBounds(int x, int y, int z)
    {
        return x < CHUNK_SIZE && y < CHUNK_SIZE && z < CHUNK_SIZE && x > -1 && y > -1 && z > -1;
    }

    public boolean isSolid(int x, int y, int z)
    {
        if(!containsVoxels) return false;
        if(voxels[x][y][z] == 0) return true;
        return VoxelTypes.getFromID(voxels[x][y][z]).isSolid();
    }


    public boolean isLiquid(int x, int y, int z)
    {
        if(!containsVoxels) return false;
        if(voxels[x][y][z] == 0) return false;
        return VoxelTypes.getFromID(voxels[x][y][z]).isLiquid();
    }
}
