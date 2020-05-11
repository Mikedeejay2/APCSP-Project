package com.mikedeejay2.voxel.game.world.chunk.mesh;

public class ChunkMeshData
{
    private float[] vertices;
    private float[] textureCoords;
    private int[] indices;
    private float[] brightnesses;

    public ChunkMeshData(float[] vertices, float[] textureCoords, int[] indices, float[] brightnesses)
    {
        this.vertices = vertices;
        this.textureCoords = textureCoords;
        this.indices = indices;
        this.brightnesses = brightnesses;
    }

    public float[] getVertices()
    {
        return vertices;
    }

    public float[] getTextureCoords()
    {
        return textureCoords;
    }

    public int[] getIndices()
    {
        return indices;
    }

    public float[] getBrightnesses()
    {
        return brightnesses;
    }

    public void destroy()
    {
        vertices = null;
        textureCoords = null;
        indices = null;
        brightnesses = null;
    }
}
