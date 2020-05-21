package com.mikedeejay2.apcspfinal.graphics.renderers;

import com.mikedeejay2.apcspfinal.Main;
import com.mikedeejay2.apcspfinal.graphics.models.TexturedModel;
import com.mikedeejay2.apcspfinal.graphics.models.RawModel;
import com.mikedeejay2.apcspfinal.graphics.shaders.StaticShader;
import com.mikedeejay2.apcspfinal.graphics.objects.Entity;
import com.mikedeejay2.apcspfinal.io.Window;
import com.mikedeejay2.apcspfinal.utils.Maths;
import com.mikedeejay2.apcspfinal.world.WorldLightColor;
import org.joml.Matrix4f;

import java.util.List;
import java.util.Map;

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

    private static WorldLightColor worldLightColor;

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

    public void windowHasBeenResized()
    {
        createProjectionMatrix();
        shader.start();
        shader.loadProjectionMatrix(projectionMatrix);
        shader.stop();
    }

    public void prepare()
    {
        worldLightColor = Main.getInstance().getWorld().getWorldLightColor();
        glClearColor(worldLightColor.getSkyColorR(), worldLightColor.getSkyColorG(), worldLightColor.getSkyColorB(), 1);
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
        float aspectRatio = (float) Window.getWindowWidth() / (float) Window.getWindowHeight();
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

    public Matrix4f getProjectionMatrix()
    {
        return projectionMatrix;
    }
}
