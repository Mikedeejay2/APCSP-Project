package engine.graphics;

import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.*;

public class Mesh
{
    private Vertex[] vertices;
    private int[] indices;
    private int vao, pbo, ibo, tbo;

    public Mesh(Vertex[] vertices, int[] indices)
    {
        this.vertices = vertices;
        this.indices = indices;
    }

    public void create()
    {
        vao = glGenVertexArrays();
        glBindVertexArray(vao);

        FloatBuffer positionBuffer = MemoryUtil.memAllocFloat(vertices.length * 3);
        float[] positionData = new float[vertices.length * 3];
        for(int i = 0; i < vertices.length; i++)
        {
            positionData[i * 3] = vertices[i].getPosition().getX();
            positionData[i * 3 + 1] = vertices[i].getPosition().getY();
            positionData[i * 3 + 2] = vertices[i].getPosition().getZ();
        }

        positionBuffer.put(positionData).flip();

        pbo = storeData(positionBuffer, 0, 3);


        FloatBuffer textureBuffer = MemoryUtil.memAllocFloat(vertices.length * 2);
        float[] textureData = new float[vertices.length * 2];
        for(int i = 0; i < vertices.length; i++)
        {
            textureData[i * 2] = vertices[i].getTextureCoord().getX();
            textureData[i * 2 + 1] = vertices[i].getTextureCoord().getY();
        }

        textureBuffer.put(textureData).flip();

        tbo = storeData(textureBuffer, 2, 2);

        IntBuffer indicesBuffer = MemoryUtil.memAllocInt(indices.length);
        indicesBuffer.put(indices).flip();

        ibo = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    private int storeData(FloatBuffer buffer, int index, int size)
    {
        int bufferID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, bufferID);
        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
        glVertexAttribPointer(index, size, GL_FLOAT, false, 0, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        return bufferID;
    }

    public void destroy()
    {
        glDeleteBuffers(pbo);
        glDeleteBuffers(ibo);
        glDeleteBuffers(tbo);

        glDeleteVertexArrays(vao);
    }

    public Vertex[] getVertices()
    {
        return vertices;
    }

    public int[] getIndices()
    {
        return indices;
    }

    public int getVAO()
    {
        return vao;
    }

    public int getPBO()
    {
        return pbo;
    }

    public int getIBO()
    {
        return ibo;
    }

    public int getTBO()
    {
        return tbo;
    }
}
