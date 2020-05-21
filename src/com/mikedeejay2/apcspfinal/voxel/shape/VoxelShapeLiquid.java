package com.mikedeejay2.apcspfinal.voxel.shape;

public class VoxelShapeLiquid extends VoxelShape
{
    private static float[] vertices = new float[]
            {
                    -0.5f, 0.4375f, -0.5f, //NORTH FACE
                    -0.5f, -0.5f, -0.5f,
                    0.5f, -0.5f, -0.5f,
                    0.5f, 0.4375f, -0.5f,

                    -0.5f, 0.4375f, 0.5f, //SOUTH FACE
                    -0.5f, -0.5f, 0.5f,
                    0.5f, -0.5f, 0.5f,
                    0.5f, 0.4375f, 0.5f,

                    0.5f, 0.4375f, -0.5f, //WEST FACE
                    0.5f, -0.5f, -0.5f,
                    0.5f, -0.5f, 0.5f,
                    0.5f, 0.4375f, 0.5f,

                    -0.5f, 0.4375f, -0.5f, //EAST FACE
                    -0.5f, -0.5f, -0.5f,
                    -0.5f, -0.5f, 0.5f,
                    -0.5f, 0.4375f, 0.5f,

                    -0.5f, 0.4375f, 0.5f, //UP FACE
                    -0.5f, 0.4375f, -0.5f,
                    0.5f, 0.4375f, -0.5f,
                    0.5f, 0.4375f, 0.5f,

                    -0.5f, -0.5f, 0.5f, //DOWN FACE
                    -0.5f, -0.5f, -0.5f,
                    0.5f, -0.5f, -0.5f,
                    0.5f, -0.5f, 0.5f
            };

    private static float[] verticesNorth = new float[]
            {
                    -0.5f, 0.4375f, 0.5f, //NORTH FACE
                    -0.5f, -0.5f, 0.5f,
                    0.5f, -0.5f, 0.5f,
                    0.5f, 0.4375f, 0.5f
            };

    private static float[] verticesSouth = new float[]
            {
                    -0.5f, 0.4375f, -0.5f, //SOUTH FACE
                    -0.5f, -0.5f, -0.5f,
                    0.5f, -0.5f, -0.5f,
                    0.5f, 0.4375f, -0.5f
            };

    private static float[] verticesWest = new float[]
            {
                    0.5f, 0.4375f, -0.5f, //WEST FACE
                    0.5f, -0.5f, -0.5f,
                    0.5f, -0.5f, 0.5f,
                    0.5f, 0.4375f, 0.5f
            };

    private static float[] verticesEast = new float[]
            {
                    -0.5f, 0.4375f, -0.5f, //EAST FACE
                    -0.5f, -0.5f, -0.5f,
                    -0.5f, -0.5f, 0.5f,
                    -0.5f, 0.4375f, 0.5f
            };

    private static float[] verticesUp = new float[]
            {
                    -0.5f, 0.4375f, 0.5f, //UP FACE
                    -0.5f, 0.4375f, -0.5f,
                    0.5f, 0.4375f, -0.5f,
                    0.5f, 0.4375f, 0.5f
            };

    private static float[] verticesDown = new float[]
            {
                    -0.5f, -0.5f, 0.5f, //DOWN FACE
                    -0.5f, -0.5f, -0.5f,
                    0.5f, -0.5f, -0.5f,
                    0.5f, -0.5f, 0.5f
            };

    private static int[] indices = new int[]
            {
                    3,1,0, //NORTH FACE
                    2,1,3,

                    4,5,7, //SOUTH FACE
                    7,5,6,

                    11,9,8, //WEST FACE
                    10,9,11,

                    12,13,15, //EAST FACE
                    15,13,14,

                    19,17,16, //UP FACE
                    18,17,19,

                    20,21,23, //DOWN FACE
                    23,21,22
            };

    private static int[] indicesNorth = new int[]
            {
                    0,1,3, //SOUTH FACE
                    3,1,2,
            };

    private static int[] indicesSouth = new int[]
            {
                    3,1,0, //NORTH FACE
                    2,1,3
            };

    private static int[] indicesWest = new int[]
            {
                    3,1,0, //WEST FACE
                    2,1,3,
            };

    private static int[] indicesEast = new int[]
            {
                    0,1,3, //EAST FACE
                    3,1,2,
            };

    private static int[] indicesUp = new int[]
            {
                    3,1,0, //UP FACE
                    2,1,3,
            };

    private static int[] indicesDown = new int[]
            {
                    0,1,3, //DOWN FACE
                    3,1,2
            };

    @Override
    public float[] getVertices()
    {
        return vertices;
    }

    @Override
    public float[] getVerticesNorth()
    {
        return verticesNorth;
    }

    @Override
    public float[] getVerticesSouth()
    {
        return verticesSouth;
    }

    @Override
    public float[] getVerticesWest()
    {
        return verticesWest;
    }

    @Override
    public float[] getVerticesEast()
    {
        return verticesEast;
    }

    @Override
    public float[] getVerticesUp()
    {
        return verticesUp;
    }

    @Override
    public float[] getVerticesDown()
    {
        return verticesDown;
    }

    @Override
    public int[] getIndices()
    {
        return indices;
    }

    @Override
    public int[] getIndicesNorth()
    {
        return indicesNorth;
    }

    @Override
    public int[] getIndicesSouth()
    {
        return indicesSouth;
    }

    @Override
    public int[] getIndicesWest()
    {
        return indicesWest;
    }

    @Override
    public int[] getIndicesEast()
    {
        return indicesEast;
    }

    @Override
    public int[] getIndicesUp()
    {
        return indicesUp;
    }

    @Override
    public int[] getIndicesDown()
    {
        return indicesDown;
    }
}
