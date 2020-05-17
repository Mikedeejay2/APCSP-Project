package com.mikedeejay2.apcspfinal.voxel;

import org.joml.Vector3f;

public class Voxel
{
    private String name;
    private int ID;

    private Vector3f position;

    private boolean solid;

    @Deprecated
    public Voxel(String name)
    {
        Voxel parentVoxel = VoxelTypes.getFromName(name);
        this.name = parentVoxel.name;
        this.ID = parentVoxel.ID;
        this.position = parentVoxel.position;
        this.solid = parentVoxel.isSolid();
    }

    public Voxel(int ID, Vector3f position)
    {
        Voxel parentVoxel = VoxelTypes.getFromID(ID);
        this.name = parentVoxel.name;
        this.ID = parentVoxel.ID;
        this.solid = parentVoxel.isSolid();
        this.position = position;
    }

    //Original
    public Voxel(String name, int ID, boolean solid)
    {
        this.name = name;
        this.ID = ID;
        this.position = null;
        this.solid = solid;
    }

    public Vector3f getPosition()
    {
        return position;
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
}
