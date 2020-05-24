package com.mikedeejay2.apcspfinal.utils;

import org.joml.Vector3f;

public enum DirectionEnum
{
    NORTH,
    SOUTH,
    EAST,
    WEST,
    UP,
    DOWN;

    public static DirectionEnum getDirectionFromCurrentPoint(Vector3f currentPoint)
    {
        if(currentPoint.y >= 0)
        {
            if(Math.abs(currentPoint.y % 1) > 0.5 && Math.abs(currentPoint.y % 1) < 0.51) return DirectionEnum.DOWN;
            if(Math.abs(currentPoint.y % 1) < 0.5 && Math.abs(currentPoint.y % 1) > 0.49) return DirectionEnum.UP;
        }
        else
        {
            if(Math.abs(currentPoint.y % 1) < 0.5 && Math.abs(currentPoint.y % 1) > 0.49) return DirectionEnum.DOWN;
            if(Math.abs(currentPoint.y % 1) > 0.5 && Math.abs(currentPoint.y % 1) < 0.51) return DirectionEnum.UP;
        }
        if(currentPoint.x >= 0)
        {
            if(Math.abs(currentPoint.x % 1) > 0.5 && Math.abs(currentPoint.x % 1) < 0.51) return DirectionEnum.WEST;
            if(Math.abs(currentPoint.x % 1) < 0.5 && Math.abs(currentPoint.x % 1) > 0.49) return DirectionEnum.EAST;
        }
        else
        {
            if(Math.abs(currentPoint.x % 1) < 0.5 && Math.abs(currentPoint.x % 1) > 0.49) return DirectionEnum.WEST;
            if(Math.abs(currentPoint.x % 1) > 0.5 && Math.abs(currentPoint.x % 1) < 0.51) return DirectionEnum.EAST;
        }
        if(currentPoint.z >= 0)
        {
            if(Math.abs(currentPoint.z % 1) > 0.5 && Math.abs(currentPoint.z % 1) < 0.51) return DirectionEnum.NORTH;
            if(Math.abs(currentPoint.z % 1) < 0.5 && Math.abs(currentPoint.z % 1) > 0.49) return DirectionEnum.SOUTH;
        }
        else
        {
            if(Math.abs(currentPoint.z % 1) < 0.5 && Math.abs(currentPoint.z % 1) > 0.49) return DirectionEnum.NORTH;
            if(Math.abs(currentPoint.z % 1) > 0.5 && Math.abs(currentPoint.z % 1) < 0.51) return DirectionEnum.SOUTH;
        }
        return null;
    }
}
