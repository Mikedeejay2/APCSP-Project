package com.mikedeejay2.voxel.engine.loaders;

import com.mikedeejay2.voxel.engine.graphics.models.RawModel;
import org.lwjgl.BufferUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL30.*;

public class Loader
{
    private List<Integer> vaos = new ArrayList<Integer>();
    private List<Integer> vbos = new ArrayList<Integer>();
    private List<Integer> textures = new ArrayList<Integer>();

    public int[] loadToVAO(float[] positions, float[] textureCoords) {
        int vaoID = createVAO();
        int vboID1 = storeDataInAttributeList(0, 2, positions);
        int vboID2 = storeDataInAttributeList(1, 2, textureCoords);
        unbindVAO();
        return new int[] {vaoID, vboID1, vboID2};
    }

    public RawModel loadToVAO(float[] positions, float[] textureCoords, int[] indices, float[] brightness) {
        int vaoID = createVAO();
        bindIndicesBuffer(indices);
        int positionsVBO = storeDataInAttributeList(0, 3, positions);
        int textureCoordsVBO = storeDataInAttributeList(1, 2, textureCoords);
        int brightnessVBO = storeDataInAttributeList(2, 3, brightness);
        unbindVAO();
        return new RawModel(vaoID, indices.length, new int[] {positionsVBO, textureCoordsVBO, brightnessVBO});
    }

    public RawModel loadToVAO(float[] positions, int dimensions) {
        int vaoID = createVAO();
        this.storeDataInAttributeList(0, dimensions, positions);
        unbindVAO();
        return new RawModel(vaoID, positions.length / dimensions);
    }

    public int loadTexture(String fileName)
    {
        try
        {
            BufferedImage image = ImageIO.read(new File("./res/textures/" + fileName));
            int[] pixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
            ByteBuffer buffer = BufferUtils.createByteBuffer(image.getHeight() * image.getWidth() * 4);

            boolean hasAlpha = image.getColorModel().hasAlpha();

            int width = image.getWidth();
            int height = image.getHeight();

            for(int y = 0; y < image.getHeight(); y++)
            {
                for(int x = 0; x < image.getWidth(); x++)
                {
                    int pixel = pixels[y * image.getWidth() + x];

                    buffer.put((byte)((pixel >> 16) & 0xFF));
                    buffer.put((byte)((pixel >> 8) & 0xFF));
                    buffer.put((byte)((pixel) & 0xFF));
                    if(hasAlpha)
                        buffer.put((byte)((pixel >> 24) & 0xFF));
                    else
                        buffer.put((byte)(0xFF));
                }
            }

            buffer.flip();

            int texID = glGenTextures();
            glBindTexture(GL_TEXTURE_2D, texID);

            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);

            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, image.getWidth(), image.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);

            textures.add(texID);
            return texID;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.exit(1);
        }
        return -1;
    }

    public void cleanUp()
    {
        for(int vao : vaos)
        {
            glDeleteVertexArrays(vao);
        }
        for(int vbo : vbos)
        {
            glDeleteBuffers(vbo);
        }
        for(int texture : textures)
        {
            glDeleteTextures(texture);
        }
    }

    public void deleteVAO(int vao)
    {
        glDeleteVertexArrays(vao);
        vaos.remove(Integer.valueOf(vao));
    }

    public void deleteVBO(int vbo)
    {
        glDeleteBuffers(vbo);
        vbos.remove(Integer.valueOf(vbo));
    }

    public void deleteTexture(int texture)
    {
        glDeleteTextures(texture);
        textures.remove(Integer.valueOf(texture));
    }

    private int createVAO()
    {
        int vaoID = glGenVertexArrays();
        vaos.add(vaoID);
        glBindVertexArray(vaoID);
        return vaoID;
    }

    private int storeDataInAttributeList(int attributeNumber, int coordinateSize, float[] data) {
        int vboID = glGenBuffers();
        vbos.add(vboID);
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        FloatBuffer buffer = storeDataInFloatBuffer(data);
        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
        glVertexAttribPointer(attributeNumber, coordinateSize, GL_FLOAT, false, 0, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        return vboID;
    }

    private IntBuffer storeDataInIntBuffer(int[] data) {
        IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    private FloatBuffer storeDataInFloatBuffer(float[] data) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    private void unbindVAO()
    {
        glBindVertexArray(0);
    }

    private void bindIndicesBuffer(int[] indices) {
        int vboID = glGenBuffers();
        vbos.add(vboID);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboID);
        IntBuffer buffer = storeDataInIntBuffer(indices);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
    }

    public List<Integer> getVaos()
    {
        return vaos;
    }

    public List<Integer> getVbos()
    {
        return vbos;
    }

    public List<Integer> getTextures()
    {
        return textures;
    }
}
