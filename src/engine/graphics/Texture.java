package engine.graphics;

import org.lwjgl.BufferUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.ByteBuffer;
import java.util.HashMap;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

public class Texture
{
    private static HashMap<String, TextureResource> loadedTextures = new HashMap<String, TextureResource>();
    private TextureResource resource;
    private String fileName;
    int width, height;

    public Texture(String fileName)
    {
        System.out.println("Texture");
        this.fileName = fileName;
        TextureResource oldResource = loadedTextures.get(fileName);

        if(oldResource != null)
        {
            resource = oldResource;
            resource.addReference();
        }
        else
        {
            resource = loadTexture(fileName);
            loadedTextures.put(fileName, resource);
        }
    }

    @Override
    protected void finalize()
    {
        if(resource.removeReference() && !fileName.isEmpty())
        {
            loadedTextures.remove(fileName);
        }
    }

    public void bind(int samplerSlot)
    {
        assert(samplerSlot >= 0 && samplerSlot <= 31);
        glActiveTexture(GL_TEXTURE0 + samplerSlot);
        glBindTexture(GL_TEXTURE_2D, resource.getId());
    }

    public void bind()
    {
        bind(0);
    }

    public void bindAsRenderTarget()
    {

    }

    public int getId()
    {
        return resource.getId();
    }

    private TextureResource loadTexture(String fileName)
    {
        try
        {
            BufferedImage image = ImageIO.read(new File("./res/textures/" + fileName));
            int[] pixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
            ByteBuffer buffer = BufferUtils.createByteBuffer(image.getHeight() * image.getWidth() * 4);

            boolean hasAlpha = image.getColorModel().hasAlpha();

            width = image.getWidth();
            height = image.getHeight();

            for(int y = 0; y < image.getHeight(); y++)
            {
                for(int x = 0; x < image.getWidth(); x++)
                {
                    int pixel = pixels[y * image.getWidth() + x];

                    buffer.put((byte)((pixel >> 16) & 0xFF));
                    buffer.put((byte)((pixel >> 8) & 0xFF));
                    buffer.put((byte)((pixel) & 0xFF));
                    if(hasAlpha)
                        buffer.put((byte)((pixel >> 24) & 0xFF));
                    else
                        buffer.put((byte)(0xFF));
                }
            }

            buffer.flip();

            TextureResource resource = new TextureResource();
            glBindTexture(GL_TEXTURE_2D, resource.getId());

            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
//
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, image.getWidth(), image.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);

            return resource;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }
}
