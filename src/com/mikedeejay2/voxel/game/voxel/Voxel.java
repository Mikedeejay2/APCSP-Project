package com.mikedeejay2.voxel.game.voxel;

import com.mikedeejay2.voxel.engine.graphics.objects.Entity;
import com.mikedeejay2.voxel.game.Main;
import org.joml.Vector3f;

public class Voxel
{
    Entity entity;
    String name;

    public Voxel(String voxelName, Vector3f location)
    {
        entity = new Entity(VoxelTypes.getFromName(voxelName), location);
        this.name = voxelName;
    }

    @Deprecated
    public void render()
    {
        Main.getInstance().getRenderer().processEntity(entity);
    }

    @Override
    protected void finalize() throws Throwable
    {
        super.finalize();
        entity = null;

    }

    public Entity getEntity()
    {
        return entity;
    }

    public void setEntity(Entity entity)
    {
        this.entity = entity;
    }

    public Vector3f getLocation()
    {
        return entity.getPosition();
    }
}
