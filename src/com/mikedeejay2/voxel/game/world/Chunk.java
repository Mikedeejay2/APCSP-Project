package com.mikedeejay2.voxel.game.world;

import com.mikedeejay2.voxel.engine.graphics.models.RawModel;
import com.mikedeejay2.voxel.engine.graphics.models.TexturedModel;
import com.mikedeejay2.voxel.engine.graphics.objects.Entity;
import com.mikedeejay2.voxel.engine.graphics.textures.ModelTexture;
import com.mikedeejay2.voxel.engine.utils.PerlinNoiseGenerator;
import com.mikedeejay2.voxel.game.Main;
import com.mikedeejay2.voxel.game.voxel.Voxel;
import com.mikedeejay2.voxel.game.voxel.VoxelShape;
import com.mikedeejay2.voxel.game.voxel.VoxelTypes;
import com.mikedeejay2.voxel.game.world.generators.OverworldGenerator;
import org.joml.Vector3f;

import java.util.*;

public class Chunk
{
    public Voxel[][][] voxels;

    Entity chunkEntity;

    public Vector3f chunkLoc;
    public Vector3f chunkCoords;

    World instanceWorld;

    boolean hasLoaded;

    public Chunk(Vector3f chunkLoc, World world)
    {
        this.chunkLoc = chunkLoc;
        this.chunkCoords = new Vector3f(chunkLoc.x *  World.CHUNK_SIZE, chunkLoc.y * World.CHUNK_SIZE, chunkLoc.z * World.CHUNK_SIZE);
        this.voxels = new Voxel[World.CHUNK_SIZE][World.CHUNK_SIZE][World.CHUNK_SIZE];
        instanceWorld = world;
        hasLoaded = false;
    }

    public void populate()
    {
        instanceWorld.populateChunk(this);
        //createChunkModel();
        hasLoaded = true;
    }

    public void render()
    {
        if(!hasLoaded) return;
        if(chunkEntity == null) createChunkModel();
        if(voxels.length  != 0)
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

    public Entity createChunkModel()
    {
        List<Float> verticesList = new ArrayList<Float>();
        List<Float> textureCoordsList = new ArrayList<Float>();
        List<Integer> indicesList = new ArrayList<Integer>();
        for(int x = 0; x < World.CHUNK_SIZE; x++)
        {
            for (int y = 0; y < World.CHUNK_SIZE; y++)
            {
                for (int z = 0; z < World.CHUNK_SIZE; z++)
                {
                    if(containsVoxelAtOffset(x, y, z))
                    {
                        if(!containsVoxelAtOffset(x+1, y, z))
                        {
                            int indicesSize = calcIndicesSize(indicesList);
                            for (int i = 0; i < VoxelShape.getVerticesFaceWest().length; i++)
                            {
                                switch (i % 3)
                                {
                                    case 0: verticesList.add(VoxelShape.getVerticesFaceWest()[i]+x); break;
                                    case 1: verticesList.add(VoxelShape.getVerticesFaceWest()[i]+y); break;
                                    case 2: verticesList.add(VoxelShape.getVerticesFaceWest()[i]+z); break;
                                }
                            }
                            for (float texCoord : VoxelShape.getTextureCoordsSingleSide())
                                textureCoordsList.add(texCoord);
                            for (int index : VoxelShape.getIndicesFaceWest())
                                indicesList.add((int)Math.ceil(index + indicesSize/1.5));
                        }
                        if(!containsVoxelAtOffset(x-1, y, z))
                        {
                            int indicesSize = calcIndicesSize(indicesList);
                            for (int i = 0; i < VoxelShape.getVerticesFaceEast().length; i++)
                            {
                                switch (i % 3)
                                {
                                    case 0: verticesList.add(VoxelShape.getVerticesFaceEast()[i]+x); break;
                                    case 1: verticesList.add(VoxelShape.getVerticesFaceEast()[i]+y); break;
                                    case 2: verticesList.add(VoxelShape.getVerticesFaceEast()[i]+z); break;
                                }
                            }
                            for (float texCoord : VoxelShape.getTextureCoordsSingleSide())
                                textureCoordsList.add(texCoord);
                            for (int index : VoxelShape.getIndicesFaceEast())
                                indicesList.add((int)Math.ceil(index + indicesSize/1.5));
                        }
                        if(!containsVoxelAtOffset(x, y+1, z))
                        {
                            int indicesSize = indicesList.size();
                            for (int i = 0; i < VoxelShape.getVerticesFaceUp().length; i++)
                            {
                                switch (i % 3)
                                {
                                    case 0: verticesList.add(VoxelShape.getVerticesFaceUp()[i]+x); break;
                                    case 1: verticesList.add(VoxelShape.getVerticesFaceUp()[i]+y); break;
                                    case 2: verticesList.add(VoxelShape.getVerticesFaceUp()[i]+z); break;
                                }
                            }
                            for (float texCoord : VoxelShape.getTextureCoordsSingleSide())
                                textureCoordsList.add(texCoord);
                            for (int index : VoxelShape.getIndicesFaceUp())
                                indicesList.add((int)Math.ceil(index + indicesSize/1.5));
                        }
                        if(!containsVoxelAtOffset(x, y-1, z))
                        {
                            int indicesSize = calcIndicesSize(indicesList);
                            for (int i = 0; i < VoxelShape.getVerticesFaceDown().length; i++)
                            {
                                switch (i % 3)
                                {
                                    case 0: verticesList.add(VoxelShape.getVerticesFaceDown()[i]+x); break;
                                    case 1: verticesList.add(VoxelShape.getVerticesFaceDown()[i]+y); break;
                                    case 2: verticesList.add(VoxelShape.getVerticesFaceDown()[i]+z); break;
                                }
                            }
                            for (float texCoord : VoxelShape.getTextureCoordsSingleSide())
                                textureCoordsList.add(texCoord);
                            for (int index : VoxelShape.getIndicesFaceDown())
                                indicesList.add((int)Math.ceil(index + indicesSize/1.5));
                        }
                        if(!containsVoxelAtOffset(x, y, z+1))
                        {
                            int indicesSize = calcIndicesSize(indicesList);
                            for (int i = 0; i < VoxelShape.getVerticesFaceSouth().length; i++)
                            {
                                switch (i % 3)
                                {
                                    case 0: verticesList.add(VoxelShape.getVerticesFaceSouth()[i]+x); break;
                                    case 1: verticesList.add(VoxelShape.getVerticesFaceSouth()[i]+y); break;
                                    case 2: verticesList.add(VoxelShape.getVerticesFaceSouth()[i]+z); break;
                                }
                            }
                            for (float texCoord : VoxelShape.getTextureCoordsSingleSide())
                                textureCoordsList.add(texCoord);
                            for (int index : VoxelShape.getIndicesFaceSouth())
                                indicesList.add((int)Math.ceil(index + indicesSize/1.5));
                        }
                        if(!containsVoxelAtOffset(x, y, z-1))
                        {
                            int indicesSize = calcIndicesSize(indicesList);
                            for (int i = 0; i < VoxelShape.getVerticesFaceNorth().length; i++)
                            {
                                switch (i % 3)
                                {
                                    case 0: verticesList.add(VoxelShape.getVerticesFaceNorth()[i]+x); break;
                                    case 1: verticesList.add(VoxelShape.getVerticesFaceNorth()[i]+y); break;
                                    case 2: verticesList.add(VoxelShape.getVerticesFaceNorth()[i]+z); break;
                                }
                            }
                            for (float texCoord : VoxelShape.getTextureCoordsSingleSide())
                                textureCoordsList.add(texCoord);
                            for (int index : VoxelShape.getIndicesFaceNorth())
                                indicesList.add((int)Math.ceil(index + indicesSize/1.5));
                        }
                    }
                }
            }
        }

        float[] vertices = new float[verticesList.size()];
        float[] textureCoords = new float[textureCoordsList.size()];
        int[] indices = new int[indicesList.size()];

        for(int i = 0; i < vertices.length; i++) vertices[i] = verticesList.get(i);
        for(int i = 0; i < textureCoords.length; i++) textureCoords[i] = textureCoordsList.get(i);
        for(int i = 0; i < indices.length; i++) indices[i] = indicesList.get(i);

        RawModel chunkModel = Main.getLoader().loadToVAO(vertices, textureCoords, indices);

        String name = "";
        if (Math.abs(chunkLoc.x) % 2 == 0 && Math.abs(chunkLoc.z) % 2 == 0 && Math.abs(chunkLoc.y) % 2 == 0)
            name = "diamond_block";
        else if (Math.abs(chunkLoc.x) % 2 == 0 && Math.abs(chunkLoc.z) % 2 == 1 && Math.abs(chunkLoc.y) % 2 == 0)
            name = "gold_block";
        else if (Math.abs(chunkLoc.x) % 2 == 1 && Math.abs(chunkLoc.z) % 2 == 1 && Math.abs(chunkLoc.y) % 2 == 0)
            name = "diamond_block";
        else if (Math.abs(chunkLoc.x) % 2 == 1 && Math.abs(chunkLoc.z) % 2 == 0 && Math.abs(chunkLoc.y) % 2 == 0)
            name = "gold_block";
        else if (Math.abs(chunkLoc.x) % 2 == 0 && Math.abs(chunkLoc.z) % 2 == 0 && Math.abs(chunkLoc.y) % 2 == 1)
            name = "diamond_block";
        else if (Math.abs(chunkLoc.x) % 2 == 0 && Math.abs(chunkLoc.z) % 2 == 1 && Math.abs(chunkLoc.y) % 2 == 1)
            name = "gold_block";
        else if (Math.abs(chunkLoc.x) % 2 == 1 && Math.abs(chunkLoc.z) % 2 == 1 && Math.abs(chunkLoc.y) % 2 == 1)
            name = "diamond_block";
        else if (Math.abs(chunkLoc.x) % 2 == 1 && Math.abs(chunkLoc.z) % 2 == 0 && Math.abs(chunkLoc.y) % 2 == 1)
            name = "gold_block";

        ModelTexture modelTexture = VoxelTypes.getFromName(name).getTexture();

        TexturedModel texturedModel = new TexturedModel(chunkModel, modelTexture);
        chunkEntity = new Entity(texturedModel, chunkCoords);

        vertices = null;
        textureCoords = null;
        indices = null;
        verticesList.clear();
        textureCoordsList.clear();
        indicesList.clear();
        verticesList = null;
        textureCoordsList = null;
        indicesList = null;

        return chunkEntity;
    }

    private int calcIndicesSize(List<Integer> indices)
    {
        int indicesSize = indices.size()-1;
        if(indices.size() < 0) return 0;
        return indicesSize;
    }

    public boolean containsVoxelAtOffset(int x, int y, int z)
    {
        if(x < 0 || y < 0 || z < 0 || x > World.CHUNK_SIZE-1 || y > World.CHUNK_SIZE-1 || z > World.CHUNK_SIZE-1) return false;
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

    public Chunk getChunkDown()
    {
        return instanceWorld.getChunkFromChunkLoc(new Vector3f(chunkLoc.x, chunkLoc.y-1, chunkLoc.z));
    }

    public Chunk getChunkUp()
    {
        return instanceWorld.getChunkFromChunkLoc(new Vector3f(chunkLoc.x, chunkLoc.y+1, chunkLoc.z));
    }
}
