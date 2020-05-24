package com.mikedeejay2.apcspfinal.world.chunk.mesh.algorithms;

import com.mikedeejay2.apcspfinal.voxel.VoxelTypes;

import java.util.List;

public class TextureCoordAlgorithms
{
    public static  void createTextureCoord(List<Float> textureCoordsList, int id)
    {
        float[] textureCoords = VoxelTypes.getTextureAtlas().getTextureCoords(id);
        for (int i = 0; i < textureCoords.length; i++)
        {
            textureCoordsList.add(textureCoords[i]);
        }
    }
}
