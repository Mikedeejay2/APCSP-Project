package engine.models;

public class RawModel {

    private int vaoID;
    private int vertexCount;

    public RawModel(int vaoID, int vertexCount){
        System.out.println("RawModel");
        this.vaoID = vaoID;
        this.vertexCount = vertexCount;
    }

    public int getVaoID() {
        return vaoID;
    }

    public int getVertexCount() {
        return vertexCount;
    }
}
