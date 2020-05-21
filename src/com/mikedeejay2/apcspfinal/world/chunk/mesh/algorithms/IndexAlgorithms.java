package com.mikedeejay2.apcspfinal.world.chunk.mesh.algorithms;

import com.mikedeejay2.apcspfinal.voxel.Voxel;
import com.mikedeejay2.apcspfinal.voxel.VoxelShape;
import com.mikedeejay2.apcspfinal.world.World;
import com.mikedeejay2.apcspfinal.world.chunk.Chunk;
import org.joml.Vector3f;

import java.util.List;

public class IndexAlgorithms
{

    public static  void createIndex(int x, int y, int z, int[] indices, List<Integer> indicesList)
    {
        int indicesSize = (int) (indicesList.size() / 1.5);
        for (int i = 0; i < indices.length; i++)
        {
            indicesList.add((int) (indices[i] + indicesSize));
        }
    }
}
