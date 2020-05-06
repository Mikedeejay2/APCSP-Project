package com.mikedeejay2.voxel.engine.voxel.generators;

import com.mikedeejay2.voxel.engine.graphics.models.RawModel;
import com.mikedeejay2.voxel.engine.graphics.models.TexturedModel;
import com.mikedeejay2.voxel.engine.graphics.objects.Entity;
import com.mikedeejay2.voxel.engine.graphics.textures.ModelTexture;
import com.mikedeejay2.voxel.engine.utils.DirectionEnum;
import com.mikedeejay2.voxel.game.Main;
import com.mikedeejay2.voxel.game.voxel.VoxelShape;
import com.mikedeejay2.voxel.game.voxel.VoxelTypes;
import com.mikedeejay2.voxel.game.world.Chunk;
import com.mikedeejay2.voxel.game.world.World;
import org.joml.Vector3d;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class ChunkMeshGenerator
{
    public static Entity createChunkEntity(Chunk chunk)
    {
        if(chunk.verticesTemp == null || chunk.textureCoordsTemp == null || chunk.indicesTemp == null || chunk.brightnessTemp == null) return null;
        RawModel model = Main.getLoader().loadToVAO(chunk.verticesTemp, chunk.textureCoordsTemp, chunk.indicesTemp, chunk.brightnessTemp);

        chunk.shouldRender = chunk.verticesTemp.length >= 12;

        chunk.verticesTemp = null;
        chunk.textureCoordsTemp = null;
        chunk.indicesTemp = null;
        chunk.brightnessTemp = null;

        String name = generateTemporaryBlockName(chunk);

        ModelTexture modelTexture = VoxelTypes.getTexture(name);
        TexturedModel texturedModel = new TexturedModel(model, modelTexture);
        Entity entity = new Entity(texturedModel, chunk.chunkCoords);

        if(chunk.chunkEntity != null) chunk.chunkEntity.destroy();
        chunk.chunkEntity = null;
        chunk.chunkEntity = entity;
        chunk.entityShouldBeRemade = false;
        return entity;
    }







    public static float[] createVertices(World world, Chunk chunk, boolean shouldUpdateNeighbors)
    {
        if(!chunk.containsVoxels) return new float[0];
        List<Float> verticesList = new ArrayList<Float>();
        for (int x = 0; x < World.CHUNK_SIZE; x++)
        {
            for (int y = 0; y < World.CHUNK_SIZE; y++)
            {
                for (int z = 0; z < World.CHUNK_SIZE; z++)
                {
                    if (chunk.containsVoxelAtOffset(x, y, z))
                    {
                        if (!edgeCheck(x, y, z))
                            createVertexSlice(chunk, x, y, z, verticesList);
                        else
                            createVertexSliceEdgeCase(world, chunk, x, y, z, verticesList, shouldUpdateNeighbors);
                    }
                }
            }
        }
        float[] vertices = new float[verticesList.size()];
        for(int i = 0; i < vertices.length; i++) vertices[i] = verticesList.get(i);
        verticesList.clear(); verticesList = null;
        return vertices;
    }









    public static float[] createTextureCoords(World world, Chunk chunk, boolean shouldUpdateNeighbors)
    {
        if(!chunk.containsVoxels) return new float[0];
        List<Float> textureCoordsList = new ArrayList<Float>();
        for(int x = 0; x < World.CHUNK_SIZE; x++)
        {
            for (int y = 0; y < World.CHUNK_SIZE; y++)
            {
                for (int z = 0; z < World.CHUNK_SIZE; z++)
                {
                    if (chunk.containsVoxelAtOffset(x, y, z))
                    {
                        if (!edgeCheck(x, y, z))
                            createTextureCoordSlice(chunk, textureCoordsList, x, y, z);
                        else
                            createTextureCoordSliceEdgeCase(world, chunk, textureCoordsList, x, y, z, shouldUpdateNeighbors);
                    }
                }
            }
        }
        float[] textureCoords = new float[textureCoordsList.size()];
        for(int i = 0; i < textureCoords.length; i++) textureCoords[i] = textureCoordsList.get(i);
        textureCoordsList.clear();
        textureCoordsList = null;
        return textureCoords;
    }


    public static int[] createIndices(World world, Chunk chunk, boolean shouldUpdateNeighbors)
    {
        if(!chunk.containsVoxels) return new int[0];
        List<Integer> indicesList = new ArrayList<Integer>();
        for(int x = 0; x < World.CHUNK_SIZE; x++)
        {
            for (int y = 0; y < World.CHUNK_SIZE; y++)
            {
                for (int z = 0; z < World.CHUNK_SIZE; z++)
                {
                    if (chunk.containsVoxelAtOffset(x, y, z))
                    {
                        if (!edgeCheck(x, y, z))
                            createIndexSlice(chunk, indicesList, x, y, z);
                        else
                            createIndexSliceEdgeCase(world, chunk, indicesList, x, y, z, shouldUpdateNeighbors);
                    }
                }
            }
        }
        int[] indices = new int[indicesList.size()];
        for(int i = 0; i < indices.length; i++) indices[i] = indicesList.get(i);
        indicesList.clear();
        indicesList = null;
        return indices;
    }


    public static float[] createBrightness(World world, Chunk chunk, boolean shouldUpdateNeighbors)
    {
        if(!chunk.containsVoxels) return new float[0];
        List<Float> brightnessList = new ArrayList<Float>();
        for(int x = 0; x < World.CHUNK_SIZE; x++)
        {
            for (int y = 0; y < World.CHUNK_SIZE; y++)
            {
                for (int z = 0; z < World.CHUNK_SIZE; z++)
                {
                    if (chunk.containsVoxelAtOffset(x, y, z))
                    {
                        if (!edgeCheck(x, y, z))
                            createBrightnessSlice(world, chunk, brightnessList, x, y, z);
                        else
                            createBrightnessSliceEdgeCase(world, chunk, brightnessList, x, y, z, shouldUpdateNeighbors);
                    }
                }
            }
        }
        float[] brightness = new float[brightnessList.size()];
        for(int i = 0; i < brightness.length; i++) brightness[i] = brightnessList.get(i);
        brightnessList.clear();
        brightnessList = null;
        return brightness;
    }


    private static boolean edgeCheck(float x, float y, float z)
    {
        return x == 0 || y == 0 || z == 0 || x == World.CHUNK_SIZE-1 || y == World.CHUNK_SIZE-1 || z == World.CHUNK_SIZE-1;
    }

    private static boolean edgeCheckSingle(float x)
    {
        return x == 0 || x == World.CHUNK_SIZE-1;
    }


    private static final float AO_DEFAULT = 0.2f;

    private static void createLightValue(World world, Chunk chunk, float value, List<Float> brightnessList, int x, int y, int z, boolean edge, DirectionEnum direction)
    {
        if(!edge)
        {
            for (int i = 0; i < VoxelShape.getBrightnessSingleSide().length; i++)
            {
                //genBrightnessWithChunkLoc(world, chunk, value, (int) (chunk.chunkCoords.x + x), (int) (chunk.chunkCoords.y + y), (int) (chunk.chunkCoords.z + z), i, brightnessList, direction, -5.5f);
                genBrightness(chunk, value, x, y, z, i, brightnessList, direction, 0.2f);
                //brightnessList.add((float)x/World.CHUNK_SIZE);
                //brightnessList.add(value);
            }
        }
        else
        {
            for (int i = 0; i < VoxelShape.getBrightnessSingleSide().length; i++)
            {
                genBrightnessWithChunkLoc(world, chunk, value, (int) (chunk.chunkCoords.x + x), (int) (chunk.chunkCoords.y + y), (int) (chunk.chunkCoords.z + z), i, brightnessList, direction, 0.2f);
            }
        }
    }

    private static void genBrightnessWithChunkLoc(World world, Chunk chunk, float value, int x, int y, int z, int index, List<Float> brightnessList, DirectionEnum direction, float AO)
    {
        switch (direction)
        {
            case WEST: //           X+1
                switch (index)
                {
                    case 0: case 1: case 2:
                    if(world.isVoxelAtCoordinate(x+1, y+1, z)) brightnessList.add(value - AO); else
                    if(world.isVoxelAtCoordinate(x+1, y+1, z-1)) brightnessList.add(value - AO); else
                    if(world.isVoxelAtCoordinate(x+1, y, z-1)) brightnessList.add(value - AO); else
                        brightnessList.add(value);
                    break;
                    case 3: case 4: case 5:
                    if(world.isVoxelAtCoordinate(x+1, y-1, z)) brightnessList.add(value - AO); else
                    if(world.isVoxelAtCoordinate(x+1, y-1, z-1)) brightnessList.add(value - AO); else
                    if(world.isVoxelAtCoordinate(x+1, y, z-1)) brightnessList.add(value - AO); else
                        brightnessList.add(value);
                    break;
                    case 6: case 7: case 8:
                    if(world.isVoxelAtCoordinate(x+1, y-1, z)) brightnessList.add(value - AO); else
                    if(world.isVoxelAtCoordinate(x+1, y-1, z+1)) brightnessList.add(value - AO); else
                    if(world.isVoxelAtCoordinate(x+1, y, z+1)) brightnessList.add(value - AO); else
                        brightnessList.add(value);
                    break;
                    case 9: case 10: case 11:
                    if(world.isVoxelAtCoordinate(x+1, y+1, z)) brightnessList.add(value - AO); else
                    if(world.isVoxelAtCoordinate(x+1, y+1, z+1)) brightnessList.add(value - AO); else
                    if(world.isVoxelAtCoordinate(x+1, y, z+1)) brightnessList.add(value - AO); else
                        brightnessList.add(value);
                    break;
                }
                break;
            case EAST: //         X-1
                switch (index)
                {
                    case 0: case 1: case 2:
                    if(world.isVoxelAtCoordinate(x-1, y+1, z)) brightnessList.add(value - AO); else
                    if(world.isVoxelAtCoordinate(x-1, y+1, z-1)) brightnessList.add(value - AO); else
                    if(world.isVoxelAtCoordinate(x-1, y, z-1)) brightnessList.add(value - AO); else
                        brightnessList.add(value);
                    break;
                    case 3: case 4: case 5:
                    if(world.isVoxelAtCoordinate(x-1, y-1, z)) brightnessList.add(value - AO); else
                    if(world.isVoxelAtCoordinate(x-1, y-1, z-1)) brightnessList.add(value - AO); else
                    if(world.isVoxelAtCoordinate(x-1, y, z-1)) brightnessList.add(value - AO); else
                        brightnessList.add(value);
                    break;
                    case 6: case 7: case 8:
                    if(world.isVoxelAtCoordinate(x-1, y-1, z)) brightnessList.add(value - AO); else
                    if(world.isVoxelAtCoordinate(x-1, y-1, z+1)) brightnessList.add(value - AO); else
                    if(world.isVoxelAtCoordinate(x-1, y, z+1)) brightnessList.add(value - AO); else
                        brightnessList.add(value);
                    break;
                    case 9: case 10: case 11:
                    if(world.isVoxelAtCoordinate(x-1, y+1, z)) brightnessList.add(value - AO); else
                    if(world.isVoxelAtCoordinate(x-1, y+1, z+1)) brightnessList.add(value - AO); else
                    if(world.isVoxelAtCoordinate(x-1, y, z+1)) brightnessList.add(value - AO); else
                        brightnessList.add(value);
                    break;
                }
                break;
            case UP: //          Y+1
                switch (index)
                {
                    case 0: case 1: case 2:
                    if(world.isVoxelAtCoordinate(x-1, y+1, z)) brightnessList.add(value - AO); else
                    if(world.isVoxelAtCoordinate(x-1, y+1, z+1)) brightnessList.add(value - AO); else
                    if(world.isVoxelAtCoordinate(x, y+1, z+1)) brightnessList.add(value - AO); else
                        brightnessList.add(value);
                    break;
                    case 3: case 4: case 5:
                    if(world.isVoxelAtCoordinate(x-1, y+1, z)) brightnessList.add(value - AO); else
                    if(world.isVoxelAtCoordinate(x-1, y+1, z-1)) brightnessList.add(value - AO); else
                    if(world.isVoxelAtCoordinate(x, y+1, z-1)) brightnessList.add(value - AO); else
                        brightnessList.add(value);
                    break;
                    case 6: case 7: case 8:
                    if(world.isVoxelAtCoordinate(x+1, y+1, z)) brightnessList.add(value - AO); else
                    if(world.isVoxelAtCoordinate(x+1, y+1, z-1)) brightnessList.add(value - AO); else
                    if(world.isVoxelAtCoordinate(x, y+1, z-1)) brightnessList.add(value - AO); else
                        brightnessList.add(value);
                    break;
                    case 9: case 10: case 11:
                    if(world.isVoxelAtCoordinate(x+1, y+1, z)) brightnessList.add(value - AO); else
                    if(world.isVoxelAtCoordinate(x+1, y+1, z+1)) brightnessList.add(value - AO); else
                    if(world.isVoxelAtCoordinate(x, y+1, z+1)) brightnessList.add(value - AO); else
                        brightnessList.add(value);
                    break;
                }
                break;
            case DOWN: //           Y-1
                switch (index)
                {
                    case 0: case 1: case 2:
                    if(world.isVoxelAtCoordinate(x-1, y-1, z)) brightnessList.add(value - AO); else
                    if(world.isVoxelAtCoordinate(x-1, y-1, z+1)) brightnessList.add(value - AO); else
                    if(world.isVoxelAtCoordinate(x, y-1, z+1)) brightnessList.add(value - AO); else
                        brightnessList.add(value);
                    break;
                    case 3: case 4: case 5:
                    if(world.isVoxelAtCoordinate(x-1, y-1, z)) brightnessList.add(value - AO); else
                    if(world.isVoxelAtCoordinate(x-1, y-1, z-1)) brightnessList.add(value - AO); else
                    if(world.isVoxelAtCoordinate(x, y-1, z-1)) brightnessList.add(value - AO); else
                        brightnessList.add(value);
                    break;
                    case 6: case 7: case 8:
                    if(world.isVoxelAtCoordinate(x+1, y-1, z)) brightnessList.add(value - AO); else
                    if(world.isVoxelAtCoordinate(x+1, y-1, z-1)) brightnessList.add(value - AO); else
                    if(world.isVoxelAtCoordinate(x, y-1, z-1)) brightnessList.add(value - AO); else
                        brightnessList.add(value);
                    break;
                    case 9: case 10: case 11:
                    if(world.isVoxelAtCoordinate(x+1, y-1, z)) brightnessList.add(value - AO); else
                    if(world.isVoxelAtCoordinate(x+1, y-1, z+1)) brightnessList.add(value - AO); else
                    if(world.isVoxelAtCoordinate(x, y-1, z+1)) brightnessList.add(value - AO); else
                        brightnessList.add(value);
                    break;
                }
                break;
            case NORTH: //          Z+1
                switch (index)
                {
                    case 0: case 1: case 2:
                    if(world.isVoxelAtCoordinate(x, y+1, z+1)) brightnessList.add(value - AO); else
                    if(world.isVoxelAtCoordinate(x-1, y+1, z+1)) brightnessList.add(value - AO); else
                    if(world.isVoxelAtCoordinate(x-1, y, z+1)) brightnessList.add(value - AO); else
                        brightnessList.add(value);
                    break;
                    case 3: case 4: case 5:
                    if(world.isVoxelAtCoordinate(x, y-1, z+1)) brightnessList.add(value - AO); else
                    if(world.isVoxelAtCoordinate(x-1, y-1, z+1)) brightnessList.add(value - AO); else
                    if(world.isVoxelAtCoordinate(x-1, y, z+1)) brightnessList.add(value - AO); else
                        brightnessList.add(value);
                    break;
                    case 6: case 7: case 8:
                    if(world.isVoxelAtCoordinate(x, y-1, z+1)) brightnessList.add(value - AO); else
                    if(world.isVoxelAtCoordinate(x+1, y-1, z+1)) brightnessList.add(value - AO); else
                    if(world.isVoxelAtCoordinate(x+1, y, z+1)) brightnessList.add(value - AO); else
                        brightnessList.add(value);
                    break;
                    case 9: case 10: case 11:
                    if(world.isVoxelAtCoordinate(x, y+1, z+1)) brightnessList.add(value - AO); else
                    if(world.isVoxelAtCoordinate(x+1, y+1, z+1)) brightnessList.add(value - AO); else
                    if(world.isVoxelAtCoordinate(x+1, y, z+1)) brightnessList.add(value - AO); else
                        brightnessList.add(value);
                    break;
                }
                break;
            case SOUTH: //         Z-1
                switch (index)
                {
                    case 0: case 1: case 2:
                    if(world.isVoxelAtCoordinate(x, y+1, z-1)) brightnessList.add(value - AO); else
                    if(world.isVoxelAtCoordinate(x-1, y+1, z-1)) brightnessList.add(value - AO); else
                    if(world.isVoxelAtCoordinate(x-1, y, z-1)) brightnessList.add(value - AO); else
                        brightnessList.add(value);
                    break;
                    case 3: case 4: case 5:
                    if(world.isVoxelAtCoordinate(x, y-1, z-1)) brightnessList.add(value - AO); else
                    if(world.isVoxelAtCoordinate(x-1, y-1, z-1)) brightnessList.add(value - AO); else
                    if(world.isVoxelAtCoordinate(x-1, y, z-1)) brightnessList.add(value - AO); else
                        brightnessList.add(value);
                    break;
                    case 6: case 7: case 8:
                    if(world.isVoxelAtCoordinate(x, y-1, z-1)) brightnessList.add(value - AO); else
                    if(world.isVoxelAtCoordinate(x+1, y-1, z-1)) brightnessList.add(value - AO); else
                    if(world.isVoxelAtCoordinate(x+1, y, z-1)) brightnessList.add(value - AO); else
                        brightnessList.add(value);
                    break;
                    case 9: case 10: case 11:
                    if(world.isVoxelAtCoordinate(x, y+1, z-1)) brightnessList.add(value - AO); else
                    if(world.isVoxelAtCoordinate(x+1, y+1, z-1)) brightnessList.add(value - AO); else
                    if(world.isVoxelAtCoordinate(x+1, y, z-1)) brightnessList.add(value - AO); else
                        brightnessList.add(value);
                    break;
                }
                break;
        }
    }

    private static void genBrightness(Chunk chunk, float value, int x, int y, int z, int index, List<Float> brightnessList, DirectionEnum direction, float AO)
    {
        switch (direction)
        {
            case WEST: //           X+1
                switch (index)
                {
                    case 0: case 1: case 2:
                    if(chunk.containsVoxelAtOffset(x+1, y+1, z)) brightnessList.add(value - AO); else
                    if(chunk.containsVoxelAtOffset(x+1, y+1, z-1)) brightnessList.add(value - AO); else
                    if(chunk.containsVoxelAtOffset(x+1, y, z-1)) brightnessList.add(value - AO); else
                        brightnessList.add(value);
                    break;
                    case 3: case 4: case 5:
                    if(chunk.containsVoxelAtOffset(x+1, y-1, z)) brightnessList.add(value - AO); else
                    if(chunk.containsVoxelAtOffset(x+1, y-1, z-1)) brightnessList.add(value - AO); else
                    if(chunk.containsVoxelAtOffset(x+1, y, z-1)) brightnessList.add(value - AO); else
                        brightnessList.add(value);
                    break;
                    case 6: case 7: case 8:
                    if(chunk.containsVoxelAtOffset(x+1, y-1, z)) brightnessList.add(value - AO); else
                    if(chunk.containsVoxelAtOffset(x+1, y-1, z+1)) brightnessList.add(value - AO); else
                    if(chunk.containsVoxelAtOffset(x+1, y, z+1)) brightnessList.add(value - AO); else
                        brightnessList.add(value);
                    break;
                    case 9: case 10: case 11:
                    if(chunk.containsVoxelAtOffset(x+1, y+1, z)) brightnessList.add(value - AO); else
                    if(chunk.containsVoxelAtOffset(x+1, y+1, z+1)) brightnessList.add(value - AO); else
                    if(chunk.containsVoxelAtOffset(x+1, y, z+1)) brightnessList.add(value - AO); else
                        brightnessList.add(value);
                    break;
                }
                break;
            case EAST: //         X-1
                switch (index)
                {
                    case 0: case 1: case 2:
                    if(chunk.containsVoxelAtOffset(x-1, y+1, z)) brightnessList.add(value - AO); else
                    if(chunk.containsVoxelAtOffset(x-1, y+1, z-1)) brightnessList.add(value - AO); else
                    if(chunk.containsVoxelAtOffset(x-1, y, z-1)) brightnessList.add(value - AO); else
                        brightnessList.add(value);
                    break;
                    case 3: case 4: case 5:
                    if(chunk.containsVoxelAtOffset(x-1, y-1, z)) brightnessList.add(value - AO); else
                    if(chunk.containsVoxelAtOffset(x-1, y-1, z-1)) brightnessList.add(value - AO); else
                    if(chunk.containsVoxelAtOffset(x-1, y, z-1)) brightnessList.add(value - AO); else
                        brightnessList.add(value);
                    break;
                    case 6: case 7: case 8:
                    if(chunk.containsVoxelAtOffset(x-1, y-1, z)) brightnessList.add(value - AO); else
                    if(chunk.containsVoxelAtOffset(x-1, y-1, z+1)) brightnessList.add(value - AO); else
                    if(chunk.containsVoxelAtOffset(x-1, y, z+1)) brightnessList.add(value - AO); else
                        brightnessList.add(value);
                    break;
                    case 9: case 10: case 11:
                    if(chunk.containsVoxelAtOffset(x-1, y+1, z)) brightnessList.add(value - AO); else
                    if(chunk.containsVoxelAtOffset(x-1, y+1, z+1)) brightnessList.add(value - AO); else
                    if(chunk.containsVoxelAtOffset(x-1, y, z+1)) brightnessList.add(value - AO); else
                        brightnessList.add(value);
                    break;
                }
                break;
            case UP: //          Y+1
                switch (index)
                {
                    case 0: case 1: case 2:
                    if(chunk.containsVoxelAtOffset(x-1, y+1, z)) brightnessList.add(value - AO); else
                    if(chunk.containsVoxelAtOffset(x-1, y+1, z+1)) brightnessList.add(value - AO); else
                    if(chunk.containsVoxelAtOffset(x, y+1, z+1)) brightnessList.add(value - AO); else
                        brightnessList.add(value);
                        break;
                    case 3: case 4: case 5:
                    if(chunk.containsVoxelAtOffset(x-1, y+1, z)) brightnessList.add(value - AO); else
                    if(chunk.containsVoxelAtOffset(x-1, y+1, z-1)) brightnessList.add(value - AO); else
                    if(chunk.containsVoxelAtOffset(x, y+1, z-1)) brightnessList.add(value - AO); else
                        brightnessList.add(value);
                        break;
                    case 6: case 7: case 8:
                    if(chunk.containsVoxelAtOffset(x+1, y+1, z)) brightnessList.add(value - AO); else
                    if(chunk.containsVoxelAtOffset(x+1, y+1, z-1)) brightnessList.add(value - AO); else
                    if(chunk.containsVoxelAtOffset(x, y+1, z-1)) brightnessList.add(value - AO); else
                        brightnessList.add(value);
                        break;
                    case 9: case 10: case 11:
                    if(chunk.containsVoxelAtOffset(x+1, y+1, z)) brightnessList.add(value - AO); else
                    if(chunk.containsVoxelAtOffset(x+1, y+1, z+1)) brightnessList.add(value - AO); else
                    if(chunk.containsVoxelAtOffset(x, y+1, z+1)) brightnessList.add(value - AO); else
                        brightnessList.add(value);
                        break;
                }
                break;
            case DOWN: //           Y-1
                switch (index)
                {
                    case 0: case 1: case 2:
                    if(chunk.containsVoxelAtOffset(x-1, y-1, z)) brightnessList.add(value - AO); else
                    if(chunk.containsVoxelAtOffset(x-1, y-1, z+1)) brightnessList.add(value - AO); else
                    if(chunk.containsVoxelAtOffset(x, y-1, z+1)) brightnessList.add(value - AO); else
                        brightnessList.add(value);
                    break;
                    case 3: case 4: case 5:
                    if(chunk.containsVoxelAtOffset(x-1, y-1, z)) brightnessList.add(value - AO); else
                    if(chunk.containsVoxelAtOffset(x-1, y-1, z-1)) brightnessList.add(value - AO); else
                    if(chunk.containsVoxelAtOffset(x, y-1, z-1)) brightnessList.add(value - AO); else
                        brightnessList.add(value);
                    break;
                    case 6: case 7: case 8:
                    if(chunk.containsVoxelAtOffset(x+1, y-1, z)) brightnessList.add(value - AO); else
                    if(chunk.containsVoxelAtOffset(x+1, y-1, z-1)) brightnessList.add(value - AO); else
                    if(chunk.containsVoxelAtOffset(x, y-1, z-1)) brightnessList.add(value - AO); else
                        brightnessList.add(value);
                    break;
                    case 9: case 10: case 11:
                    if(chunk.containsVoxelAtOffset(x+1, y-1, z)) brightnessList.add(value - AO); else
                    if(chunk.containsVoxelAtOffset(x+1, y-1, z+1)) brightnessList.add(value - AO); else
                    if(chunk.containsVoxelAtOffset(x, y-1, z+1)) brightnessList.add(value - AO); else
                        brightnessList.add(value);
                    break;
                }
                break;
            case NORTH: //          Z+1
                switch (index)
                {
                    case 0: case 1: case 2:
                    if(chunk.containsVoxelAtOffset(x, y+1, z+1)) brightnessList.add(value - AO); else
                    if(chunk.containsVoxelAtOffset(x-1, y+1, z+1)) brightnessList.add(value - AO); else
                    if(chunk.containsVoxelAtOffset(x-1, y, z+1)) brightnessList.add(value - AO); else
                        brightnessList.add(value);
                    break;
                    case 3: case 4: case 5:
                    if(chunk.containsVoxelAtOffset(x, y-1, z+1)) brightnessList.add(value - AO); else
                    if(chunk.containsVoxelAtOffset(x-1, y-1, z+1)) brightnessList.add(value - AO); else
                    if(chunk.containsVoxelAtOffset(x-1, y, z+1)) brightnessList.add(value - AO); else
                        brightnessList.add(value);
                    break;
                    case 6: case 7: case 8:
                    if(chunk.containsVoxelAtOffset(x, y-1, z+1)) brightnessList.add(value - AO); else
                    if(chunk.containsVoxelAtOffset(x+1, y-1, z+1)) brightnessList.add(value - AO); else
                    if(chunk.containsVoxelAtOffset(x+1, y, z+1)) brightnessList.add(value - AO); else
                        brightnessList.add(value);
                    break;
                    case 9: case 10: case 11:
                    if(chunk.containsVoxelAtOffset(x, y+1, z+1)) brightnessList.add(value - AO); else
                    if(chunk.containsVoxelAtOffset(x+1, y+1, z+1)) brightnessList.add(value - AO); else
                    if(chunk.containsVoxelAtOffset(x+1, y, z+1)) brightnessList.add(value - AO); else
                        brightnessList.add(value);
                    break;
                }
                break;
            case SOUTH: //         Z-1
                switch (index)
                {
                    case 0: case 1: case 2:
                    if(chunk.containsVoxelAtOffset(x, y+1, z-1)) brightnessList.add(value - AO); else
                    if(chunk.containsVoxelAtOffset(x-1, y+1, z-1)) brightnessList.add(value - AO); else
                    if(chunk.containsVoxelAtOffset(x-1, y, z-1)) brightnessList.add(value - AO); else
                        brightnessList.add(value);
                    break;
                    case 3: case 4: case 5:
                    if(chunk.containsVoxelAtOffset(x, y-1, z-1)) brightnessList.add(value - AO); else
                    if(chunk.containsVoxelAtOffset(x-1, y-1, z-1)) brightnessList.add(value - AO); else
                    if(chunk.containsVoxelAtOffset(x-1, y, z-1)) brightnessList.add(value - AO); else
                        brightnessList.add(value);
                    break;
                    case 6: case 7: case 8:
                    if(chunk.containsVoxelAtOffset(x, y-1, z-1)) brightnessList.add(value - AO); else
                    if(chunk.containsVoxelAtOffset(x+1, y-1, z-1)) brightnessList.add(value - AO); else
                    if(chunk.containsVoxelAtOffset(x+1, y, z-1)) brightnessList.add(value - AO); else
                        brightnessList.add(value);
                    break;
                    case 9: case 10: case 11:
                    if(chunk.containsVoxelAtOffset(x, y+1, z-1)) brightnessList.add(value - AO); else
                    if(chunk.containsVoxelAtOffset(x+1, y+1, z-1)) brightnessList.add(value - AO); else
                    if(chunk.containsVoxelAtOffset(x+1, y, z-1)) brightnessList.add(value - AO); else
                        brightnessList.add(value);
                    break;
                }
                break;
        }
    }

    private static void calculateBrightnessLevels()
    {

    }

    private static void createBrightnessSlice(World world, Chunk chunk, List<Float> brightnessList, int x, int y, int z)
    {
        if (!chunk.containsVoxelAtOffset(x + 1, y, z)) createLightValue(world, chunk, 0.9f, brightnessList, x, y, z, false, DirectionEnum.WEST);
        if (!chunk.containsVoxelAtOffset(x - 1, y, z)) createLightValue(world, chunk, 0.8f, brightnessList, x, y, z, false, DirectionEnum.EAST);
        if (!chunk.containsVoxelAtOffset(x, y + 1, z)) createLightValue(world, chunk, 1.0f, brightnessList, x, y, z, false, DirectionEnum.UP);
        if (!chunk.containsVoxelAtOffset(x, y - 1, z)) createLightValue(world, chunk, 0.7f, brightnessList, x, y, z, false, DirectionEnum.DOWN);
        if (!chunk.containsVoxelAtOffset(x, y, z + 1)) createLightValue(world, chunk, 0.7f, brightnessList, x, y, z, false, DirectionEnum.NORTH);
        if (!chunk.containsVoxelAtOffset(x, y, z - 1)) createLightValue(world, chunk, 0.7f, brightnessList, x, y, z, false, DirectionEnum.SOUTH);
    }

    private static void createBrightnessSliceEdgeCase(World world, Chunk chunk, List<Float> brightnessList, int x, int y, int z, boolean shouldUpdateNeighbors)
    {
        if (!chunk.containsVoxelAtOffset(x + 1, y, z))
            if(x != World.CHUNK_SIZE-1)
                createLightValue(world, chunk, 0.9f, brightnessList, x, y, z, true, DirectionEnum.WEST);
            else if(world.chunkAtChunkLoc(new Vector3f(chunk.chunkLoc.x + 1, chunk.chunkLoc.y, chunk.chunkLoc.z)))
                    if(world.getChunk(new Vector3f(chunk.chunkLoc.x + 1, chunk.chunkLoc.y, chunk.chunkLoc.z)).getVoxelIDAtOffset(0, y, z) == 0)
            {
                chunk.shouldUpdateNeighbors = shouldUpdateNeighbors;
                createLightValue(world, chunk, 0.9f, brightnessList, x, y, z, true, DirectionEnum.WEST);
            }

        if (!chunk.containsVoxelAtOffset(x - 1, y, z))
            if(x != 0)
                createLightValue(world, chunk, 0.8f, brightnessList, x, y, z, true, DirectionEnum.EAST);
            else if(world.chunkAtChunkLoc(new Vector3f(chunk.chunkLoc.x - 1, chunk.chunkLoc.y, chunk.chunkLoc.z)))
                if(world.getChunk(new Vector3f(chunk.chunkLoc.x - 1, chunk.chunkLoc.y, chunk.chunkLoc.z)).getVoxelIDAtOffset(World.CHUNK_SIZE-1, y, z) == 0)
            {
                chunk.shouldUpdateNeighbors = shouldUpdateNeighbors;
                createLightValue(world, chunk, 0.8f, brightnessList, x, y, z, true, DirectionEnum.EAST);
            }

        if (!chunk.containsVoxelAtOffset(x, y + 1, z))
            if(y != World.CHUNK_SIZE-1)
                createLightValue(world, chunk, 1.0f, brightnessList, x, y, z, true, DirectionEnum.UP);
            else if(world.chunkAtChunkLoc(new Vector3f(chunk.chunkLoc.x, chunk.chunkLoc.y + 1, chunk.chunkLoc.z)))
                if(world.getChunk(new Vector3f(chunk.chunkLoc.x, chunk.chunkLoc.y + 1, chunk.chunkLoc.z)).getVoxelIDAtOffset(x, 0, z) == 0)
            {
                chunk.shouldUpdateNeighbors = shouldUpdateNeighbors;
                createLightValue(world, chunk, 1.0f, brightnessList, x, y, z, true, DirectionEnum.UP);
            }

        if (!chunk.containsVoxelAtOffset(x, y - 1, z))
            if(y != 0)
                createLightValue(world, chunk, 0.7f, brightnessList, x, y, z, true, DirectionEnum.DOWN);
            else if(world.chunkAtChunkLoc(new Vector3f(chunk.chunkLoc.x, chunk.chunkLoc.y - 1, chunk.chunkLoc.z)))
                if(world.getChunk(new Vector3f(chunk.chunkLoc.x, chunk.chunkLoc.y - 1, chunk.chunkLoc.z)).getVoxelIDAtOffset(x, World.CHUNK_SIZE-1, z) == 0)
            {
                chunk.shouldUpdateNeighbors = shouldUpdateNeighbors;
                createLightValue(world, chunk, 0.7f, brightnessList, x, y, z, true, DirectionEnum.DOWN);
            }

        if (!chunk.containsVoxelAtOffset(x, y, z + 1))
            if(z != World.CHUNK_SIZE-1)
                createLightValue(world, chunk, 0.7f, brightnessList, x, y, z, true, DirectionEnum.NORTH);
            else if(world.chunkAtChunkLoc(new Vector3f(chunk.chunkLoc.x, chunk.chunkLoc.y, chunk.chunkLoc.z + 1)))
                if(world.getChunk(new Vector3f(chunk.chunkLoc.x, chunk.chunkLoc.y, chunk.chunkLoc.z + 1)).getVoxelIDAtOffset(x, y, 0) == 0)
            {
                chunk.shouldUpdateNeighbors = shouldUpdateNeighbors;
                createLightValue(world, chunk, 0.7f, brightnessList, x, y, z, true, DirectionEnum.NORTH);
            }

        if (!chunk.containsVoxelAtOffset(x, y, z - 1))
            if(z != 0)
                createLightValue(world, chunk, 0.7f, brightnessList, x, y, z, true, DirectionEnum.SOUTH);
            else if(world.chunkAtChunkLoc(new Vector3f(chunk.chunkLoc.x, chunk.chunkLoc.y, chunk.chunkLoc.z - 1)))
                if(world.getChunk(new Vector3f(chunk.chunkLoc.x, chunk.chunkLoc.y, chunk.chunkLoc.z - 1)).getVoxelIDAtOffset(x, y, World.CHUNK_SIZE-1) == 0)
            {
                chunk.shouldUpdateNeighbors = shouldUpdateNeighbors;
                createLightValue(world, chunk, 0.7f, brightnessList, x, y, z, true, DirectionEnum.SOUTH);
            }
    }

    private static void createIndex(int x, int y, int z, int[] indices, List<Integer> indicesList)
    {
        int indicesSize = indicesList.size();
        for (int i = 0; i < indices.length; i++)
        {
            int index = indices[i];
            indicesList.add((int) Math.ceil(index + indicesSize / 1.5));
        }
    }

    private static void createIndexSlice(Chunk chunk, List<Integer> indicesList, int x, int y, int z)
    {
        if (!chunk.containsVoxelAtOffset(x + 1, y, z)) createIndex(x, y, z, VoxelShape.getIndicesFaceWest(), indicesList);
        if (!chunk.containsVoxelAtOffset(x - 1, y, z)) createIndex(x, y, z, VoxelShape.getIndicesFaceEast(), indicesList);
        if (!chunk.containsVoxelAtOffset(x, y + 1, z)) createIndex(x, y, z, VoxelShape.getIndicesFaceUp(), indicesList);
        if (!chunk.containsVoxelAtOffset(x, y - 1, z)) createIndex(x, y, z, VoxelShape.getIndicesFaceDown(), indicesList);
        if (!chunk.containsVoxelAtOffset(x, y, z + 1)) createIndex(x, y, z, VoxelShape.getIndicesFaceSouth(), indicesList);
        if (!chunk.containsVoxelAtOffset(x, y, z - 1)) createIndex(x, y, z, VoxelShape.getIndicesFaceNorth(), indicesList);
    }

    private static void createIndexSliceEdgeCase(World world, Chunk chunk, List<Integer> indicesList, int x, int y, int z, boolean shouldUpdateNeighbors)
    {
        if (!chunk.containsVoxelAtOffset(x + 1, y, z))
            if(x != World.CHUNK_SIZE-1)
                createIndex(x, y, z, VoxelShape.getIndicesFaceWest(), indicesList);
            else if(world.chunkAtChunkLoc(new Vector3f(chunk.chunkLoc.x + 1, chunk.chunkLoc.y, chunk.chunkLoc.z)))
                if(world.getChunk(new Vector3f(chunk.chunkLoc.x + 1, chunk.chunkLoc.y, chunk.chunkLoc.z)).getVoxelIDAtOffset(0, y, z) == 0)
            {
                chunk.shouldUpdateNeighbors = shouldUpdateNeighbors;
                createIndex(x, y, z, VoxelShape.getIndicesFaceWest(), indicesList);
            }

        if (!chunk.containsVoxelAtOffset(x - 1, y, z))
            if(x != 0)
                createIndex(x, y, z, VoxelShape.getIndicesFaceEast(), indicesList);
            else if(world.chunkAtChunkLoc(new Vector3f(chunk.chunkLoc.x - 1, chunk.chunkLoc.y, chunk.chunkLoc.z)))
                if(world.getChunk(new Vector3f(chunk.chunkLoc.x - 1, chunk.chunkLoc.y, chunk.chunkLoc.z)).getVoxelIDAtOffset(World.CHUNK_SIZE-1, y, z) == 0)
            {
                chunk.shouldUpdateNeighbors = shouldUpdateNeighbors;
                createIndex(x, y, z, VoxelShape.getIndicesFaceEast(), indicesList);
            }

        if (!chunk.containsVoxelAtOffset(x, y + 1, z))
            if(y != World.CHUNK_SIZE-1)
                createIndex(x, y, z, VoxelShape.getIndicesFaceUp(), indicesList);
            else if(world.chunkAtChunkLoc(new Vector3f(chunk.chunkLoc.x, chunk.chunkLoc.y + 1, chunk.chunkLoc.z)))
                if(world.getChunk(new Vector3f(chunk.chunkLoc.x, chunk.chunkLoc.y + 1, chunk.chunkLoc.z)).getVoxelIDAtOffset(x, 0, z) == 0)
            {
                chunk.shouldUpdateNeighbors = shouldUpdateNeighbors;
                createIndex(x, y, z, VoxelShape.getIndicesFaceUp(), indicesList);
            }

        if (!chunk.containsVoxelAtOffset(x, y - 1, z))
            if(y != 0)
                createIndex(x, y, z, VoxelShape.getIndicesFaceDown(), indicesList);
            else if(world.chunkAtChunkLoc(new Vector3f(chunk.chunkLoc.x, chunk.chunkLoc.y - 1, chunk.chunkLoc.z)))
                if(world.getChunk(new Vector3f(chunk.chunkLoc.x, chunk.chunkLoc.y - 1, chunk.chunkLoc.z)).getVoxelIDAtOffset(x, World.CHUNK_SIZE-1, z) == 0)
            {
                chunk.shouldUpdateNeighbors = shouldUpdateNeighbors;
                createIndex(x, y, z, VoxelShape.getIndicesFaceDown(), indicesList);
            }

        if (!chunk.containsVoxelAtOffset(x, y, z + 1))
            if(z != World.CHUNK_SIZE-1)
                createIndex(x, y, z, VoxelShape.getIndicesFaceSouth(), indicesList);
            else if(world.chunkAtChunkLoc(new Vector3f(chunk.chunkLoc.x, chunk.chunkLoc.y, chunk.chunkLoc.z + 1)))
                if(world.getChunk(new Vector3f(chunk.chunkLoc.x, chunk.chunkLoc.y, chunk.chunkLoc.z + 1)).getVoxelIDAtOffset(x, y, 0) == 0)
            {
                chunk.shouldUpdateNeighbors = shouldUpdateNeighbors;
                createIndex(x, y, z, VoxelShape.getIndicesFaceSouth(), indicesList);
            }

        if (!chunk.containsVoxelAtOffset(x, y, z - 1))
            if(z != 0)
                createIndex(x, y, z, VoxelShape.getIndicesFaceNorth(), indicesList);
            else if(world.chunkAtChunkLoc(new Vector3f(chunk.chunkLoc.x, chunk.chunkLoc.y, chunk.chunkLoc.z - 1)))
                if(world.getChunk(new Vector3f(chunk.chunkLoc.x, chunk.chunkLoc.y, chunk.chunkLoc.z - 1)).getVoxelIDAtOffset(x, y, World.CHUNK_SIZE-1) == 0)
            {
                chunk.shouldUpdateNeighbors = shouldUpdateNeighbors;
                createIndex(x, y, z, VoxelShape.getIndicesFaceNorth(), indicesList);
            }
    }

    private  static void createTextureCoord(List<Float> textureCoordsList)
    {
        for (int i = 0; i < VoxelShape.getTextureCoordsSingleSide().length; i++)
        {
            float texCoord = VoxelShape.getTextureCoordsSingleSide()[i];
            textureCoordsList.add(texCoord);
        }
    }

    private static void createTextureCoordSlice(Chunk chunk, List<Float> textureCoordsList, int x, int y, int z)
    {
        if (!chunk.containsVoxelAtOffset(x + 1, y, z)) createTextureCoord(textureCoordsList);
        if (!chunk.containsVoxelAtOffset(x - 1, y, z)) createTextureCoord(textureCoordsList);
        if (!chunk.containsVoxelAtOffset(x, y + 1, z)) createTextureCoord(textureCoordsList);
        if (!chunk.containsVoxelAtOffset(x, y - 1, z)) createTextureCoord(textureCoordsList);
        if (!chunk.containsVoxelAtOffset(x, y, z + 1)) createTextureCoord(textureCoordsList);
        if (!chunk.containsVoxelAtOffset(x, y, z - 1)) createTextureCoord(textureCoordsList);
    }

    private static void createTextureCoordSliceEdgeCase(World world, Chunk chunk, List<Float> textureCoordsList, int x, int y, int z, boolean shouldUpdateNeighbors)
    {
        if (!chunk.containsVoxelAtOffset(x + 1, y, z))
            if(x != World.CHUNK_SIZE-1)
                createTextureCoord(textureCoordsList);
            else if(world.chunkAtChunkLoc(new Vector3f(chunk.chunkLoc.x + 1, chunk.chunkLoc.y, chunk.chunkLoc.z)))
                if(world.getChunk(new Vector3f(chunk.chunkLoc.x + 1, chunk.chunkLoc.y, chunk.chunkLoc.z)).getVoxelIDAtOffset(0, y, z) == 0)
            {
                chunk.shouldUpdateNeighbors = shouldUpdateNeighbors;
                createTextureCoord(textureCoordsList);
            }

        if (!chunk.containsVoxelAtOffset(x - 1, y, z))
            if(x != 0)
                createTextureCoord(textureCoordsList);
            else if(world.chunkAtChunkLoc(new Vector3f(chunk.chunkLoc.x - 1, chunk.chunkLoc.y, chunk.chunkLoc.z)))
                if(world.getChunk(new Vector3f(chunk.chunkLoc.x - 1, chunk.chunkLoc.y, chunk.chunkLoc.z)).getVoxelIDAtOffset(World.CHUNK_SIZE-1, y, z) == 0)
            {
                chunk.shouldUpdateNeighbors = shouldUpdateNeighbors;
                createTextureCoord(textureCoordsList);
            }

        if (!chunk.containsVoxelAtOffset(x, y + 1, z))
            if(y != World.CHUNK_SIZE-1)
                createTextureCoord(textureCoordsList);
            else if(world.chunkAtChunkLoc(new Vector3f(chunk.chunkLoc.x, chunk.chunkLoc.y + 1, chunk.chunkLoc.z)))
                if(world.getChunk(new Vector3f(chunk.chunkLoc.x, chunk.chunkLoc.y + 1, chunk.chunkLoc.z)).getVoxelIDAtOffset(x, 0, z) == 0)
            {
                chunk.shouldUpdateNeighbors = shouldUpdateNeighbors;
                createTextureCoord(textureCoordsList);
            }

        if (!chunk.containsVoxelAtOffset(x, y - 1, z))
            if(y != 0)
                createTextureCoord(textureCoordsList);
            else if(world.chunkAtChunkLoc(new Vector3f(chunk.chunkLoc.x, chunk.chunkLoc.y - 1, chunk.chunkLoc.z)))
                if(world.getChunk(new Vector3f(chunk.chunkLoc.x, chunk.chunkLoc.y - 1, chunk.chunkLoc.z)).getVoxelIDAtOffset(x, World.CHUNK_SIZE-1, z) == 0)
            {
                chunk.shouldUpdateNeighbors = shouldUpdateNeighbors;
                createTextureCoord(textureCoordsList);
            }

        if (!chunk.containsVoxelAtOffset(x, y, z + 1))
            if(z != World.CHUNK_SIZE-1)
                createTextureCoord(textureCoordsList);
            else if(world.chunkAtChunkLoc(new Vector3f(chunk.chunkLoc.x, chunk.chunkLoc.y, chunk.chunkLoc.z + 1)))
                if(world.getChunk(new Vector3f(chunk.chunkLoc.x, chunk.chunkLoc.y, chunk.chunkLoc.z + 1)).getVoxelIDAtOffset(x, y, 0) == 0)
            {
                chunk.shouldUpdateNeighbors = shouldUpdateNeighbors;
                createTextureCoord(textureCoordsList);
            }

        if (!chunk.containsVoxelAtOffset(x, y, z - 1))
            if(z != 0)
                createTextureCoord(textureCoordsList);
            else if(world.chunkAtChunkLoc(new Vector3f(chunk.chunkLoc.x, chunk.chunkLoc.y, chunk.chunkLoc.z - 1)))
                if(world.getChunk(new Vector3f(chunk.chunkLoc.x, chunk.chunkLoc.y, chunk.chunkLoc.z - 1)).getVoxelIDAtOffset(x, y, World.CHUNK_SIZE-1) == 0)
            {
                chunk.shouldUpdateNeighbors = shouldUpdateNeighbors;
                createTextureCoord(textureCoordsList);
            }
    }

    private static void createVertex(int x, int y, int z, float[] vertices, List<Float> verticesList)
    {
        for (int i = 0; i < vertices.length; i++)
        {
            switch (i % 3)
            {
                case 0: verticesList.add(vertices[i] + (x * VoxelShape.VOXEL_SIZE)); break;
                case 1: verticesList.add(vertices[i] + (y * VoxelShape.VOXEL_SIZE)); break;
                case 2: verticesList.add(vertices[i] + (z * VoxelShape.VOXEL_SIZE)); break;
            }
        }
    }

    private static void createVertexSlice(Chunk chunk, int x, int y, int z, List<Float> verticesList)
    {
        if (!chunk.containsVoxelAtOffset(x + 1, y, z)) createVertex(x, y, z, VoxelShape.getVerticesFaceWest(), verticesList);
        if (!chunk.containsVoxelAtOffset(x - 1, y, z)) createVertex(x, y, z, VoxelShape.getVerticesFaceEast(), verticesList);
        if (!chunk.containsVoxelAtOffset(x, y + 1, z)) createVertex(x, y, z, VoxelShape.getVerticesFaceUp(), verticesList);
        if (!chunk.containsVoxelAtOffset(x, y - 1, z)) createVertex(x, y, z, VoxelShape.getVerticesFaceDown(), verticesList);
        if (!chunk.containsVoxelAtOffset(x, y, z + 1)) createVertex(x, y, z, VoxelShape.getVerticesFaceSouth(), verticesList);
        if (!chunk.containsVoxelAtOffset(x, y, z - 1)) createVertex(x, y, z, VoxelShape.getVerticesFaceNorth(), verticesList);
    }

    private static void createVertexSliceEdgeCase(World world, Chunk chunk, int x, int y, int z, List<Float> verticesList, boolean shouldUpdateNeighbors)
    {
        if (!chunk.containsVoxelAtOffset(x + 1, y, z))
            if(x != World.CHUNK_SIZE-1)
            createVertex(x, y, z, VoxelShape.getVerticesFaceWest(), verticesList);
            else if(world.chunkAtChunkLoc(new Vector3f(chunk.chunkLoc.x + 1, chunk.chunkLoc.y, chunk.chunkLoc.z)))
                if(world.getChunk(new Vector3f(chunk.chunkLoc.x + 1, chunk.chunkLoc.y, chunk.chunkLoc.z)).getVoxelIDAtOffset(0, y, z) == 0)
            {
                chunk.shouldUpdateNeighbors = shouldUpdateNeighbors;
                createVertex(x, y, z, VoxelShape.getVerticesFaceWest(), verticesList);
            }

        if (!chunk.containsVoxelAtOffset(x - 1, y, z))
            if(x != 0)
            createVertex(x, y, z, VoxelShape.getVerticesFaceEast(), verticesList);
            else if(world.chunkAtChunkLoc(new Vector3f(chunk.chunkLoc.x - 1, chunk.chunkLoc.y, chunk.chunkLoc.z)))
                if(world.getChunk(new Vector3f(chunk.chunkLoc.x - 1, chunk.chunkLoc.y, chunk.chunkLoc.z)).getVoxelIDAtOffset(World.CHUNK_SIZE-1, y, z) == 0)
            {
                chunk.shouldUpdateNeighbors = shouldUpdateNeighbors;
                createVertex(x, y, z, VoxelShape.getVerticesFaceEast(), verticesList);
            }

        if (!chunk.containsVoxelAtOffset(x, y + 1, z))
            if(y != World.CHUNK_SIZE-1)
            createVertex(x, y, z, VoxelShape.getVerticesFaceUp(), verticesList);
            else if(world.chunkAtChunkLoc(new Vector3f(chunk.chunkLoc.x, chunk.chunkLoc.y + 1, chunk.chunkLoc.z)))
                if(world.getChunk(new Vector3f(chunk.chunkLoc.x, chunk.chunkLoc.y + 1, chunk.chunkLoc.z)).getVoxelIDAtOffset(x, 0, z) == 0)
            {
                chunk.shouldUpdateNeighbors = shouldUpdateNeighbors;
                createVertex(x, y, z, VoxelShape.getVerticesFaceUp(), verticesList);
            }

        if (!chunk.containsVoxelAtOffset(x, y - 1, z))
            if(y != 0)
            createVertex(x, y, z, VoxelShape.getVerticesFaceDown(), verticesList);
            else if(world.chunkAtChunkLoc(new Vector3f(chunk.chunkLoc.x, chunk.chunkLoc.y - 1, chunk.chunkLoc.z)))
                if(world.getChunk(new Vector3f(chunk.chunkLoc.x, chunk.chunkLoc.y - 1, chunk.chunkLoc.z)).getVoxelIDAtOffset(x, World.CHUNK_SIZE-1, z) == 0)
            {
                chunk.shouldUpdateNeighbors = shouldUpdateNeighbors;
                createVertex(x, y, z, VoxelShape.getVerticesFaceDown(), verticesList);
            }

        if (!chunk.containsVoxelAtOffset(x, y, z + 1))
            if(z != World.CHUNK_SIZE-1)
            createVertex(x, y, z, VoxelShape.getVerticesFaceSouth(), verticesList);
            else if(world.chunkAtChunkLoc(new Vector3f(chunk.chunkLoc.x, chunk.chunkLoc.y, chunk.chunkLoc.z + 1)))
                if(world.getChunk(new Vector3f(chunk.chunkLoc.x, chunk.chunkLoc.y, chunk.chunkLoc.z + 1)).getVoxelIDAtOffset(x, y, 0) == 0)
            {
                chunk.shouldUpdateNeighbors = shouldUpdateNeighbors;
                createVertex(x, y, z, VoxelShape.getVerticesFaceSouth(), verticesList);
            }

        if (!chunk.containsVoxelAtOffset(x, y, z - 1))
            if(z != 0)
            createVertex(x, y, z, VoxelShape.getVerticesFaceNorth(), verticesList);
            else if(world.chunkAtChunkLoc(new Vector3f(chunk.chunkLoc.x, chunk.chunkLoc.y, chunk.chunkLoc.z - 1)))
                if(world.getChunk(new Vector3f(chunk.chunkLoc.x, chunk.chunkLoc.y, chunk.chunkLoc.z - 1)).getVoxelIDAtOffset(x, y, World.CHUNK_SIZE-1) == 0)
            {
                chunk.shouldUpdateNeighbors = shouldUpdateNeighbors;
                createVertex(x, y, z, VoxelShape.getVerticesFaceNorth(), verticesList);
            }
    }

    public static String generateTemporaryBlockName(Chunk chunk)
    {
        String name = "";
        if (Math.abs(chunk.chunkLoc.x) % 2 == 0 && Math.abs(chunk.chunkLoc.z) % 2 == 0 && Math.abs(chunk.chunkLoc.y) % 2 == 0)
            name = "white_concrete";
        else if (Math.abs(chunk.chunkLoc.x) % 2 == 0 && Math.abs(chunk.chunkLoc.z) % 2 == 1 && Math.abs(chunk.chunkLoc.y) % 2 == 0)
            name = "black_concrete";
        else if (Math.abs(chunk.chunkLoc.x) % 2 == 1 && Math.abs(chunk.chunkLoc.z) % 2 == 1 && Math.abs(chunk.chunkLoc.y) % 2 == 0)
            name = "white_concrete";
        else if (Math.abs(chunk.chunkLoc.x) % 2 == 1 && Math.abs(chunk.chunkLoc.z) % 2 == 0 && Math.abs(chunk.chunkLoc.y) % 2 == 0)
            name = "black_concrete";
        else if (Math.abs(chunk.chunkLoc.x) % 2 == 0 && Math.abs(chunk.chunkLoc.z) % 2 == 0 && Math.abs(chunk.chunkLoc.y) % 2 == 1)
            name = "black_concrete";
        else if (Math.abs(chunk.chunkLoc.x) % 2 == 0 && Math.abs(chunk.chunkLoc.z) % 2 == 1 && Math.abs(chunk.chunkLoc.y) % 2 == 1)
            name = "white_concrete";
        else if (Math.abs(chunk.chunkLoc.x) % 2 == 1 && Math.abs(chunk.chunkLoc.z) % 2 == 1 && Math.abs(chunk.chunkLoc.y) % 2 == 1)
            name = "black_concrete";
        else if (Math.abs(chunk.chunkLoc.x) % 2 == 1 && Math.abs(chunk.chunkLoc.z) % 2 == 0 && Math.abs(chunk.chunkLoc.y) % 2 == 1)
            name = "white_concrete";

        return name;
    }
}
