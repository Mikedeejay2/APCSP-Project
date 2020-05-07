package com.mikedeejay2.voxel.engine.graphics.renderers;

import com.mikedeejay2.voxel.engine.graphics.models.RawModel;
import com.mikedeejay2.voxel.engine.graphics.models.TexturedModel;
import com.mikedeejay2.voxel.engine.graphics.shaders.StaticShader;
import com.mikedeejay2.voxel.engine.graphics.objects.Entity;
import com.mikedeejay2.voxel.engine.io.Window;
import com.mikedeejay2.voxel.engine.utils.Maths;
import com.mikedeejay2.voxel.game.Main;
import com.mikedeejay2.voxel.game.world.chunk.Chunk;
import com.mikedeejay2.voxel.game.world.chunk.mesh.ChunkMeshConsumer;
import com.mikedeejay2.voxel.game.world.chunk.mesh.ChunkMeshGenerator;
import com.mikedeejay2.voxel.game.world.chunk.mesh.ChunkMeshProducer;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL30;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL32.GL_DEPTH_CLAMP;

public class Renderer
{
    private Matrix4f transformationMatrix;

    private Matrix4f projectionMatrix;

    private StaticShader shader;

    private static final float FOV = 100;
    private static final float NEAR_PLANE = 0.1f;
    private static final float FAR_PLANE = 1000;

    public static final float RED = 0.6f;
    public static final float GREEN = 0.8f;
    public static final float BLUE = 1.0f;

    public Renderer(StaticShader shader)
    {
        glEnable(GL_CW);
        glCullFace(GL_BACK);
        glEnable(GL_CULL_FACE);
        glEnable(GL_DEPTH_TEST);

        glEnable(GL_DEPTH_CLAMP);

        glEnable(GL_TEXTURE_2D);
        this.shader = shader;
        transformationMatrix = new Matrix4f();
        projectionMatrix = new Matrix4f();
        createProjectionMatrix();
        shader.start();
        shader.loadProjectionMatrix(projectionMatrix);
        shader.stop();
    }

    public void prepare()
    {
        glClearColor(RED, GREEN, BLUE, 1);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    public void render(Map<TexturedModel, List<Entity>> entities)
    {
        for(TexturedModel model : entities.keySet())
        {
            prepareTexturedModel(model);
            List<Entity> batch = entities.get(model);
            for(Entity entity : batch)
            {
                prepareInstance(entity);
                glDrawElements(GL_TRIANGLES, model.getRawModel().getVertexCount(), GL_UNSIGNED_INT, 0);
            }
            unbindTexturedModel();
        }
    }

    private void prepareTexturedModel(TexturedModel texturedModel)
    {
        RawModel model = texturedModel.getRawModel();
        glBindVertexArray(model.getVaoID());
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glEnableVertexAttribArray(2);
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, texturedModel.getTexture().getID());
    }

    private void unbindTexturedModel()
    {
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(2);
        glBindVertexArray(0);
    }

    private void prepareInstance(Entity entity)
    {
        Maths.createTransformationMatrix(entity.getPosition(), entity.getRotX(), entity.getRotY(), entity.getRotZ(), entity.getScale(), transformationMatrix);
        shader.loadTransformationMatrix(transformationMatrix);
    }

    private void createProjectionMatrix()
    {
        float aspectRatio = (float) Window.getWidth() / (float) Window.getHeight();
        float y_scale = (1f / (float) Math.tan(Math.toRadians(FOV / 2f))) * aspectRatio;
        float x_scale = y_scale / aspectRatio;
        float frustrum_length = FAR_PLANE - NEAR_PLANE;

        projectionMatrix.m00(x_scale);
        projectionMatrix.m11(y_scale);
        projectionMatrix.m22(-((FAR_PLANE + NEAR_PLANE) / frustrum_length));
        projectionMatrix.m23(-1);
        projectionMatrix.m32(-((2 * NEAR_PLANE * FAR_PLANE) / frustrum_length));
        projectionMatrix.m33(0);
    }
}
