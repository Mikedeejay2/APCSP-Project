package com.mikedeejay2.apcspfinal.graphics.textures;

import org.lwjgl.opengl.GL30;

public class ModelTexture
{
    private int textureID;

    private int width;
    private int height;

    public ModelTexture(int id, int width, int height)
    {
        this.textureID = id;
        this.width = width;
        this.height = height;
    }

    public int getID()
    {
        return textureID;
    }

    public void destroy()
    {
        GL30.glDeleteTextures(textureID);
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }
}
