package com.mikedeejay2.voxel.engine.graphics.shaders;

import com.mikedeejay2.voxel.engine.graphics.objects.Camera;
import com.mikedeejay2.voxel.engine.utils.Maths;
import org.joml.Matrix4f;

public class StaticShader extends ShaderProgram
{
    private static final String VERTEX_FILE = "res/shaders/staticVertex.glsl";
    private static final String FRAGMENT_FILE = "res/shaders/staticFragment.glsl";

    private int location_transformationMatrix;
    private int location_projectionMatrix;
    private int location_viewMatrix;

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
        location_projectionMatrix = super.getUniformLocation("projectionMatrix");
        location_viewMatrix = super.getUniformLocation("viewMatrix");
    }

    public void loadTransformationMatrix(Matrix4f matrix)
    {
        super.loadMatrix(location_transformationMatrix, matrix);
    }

    public void loadViewMatrix(Camera camera)
    {
        Maths.createViewMatrix(camera, camera.getViewMatrix());
        super.loadMatrix(location_viewMatrix, camera.getViewMatrix());
    }

    public void loadProjectionMatrix(Matrix4f projection)
    {
        super.loadMatrix(location_projectionMatrix, projection);
    }
}
