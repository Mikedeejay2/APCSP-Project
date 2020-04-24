package com.mikedeejay2.voxel.game.voxel;

import com.mikedeejay2.voxel.engine.graphics.models.RawModel;
import com.mikedeejay2.voxel.engine.graphics.models.TexturedModel;
import com.mikedeejay2.voxel.engine.graphics.objects.Entity;
import com.mikedeejay2.voxel.engine.graphics.textures.ModelTexture;

import static com.mikedeejay2.voxel.game.Main.loader;

public class VoxelTypes
{
    public static final TexturedModel dirt = loadVoxel("dirt");

    public static TexturedModel loadVoxel(String name)
    {
        ModelTexture voxelT = new ModelTexture(loader.loadTexture("block/" + name + ".png"));
        System.out.println("wqeoifjweoif");
        return new TexturedModel(VoxelShape.getVoxelModel(), voxelT);
    }
}
