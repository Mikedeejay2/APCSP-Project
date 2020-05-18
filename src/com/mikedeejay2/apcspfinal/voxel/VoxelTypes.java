package com.mikedeejay2.apcspfinal.voxel;

import com.mikedeejay2.apcspfinal.Main;
import com.mikedeejay2.apcspfinal.graphics.textures.TextureAtlas;

import java.util.HashMap;

public class VoxelTypes
{
    public static final int AMT_OF_VOXELS = 5;

    public static HashMap<String, Voxel> voxelsByName = new HashMap<String, Voxel>();
    public static Voxel[] voxelsByID = new Voxel[AMT_OF_VOXELS+1];


    public static final TextureAtlas textureAtlas = new TextureAtlas("block/blocks.png", Main.getLoader());

    public static final Voxel dirt = loadVoxel("dirt", 1, true);
    public static final Voxel grass = loadVoxel("grass", 2, true);
    public static final Voxel stone = loadVoxel("stone", 3, true);
    public static final Voxel water = loadVoxel("water", 4, false);
    public static final Voxel sand = loadVoxel("sand", 5, true);







    private static Voxel loadVoxel(String name, int ID, boolean solid)
    {
        Voxel voxel = new Voxel(name, ID, solid);
        voxelsByName.put(name, voxel);
        voxelsByID[ID] = voxel;
        return voxel;
    }

    public static Voxel getFromName(String voxelName)
    {
        if(voxelsByName.containsKey(voxelName))
            return voxelsByName.get(voxelName);
        return null;
    }

    public static Voxel getFromID(int ID)
    {
        if(ID < voxelsByID.length)
            return voxelsByID[ID];
        return null;
    }

    public static int getIDFromName(String voxelName)
    {
        if(voxelsByName.containsKey(voxelName))
            return voxelsByName.get(voxelName).getID();
        return 0;
    }

    public static String getNameFromID(int ID)
    {
        if(ID < voxelsByID.length)
            return voxelsByID[ID].getName();
        return null;
    }

    public static TextureAtlas getTextureAtlas()
    {
        return textureAtlas;
    }
}
