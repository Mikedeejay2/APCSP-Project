package com.mikedeejay2.apcspfinal.graphics.textures;

import org.lwjgl.opengl.GL30;

/*
 * This class is loosely organized off of ThinMatrix's
 * LWJGL tutorials, but was created by me without
 * rewatching any tutorials. I learned LWJGL from ThinMatrix,
 * so similarities will be visible.
 */
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
