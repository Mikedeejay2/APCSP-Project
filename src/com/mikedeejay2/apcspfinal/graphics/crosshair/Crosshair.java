package com.mikedeejay2.apcspfinal.graphics.crosshair;

import com.mikedeejay2.apcspfinal.Main;
import com.mikedeejay2.apcspfinal.io.Window;
import com.mikedeejay2.apcspfinal.loaders.Loader;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Crosshair
{
    private static final String crosshairPath = "crosshair/crosshair.png";

    private static float ar = (float)Window.getWindowWidth() / Window.getWindowHeight();

    private static final float[] textureCoords = new float[]
            {
                    0, 1,
                    0, 0,
                    1, 0,

                    1, 0,
                    1, 1,
                    0, 1
            };

    private static final float[] positions = new float[]
            {
                    -0.015f, 0.015f*ar,
                    -0.015f, -0.015f*ar,
                    0.015f, -0.015f*ar,

                    0.015f, -0.015f*ar,
                    0.015f, 0.015f*ar,
                    -0.015f, 0.015f*ar
            };

    private int meshVao;
    private int vertexCount;

    private int texture;

    private Vector3f color = new Vector3f(1f, 1f, 1f);

    private Vector2f position;

    private int[] vbos;

    public Crosshair(Loader loader)
    {
        int[] vaovbo = loader.loadToVAO(positions, textureCoords);
        meshVao = vaovbo[0];
        vbos = new int[] {vaovbo[1], vaovbo[2]};
        vertexCount = positions.length/2;
        texture = loader.loadTexture(crosshairPath);
    }

    public int getMeshVao()
    {
        return meshVao;
    }

    public int getVertexCount()
    {
        return vertexCount;
    }

    public Vector3f getColor()
    {
        return color;
    }

    public Vector2f getPosition()
    {
        return position;
    }

    public int[] getVbos()
    {
        return vbos;
    }

    public void destroy()
    {
        Main.getLoader().deleteVAO(meshVao);
        for(int i = 0; i < vbos.length; i++)
        {
            Main.getLoader().deleteVBO(vbos[i]);
        }
        Main.getLoader().deleteTexture(texture);
    }

    public int getTexture()
    {
        return texture;
    }
}
