package com.mikedeejay2.apcspfinal.graphics.textures;

import org.lwjgl.opengl.GL30;

public class ModelTexture
{
    private int textureID;

    public ModelTexture(int id)
    {
        this.textureID = id;
    }

    public int getID()
    {
        return textureID;
    }

    public void destroy()
    {
        GL30.glDeleteTextures(textureID);
    }
}
