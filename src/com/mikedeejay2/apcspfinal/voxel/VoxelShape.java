package com.mikedeejay2.apcspfinal.voxel;

public class VoxelShape
{

    private static float[] vertices = new float[]
            {
                    -0.5f, 0.5f, -0.5f, //NORTH FACE
                    -0.5f, -0.5f, -0.5f,
                    0.5f, -0.5f, -0.5f,
                    0.5f, 0.5f, -0.5f,

                    -0.5f, 0.5f, 0.5f, //SOUTH FACE
                    -0.5f, -0.5f, 0.5f,
                    0.5f, -0.5f, 0.5f,
                    0.5f, 0.5f, 0.5f,

                    0.5f, 0.5f, -0.5f, //WEST FACE
                    0.5f, -0.5f, -0.5f,
                    0.5f, -0.5f, 0.5f,
                    0.5f, 0.5f, 0.5f,

                    -0.5f, 0.5f, -0.5f, //EAST FACE
                    -0.5f, -0.5f, -0.5f,
                    -0.5f, -0.5f, 0.5f,
                    -0.5f, 0.5f, 0.5f,

                    -0.5f, 0.5f, 0.5f, //UP FACE
                    -0.5f, 0.5f, -0.5f,
                    0.5f, 0.5f, -0.5f,
                    0.5f, 0.5f, 0.5f,

                    -0.5f, -0.5f, 0.5f, //DOWN FACE
                    -0.5f, -0.5f, -0.5f,
                    0.5f, -0.5f, -0.5f,
                    0.5f, -0.5f, 0.5f
            };

    private static float[] verticesFaceNorth = new float[]
            {
                    -0.5f, 0.5f, -0.5f, //NORTH FACE
                    -0.5f, -0.5f, -0.5f,
                    0.5f, -0.5f, -0.5f,
                    0.5f, 0.5f, -0.5f
            };

    private static float[] verticesFaceSouth = new float[]
            {
                    -0.5f, 0.5f, 0.5f, //SOUTH FACE
                    -0.5f, -0.5f, 0.5f,
                    0.5f, -0.5f, 0.5f,
                    0.5f, 0.5f, 0.5f
            };

    private static float[] verticesFaceWest = new float[]
            {
                    0.5f, 0.5f, -0.5f, //WEST FACE
                    0.5f, -0.5f, -0.5f,
                    0.5f, -0.5f, 0.5f,
                    0.5f, 0.5f, 0.5f
            };

    private static float[] verticesFaceEast = new float[]
            {
                    -0.5f, 0.5f, -0.5f, //EAST FACE
                    -0.5f, -0.5f, -0.5f,
                    -0.5f, -0.5f, 0.5f,
                    -0.5f, 0.5f, 0.5f
            };

    private static float[] verticesFaceUp = new float[]
            {
                    -0.5f, 0.5f, 0.5f, //UP FACE
                    -0.5f, 0.5f, -0.5f,
                    0.5f, 0.5f, -0.5f,
                    0.5f, 0.5f, 0.5f
            };

    private static float[] verticesFaceDown = new float[]
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

    private static int[] indicesFaceNorth = new int[]
            {
                    3,1,0, //NORTH FACE
                    2,1,3
            };

    private static int[] indicesFaceSouth = new int[]
            {
                    0,1,3, //SOUTH FACE
                    3,1,2,
            };

    private static int[] indicesFaceWest = new int[]
            {
                    3,1,0, //WEST FACE
                    2,1,3,
            };

    private static int[] indicesFaceEast = new int[]
            {
                    0,1,3, //EAST FACE
                    3,1,2,
            };

    private static int[] indicesFaceUp = new int[]
            {
                    3,1,0, //UP FACE
                    2,1,3,
            };

    private static int[] indicesFaceDown = new int[]
            {
                    0,1,3, //DOWN FACE
                    3,1,2
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

    private static float[] textureCoordsSingleSide = new float[]
            {
                    0, 0,
                    0, 0.0078125f,
                    0.0078125f, 0.0078125f,
                    0.0078125f, 0
            };

    private static float[] brightness = new float[]
            {
                    1, 1, 1,
                    1, 1, 1,
                    1, 1, 1,
                    1, 1, 1,

                    1, 1, 1,
                    1, 1, 1,
                    1, 1, 1,
                    1, 1, 1,

                    1, 1, 1,
                    1, 1, 1,
                    1, 1, 1,
                    1, 1, 1,

                    1, 1, 1,
                    1, 1, 1,
                    1, 1, 1,
                    1, 1, 1,

                    1, 1, 1,
                    1, 1, 1,
                    1, 1, 1,
                    1, 1, 1,

                    1, 1, 1,
                    1, 1, 1,
                    1, 1, 1,
                    1, 1, 1
            };

    private static float[] brightnessSingleSide = new float[]
            {
                    1, 1, 1,
                    1, 1, 1,
                    1, 1, 1,
                    1, 1, 1
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

    public static float[] getVerticesFaceNorth()
    {
        return verticesFaceNorth;
    }

    public static float[] getVerticesFaceSouth()
    {
        return verticesFaceSouth;
    }

    public static float[] getVerticesFaceWest()
    {
        return verticesFaceWest;
    }

    public static float[] getVerticesFaceEast()
    {
        return verticesFaceEast;
    }

    public static float[] getVerticesFaceUp()
    {
        return verticesFaceUp;
    }

    public static float[] getVerticesFaceDown()
    {
        return verticesFaceDown;
    }

    public static float[] getTextureCoordsSingleSide()
    {
        return textureCoordsSingleSide;
    }

    public static int[] getIndicesFaceNorth()
    {
        return indicesFaceNorth;
    }

    public static int[] getIndicesFaceSouth()
    {
        return indicesFaceSouth;
    }

    public static int[] getIndicesFaceWest()
    {
        return indicesFaceWest;
    }

    public static int[] getIndicesFaceEast()
    {
        return indicesFaceEast;
    }

    public static int[] getIndicesFaceUp()
    {
        return indicesFaceUp;
    }

    public static int[] getIndicesFaceDown()
    {
        return indicesFaceDown;
    }

    public static float[] getBrightness()
    {
        return brightness;
    }

    public static float[] getBrightnessSingleSide()
    {
        return brightnessSingleSide;
    }
}
