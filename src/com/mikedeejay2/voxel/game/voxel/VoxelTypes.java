package com.mikedeejay2.voxel.game.voxel;

import com.mikedeejay2.voxel.engine.graphics.textures.ModelTexture;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.mikedeejay2.voxel.game.Main.loader;

public class VoxelTypes
{
    public static HashMap<String, Voxel> voxelsByName = new HashMap<String, Voxel>();
    public static HashMap<Integer, Voxel> voxelsByID = new HashMap<Integer, Voxel>();

    public static final Voxel dirt = loadVoxel("dirt", 1);
    public static final Voxel stone = loadVoxel("stone", 2);
    public static final Voxel diamond_block = loadVoxel("diamond_block", 3);
    public static final Voxel gold_block = loadVoxel("gold_block", 4);
    public static final Voxel white_concrete = loadVoxel("white_concrete", 5);
    public static final Voxel black_concrete = loadVoxel("black_concrete", 6);
    public static final Voxel light_gray_concrete = loadVoxel("light_gray_concrete", 7);
    public static final Voxel lime_concrete = loadVoxel("lime_concrete", 8);
    public static final Voxel light_blue_concrete = loadVoxel("light_blue_concrete", 9);







    private static Voxel loadVoxel(String name, int ID)
    {
        Voxel voxel = new Voxel(name, ID);
        voxelsByName.put(name, voxel);
        voxelsByID.put(ID, voxel);
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
        if(voxelsByID.containsKey(ID))
            return voxelsByID.get(ID);
        return null;
    }

    public static ModelTexture getTexture(String voxelName)
    {
        return getFromName(voxelName).getTexture();
    }

    public static int getIDFromName(String voxelName)
    {
        if(voxelsByName.containsKey(voxelName))
            return voxelsByName.get(voxelName).getID();
        return 0;
    }

    public static String getNameFromID(int ID)
    {
        if(voxelsByID.containsKey(ID))
            return voxelsByID.get(ID).getName();
        return null;
    }
}
