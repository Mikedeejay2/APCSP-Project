package com.mikedeejay2.voxel.engine.graphics.renderers;

import com.mikedeejay2.voxel.engine.graphics.models.TexturedModel;
import com.mikedeejay2.voxel.engine.graphics.objects.Camera;
import com.mikedeejay2.voxel.engine.graphics.objects.Entity;
import com.mikedeejay2.voxel.engine.graphics.shaders.StaticShader;
import com.mikedeejay2.voxel.game.Main;
import com.mikedeejay2.voxel.game.world.World;
import com.mikedeejay2.voxel.game.world.chunk.Chunk;
import com.mikedeejay2.voxel.game.world.chunk.mesh.ChunkMeshConsumer;
import com.mikedeejay2.voxel.game.world.chunk.mesh.ChunkMeshGenerator;
import com.mikedeejay2.voxel.game.world.chunk.mesh.ChunkMeshProducer;
import com.mikedeejay2.voxel.game.world.chunk.mesh.MeshRequest;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MasterRenderer
{
    private StaticShader shader = new StaticShader();
    private Renderer renderer = new Renderer(shader);

    private Map<TexturedModel, List<Entity>> entities = new HashMap<TexturedModel, List<Entity>>();

    public ChunkMeshProducer chunkMeshProducer;
    public ChunkMeshConsumer chunkMeshConsumer;
    public Thread chunkMeshProducerThread;
    public Thread chunkMeshConsumerThread;

    public ChunkMeshGenerator chunkMeshGenerator;

    public MasterRenderer()
    {
        chunkMeshGenerator = new ChunkMeshGenerator();

        chunkMeshProducer = new ChunkMeshProducer(chunkMeshGenerator);
        chunkMeshConsumer = new ChunkMeshConsumer(chunkMeshGenerator);

        chunkMeshProducerThread = new Thread(chunkMeshProducer, "chunkMeshProducer");
        chunkMeshConsumerThread = new Thread(chunkMeshConsumer, "chunkMeshConsumer");
        chunkMeshProducerThread.start();
        chunkMeshConsumerThread.start();
    }

    public void render(Camera camera)
    {
        renderer.prepare();
        shader.start();
        shader.loadSkyColor(Renderer.RED, Renderer.GREEN, Renderer.BLUE);
        shader.loadViewMatrix(camera);
        renderer.render(entities);
        shader.stop();
        entities.clear();
    }

    public void processEntity(Entity entity)
    {
        TexturedModel entityModel = entity.getModel();
        List<Entity> batch = entities.get(entityModel);
        if(batch != null)
        {
            batch.add(entity);
        }
        else
        {
            List<Entity> newBatch = new ArrayList<Entity>();
            newBatch.add(entity);
            entities.put(entityModel, newBatch);
        }
    }

    public void genMesh(Chunk chunk, World world, boolean shouldUpdateNeighbors)
    {
        chunkMeshProducer.addRequest(new MeshRequest(chunk, world, shouldUpdateNeighbors));
    }

    public void cleanUp()
    {
        shader.cleanUp();
        chunkMeshProducerThread.stop();
        chunkMeshConsumerThread.stop();
    }
}
