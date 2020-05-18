package com.mikedeejay2.apcspfinal.graphics.textures;

import com.mikedeejay2.apcspfinal.loaders.Loader;

public class TextureAtlas
{
    private ModelTexture texture;

    private final int TILE_WIDTH = 16;
    private final int tilesPerRow;

    private final int width;

    public TextureAtlas(String filePath, Loader loader)
    {
        this.texture = new ModelTexture(loader.loadTexture(filePath), loader.getImageWidth(filePath), loader.getImageHeight(filePath));
        this.tilesPerRow = texture.getWidth()/TILE_WIDTH;
        this.width = texture.getWidth();
    }

    public float[] getTextureCoords(int id)
    {
        int idCorrected = id-1;
        float idMod = (idCorrected%tilesPerRow)*TILE_WIDTH;
        float idDiv = (float)(id/tilesPerRow)*TILE_WIDTH;
        return new float[]
                {
                        idMod/width, idDiv/width, // Top Left
                        idMod/width, (idDiv+TILE_WIDTH)/width, // Bottom Left
                        ((idMod)+TILE_WIDTH)/width, (idDiv+TILE_WIDTH)/width, // Bottom Right
                        ((idMod)+TILE_WIDTH)/width, idDiv/width // Top Right
                };
    }

    public ModelTexture getTexture()
    {
        return texture;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return texture.getHeight();
    }

    public int getTextureID()
    {
        return texture.getID();
    }
}
