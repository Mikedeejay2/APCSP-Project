package com.mikedeejay2.voxel.game.voxel;

import com.mikedeejay2.voxel.engine.graphics.objects.Entity;
import com.mikedeejay2.voxel.engine.graphics.textures.ModelTexture;
import com.mikedeejay2.voxel.game.Main;
import org.joml.Vector3f;

import static com.mikedeejay2.voxel.game.Main.loader;

public class Voxel
{
    private ModelTexture texture;

    private String name;
    private int ID;

    private Vector3f position;

    @Deprecated
    public Voxel(String name)
    {
        Voxel parentVoxel = VoxelTypes.getFromName(name);
        this.texture = parentVoxel.texture;
        this.name = parentVoxel.name;
        this.ID = parentVoxel.ID;
        this.position = parentVoxel.position;
    }

    public Voxel(int ID, Vector3f position)
    {
        Voxel parentVoxel = VoxelTypes.getFromID(ID);
        this.texture = parentVoxel.texture;
        this.name = parentVoxel.name;
        this.ID = parentVoxel.ID;
        this.position = position;
    }

    //Original
    public Voxel(String name, int ID)
    {
        this.texture = new ModelTexture(loader.loadTexture("block/" + name + ".png"));
        this.name = name;
        this.ID = ID;
        this.position = null;
    }

    public Vector3f getPosition()
    {
        return position;
    }

    public String getName()
    {
        return name;
    }

    public int getID()
    {
        return ID;
    }

    public ModelTexture getTexture()
    {
        return texture;
    }
}
