package engine.renderers;

import engine.graphics.Mesh;
import engine.graphics.shaders.ShaderProgram;
import engine.maths.Vector3f;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL32.GL_DEPTH_CLAMP;

public class Renderer
{
    private Vector3f backgroundColor;

    private ShaderProgram shader;

    public Renderer(ShaderProgram shader)
    {
        this.shader = shader;
        backgroundColor = new Vector3f(0, 0, 0);
    }

    public void prepare()
    {
        glClearColor(backgroundColor.getX(), backgroundColor.getY(), backgroundColor.getZ(), 1.0f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

//        glFrontFace(GL_CW);
//        glCullFace(GL_BACK);
//        glEnable(GL_CULL_FACE);
//        glEnable(GL_DEPTH_TEST);
//
//        glEnable(GL_DEPTH_CLAMP);
//
//        glEnable(GL_TEXTURE_2D);
    }

    public void render()
    {
        //TODO: THIS
    }

    public void setBackgroundColor(float r, float g, float b)
    {
        backgroundColor.set(r, g, b);
    }

    public void renderMesh(Mesh mesh)
    {
        glBindVertexArray(mesh.getVAO());
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glEnableVertexAttribArray(2);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, mesh.getIBO());
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, mesh.getMaterial().getTextureID());
        shader.start();
        shader.setUniform("scale", 2.0f);

        glDrawElements(GL_TRIANGLES, mesh.getIndices().length, GL_UNSIGNED_INT, 0);

        shader.stop();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(2);
        glBindVertexArray(0);
    }
}
