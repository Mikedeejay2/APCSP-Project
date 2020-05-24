package com.mikedeejay2.apcspfinal.world.chunk.mesh.algorithms;

import com.mikedeejay2.apcspfinal.Main;
import com.mikedeejay2.apcspfinal.utils.DirectionEnum;
import com.mikedeejay2.apcspfinal.voxel.Voxel;
import com.mikedeejay2.apcspfinal.voxel.shape.VoxelShape;
import com.mikedeejay2.apcspfinal.world.World;
import com.mikedeejay2.apcspfinal.world.WorldLightColor;
import com.mikedeejay2.apcspfinal.world.chunk.Chunk;

import java.util.List;

public class BrightnessAlgorithms
{
    public static boolean smoothLighting = true;

    public static final float AO_DEFAULT = 0.2f;

    public static  void createLightValue(World world, Chunk chunk, float value, List<Float> brightnessList, int x, int y, int z, boolean edge, DirectionEnum direction)
    {
        WorldLightColor worldLightColor = Main.getInstance().getWorld().getWorldLightColor();
        Voxel voxel = chunk.getVoxelAtOffset(x, y, z);
        if(voxel == null) return;
        VoxelShape voxelShape = voxel.getVoxelShape();

        if(!edge)
        {
            for (int i = 0; i < voxelShape.getVerticesEast().length/3f; i++)
            {
                if(smoothLighting) genBrightness(world, chunk, worldLightColor.getSunColorR()*value, worldLightColor.getSunColorG()*value, worldLightColor.getSunColorB()*value,
                        x, y, z, i, brightnessList, direction, AO_DEFAULT, false);
                else brightnessList.add(value);
            }
        }
        else
        {
            for (int i = 0; i < voxelShape.getVerticesEast().length/3f; i++)
            {
                if(smoothLighting) genBrightness(world, chunk, worldLightColor.getSunColorR()*value, worldLightColor.getSunColorG()*value, worldLightColor.getSunColorB()*value,
                        (int) (chunk.chunkCoords.x + x), (int) (chunk.chunkCoords.y + y), (int) (chunk.chunkCoords.z + z), i, brightnessList, direction, AO_DEFAULT, true);
                else brightnessList.add(value);
            }
        }
    }

    public static  void genBrightness(World world, Chunk chunk, float r, float g, float b, int x, int y, int z, int index, List<Float> brightnessList, DirectionEnum direction, float AO, boolean worldSpace)
    {
        switch (direction)
        {
            case WEST: genBrightnessWest(world, chunk, r, g, b, x, y, z, index, brightnessList, AO, worldSpace); break;
            case EAST: genBrightnessEast(world, chunk, r, g, b, x, y, z, index, brightnessList, AO, worldSpace); break;
            case UP: genBrightnessUp(world, chunk, r, g, b, x, y, z, index, brightnessList, AO, worldSpace); break;
            case DOWN: genBrightnessDown(world, chunk, r, g, b, x, y, z, index, brightnessList, AO, worldSpace); break;
            case NORTH: genBrightnessNorth(world, chunk, r, g, b, x, y, z, index, brightnessList, AO, worldSpace); break;
            case SOUTH: genBrightnessSouth(world, chunk, r, g, b, x, y, z, index, brightnessList, AO, worldSpace); break;
        }
    }

    private static void genBrightnessWest(World world, Chunk chunk, float r, float g, float b, int x, int y, int z, int index, List<Float> brightnessList, float AO, boolean worldSpace)
    {
        switch (index)
        {
            case 0:
            if(isVoxelAtCoordinate(world, chunk, x+1, y+1, z, worldSpace)) { brightnessList.add(r - AO); brightnessList.add(g - AO); brightnessList.add(b - AO); } else
            if(isVoxelAtCoordinate(world, chunk, x+1, y+1, z-1, worldSpace)) { brightnessList.add(r - AO); brightnessList.add(g - AO); brightnessList.add(b - AO); } else
            if(isVoxelAtCoordinate(world, chunk, x+1, y, z-1, worldSpace)) { brightnessList.add(r - AO); brightnessList.add(g - AO); brightnessList.add(b - AO); }
            else { brightnessList.add(r); brightnessList.add(g); brightnessList.add(b); }
            break;
            case 1:
            if(isVoxelAtCoordinate(world, chunk, x+1, y-1, z, worldSpace)) { brightnessList.add(r - AO); brightnessList.add(g - AO); brightnessList.add(b - AO); } else
            if(isVoxelAtCoordinate(world, chunk, x+1, y-1, z-1, worldSpace)) { brightnessList.add(r - AO); brightnessList.add(g - AO); brightnessList.add(b - AO); } else
            if(isVoxelAtCoordinate(world, chunk, x+1, y, z-1, worldSpace)) { brightnessList.add(r - AO); brightnessList.add(g - AO); brightnessList.add(b - AO); }
            else { brightnessList.add(r); brightnessList.add(g); brightnessList.add(b); }
            break;
            case 2:
            if(isVoxelAtCoordinate(world, chunk, x+1, y-1, z, worldSpace)) { brightnessList.add(r - AO); brightnessList.add(g - AO); brightnessList.add(b - AO); } else
            if(isVoxelAtCoordinate(world, chunk, x+1, y-1, z+1, worldSpace)) { brightnessList.add(r - AO); brightnessList.add(g - AO); brightnessList.add(b - AO); } else
            if(isVoxelAtCoordinate(world, chunk, x+1, y, z+1, worldSpace)) { brightnessList.add(r - AO); brightnessList.add(g - AO); brightnessList.add(b - AO); }
            else { brightnessList.add(r); brightnessList.add(g); brightnessList.add(b); }
            break;
            case 3:
            if(isVoxelAtCoordinate(world, chunk, x+1, y+1, z, worldSpace)) { brightnessList.add(r - AO); brightnessList.add(g - AO); brightnessList.add(b - AO); } else
            if(isVoxelAtCoordinate(world, chunk, x+1, y+1, z+1, worldSpace)) { brightnessList.add(r - AO); brightnessList.add(g - AO); brightnessList.add(b - AO); } else
            if(isVoxelAtCoordinate(world, chunk, x+1, y, z+1, worldSpace)) { brightnessList.add(r - AO); brightnessList.add(g - AO); brightnessList.add(b - AO); }
            else { brightnessList.add(r); brightnessList.add(g); brightnessList.add(b); }
            break;
        }
    }

    private static void genBrightnessEast(World world, Chunk chunk, float r, float g, float b, int x, int y, int z, int index, List<Float> brightnessList, float AO, boolean worldSpace)
    {
        switch (index)
        {
            case 0:
            if(isVoxelAtCoordinate(world, chunk, x-1, y+1, z, worldSpace)) { brightnessList.add(r - AO); brightnessList.add(g - AO); brightnessList.add(b - AO); } else
            if(isVoxelAtCoordinate(world, chunk, x-1, y+1, z-1, worldSpace)) { brightnessList.add(r - AO); brightnessList.add(g - AO); brightnessList.add(b - AO); } else
            if(isVoxelAtCoordinate(world, chunk, x-1, y, z-1, worldSpace)) { brightnessList.add(r - AO); brightnessList.add(g - AO); brightnessList.add(b - AO); }
            else { brightnessList.add(r); brightnessList.add(g); brightnessList.add(b); }
            break;
            case 1:
            if(isVoxelAtCoordinate(world, chunk, x-1, y-1, z, worldSpace)) { brightnessList.add(r - AO); brightnessList.add(g - AO); brightnessList.add(b - AO); } else
            if(isVoxelAtCoordinate(world, chunk, x-1, y-1, z-1, worldSpace)) { brightnessList.add(r - AO); brightnessList.add(g - AO); brightnessList.add(b - AO); } else
            if(isVoxelAtCoordinate(world, chunk, x-1, y, z-1, worldSpace))
            { brightnessList.add(r - AO); brightnessList.add(g - AO); brightnessList.add(b - AO); }
            else { brightnessList.add(r); brightnessList.add(g); brightnessList.add(b); }
            break;
            case 2:
            if(isVoxelAtCoordinate(world, chunk, x-1, y-1, z, worldSpace)) { brightnessList.add(r - AO); brightnessList.add(g - AO); brightnessList.add(b - AO); } else
            if(isVoxelAtCoordinate(world, chunk, x-1, y-1, z+1, worldSpace)) { brightnessList.add(r - AO); brightnessList.add(g - AO); brightnessList.add(b - AO); } else
            if(isVoxelAtCoordinate(world, chunk, x-1, y, z+1, worldSpace)) { brightnessList.add(r - AO); brightnessList.add(g - AO); brightnessList.add(b - AO); }
            else { brightnessList.add(r); brightnessList.add(g); brightnessList.add(b); }
            break;
            case 3:
            if(isVoxelAtCoordinate(world, chunk, x-1, y+1, z, worldSpace)) { brightnessList.add(r - AO); brightnessList.add(g - AO); brightnessList.add(b - AO); } else
            if(isVoxelAtCoordinate(world, chunk, x-1, y+1, z+1, worldSpace)) { brightnessList.add(r - AO); brightnessList.add(g - AO); brightnessList.add(b - AO); } else
            if(isVoxelAtCoordinate(world, chunk, x-1, y, z+1, worldSpace)) { brightnessList.add(r - AO); brightnessList.add(g - AO); brightnessList.add(b - AO); }
            else { brightnessList.add(r); brightnessList.add(g); brightnessList.add(b); }
            break;
        }
    }

    private static void genBrightnessUp(World world, Chunk chunk, float r, float g, float b, int x, int y, int z, int index, List<Float> brightnessList, float AO, boolean worldSpace)
    {
        switch (index)
        {
            case 0:
            if(isVoxelAtCoordinate(world, chunk, x-1, y+1, z, worldSpace))  { brightnessList.add(r - AO); brightnessList.add(g - AO); brightnessList.add(b - AO); } else
            if(isVoxelAtCoordinate(world, chunk, x-1, y+1, z+1, worldSpace)) { brightnessList.add(r - AO); brightnessList.add(g - AO); brightnessList.add(b - AO); } else
            if(isVoxelAtCoordinate(world, chunk, x, y+1, z+1, worldSpace)) { brightnessList.add(r - AO); brightnessList.add(g - AO); brightnessList.add(b - AO); }
            else { brightnessList.add(r); brightnessList.add(g); brightnessList.add(b); }
            break;
            case 1:
            if(isVoxelAtCoordinate(world, chunk, x-1, y+1, z, worldSpace)) { brightnessList.add(r - AO); brightnessList.add(g - AO); brightnessList.add(b - AO); } else
            if(isVoxelAtCoordinate(world, chunk, x-1, y+1, z-1, worldSpace)) 
            { brightnessList.add(r - AO); brightnessList.add(g - AO); brightnessList.add(b - AO); } else
            if(isVoxelAtCoordinate(world, chunk, x, y+1, z-1, worldSpace))  { brightnessList.add(r - AO); brightnessList.add(g - AO); brightnessList.add(b - AO); }
            else { brightnessList.add(r); brightnessList.add(g); brightnessList.add(b); }
            break;
            case 2:
            if(isVoxelAtCoordinate(world, chunk, x+1, y+1, z, worldSpace)) { brightnessList.add(r - AO); brightnessList.add(g - AO); brightnessList.add(b - AO); } else
            if(isVoxelAtCoordinate(world, chunk, x+1, y+1, z-1, worldSpace)) { brightnessList.add(r - AO); brightnessList.add(g - AO); brightnessList.add(b - AO); } else
            if(isVoxelAtCoordinate(world, chunk, x, y+1, z-1, worldSpace)) { brightnessList.add(r - AO); brightnessList.add(g - AO); brightnessList.add(b - AO); }
            else { brightnessList.add(r); brightnessList.add(g); brightnessList.add(b); }
            break;
            case 3:
            if(isVoxelAtCoordinate(world, chunk, x+1, y+1, z, worldSpace)) { brightnessList.add(r - AO); brightnessList.add(g - AO); brightnessList.add(b - AO); } else
            if(isVoxelAtCoordinate(world, chunk, x+1, y+1, z+1, worldSpace)) { brightnessList.add(r - AO); brightnessList.add(g - AO); brightnessList.add(b - AO); } else
            if(isVoxelAtCoordinate(world, chunk, x, y+1, z+1, worldSpace)) 
            { brightnessList.add(r - AO); brightnessList.add(g - AO); brightnessList.add(b - AO); }
            else { brightnessList.add(r); brightnessList.add(g); brightnessList.add(b); }
            break;
        }
    }

    private static void genBrightnessDown(World world, Chunk chunk, float r, float g, float b, int x, int y, int z, int index, List<Float> brightnessList, float AO, boolean worldSpace)
    {
        switch (index)
        {
            case 0:
            if(isVoxelAtCoordinate(world, chunk, x-1, y-1, z, worldSpace)) { brightnessList.add(r - AO); brightnessList.add(g - AO); brightnessList.add(b - AO); } else
            if(isVoxelAtCoordinate(world, chunk, x-1, y-1, z+1, worldSpace))
            { brightnessList.add(r - AO); brightnessList.add(g - AO); brightnessList.add(b - AO); } else
            if(isVoxelAtCoordinate(world, chunk, x, y-1, z+1, worldSpace)) 
            { brightnessList.add(r - AO); brightnessList.add(g - AO); brightnessList.add(b - AO); }
            else { brightnessList.add(r); brightnessList.add(g); brightnessList.add(b); }
            break;
            case 1:
            if(isVoxelAtCoordinate(world, chunk, x-1, y-1, z, worldSpace)) { brightnessList.add(r - AO); brightnessList.add(g - AO); brightnessList.add(b - AO); } else
            if(isVoxelAtCoordinate(world, chunk, x-1, y-1, z-1, worldSpace)) { brightnessList.add(r - AO); brightnessList.add(g - AO); brightnessList.add(b - AO); } else
            if(isVoxelAtCoordinate(world, chunk, x, y-1, z-1, worldSpace)) { brightnessList.add(r - AO); brightnessList.add(g - AO); brightnessList.add(b - AO); }
            else { brightnessList.add(r); brightnessList.add(g); brightnessList.add(b); }
            break;
            case 2:
            if(isVoxelAtCoordinate(world, chunk, x+1, y-1, z, worldSpace)) { brightnessList.add(r - AO); brightnessList.add(g - AO); brightnessList.add(b - AO); } else
            if(isVoxelAtCoordinate(world, chunk, x+1, y-1, z-1, worldSpace)) { brightnessList.add(r - AO); brightnessList.add(g - AO); brightnessList.add(b - AO); } else
            if(isVoxelAtCoordinate(world, chunk, x, y-1, z-1, worldSpace)) { brightnessList.add(r - AO); brightnessList.add(g - AO); brightnessList.add(b - AO); }
            else { brightnessList.add(r); brightnessList.add(g); brightnessList.add(b); }
            break;
            case 3:
            if(isVoxelAtCoordinate(world, chunk, x+1, y-1, z, worldSpace)) { brightnessList.add(r - AO); brightnessList.add(g - AO); brightnessList.add(b - AO); } else
            if(isVoxelAtCoordinate(world, chunk, x+1, y-1, z+1, worldSpace)) { brightnessList.add(r - AO); brightnessList.add(g - AO); brightnessList.add(b - AO); } else
            if(isVoxelAtCoordinate(world, chunk, x, y-1, z+1, worldSpace)) { brightnessList.add(r - AO); brightnessList.add(g - AO); brightnessList.add(b - AO); }
            else { brightnessList.add(r); brightnessList.add(g); brightnessList.add(b); }
            break;
        }
    }

    private static void genBrightnessNorth(World world, Chunk chunk, float r, float g, float b, int x, int y, int z, int index, List<Float> brightnessList, float AO, boolean worldSpace)
    {
        switch (index)
        {
            case 0:
            if(isVoxelAtCoordinate(world, chunk, x, y+1, z+1, worldSpace)) { brightnessList.add(r - AO); brightnessList.add(g - AO); brightnessList.add(b - AO); } else
            if(isVoxelAtCoordinate(world, chunk, x-1, y+1, z+1, worldSpace)) 
            { brightnessList.add(r - AO); brightnessList.add(g - AO); brightnessList.add(b - AO); } else
            if(isVoxelAtCoordinate(world, chunk, x-1, y, z+1, worldSpace)) 
            { brightnessList.add(r - AO); brightnessList.add(g - AO); brightnessList.add(b - AO); }
            else { brightnessList.add(r); brightnessList.add(g); brightnessList.add(b); }
            break;
            case 1:
            if(isVoxelAtCoordinate(world, chunk, x, y-1, z+1, worldSpace)) { brightnessList.add(r - AO); brightnessList.add(g - AO); brightnessList.add(b - AO); } else
            if(isVoxelAtCoordinate(world, chunk, x-1, y-1, z+1, worldSpace)) { brightnessList.add(r - AO); brightnessList.add(g - AO); brightnessList.add(b - AO); } else
            if(isVoxelAtCoordinate(world, chunk, x-1, y, z+1, worldSpace)) { brightnessList.add(r - AO); brightnessList.add(g - AO); brightnessList.add(b - AO); }
            else { brightnessList.add(r); brightnessList.add(g); brightnessList.add(b); }
            break;
            case 2:
            if(isVoxelAtCoordinate(world, chunk, x, y-1, z+1, worldSpace)) { brightnessList.add(r - AO); brightnessList.add(g - AO); brightnessList.add(b - AO); } else
            if(isVoxelAtCoordinate(world, chunk, x+1, y-1, z+1, worldSpace))  { brightnessList.add(r - AO); brightnessList.add(g - AO); brightnessList.add(b - AO); } else
            if(isVoxelAtCoordinate(world, chunk, x+1, y, z+1, worldSpace)) { brightnessList.add(r - AO); brightnessList.add(g - AO); brightnessList.add(b - AO); }
            else { brightnessList.add(r); brightnessList.add(g); brightnessList.add(b); }
            break;
            case 3:
            if(isVoxelAtCoordinate(world, chunk, x, y+1, z+1, worldSpace)) 
            { brightnessList.add(r - AO); brightnessList.add(g - AO); brightnessList.add(b - AO); } else
            if(isVoxelAtCoordinate(world, chunk, x+1, y+1, z+1, worldSpace)) { brightnessList.add(r - AO); brightnessList.add(g - AO); brightnessList.add(b - AO); } else
            if(isVoxelAtCoordinate(world, chunk, x+1, y, z+1, worldSpace))  { brightnessList.add(r - AO); brightnessList.add(g - AO); brightnessList.add(b - AO); }
            else { brightnessList.add(r); brightnessList.add(g); brightnessList.add(b); }
            break;
        }
    }

    private static void genBrightnessSouth(World world, Chunk chunk, float r, float g, float b, int x, int y, int z, int index, List<Float> brightnessList, float AO, boolean worldSpace)
    {
        switch (index)
        {
            case 0:
            if(isVoxelAtCoordinate(world, chunk, x, y+1, z-1, worldSpace))  { brightnessList.add(r - AO); brightnessList.add(g - AO); brightnessList.add(b - AO); } else
            if(isVoxelAtCoordinate(world, chunk, x-1, y+1, z-1, worldSpace)) { brightnessList.add(r - AO); brightnessList.add(g - AO); brightnessList.add(b - AO); } else
            if(isVoxelAtCoordinate(world, chunk, x-1, y, z-1, worldSpace)) { brightnessList.add(r - AO); brightnessList.add(g - AO); brightnessList.add(b - AO); }
            else { brightnessList.add(r); brightnessList.add(g); brightnessList.add(b); }
            break;
            case 1:
            if(isVoxelAtCoordinate(world, chunk, x, y-1, z-1, worldSpace)) { brightnessList.add(r - AO); brightnessList.add(g - AO); brightnessList.add(b - AO); } else
            if(isVoxelAtCoordinate(world, chunk, x-1, y-1, z-1, worldSpace)) { brightnessList.add(r - AO); brightnessList.add(g - AO); brightnessList.add(b - AO); } else
            if(isVoxelAtCoordinate(world, chunk, x-1, y, z-1, worldSpace)) { brightnessList.add(r - AO); brightnessList.add(g - AO); brightnessList.add(b - AO); }
            else { brightnessList.add(r); brightnessList.add(g); brightnessList.add(b); }
            break;
            case 2:
            if(isVoxelAtCoordinate(world, chunk, x, y-1, z-1, worldSpace)) { brightnessList.add(r - AO); brightnessList.add(g - AO); brightnessList.add(b - AO); } else
            if(isVoxelAtCoordinate(world, chunk, x+1, y-1, z-1, worldSpace)) { brightnessList.add(r - AO); brightnessList.add(g - AO); brightnessList.add(b - AO); } else
            if(isVoxelAtCoordinate(world, chunk, x+1, y, z-1, worldSpace)) { brightnessList.add(r - AO); brightnessList.add(g - AO); brightnessList.add(b - AO); }
            else { brightnessList.add(r); brightnessList.add(g); brightnessList.add(b); }
            break;
            case 3:
            if(isVoxelAtCoordinate(world, chunk, x, y+1, z-1, worldSpace)) { brightnessList.add(r - AO); brightnessList.add(g - AO); brightnessList.add(b - AO); } else
            if(isVoxelAtCoordinate(world, chunk, x+1, y+1, z-1, worldSpace)) { brightnessList.add(r - AO); brightnessList.add(g - AO); brightnessList.add(b - AO); } else
            if(isVoxelAtCoordinate(world, chunk, x+1, y, z-1, worldSpace)) { brightnessList.add(r - AO); brightnessList.add(g - AO); brightnessList.add(b - AO); }
            else { brightnessList.add(r); brightnessList.add(g); brightnessList.add(b); }
            break;
        }
    }

    private static boolean isVoxelAtCoordinate(World world, Chunk chunk, int x, int y, int z, boolean worldSpace)
    {
        if(worldSpace)
        {
            return world.isVoxelAtCoordinate(x, y, z, true);
        }
        else
        {
            return chunk.containsVoxelAtOffset(x, y, z, true);
        }
    }
}
