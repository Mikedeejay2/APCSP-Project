package engine.loaders;

import engine.models.RawModel;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

public class Loader
{
    static List<Integer> vaos = new ArrayList<Integer>();
    static List<Integer> vbos = new ArrayList<Integer>();

    public RawModel loadToVAO(float[] vertices)
    {
        int vaoID = createVAO();
        storeDataInAttributeList(vertices, 0, 3);
        glBindVertexArray(0);

        return new RawModel(vaoID, vertices.length);
    }

    private int createVAO()
    {
        int vaoID = glGenVertexArrays();
        vaos.add(vaoID);
        glBindVertexArray(vaoID);

        return vaoID;
    }

    private void storeDataInAttributeList(float[] data, int attributeNumber, int dimensions)
    {
        int vboID = glGenBuffers();
        vbos.add(vboID);
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        FloatBuffer buffer = storeDataInFloatBuffer(data);
        glBufferData(GL_ARRAY_BUFFER, data, GL_STATIC_DRAW);
        glVertexAttribPointer(attributeNumber, dimensions, GL_FLOAT, false, 0, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

    }

    private FloatBuffer storeDataInFloatBuffer(float[] data)
    {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();

        return buffer;
    }

    public void cleanUp()
    {
        for(int vao : vaos) glDeleteVertexArrays(vao);
        for(int vbo : vbos) glDeleteVertexArrays(vbo);
    }
}
