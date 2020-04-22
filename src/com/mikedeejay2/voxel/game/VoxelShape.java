package com.mikedeejay2.voxel.game;

public class VoxelShape
{
    private static float[] vertices = new float[]
            {
                    -0.5f, 0.5f, -0.5f,
                    -0.5f, -0.5f, -0.5f,
                    0.5f, -0.5f, -0.5f,
                    0.5f, 0.5f, -0.5f,

                    -0.5f, 0.5f, 0.5f,
                    -0.5f, -0.5f, 0.5f,
                    0.5f, -0.5f, 0.5f,
                    0.5f, 0.5f, 0.5f,

                    0.5f, 0.5f, -0.5f,
                    0.5f, -0.5f, -0.5f,
                    0.5f, -0.5f, 0.5f,
                    0.5f, 0.5f, 0.5f,

                    -0.5f, 0.5f, -0.5f,
                    -0.5f, -0.5f, -0.5f,
                    -0.5f, -0.5f, 0.5f,
                    -0.5f, 0.5f, 0.5f,

                    -0.5f, 0.5f, 0.5f,
                    -0.5f, 0.5f, -0.5f,
                    0.5f, 0.5f, -0.5f,
                    0.5f, 0.5f, 0.5f,

                    -0.5f, -0.5f, 0.5f,
                    -0.5f, -0.5f, -0.5f,
                    0.5f, -0.5f, -0.5f,
                    0.5f, -0.5f, 0.5f
            };

    private static int[] indices = new int[]
            {
                    3,1,0,
                    2,1,3,
                    4,5,7,
                    7,5,6,
                    11,9,8,
                    10,9,11,
                    12,13,15,
                    15,13,14,
                    19,17,16,
                    18,17,19,
                    20,21,23,
                    23,21,22
            };

    private static float[] textureCoords = new float[]
            {
                    0, 0,
                    0, 1,
                    1, 1,
                    1, 0,
                    0, 0,
                    0, 1,
                    1, 1,
                    1, 0,
                    0, 0,
                    0, 1,
                    1, 1,
                    1, 0,
                    0, 0,
                    0, 1,
                    1, 1,
                    1, 0,
                    0, 0,
                    0, 1,
                    1, 1,
                    1, 0,
                    0, 0,
                    0, 1,
                    1, 1,
                    1, 0
            };






    public static float[] getVertices()
    {
        return vertices;
    }

    public static int[] getIndices()
    {
        return indices;
    }

    public static float[] getTextureCoords()
    {
        return textureCoords;
    }
}
