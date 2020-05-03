package com.mikedeejay2.voxel.engine.graphics.models;

import com.mikedeejay2.voxel.game.Main;
import org.lwjgl.opengl.GL30;

public class RawModel {

    private int vaoID;
    private int vertexCount;

    public RawModel(int vaoID, int vertexCount){
        //System.out.println("RawModel");
        this.vaoID = vaoID;
        this.vertexCount = vertexCount;
    }

    public int getVaoID() {
        return vaoID;
    }

    public int getVertexCount() {
        return vertexCount;
    }

    public void destroy()
    {
        Main.getLoader().deleteVAO(vaoID);
        vertexCount = 0;
    }
}
