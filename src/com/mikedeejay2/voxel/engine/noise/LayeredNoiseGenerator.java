package com.mikedeejay2.voxel.engine.noise;

public class LayeredNoiseGenerator extends SimplexNoiseGenerator
{
    private final float SCALE = 1000;

    public LayeredNoiseGenerator(long seed)
    {
        super(seed);
    }

    public float getLayeredNoise(float x, float z, int layers)
    {
        float noise = 0;
        noise += eval(x/1000f, z/1000f)*100f;
        noise += eval(x/100f, z/100f)*20f;
        noise += eval(x/20f, z/20f)*5f;
        return noise+10;
    }
}
