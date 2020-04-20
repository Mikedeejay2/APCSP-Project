package engine.graphics;

import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;

public class TextureResource
{
    private int id;
    private int refCount;

    public TextureResource()
    {
        System.out.println("TextureResource");
        this.id = glGenTextures();
        this.refCount = 1;
    }

    @Override
    protected void finalize() throws Throwable
    {
        super.finalize();
        //glDeleteBuffers(id); //TODO: HUH? WHY THIS BROKEN?
    }

    public void addReference()
    {
        refCount++;
    }

    public boolean removeReference()
    {
        refCount--;
        return refCount == 0;
    }

    public int getId()
    {
        return id;
    }
}