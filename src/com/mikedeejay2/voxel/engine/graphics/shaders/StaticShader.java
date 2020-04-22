package com.mikedeejay2.voxel.engine.graphics.shaders;

import org.joml.Matrix4f;

public class StaticShader extends ShaderProgram
{
    private static final String VERTEX_FILE = "res/shaders/staticVertex.glsl";
    private static final String FRAGMENT_FILE = "res/shaders/staticFragment.glsl";

    private int location_transformationMatrix;

    public StaticShader()
    {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void bindAttributes()
    {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textureCoords");
    }

    @Override
    protected void getAllUniformLocations()
    {
        location_transformationMatrix = super.getUniformLocation("transformationMatrix");
    }

    public void loadTransformationMatrix(Matrix4f matrix)
    {
        super.loadMatrix(location_transformationMatrix, matrix);
    }
}
