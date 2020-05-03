package com.mikedeejay2.voxel.engine.graphics.models;

import com.mikedeejay2.voxel.engine.graphics.textures.ModelTexture;
import org.lwjgl.opengl.GL30;

public class TexturedModel
{
    private RawModel rawModel;
    private ModelTexture texture;

    public TexturedModel(RawModel model, ModelTexture texture)
    {
        this.rawModel = model;
        this.texture = texture;
    }

    public RawModel getRawModel()
    {
        return rawModel;
    }

    public ModelTexture getTexture()
    {
        return texture;
    }

    @Override
    protected void finalize() throws Throwable
    {
        super.finalize();
        rawModel = null;
        texture = null;
    }

    public void destroy()
    {
        rawModel.destroy();
        rawModel = null;
        texture = null;
    }
}
