package com.mikedeejay2.apcspfinal.world.noise;

import java.util.Random;

public class PerlinNoiseGenerator {

    public static float AMPLITUDE = 70f;
    public static int OCTAVES = 7;
    public static float ROUGHNESS = 0.1f;

    private Random random = new Random();
    private int seed;
    private int offsetX = 0;
    private int offsetZ = 0;

    public PerlinNoiseGenerator() {
        this.seed = 392874598;
    }

    public PerlinNoiseGenerator(int gridX, int gridZ, int vertexCount, int seed) {
        this.seed = seed;
        offsetX = gridX * (vertexCount-1);
        offsetZ = gridZ * (vertexCount-1);
    }

    public float genNoise(int x, int z) {

        x = x < 0 ? -x : x;
        z = z < 0 ? -z : z;

        float total = 0;
        float d = (float) Math.pow(2, OCTAVES-1);
        for(int i=0;i<OCTAVES;i++){
            float freq = (float) (Math.pow(2, i) / d);
            float amp = (float) Math.pow(ROUGHNESS, i) * AMPLITUDE;
            total += getMixedNoise((x+ offsetX)*freq, (z + offsetZ)*freq) * amp;
        }

        return (float) (int) total;

    }

    private float getMixedNoise(float x, float z){
        int intX = (int) x;
        int intZ = (int) z;
        float fracX = x - intX;
        float fracZ = z - intZ;

        float v1 = genSmoothNoise(intX, intZ);
        float v2 = genSmoothNoise(intX + 1, intZ);
        float v3 = genSmoothNoise(intX, intZ + 1);
        float v4 = genSmoothNoise(intX + 1, intZ + 1);
        float i1 = interpolate(v1, v2, fracX);
        float i2 = interpolate(v3, v4, fracX);
        return interpolate(i1, i2, fracZ);
    }

    private float interpolate(float a, float b, float blend){
        double theta = blend * Math.PI;
        float f = (float)(1f - Math.cos(theta)) * 0.5f;
        return a * (1f - f) + b * f;
    }

    private float genSmoothNoise(int x, int z) {
        float corners = (createNoise(x - 1, z - 1) + createNoise(x + 1, z - 1) + createNoise(x - 1, z + 1)
                + createNoise(x + 1, z + 1)) / 16f;
        float sides = (createNoise(x - 1, z) + createNoise(x + 1, z) + createNoise(x, z - 1)
                + createNoise(x, z + 1)) / 8f;
        float center = createNoise(x, z) / 4f;
        return corners + sides + center;
    }

    private float createNoise(int x, int z) {
        random.setSeed(x * 49632 + z * 325176 + seed);
        return random.nextFloat() * 2f - 1f;
    }


}
