package com.mikedeejay2.apcspfinal.collision;

import org.joml.Vector3f;

public class AxisAlignedBB
{
    //Bottom left corner
    public float x1, y1, z1;

    //Top left corner
    public float x2, y2, z2;

    public AxisAlignedBB(float x1, float y1, float z1, float x2, float y2, float z2)
    {
        this.x1 = x1;
        this.y1 = y1;
        this.z1 = z1;
        this.x2 = x2;
        this.y2 = y2;
        this.z2 = z2;
    }

    public boolean intersect(AxisAlignedBB other, Vector3f currentPos, Vector3f otherPos)
    {
        if(currentPos.x + x2 < otherPos.x + other.x1) return false;
        if(currentPos.y + y2 < otherPos.y + other.y1) return false;
        if(currentPos.z + z2 < otherPos.z + other.z1) return false;

        if(currentPos.x + x1 > otherPos.x + other.x2) return false;
        if(currentPos.y + y1 > otherPos.y + other.y2) return false;
        if(currentPos.z + z1 > otherPos.z + other.z2) return false;

        return true;
    }







    public float getX1()
    {
        return x1;
    }

    public void setX1(float x1)
    {
        this.x1 = x1;
    }

    public float getY1()
    {
        return y1;
    }

    public void setY1(float y1)
    {
        this.y1 = y1;
    }

    public float getX2()
    {
        return x2;
    }

    public void setX2(float x2)
    {
        this.x2 = x2;
    }

    public float getY2()
    {
        return y2;
    }

    public void setY2(float y2)
    {
        this.y2 = y2;
    }

    public float getZ1()
    {
        return z1;
    }

    public void setZ1(float z1)
    {
        this.z1 = z1;
    }

    public float getZ2()
    {
        return z2;
    }

    public void setZ2(float z2)
    {
        this.z2 = z2;
    }
}
