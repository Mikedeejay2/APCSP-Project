package com.mikedeejay2.apcspfinal.world.chunk.mesh.algorithms;

import com.mikedeejay2.apcspfinal.world.World;

public class EdgeCheck
{


    public static boolean edgeCheck(float x, float y, float z)
    {
        return x == 0 || y == 0 || z == 0 || x == World.CHUNK_SIZE-1 || y == World.CHUNK_SIZE-1 || z == World.CHUNK_SIZE-1;
    }

    public static boolean edgeCheckSingle(float x)
    {
        return x == 0 || x == World.CHUNK_SIZE-1;
    }
}
