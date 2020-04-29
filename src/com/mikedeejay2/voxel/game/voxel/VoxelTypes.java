package com.mikedeejay2.voxel.game.voxel;

import com.mikedeejay2.voxel.engine.graphics.models.RawModel;
import com.mikedeejay2.voxel.engine.graphics.models.TexturedModel;
import com.mikedeejay2.voxel.engine.graphics.objects.Entity;
import com.mikedeejay2.voxel.engine.graphics.textures.ModelTexture;

import java.util.HashMap;

import static com.mikedeejay2.voxel.game.Main.loader;

public class VoxelTypes
{
    public static HashMap<String, TexturedModel> allVoxelTypes = new HashMap<String, TexturedModel>();

    public static final TexturedModel dirt = loadVoxel("dirt");
    public static final TexturedModel stone = loadVoxel("stone");
    public static final TexturedModel diamond_block = loadVoxel("diamond_block");
    public static final TexturedModel gold_block = loadVoxel("gold_block");







    public static TexturedModel loadVoxel(String name)
    {
        ModelTexture voxelT = new ModelTexture(loader.loadTexture("block/" + name + ".png"));
        TexturedModel voxel = new TexturedModel(VoxelShape.getVoxelModel(), voxelT);
        allVoxelTypes.put(name, voxel);
        return voxel;
    }

    public static TexturedModel getFromName(String voxelName)
    {
        if(allVoxelTypes.containsKey(voxelName))
            return allVoxelTypes.get(voxelName);
        return null;
    }
}
