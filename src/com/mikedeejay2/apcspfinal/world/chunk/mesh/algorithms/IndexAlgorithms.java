package com.mikedeejay2.apcspfinal.world.chunk.mesh.algorithms;

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
