package com.mikedeejay2.voxel.engine.graphics.models;

import com.mikedeejay2.voxel.game.Main;

public class RawModel {

    private int vaoID;

    private int[] vbos;

    private int vertexCount;

    public RawModel(int vaoID, int vertexCount, int[] vbos){
        this.vaoID = vaoID;
        this.vertexCount = vertexCount;
        this.vbos = vbos;
    }

    public RawModel(int vaoID, int vertexCount){
        this.vaoID = vaoID;
        this.vertexCount = vertexCount;
        vbos = new int[0];
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
        for(int i = 0; i < vbos.length; i++)
            Main.getLoader().deleteVBO(vbos[i]);
        vertexCount = 0;
    }
}
