package com.mikedeejay2.apcspfinal.voxel;

public class Voxel
{
    private String name;
    private int ID;

    VoxelShape voxelShape;

    private boolean solid;
    private boolean liquid;

    public Voxel(String name, VoxelShape voxelshape, int ID, boolean solid, boolean liquid)
    {
        this.name = name;
        this.ID = ID;
        this.solid = solid;
        this.liquid = liquid;
        this.voxelShape = voxelshape;
    }

    public String getName()
    {
        return name;
    }

    public int getID()
    {
        return ID;
    }

    public boolean isSolid()
    {
        return solid;
    }

    public boolean isLiquid()
    {
        return liquid;
    }

    public VoxelShape getVoxelShape()
    {
        return voxelShape;
    }
}
