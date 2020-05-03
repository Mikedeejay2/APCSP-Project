package com.mikedeejay2.voxel.game.world;

import com.mikedeejay2.voxel.engine.graphics.models.RawModel;
import com.mikedeejay2.voxel.engine.graphics.models.TexturedModel;
import com.mikedeejay2.voxel.engine.graphics.objects.Entity;
import com.mikedeejay2.voxel.engine.graphics.textures.ModelTexture;
import com.mikedeejay2.voxel.engine.utils.Maths;
import com.mikedeejay2.voxel.game.Main;
import com.mikedeejay2.voxel.game.voxel.Voxel;
import com.mikedeejay2.voxel.game.voxel.VoxelShape;
import com.mikedeejay2.voxel.game.voxel.VoxelTypes;
import org.joml.Vector3f;

import java.util.*;

public class Chunk
{
    public String[][][] voxels;

    Entity chunkEntity;

    public Vector3f chunkLoc;
    public Vector3f chunkCoords;

    private float[] verticesTemp;
    private float[] textureCoordsTemp;
    private int[] indicesTemp;
    private float[] brightnessTemp;

    World instanceWorld;

    boolean hasLoaded;
    boolean containsVoxels;

    public Chunk(Vector3f chunkLoc, World world)
    {
        this.chunkLoc = chunkLoc;
        this.chunkCoords = new Vector3f(chunkLoc.x *  World.CHUNK_SIZE, chunkLoc.y * World.CHUNK_SIZE, chunkLoc.z * World.CHUNK_SIZE);
        this.voxels = new String[World.CHUNK_SIZE][World.CHUNK_SIZE][World.CHUNK_SIZE];
        instanceWorld = world;
        hasLoaded = false;
        containsVoxels = false;
    }

    public void populate()
    {
        instanceWorld.populateChunk(this);
        verticesTemp = createVertices();
        textureCoordsTemp = createTextureCoords();
        indicesTemp = createIndices();
        brightnessTemp = createBrightness();
        hasLoaded = true;
    }

    public void render()
    {
        if(!hasLoaded) return;
        if(chunkEntity == null) createChunkEntity();
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

    public Entity createChunkEntity()
    {
        if(verticesTemp == null || textureCoordsTemp == null || indicesTemp == null || brightnessTemp == null) return null;
        RawModel model = Main.getLoader().loadToVAO(verticesTemp, textureCoordsTemp, indicesTemp, brightnessTemp);

        verticesTemp = null;
        textureCoordsTemp = null;
        indicesTemp = null;

        String name = "";
        if (Math.abs(chunkLoc.x) % 2 == 0 && Math.abs(chunkLoc.z) % 2 == 0 && Math.abs(chunkLoc.y) % 2 == 0)
            name = "white_concrete";
        else if (Math.abs(chunkLoc.x) % 2 == 0 && Math.abs(chunkLoc.z) % 2 == 1 && Math.abs(chunkLoc.y) % 2 == 0)
            name = "black_concrete";
        else if (Math.abs(chunkLoc.x) % 2 == 1 && Math.abs(chunkLoc.z) % 2 == 1 && Math.abs(chunkLoc.y) % 2 == 0)
            name = "white_concrete";
        else if (Math.abs(chunkLoc.x) % 2 == 1 && Math.abs(chunkLoc.z) % 2 == 0 && Math.abs(chunkLoc.y) % 2 == 0)
            name = "black_concrete";
        else if (Math.abs(chunkLoc.x) % 2 == 0 && Math.abs(chunkLoc.z) % 2 == 0 && Math.abs(chunkLoc.y) % 2 == 1)
            name = "black_concrete";
        else if (Math.abs(chunkLoc.x) % 2 == 0 && Math.abs(chunkLoc.z) % 2 == 1 && Math.abs(chunkLoc.y) % 2 == 1)
            name = "white_concrete";
        else if (Math.abs(chunkLoc.x) % 2 == 1 && Math.abs(chunkLoc.z) % 2 == 1 && Math.abs(chunkLoc.y) % 2 == 1)
            name = "black_concrete";
        else if (Math.abs(chunkLoc.x) % 2 == 1 && Math.abs(chunkLoc.z) % 2 == 0 && Math.abs(chunkLoc.y) % 2 == 1)
            name = "white_concrete";

        ModelTexture modelTexture = VoxelTypes.getFromName(name).getTexture();

        TexturedModel texturedModel = new TexturedModel(model, modelTexture);

        Entity entity = new Entity(texturedModel, chunkCoords);

        chunkEntity = null;
        chunkEntity = entity;

        return entity;
    }

//    @Deprecated
//    public Entity createChunkModel()
//    {
//        List<Float> verticesList = new ArrayList<Float>();
//        List<Float> textureCoordsList = new ArrayList<Float>();
//        List<Integer> indicesList = new ArrayList<Integer>();
//        for(int x = 0; x < World.CHUNK_SIZE; x++)
//        {
//            for (int y = 0; y < World.CHUNK_SIZE; y++)
//            {
//                for (int z = 0; z < World.CHUNK_SIZE; z++)
//                {
//                    if(containsVoxelAtOffset(x, y, z))
//                    {
//                        if(!containsVoxelAtOffset(x+1, y, z))
//                        {
//                            int indicesSize = indicesList.size();
//                            for (int i = 0; i < VoxelShape.getVerticesFaceWest().length; i++)
//                            {
//                                switch (i % 3)
//                                {
//                                    case 0: verticesList.add(VoxelShape.getVerticesFaceWest()[i]+x); break;
//                                    case 1: verticesList.add(VoxelShape.getVerticesFaceWest()[i]+y); break;
//                                    case 2: verticesList.add(VoxelShape.getVerticesFaceWest()[i]+z); break;
//                                }
//                            }
//                            for (float texCoord : VoxelShape.getTextureCoordsSingleSide())
//                                textureCoordsList.add(texCoord);
//                            for (int index : VoxelShape.getIndicesFaceWest())
//                                indicesList.add((int)Math.ceil(index + indicesSize/1.5));
//                        }
//                        if(!containsVoxelAtOffset(x-1, y, z))
//                        {
//                            int indicesSize = indicesList.size();
//                            for (int i = 0; i < VoxelShape.getVerticesFaceEast().length; i++)
//                            {
//                                switch (i % 3)
//                                {
//                                    case 0: verticesList.add(VoxelShape.getVerticesFaceEast()[i]+x); break;
//                                    case 1: verticesList.add(VoxelShape.getVerticesFaceEast()[i]+y); break;
//                                    case 2: verticesList.add(VoxelShape.getVerticesFaceEast()[i]+z); break;
//                                }
//                            }
//                            for (float texCoord : VoxelShape.getTextureCoordsSingleSide())
//                                textureCoordsList.add(texCoord);
//                            for (int index : VoxelShape.getIndicesFaceEast())
//                                indicesList.add((int)Math.ceil(index + indicesSize/1.5));
//                        }
//                        if(!containsVoxelAtOffset(x, y+1, z))
//                        {
//                            int indicesSize = indicesList.size();
//                            for (int i = 0; i < VoxelShape.getVerticesFaceUp().length; i++)
//                            {
//                                switch (i % 3)
//                                {
//                                    case 0: verticesList.add(VoxelShape.getVerticesFaceUp()[i]+x); break;
//                                    case 1: verticesList.add(VoxelShape.getVerticesFaceUp()[i]+y); break;
//                                    case 2: verticesList.add(VoxelShape.getVerticesFaceUp()[i]+z); break;
//                                }
//                            }
//                            for (float texCoord : VoxelShape.getTextureCoordsSingleSide())
//                                textureCoordsList.add(texCoord);
//                            for (int index : VoxelShape.getIndicesFaceUp())
//                                indicesList.add((int)Math.ceil(index + indicesSize/1.5));
//                        }
//                        if(!containsVoxelAtOffset(x, y-1, z))
//                        {
//                            int indicesSize = indicesList.size();
//                            for (int i = 0; i < VoxelShape.getVerticesFaceDown().length; i++)
//                            {
//                                switch (i % 3)
//                                {
//                                    case 0: verticesList.add(VoxelShape.getVerticesFaceDown()[i]+x); break;
//                                    case 1: verticesList.add(VoxelShape.getVerticesFaceDown()[i]+y); break;
//                                    case 2: verticesList.add(VoxelShape.getVerticesFaceDown()[i]+z); break;
//                                }
//                            }
//                            for (float texCoord : VoxelShape.getTextureCoordsSingleSide())
//                                textureCoordsList.add(texCoord);
//                            for (int index : VoxelShape.getIndicesFaceDown())
//                                indicesList.add((int)Math.ceil(index + indicesSize/1.5));
//                        }
//                        if(!containsVoxelAtOffset(x, y, z+1))
//                        {
//                            int indicesSize = indicesList.size();
//                            for (int i = 0; i < VoxelShape.getVerticesFaceSouth().length; i++)
//                            {
//                                switch (i % 3)
//                                {
//                                    case 0: verticesList.add(VoxelShape.getVerticesFaceSouth()[i]+x); break;
//                                    case 1: verticesList.add(VoxelShape.getVerticesFaceSouth()[i]+y); break;
//                                    case 2: verticesList.add(VoxelShape.getVerticesFaceSouth()[i]+z); break;
//                                }
//                            }
//                            for (float texCoord : VoxelShape.getTextureCoordsSingleSide())
//                                textureCoordsList.add(texCoord);
//                            for (int index : VoxelShape.getIndicesFaceSouth())
//                                indicesList.add((int)Math.ceil(index + indicesSize/1.5));
//                        }
//                        if(!containsVoxelAtOffset(x, y, z-1))
//                        {
//                            int indicesSize = indicesList.size();
//                            for (int i = 0; i < VoxelShape.getVerticesFaceNorth().length; i++)
//                            {
//                                switch (i % 3)
//                                {
//                                    case 0: verticesList.add(VoxelShape.getVerticesFaceNorth()[i]+x); break;
//                                    case 1: verticesList.add(VoxelShape.getVerticesFaceNorth()[i]+y); break;
//                                    case 2: verticesList.add(VoxelShape.getVerticesFaceNorth()[i]+z); break;
//                                }
//                            }
//                            for (float texCoord : VoxelShape.getTextureCoordsSingleSide())
//                                textureCoordsList.add(texCoord);
//                            for (int index : VoxelShape.getIndicesFaceNorth())
//                                indicesList.add((int)Math.ceil(index + indicesSize/1.5));
//                        }
//                    }
//                }
//            }
//        }
//
//        float[] vertices = new float[verticesList.size()];
//        float[] textureCoords = new float[textureCoordsList.size()];
//        int[] indices = new int[indicesList.size()];
//
//        for(int i = 0; i < vertices.length; i++) vertices[i] = verticesList.get(i);
//        for(int i = 0; i < textureCoords.length; i++) textureCoords[i] = textureCoordsList.get(i);
//        for(int i = 0; i < indices.length; i++) indices[i] = indicesList.get(i);
//
//        RawModel chunkModel = Main.getLoader().loadToVAO(vertices, textureCoords, indices);
//
//        String name = "";
//        if (Math.abs(chunkLoc.x) % 2 == 0 && Math.abs(chunkLoc.z) % 2 == 0 && Math.abs(chunkLoc.y) % 2 == 0)
//            name = "diamond_block";
//        else if (Math.abs(chunkLoc.x) % 2 == 0 && Math.abs(chunkLoc.z) % 2 == 1 && Math.abs(chunkLoc.y) % 2 == 0)
//            name = "gold_block";
//        else if (Math.abs(chunkLoc.x) % 2 == 1 && Math.abs(chunkLoc.z) % 2 == 1 && Math.abs(chunkLoc.y) % 2 == 0)
//            name = "diamond_block";
//        else if (Math.abs(chunkLoc.x) % 2 == 1 && Math.abs(chunkLoc.z) % 2 == 0 && Math.abs(chunkLoc.y) % 2 == 0)
//            name = "gold_block";
//        else if (Math.abs(chunkLoc.x) % 2 == 0 && Math.abs(chunkLoc.z) % 2 == 0 && Math.abs(chunkLoc.y) % 2 == 1)
//            name = "gold_block";
//        else if (Math.abs(chunkLoc.x) % 2 == 0 && Math.abs(chunkLoc.z) % 2 == 1 && Math.abs(chunkLoc.y) % 2 == 1)
//            name = "diamond_block";
//        else if (Math.abs(chunkLoc.x) % 2 == 1 && Math.abs(chunkLoc.z) % 2 == 1 && Math.abs(chunkLoc.y) % 2 == 1)
//            name = "gold_block";
//        else if (Math.abs(chunkLoc.x) % 2 == 1 && Math.abs(chunkLoc.z) % 2 == 0 && Math.abs(chunkLoc.y) % 2 == 1)
//            name = "diamond_block";
//
//        ModelTexture modelTexture = VoxelTypes.getFromName(name).getTexture();
//
//        TexturedModel texturedModel = new TexturedModel(chunkModel, modelTexture);
//        chunkEntity = new Entity(texturedModel, chunkCoords);
//
//        vertices = null;
//        textureCoords = null;
//        indices = null;
//        verticesList.clear();
//        textureCoordsList.clear();
//        indicesList.clear();
//        verticesList = null;
//        textureCoordsList = null;
//        indicesList = null;
//
//        return chunkEntity;
//    }

    public float[] createBrightness()
    {
        List<Float> brightnessList = new ArrayList<Float>();
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
                            for (int i = 0; i < VoxelShape.getBrightnessSingleSide().length; i++)
                            {
                                brightnessList.add(0.9f);
                            }
                        }
                        if(!containsVoxelAtOffset(x-1, y, z))
                        {
                            for (int i = 0; i < VoxelShape.getBrightnessSingleSide().length; i++)
                            {
                                brightnessList.add(0.8f);
                            }
                        }
                        if(!containsVoxelAtOffset(x, y+1, z))
                        {
                            for (int i = 0; i < VoxelShape.getBrightnessSingleSide().length; i++)
                            {
                                brightnessList.add(1.0f);
                            }
                        }
                        if(!containsVoxelAtOffset(x, y-1, z))
                        {
                            for (int i = 0; i < VoxelShape.getBrightnessSingleSide().length; i++)
                            {
                                brightnessList.add(0.7f);
                            }
                        }
                        if(!containsVoxelAtOffset(x, y, z+1))
                        {
                            for (int i = 0; i < VoxelShape.getBrightnessSingleSide().length; i++)
                            {
                                brightnessList.add(0.7f);
                            }
                        }
                        if(!containsVoxelAtOffset(x, y, z-1))
                        {
                            for (int i = 0; i < VoxelShape.getBrightnessSingleSide().length; i++)
                            {
                                brightnessList.add(0.7f);
                            }
                        }
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

    public float[] createTextureCoords()
    {
        List<Float> textureCoordsList = new ArrayList<Float>();
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
                            for (int i = 0; i < VoxelShape.getTextureCoordsSingleSide().length; i++)
                            {
                                float texCoord = VoxelShape.getTextureCoordsSingleSide()[i];
                                textureCoordsList.add(texCoord);
                            }
                        }
                        if(!containsVoxelAtOffset(x-1, y, z))
                        {
                            for (int i = 0; i < VoxelShape.getTextureCoordsSingleSide().length; i++)
                            {
                                float texCoord = VoxelShape.getTextureCoordsSingleSide()[i];
                                textureCoordsList.add(texCoord);
                            }
                        }
                        if(!containsVoxelAtOffset(x, y+1, z))
                        {
                            for (int i = 0; i < VoxelShape.getTextureCoordsSingleSide().length; i++)
                            {
                                float texCoord = VoxelShape.getTextureCoordsSingleSide()[i];
                                textureCoordsList.add(texCoord);
                            }
                        }
                        if(!containsVoxelAtOffset(x, y-1, z))
                        {
                            for (int i = 0; i < VoxelShape.getTextureCoordsSingleSide().length; i++)
                            {
                                float texCoord = VoxelShape.getTextureCoordsSingleSide()[i];
                                textureCoordsList.add(texCoord);
                            }
                        }
                        if(!containsVoxelAtOffset(x, y, z+1))
                        {
                            for (int i = 0; i < VoxelShape.getTextureCoordsSingleSide().length; i++)
                            {
                                float texCoord = VoxelShape.getTextureCoordsSingleSide()[i];
                                textureCoordsList.add(texCoord);
                            }
                        }
                        if(!containsVoxelAtOffset(x, y, z-1))
                        {
                            for (int i = 0; i < VoxelShape.getTextureCoordsSingleSide().length; i++)
                            {
                                float texCoord = VoxelShape.getTextureCoordsSingleSide()[i];
                                textureCoordsList.add(texCoord);
                            }
                        }
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

    public int[] createIndices()
    {
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
                            int indicesSize = indicesList.size();
                            for (int i = 0; i < VoxelShape.getIndicesFaceWest().length; i++)
                            {
                                int index = VoxelShape.getIndicesFaceWest()[i];
                                indicesList.add((int)Math.ceil(index + indicesSize/1.5));
                            }
                        }
                        if(!containsVoxelAtOffset(x-1, y, z))
                        {
                            int indicesSize = indicesList.size();
                            for (int i = 0; i < VoxelShape.getIndicesFaceEast().length; i++)
                            {
                                int index = VoxelShape.getIndicesFaceEast()[i];
                                indicesList.add((int)Math.ceil(index + indicesSize/1.5));
                            }
                        }
                        if(!containsVoxelAtOffset(x, y+1, z))
                        {
                            int indicesSize = indicesList.size();
                            for (int i = 0; i < VoxelShape.getIndicesFaceUp().length; i++)
                            {
                                int index = VoxelShape.getIndicesFaceUp()[i];
                                indicesList.add((int)Math.ceil(index + indicesSize/1.5));
                            }
                        }
                        if(!containsVoxelAtOffset(x, y-1, z))
                        {
                            int indicesSize = indicesList.size();
                            for (int i = 0; i < VoxelShape.getIndicesFaceDown().length; i++)
                            {
                                int index = VoxelShape.getIndicesFaceDown()[i];
                                indicesList.add((int)Math.ceil(index + indicesSize/1.5));
                            }
                        }
                        if(!containsVoxelAtOffset(x, y, z+1))
                        {
                            int indicesSize = indicesList.size();
                            for (int i = 0; i < VoxelShape.getIndicesFaceSouth().length; i++)
                            {
                                int index = VoxelShape.getIndicesFaceSouth()[i];
                                indicesList.add((int)Math.ceil(index + indicesSize/1.5));
                            }
                        }
                        if(!containsVoxelAtOffset(x, y, z-1))
                        {
                            int indicesSize = indicesList.size();
                            for (int i = 0; i < VoxelShape.getIndicesFaceNorth().length; i++)
                            {
                                int index = VoxelShape.getIndicesFaceNorth()[i];
                                indicesList.add((int)Math.ceil(index + indicesSize/1.5));
                            }
                        }
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

    public float[] createVertices()
    {
        List<Float> verticesList = new ArrayList<Float>();
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
                            for (int i = 0; i < VoxelShape.getVerticesFaceWest().length; i++)
                            {
                                switch (i % 3)
                                {
                                    case 0: verticesList.add(VoxelShape.getVerticesFaceWest()[i]+x); break;
                                    case 1: verticesList.add(VoxelShape.getVerticesFaceWest()[i]+y); break;
                                    case 2: verticesList.add(VoxelShape.getVerticesFaceWest()[i]+z); break;
                                }
                            }
                        }
                        if(!containsVoxelAtOffset(x-1, y, z))
                        {
                            for (int i = 0; i < VoxelShape.getVerticesFaceEast().length; i++)
                            {
                                switch (i % 3)
                                {
                                    case 0: verticesList.add(VoxelShape.getVerticesFaceEast()[i]+x); break;
                                    case 1: verticesList.add(VoxelShape.getVerticesFaceEast()[i]+y); break;
                                    case 2: verticesList.add(VoxelShape.getVerticesFaceEast()[i]+z); break;
                                }
                            }
                        }
                        if(!containsVoxelAtOffset(x, y+1, z))
                        {
                            for (int i = 0; i < VoxelShape.getVerticesFaceUp().length; i++)
                            {
                                switch (i % 3)
                                {
                                    case 0: verticesList.add(VoxelShape.getVerticesFaceUp()[i]+x); break;
                                    case 1: verticesList.add(VoxelShape.getVerticesFaceUp()[i]+y); break;
                                    case 2: verticesList.add(VoxelShape.getVerticesFaceUp()[i]+z); break;
                                }
                            }
                        }
                        if(!containsVoxelAtOffset(x, y-1, z))
                        {
                            for (int i = 0; i < VoxelShape.getVerticesFaceDown().length; i++)
                            {
                                switch (i % 3)
                                {
                                    case 0: verticesList.add(VoxelShape.getVerticesFaceDown()[i]+x); break;
                                    case 1: verticesList.add(VoxelShape.getVerticesFaceDown()[i]+y); break;
                                    case 2: verticesList.add(VoxelShape.getVerticesFaceDown()[i]+z); break;
                                }
                            }
                        }
                        if(!containsVoxelAtOffset(x, y, z+1))
                        {
                            for (int i = 0; i < VoxelShape.getVerticesFaceSouth().length; i++)
                            {
                                switch (i % 3)
                                {
                                    case 0: verticesList.add(VoxelShape.getVerticesFaceSouth()[i]+x); break;
                                    case 1: verticesList.add(VoxelShape.getVerticesFaceSouth()[i]+y); break;
                                    case 2: verticesList.add(VoxelShape.getVerticesFaceSouth()[i]+z); break;
                                }
                            }
                        }
                        if(!containsVoxelAtOffset(x, y, z-1))
                        {
                            for (int i = 0; i < VoxelShape.getVerticesFaceNorth().length; i++)
                            {
                                switch (i % 3)
                                {
                                    case 0: verticesList.add(VoxelShape.getVerticesFaceNorth()[i]+x); break;
                                    case 1: verticesList.add(VoxelShape.getVerticesFaceNorth()[i]+y); break;
                                    case 2: verticesList.add(VoxelShape.getVerticesFaceNorth()[i]+z); break;
                                }
                            }
                        }
                    }
                }
            }
        }

        float[] vertices = new float[verticesList.size()];

        for(int i = 0; i < vertices.length; i++) vertices[i] = verticesList.get(i);

        verticesList.clear();
        verticesList = null;

        return vertices;
    }

    public void addVoxel(int x, int y, int z, String name)
    {
        voxels[x][y][z] = name;
        setContainsVoxels(true);
    }

    public boolean containsVoxelAtOffset(int x, int y, int z)
    {
        if(x < 0 || y < 0 || z < 0 || x > World.CHUNK_SIZE-1 || y > World.CHUNK_SIZE-1 || z > World.CHUNK_SIZE-1) return false;
            return voxels[x][y][z] != null;
    }

    public Voxel getVoxelAtOffset(int x, int y, int z)
    {
        return new Voxel(voxels[x][y][z], new Vector3f(chunkCoords.x + x, chunkCoords.y + y, chunkCoords.z + z));
    }

    public String getVoxelNameAtOffset(int x, int y, int z)
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
