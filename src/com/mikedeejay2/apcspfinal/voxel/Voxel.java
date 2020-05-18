package com.mikedeejay2.apcspfinal.voxel;

import org.joml.Vector3f;

public class Voxel
{
    private String name;
    private int ID;

    private Vector3f position;

    private boolean solid;

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
