package com.mikedeejay2.voxel.game.voxel;

import com.mikedeejay2.voxel.engine.graphics.objects.Entity;
import com.mikedeejay2.voxel.game.Main;
import com.mikedeejay2.voxel.game.voxel.VoxelTypes;
import org.joml.Vector3f;

public class Voxel
{
    Entity voxelEntity;

    Main main = Main.getInstance();

    public Voxel(String voxelName, Vector3f location)
    {
        voxelEntity = new Entity(VoxelTypes.getFromName(voxelName), location);
    }

    public void render()
    {
        main.getRenderer().processEntity(voxelEntity);
    }

    public Entity getVoxelEntity()
    {
        return voxelEntity;
    }

    public void setVoxelEntity(Entity voxelEntity)
    {
        this.voxelEntity = voxelEntity;
    }

    public Vector3f getLocation()
    {
        return voxelEntity.getPosition();
    }
}
