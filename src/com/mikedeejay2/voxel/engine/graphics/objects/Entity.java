package com.mikedeejay2.voxel.engine.graphics.objects;

import com.mikedeejay2.voxel.engine.graphics.models.TexturedModel;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL30;

public class Entity
{
    private TexturedModel model;
    private Vector3f position;
    private float rotX, rotY, rotZ;
    private float scale;

    public Entity(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale)
    {
        this.model = model;
        this.position = position;
        this.rotX = rotX;
        this.rotY = rotY;
        this.rotZ = rotZ;
        this.scale = scale;
    }

    public Entity(TexturedModel model, Vector3f position)
    {
        this.model = model;
        this.position = position;
        this.rotX = 0;
        this.rotY = 0;
        this.rotZ = 0;
        this.scale = 1;
    }

    public void increasePosition(float dx, float dy, float dz, float delta)
    {
        this.position.x += (dx * delta);
        this.position.y += (dy * delta);
        this.position.z += (dz * delta);
    }

    public void increaseRotation(float dx, float dy, float dz, float delta)
    {
        this.rotX += dx * delta;
        this.rotY += dy * delta;
        this.rotZ += dz * delta;
    }

    public TexturedModel getModel()
    {
        return model;
    }

    public void setModel(TexturedModel model)
    {
        this.model = model;
    }

    public Vector3f getPosition()
    {
        return position;
    }

    public void setPosition(Vector3f position)
    {
        this.position = position;
    }

    public void setPosition(float x, float y, float z)
    {
        this.position.set(x, y, z);
    }

    public float getRotX()
    {
        return rotX;
    }

    public void setRotX(float rotX)
    {
        this.rotX = rotX;
    }

    public float getRotY()
    {
        return rotY;
    }

    public void setRotY(float rotY)
    {
        this.rotY = rotY;
    }

    public float getRotZ()
    {
        return rotZ;
    }

    public void setRotZ(float rotZ)
    {
        this.rotZ = rotZ;
    }

    public float getScale()
    {
        return scale;
    }

    public void setScale(float scale)
    {
        this.scale = scale;
    }

    public void destroy()
    {
        model.destroy();
        model = null;
        position = null;
    }
}
