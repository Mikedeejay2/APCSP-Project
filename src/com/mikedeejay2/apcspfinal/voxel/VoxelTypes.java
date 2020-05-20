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

    public static final Voxel dirt = loadVoxel("dirt", VoxelShape.voxelShapeCube,1, true, false);
    public static final Voxel grass = loadVoxel("grass", VoxelShape.voxelShapeCube, 2, true, false);
    public static final Voxel stone = loadVoxel("stone", VoxelShape.voxelShapeCube, 3, true, false);
    public static final Voxel water = loadVoxel("water", VoxelShape.voxelShapeLiquid, 4, true, true);
    public static final Voxel sand = loadVoxel("sand", VoxelShape.voxelShapeCube, 5, true,  false);







    private static Voxel loadVoxel(String name, VoxelShape voxelshape, int ID, boolean solid, boolean liquid)
    {
        Voxel voxel = new Voxel(name, voxelshape, ID, solid, liquid);
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
