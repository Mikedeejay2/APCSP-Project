package com.mikedeejay2.voxel.engine.voxel.generators;

import com.mikedeejay2.voxel.engine.graphics.models.RawModel;
import com.mikedeejay2.voxel.engine.graphics.models.TexturedModel;
import com.mikedeejay2.voxel.engine.graphics.objects.Entity;
import com.mikedeejay2.voxel.engine.graphics.textures.ModelTexture;
import com.mikedeejay2.voxel.game.Main;
import com.mikedeejay2.voxel.game.voxel.VoxelShape;
import com.mikedeejay2.voxel.game.voxel.VoxelTypes;
import com.mikedeejay2.voxel.game.world.Chunk;
import com.mikedeejay2.voxel.game.world.World;

import java.util.ArrayList;
import java.util.List;

public class ChunkMeshGenerator
{
    public static Entity createChunkEntity(Chunk chunk)
    {
        if(chunk.verticesTemp == null || chunk.textureCoordsTemp == null || chunk.indicesTemp == null || chunk.brightnessTemp == null) return null;
        RawModel model = Main.getLoader().loadToVAO(chunk.verticesTemp, chunk.textureCoordsTemp, chunk.indicesTemp, chunk.brightnessTemp);

        chunk.verticesTemp = null;
        chunk.textureCoordsTemp = null;
        chunk.indicesTemp = null;
        chunk.brightnessTemp = null;

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

        ModelTexture modelTexture = VoxelTypes.getTexture(name);

        TexturedModel texturedModel = new TexturedModel(model, modelTexture);

        Entity entity = new Entity(texturedModel, chunk.chunkCoords);

        chunk.chunkEntity = null;
        chunk.chunkEntity = entity;

        return entity;
    }

    public static float[] createBrightness(Chunk chunk)
    {
        List<Float> brightnessList = new ArrayList<Float>();
        for(int x = 0; x < World.CHUNK_SIZE; x++)
        {
            for (int y = 0; y < World.CHUNK_SIZE; y++)
            {
                for (int z = 0; z < World.CHUNK_SIZE; z++)
                {
                    if(chunk.containsVoxelAtOffset(x, y, z))
                    {
                        if(!chunk.containsVoxelAtOffset(x+1, y, z))
                        {
                            for (int i = 0; i < VoxelShape.getBrightnessSingleSide().length; i++)
                            {
                                brightnessList.add(0.9f);
                            }
                        }
                        if(!chunk.containsVoxelAtOffset(x-1, y, z))
                        {
                            for (int i = 0; i < VoxelShape.getBrightnessSingleSide().length; i++)
                            {
                                brightnessList.add(0.8f);
                            }
                        }
                        if(!chunk.containsVoxelAtOffset(x, y+1, z))
                        {
                            for (int i = 0; i < VoxelShape.getBrightnessSingleSide().length; i++)
                            {
                                brightnessList.add(1.0f);
                            }
                        }
                        if(!chunk.containsVoxelAtOffset(x, y-1, z))
                        {
                            for (int i = 0; i < VoxelShape.getBrightnessSingleSide().length; i++)
                            {
                                brightnessList.add(0.7f);
                            }
                        }
                        if(!chunk.containsVoxelAtOffset(x, y, z+1))
                        {
                            for (int i = 0; i < VoxelShape.getBrightnessSingleSide().length; i++)
                            {
                                brightnessList.add(0.7f);
                            }
                        }
                        if(!chunk.containsVoxelAtOffset(x, y, z-1))
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

    public static float[] createTextureCoords(Chunk chunk)
    {
        List<Float> textureCoordsList = new ArrayList<Float>();
        for(int x = 0; x < World.CHUNK_SIZE; x++)
        {
            for (int y = 0; y < World.CHUNK_SIZE; y++)
            {
                for (int z = 0; z < World.CHUNK_SIZE; z++)
                {
                    if(chunk.containsVoxelAtOffset(x, y, z))
                    {
                        if(!chunk.containsVoxelAtOffset(x+1, y, z))
                        {
                            for (int i = 0; i < VoxelShape.getTextureCoordsSingleSide().length; i++)
                            {
                                float texCoord = VoxelShape.getTextureCoordsSingleSide()[i];
                                textureCoordsList.add(texCoord);
                            }
                        }
                        if(!chunk.containsVoxelAtOffset(x-1, y, z))
                        {
                            for (int i = 0; i < VoxelShape.getTextureCoordsSingleSide().length; i++)
                            {
                                float texCoord = VoxelShape.getTextureCoordsSingleSide()[i];
                                textureCoordsList.add(texCoord);
                            }
                        }
                        if(!chunk.containsVoxelAtOffset(x, y+1, z))
                        {
                            for (int i = 0; i < VoxelShape.getTextureCoordsSingleSide().length; i++)
                            {
                                float texCoord = VoxelShape.getTextureCoordsSingleSide()[i];
                                textureCoordsList.add(texCoord);
                            }
                        }
                        if(!chunk.containsVoxelAtOffset(x, y-1, z))
                        {
                            for (int i = 0; i < VoxelShape.getTextureCoordsSingleSide().length; i++)
                            {
                                float texCoord = VoxelShape.getTextureCoordsSingleSide()[i];
                                textureCoordsList.add(texCoord);
                            }
                        }
                        if(!chunk.containsVoxelAtOffset(x, y, z+1))
                        {
                            for (int i = 0; i < VoxelShape.getTextureCoordsSingleSide().length; i++)
                            {
                                float texCoord = VoxelShape.getTextureCoordsSingleSide()[i];
                                textureCoordsList.add(texCoord);
                            }
                        }
                        if(!chunk.containsVoxelAtOffset(x, y, z-1))
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

    public static int[] createIndices(Chunk chunk)
    {
        List<Integer> indicesList = new ArrayList<Integer>();
        for(int x = 0; x < World.CHUNK_SIZE; x++)
        {
            for (int y = 0; y < World.CHUNK_SIZE; y++)
            {
                for (int z = 0; z < World.CHUNK_SIZE; z++)
                {
                    if(chunk.containsVoxelAtOffset(x, y, z))
                    {
                        if(!chunk.containsVoxelAtOffset(x+1, y, z))
                        {
                            int indicesSize = indicesList.size();
                            for (int i = 0; i < VoxelShape.getIndicesFaceWest().length; i++)
                            {
                                int index = VoxelShape.getIndicesFaceWest()[i];
                                indicesList.add((int)Math.ceil(index + indicesSize/1.5));
                            }
                        }
                        if(!chunk.containsVoxelAtOffset(x-1, y, z))
                        {
                            int indicesSize = indicesList.size();
                            for (int i = 0; i < VoxelShape.getIndicesFaceEast().length; i++)
                            {
                                int index = VoxelShape.getIndicesFaceEast()[i];
                                indicesList.add((int)Math.ceil(index + indicesSize/1.5));
                            }
                        }
                        if(!chunk.containsVoxelAtOffset(x, y+1, z))
                        {
                            int indicesSize = indicesList.size();
                            for (int i = 0; i < VoxelShape.getIndicesFaceUp().length; i++)
                            {
                                int index = VoxelShape.getIndicesFaceUp()[i];
                                indicesList.add((int)Math.ceil(index + indicesSize/1.5));
                            }
                        }
                        if(!chunk.containsVoxelAtOffset(x, y-1, z))
                        {
                            int indicesSize = indicesList.size();
                            for (int i = 0; i < VoxelShape.getIndicesFaceDown().length; i++)
                            {
                                int index = VoxelShape.getIndicesFaceDown()[i];
                                indicesList.add((int)Math.ceil(index + indicesSize/1.5));
                            }
                        }
                        if(!chunk.containsVoxelAtOffset(x, y, z+1))
                        {
                            int indicesSize = indicesList.size();
                            for (int i = 0; i < VoxelShape.getIndicesFaceSouth().length; i++)
                            {
                                int index = VoxelShape.getIndicesFaceSouth()[i];
                                indicesList.add((int)Math.ceil(index + indicesSize/1.5));
                            }
                        }
                        if(!chunk.containsVoxelAtOffset(x, y, z-1))
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

    public static float[] createVertices(Chunk chunk)
    {
        List<Float> verticesList = new ArrayList<Float>();
        for(int x = 0; x < World.CHUNK_SIZE; x++)
        {
            for (int y = 0; y < World.CHUNK_SIZE; y++)
            {
                for (int z = 0; z < World.CHUNK_SIZE; z++)
                {
                    if(chunk.containsVoxelAtOffset(x, y, z))
                    {
                        if(!chunk.containsVoxelAtOffset(x+1, y, z))
                        {
                            for (int i = 0; i < VoxelShape.getVerticesFaceWest().length; i++)
                            {
                                switch (i % 3)
                                {
                                    case 0: verticesList.add(VoxelShape.getVerticesFaceWest()[i]+(x*VoxelShape.VOXEL_SIZE)); break;
                                    case 1: verticesList.add(VoxelShape.getVerticesFaceWest()[i]+(y*VoxelShape.VOXEL_SIZE)); break;
                                    case 2: verticesList.add(VoxelShape.getVerticesFaceWest()[i]+(z*VoxelShape.VOXEL_SIZE)); break;
                                }
                            }
                        }
                        if(!chunk.containsVoxelAtOffset(x-1, y, z))
                        {
                            for (int i = 0; i < VoxelShape.getVerticesFaceEast().length; i++)
                            {
                                switch (i % 3)
                                {
                                    case 0: verticesList.add(VoxelShape.getVerticesFaceEast()[i]+(x*VoxelShape.VOXEL_SIZE)); break;
                                    case 1: verticesList.add(VoxelShape.getVerticesFaceEast()[i]+(y*VoxelShape.VOXEL_SIZE)); break;
                                    case 2: verticesList.add(VoxelShape.getVerticesFaceEast()[i]+(z*VoxelShape.VOXEL_SIZE)); break;
                                }
                            }
                        }
                        if(!chunk.containsVoxelAtOffset(x, y+1, z))
                        {
                            for (int i = 0; i < VoxelShape.getVerticesFaceUp().length; i++)
                            {
                                switch (i % 3)
                                {
                                    case 0: verticesList.add(VoxelShape.getVerticesFaceUp()[i]+(x*VoxelShape.VOXEL_SIZE)); break;
                                    case 1: verticesList.add(VoxelShape.getVerticesFaceUp()[i]+(y*VoxelShape.VOXEL_SIZE)); break;
                                    case 2: verticesList.add(VoxelShape.getVerticesFaceUp()[i]+(z*VoxelShape.VOXEL_SIZE)); break;
                                }
                            }
                        }
                        if(!chunk.containsVoxelAtOffset(x, y-1, z))
                        {
                            for (int i = 0; i < VoxelShape.getVerticesFaceDown().length; i++)
                            {
                                switch (i % 3)
                                {
                                    case 0: verticesList.add(VoxelShape.getVerticesFaceDown()[i]+(x*VoxelShape.VOXEL_SIZE)); break;
                                    case 1: verticesList.add(VoxelShape.getVerticesFaceDown()[i]+(y*VoxelShape.VOXEL_SIZE)); break;
                                    case 2: verticesList.add(VoxelShape.getVerticesFaceDown()[i]+(z*VoxelShape.VOXEL_SIZE)); break;
                                }
                            }
                        }
                        if(!chunk.containsVoxelAtOffset(x, y, z+1))
                        {
                            for (int i = 0; i < VoxelShape.getVerticesFaceSouth().length; i++)
                            {
                                switch (i % 3)
                                {
                                    case 0: verticesList.add(VoxelShape.getVerticesFaceSouth()[i]+(x*VoxelShape.VOXEL_SIZE)); break;
                                    case 1: verticesList.add(VoxelShape.getVerticesFaceSouth()[i]+(y*VoxelShape.VOXEL_SIZE)); break;
                                    case 2: verticesList.add(VoxelShape.getVerticesFaceSouth()[i]+(z*VoxelShape.VOXEL_SIZE)); break;
                                }
                            }
                        }
                        if(!chunk.containsVoxelAtOffset(x, y, z-1))
                        {
                            for (int i = 0; i < VoxelShape.getVerticesFaceNorth().length; i++)
                            {
                                switch (i % 3)
                                {
                                    case 0: verticesList.add(VoxelShape.getVerticesFaceNorth()[i]+(x*VoxelShape.VOXEL_SIZE)); break;
                                    case 1: verticesList.add(VoxelShape.getVerticesFaceNorth()[i]+(y*VoxelShape.VOXEL_SIZE)); break;
                                    case 2: verticesList.add(VoxelShape.getVerticesFaceNorth()[i]+(z*VoxelShape.VOXEL_SIZE)); break;
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
}
