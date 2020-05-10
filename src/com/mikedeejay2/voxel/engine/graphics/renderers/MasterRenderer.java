package com.mikedeejay2.voxel.engine.graphics.renderers;

import com.mikedeejay2.voxel.engine.graphics.models.TexturedModel;
import com.mikedeejay2.voxel.engine.graphics.objects.Camera;
import com.mikedeejay2.voxel.engine.graphics.objects.Entity;
import com.mikedeejay2.voxel.engine.graphics.shaders.StaticShader;
import com.mikedeejay2.voxel.game.world.World;
import com.mikedeejay2.voxel.game.world.chunk.Chunk;
import com.mikedeejay2.voxel.game.world.chunk.mesh.ChunkMeshConsumer;
import com.mikedeejay2.voxel.game.world.chunk.mesh.ChunkMeshGenerator;
import com.mikedeejay2.voxel.game.world.chunk.mesh.ChunkMeshProducer;
import com.mikedeejay2.voxel.game.world.chunk.mesh.MeshRequest;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MasterRenderer
{
    private StaticShader shader = new StaticShader();
    private Renderer renderer = new Renderer(shader);

    private Map<TexturedModel, List<Entity>> entities = new HashMap<TexturedModel, List<Entity>>();

    ChunkMeshProducer chunkMeshProducer;
    public Thread chunkMeshProducerThread;
    public ArrayList<Thread> chunkMeshConsumerThreads;

    public ChunkMeshGenerator chunkMeshGenerator;

    public MasterRenderer()
    {
        chunkMeshGenerator = new ChunkMeshGenerator();

        chunkMeshProducer = new ChunkMeshProducer(chunkMeshGenerator);
        chunkMeshProducerThread = new Thread(chunkMeshProducer, "chunkMeshProducer");
        chunkMeshProducerThread.start();

        chunkMeshConsumerThreads = new ArrayList<Thread>();
        for(int i =0; i < Runtime.getRuntime().availableProcessors(); i++)
        {
            ChunkMeshConsumer chunkMeshConsumer = new ChunkMeshConsumer(chunkMeshGenerator);
            Thread threadConsumer = new Thread(chunkMeshConsumer, "chunkMeshConsumer" + i);
            chunkMeshConsumerThreads.add(threadConsumer);
            threadConsumer.start();
        }
    }

    public void windowHasBeenResized()
    {
        renderer.windowHasBeenResized();
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

    public void genMesh(Chunk chunk, World world)
    {
        chunkMeshProducer.addRequest(new MeshRequest(chunk, world));
    }

    public void cleanUp()
    {
        shader.cleanUp();
        chunkMeshProducerThread.stop();
        for(Thread thread : chunkMeshConsumerThreads)
        {
            thread.stop();
        }
    }

    public Matrix4f getProjectionMatrix()
    {
        return renderer.getProjectionMatrix();
    }
}
