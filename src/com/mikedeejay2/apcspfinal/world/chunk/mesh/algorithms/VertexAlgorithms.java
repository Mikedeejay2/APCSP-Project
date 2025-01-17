package com.mikedeejay2.apcspfinal.world.chunk.mesh.algorithms;

import java.util.List;

public class VertexAlgorithms
{
    public static void createVertex(int x, int y, int z, float[] vertices, List<Float> verticesList)
    {
        for (int i = 0; i < vertices.length; i++)
        {
            switch (i % 3)
            {
                case 0: verticesList.add(vertices[i] + (x)); break;
                case 1: verticesList.add(vertices[i] + (y)); break;
                case 2: verticesList.add(vertices[i] + (z)); break;
            }
        }
    }
}
