package engine.graphics;

public class Material
{
    private String path;
    private Texture texture;

    public Material(String path)
    {
        this.path = path;
    }

    public void create()
    {
        texture = new Texture(path);
    }

    public void destroy()
    {
        texture.finalize();
    }

    public int getWidth()
    {
        return texture.getWidth();
    }

    public int getHeight()
    {
        return texture.getHeight();
    }

    public int getTextureID()
    {
        return texture.getId();
    }
}
