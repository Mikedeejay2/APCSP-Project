package com.mikedeejay2.apcspfinal.graphics.models;

import com.mikedeejay2.apcspfinal.Main;

public class RawModel {

    private int vaoID;

    private int[] vbos;

    private int vertexCount;

    public RawModel(int vaoID, int vertexCount, int[] vbos){
        this.vaoID = vaoID;
        this.vertexCount = vertexCount;
        this.vbos = vbos;
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
        {
            Main.getLoader().deleteVBO(vbos[i]);
        }
        vertexCount = 0;
    }
}