package com.mikedeejay2.voxel.game.voxel;

import com.mikedeejay2.voxel.engine.graphics.objects.Entity;
import com.mikedeejay2.voxel.game.Main;
import org.joml.Vector3f;

public class Voxel
{
    Entity voxelEntity;

    public Voxel(String voxelName, Vector3f location)
    {
        voxelEntity = new Entity(VoxelTypes.getFromName(voxelName), location);
    }

    public void render()
    {
        Main.getInstance().getRenderer().processEntity(voxelEntity);
    }

    @Override
    protected void finalize() throws Throwable
    {
        super.finalize();
        voxelEntity = null;

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
