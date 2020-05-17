package com.mikedeejay2.apcspfinal.voxel;

import org.joml.Vector3f;

public class Voxel
{
    private String name;
    private int ID;

    private Vector3f position;

    @Deprecated
    public Voxel(String name)
    {
        Voxel parentVoxel = VoxelTypes.getFromName(name);
        this.name = parentVoxel.name;
        this.ID = parentVoxel.ID;
        this.position = parentVoxel.position;
    }

    public Voxel(int ID, Vector3f position)
    {
        Voxel parentVoxel = VoxelTypes.getFromID(ID);
        this.name = parentVoxel.name;
        this.ID = parentVoxel.ID;
        this.position = position;
    }

    //Original
    public Voxel(String name, int ID)
    {
        this.name = name;
        this.ID = ID;
        this.position = null;
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
}
