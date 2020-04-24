package com.mikedeejay2.voxel.game;

public class TimeLoop
{
    public static double currentTime = 0;
    public static double previousTime = 0;
    public static double timeDifference = 0;

    public static double tickCount = 0;
    public static boolean shouldTick = false;

    public static void update()
    {
        shouldTick = false;
        previousTime = currentTime;
        currentTime = System.currentTimeMillis();
        timeDifference = currentTime - previousTime;

        tickCount += timeDifference;
        if(tickCount >= 50)
        {
            tickCount = 0;
            shouldTick = true;
        }


    }

    public static boolean shouldTick()
    {
        return shouldTick;
    }
}
