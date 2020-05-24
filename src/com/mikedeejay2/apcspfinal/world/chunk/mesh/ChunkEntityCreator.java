package com.mikedeejay2.apcspfinal.world.chunk.mesh;

import com.mikedeejay2.apcspfinal.Main;
import com.mikedeejay2.apcspfinal.graphics.models.RawModel;
import com.mikedeejay2.apcspfinal.graphics.models.TexturedModel;
import com.mikedeejay2.apcspfinal.graphics.objects.Entity;
import com.mikedeejay2.apcspfinal.graphics.textures.ModelTexture;
import com.mikedeejay2.apcspfinal.voxel.VoxelTypes;
import com.mikedeejay2.apcspfinal.world.chunk.Chunk;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ChunkEntityCreator
{
    private LinkedList<Chunk> queue = new LinkedList<Chunk>();
    private LinkedList<Chunk> immediateQueue = new LinkedList<Chunk>();

    public void update()
    {
        while(!immediateQueue.isEmpty()) createImmediate();
        while(!queue.isEmpty()) create();
    }

    public void create()
    {
        if(queue.isEmpty()) return;
        Chunk chunk = queue.removeFirst();
        createChunkEntity(chunk);
    }

    public void createImmediate()
    {
        if(immediateQueue.isEmpty()) return;
        try
        {
            Chunk chunk = immediateQueue.removeFirst();
            createChunkEntity(chunk);
        }
        catch(NoSuchElementException e) {}
    }

    private Entity createChunkEntity(Chunk chunk)
    {
        if(chunk.verticesTemp == null || chunk.textureCoordsTemp == null || chunk.indicesTemp == null || chunk.brightnessTemp == null) return null;

        if(chunk.chunkEntity != null) chunk.chunkEntity.destroy();
        chunk.chunkEntity = null;

        RawModel model = Main.getLoader().loadToVAO(chunk.verticesTemp, chunk.textureCoordsTemp, chunk.indicesTemp, chunk.brightnessTemp);


        ModelTexture modelTexture = VoxelTypes.getTextureAtlas().getTexture();
        TexturedModel texturedModel = new TexturedModel(model, modelTexture);
        Entity entity = new Entity(texturedModel, chunk.chunkCoords);

        chunk.chunkEntity = entity;
        chunk.entityShouldBeRemade = false;

        chunk.shouldRender = chunk.verticesTemp.length >= 12;

        chunk.verticesTemp = null;
        chunk.textureCoordsTemp = null;
        chunk.indicesTemp = null;
        chunk.brightnessTemp = null;
        return entity;
    }

    public void addChunk(Chunk chunk)
    {
        queue.add(chunk);
    }

    public void addChunkImmediate(Chunk chunk)
    {
        immediateQueue.add(chunk);
    }
}
