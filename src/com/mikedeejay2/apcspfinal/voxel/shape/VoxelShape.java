package com.mikedeejay2.apcspfinal.voxel.shape;

public abstract class VoxelShape
{
    public abstract float[] getVertices();

    public abstract float[] getVerticesUp();
    public abstract float[] getVerticesDown();
    public abstract float[] getVerticesWest();
    public abstract float[] getVerticesEast();
    public abstract float[] getVerticesNorth();
    public abstract float[] getVerticesSouth();

    public abstract int[] getIndices();
    public abstract int[] getIndicesUp();
    public abstract int[] getIndicesDown();
    public abstract int[] getIndicesWest();
    public abstract int[] getIndicesEast();
    public abstract int[] getIndicesNorth();
    public abstract int[] getIndicesSouth();

    public static VoxelShape voxelShapeCube = new VoxelShapeCube();
    public static VoxelShape voxelShapeLiquid = new VoxelShapeLiquid();
}
