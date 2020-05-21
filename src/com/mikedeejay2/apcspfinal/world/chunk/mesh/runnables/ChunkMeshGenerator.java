package com.mikedeejay2.apcspfinal.world.chunk.mesh.runnables;

import com.mikedeejay2.apcspfinal.Main;
import com.mikedeejay2.apcspfinal.utils.DirectionEnum;
import com.mikedeejay2.apcspfinal.voxel.Voxel;
import com.mikedeejay2.apcspfinal.voxel.shape.VoxelShape;
import com.mikedeejay2.apcspfinal.voxel.shape.VoxelShapeCube;
import com.mikedeejay2.apcspfinal.world.World;
import com.mikedeejay2.apcspfinal.world.chunk.Chunk;
import com.mikedeejay2.apcspfinal.world.chunk.mesh.MeshRequest;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static com.mikedeejay2.apcspfinal.world.chunk.mesh.algorithms.BrightnessAlgorithms.*;
import static com.mikedeejay2.apcspfinal.world.chunk.mesh.algorithms.IndexAlgorithms.*;
import static com.mikedeejay2.apcspfinal.world.chunk.mesh.algorithms.TextureCoordAlgorithms.*;
import static com.mikedeejay2.apcspfinal.world.chunk.mesh.algorithms.VertexAlgorithms.*;
import static com.mikedeejay2.apcspfinal.world.chunk.mesh.algorithms.EdgeCheck.*;

public class ChunkMeshGenerator
{
    public static BlockingQueue<MeshRequest> meshRequests = new LinkedBlockingQueue<MeshRequest>();
    int capacity = Runtime.getRuntime().availableProcessors()*2;

    public void consume() throws InterruptedException
    {
        while(meshRequests.size() == 0) Thread.sleep(1);//wait();
        MeshRequest meshRequest = meshRequests.take();
        createAll(meshRequest);
    }

    public void produce(ConcurrentLinkedQueue<MeshRequest> queue) throws InterruptedException
    {
        while(meshRequests.size() == capacity) Thread.sleep(1);//wait();
        if(!queue.isEmpty())
        {
            MeshRequest meshRequest = queue.remove();
            meshRequests.add(meshRequest);
        }
    }

    public void createAll(MeshRequest meshRequest)
    {
        Chunk chunk = meshRequest.getChunk();
        World world = meshRequest.getWorld();
        if(world == null || chunk == null) return;

        if(chunk.isAlreadyBeingCalculated) return;
        chunk.isAlreadyBeingCalculated = true;

        float[] vertices = new float[0];
        float[] textureCoords = new float[0];
        int[] indices = new int[0];
        float[] brightness = new float[0];
        if(chunk.containsVoxels)
        {
            ArrayList<Float> verticesList = new ArrayList<Float>();
            ArrayList<Float> textureCoordsList = new ArrayList<Float>();
            ArrayList<Integer> indicesList = new ArrayList<Integer>();
            ArrayList<Float> brightnessList = new ArrayList<Float>();

            fillAllArrayLists(verticesList, textureCoordsList, indicesList, brightnessList, world, chunk);

            vertices = new float[verticesList.size()];
            textureCoords = new float[textureCoordsList.size()];
            indices = new int[indicesList.size()];
            brightness = new float[brightnessList.size()];
//            System.out.println(vertices.length == 0 && brightness.length == 0 ? 1 : ((float)vertices.length / (float)brightness.length) + ", " + textureCoords.length + ", " + indices.length);

            convertAllToArrays(verticesList, vertices, textureCoordsList, textureCoords, indicesList, indices, brightnessList, brightness);

            chunk.verticesTemp = vertices;
            chunk.textureCoordsTemp = textureCoords;
            chunk.indicesTemp = indices;
            chunk.brightnessTemp = brightness;

            chunk.isAlreadyBeingCalculated = false;
            world.chunksProcessedThisTick++;
            if(meshRequest.isImmediate())
            {
                Main.getInstance().getEntityCreator().addChunkImmediate(chunk);
            }
            else
            {
                chunk.entityShouldBeRemade = true;
            }
        }
        else
        {
            chunk.verticesTemp = vertices;
            chunk.textureCoordsTemp = textureCoords;
            chunk.indicesTemp = indices;
            chunk.brightnessTemp = brightness;
        }
    }

    public void convertAllToArrays(ArrayList<Float> verticesList, float[] vertices, ArrayList<Float> textureCoordsList, float[] textureCoords, ArrayList<Integer> indicesList, int[] indices, ArrayList<Float> brightnessList, float[] brightness)
    {
        for(int i = 0; i < vertices.length; i++) vertices[i] = verticesList.get(i);
        for(int i = 0; i < textureCoords.length; i++) textureCoords[i] = textureCoordsList.get(i);
        for(int i = 0; i < indices.length; i++) indices[i] = indicesList.get(i);
        for(int i = 0; i < brightness.length; i++) brightness[i] = brightnessList.get(i);

        verticesList.clear();
        textureCoordsList.clear();
        indicesList.clear();
        brightnessList.clear();
    }

    public void fillAllArrayLists(ArrayList<Float> verticesList, ArrayList<Float> textureCoordsList, ArrayList<Integer> indicesList, ArrayList<Float> brightnessList, World world, Chunk chunk)
    {
        for (int x = 0; x < World.CHUNK_SIZE; x++)
        {
            for (int y = 0; y < World.CHUNK_SIZE; y++)
            {
                for (int z = 0; z < World.CHUNK_SIZE; z++)
                {
                    if(chunk.containsVoxelAtOffset(x, y, z))
                    {
                        if(!edgeCheck(x, y, z))
                        {
                            createSlice(verticesList, textureCoordsList, indicesList, brightnessList, world, chunk, x, y, z);
                        }
                        else
                        {
                            createSliceEdge(verticesList, textureCoordsList, indicesList, brightnessList, world, chunk, x, y, z);
                        }
                    }
                }
            }
        }
    }

    public void createSlice(ArrayList<Float> verticesList, ArrayList<Float> textureCoordsList, ArrayList<Integer> indicesList, ArrayList<Float> brightnessList, World world, Chunk chunk, int x, int y, int z)
    {
        Voxel voxel = chunk.getVoxelAtOffset(x, y, z);
        if(voxel == null) return;
        VoxelShape voxelShape = voxel.getVoxelShape();
        voxel = null;


        if (!chunk.containsVoxelAtOffset(x + 1, y, z) || (chunk.isLiquid(x + 1, y, z) && !chunk.isLiquid(x, y, z)))
            createSingle(x, y, z, voxelShape, DirectionEnum.WEST, false, 0.9f, verticesList, textureCoordsList, indicesList, brightnessList, world, chunk);
        if (!chunk.containsVoxelAtOffset(x - 1, y, z) || (chunk.isLiquid(x - 1, y, z) && !chunk.isLiquid(x, y, z)))
            createSingle(x, y, z, voxelShape, DirectionEnum.EAST, false, 0.8f, verticesList, textureCoordsList, indicesList, brightnessList, world, chunk);
        if (!chunk.containsVoxelAtOffset(x, y + 1, z) || (chunk.isLiquid(x, y + 1, z) && !chunk.isLiquid(x, y, z)))
            createSingle(x, y, z, voxelShape, DirectionEnum.UP, false, 1f, verticesList, textureCoordsList, indicesList, brightnessList, world, chunk);
        if (!chunk.containsVoxelAtOffset(x, y - 1, z) || (chunk.isLiquid(x, y - 1, z) && !chunk.isLiquid(x, y, z)))
            createSingle(x, y, z, voxelShape, DirectionEnum.DOWN, false, 0.7f, verticesList, textureCoordsList, indicesList, brightnessList, world, chunk);
        if (!chunk.containsVoxelAtOffset(x, y, z + 1) || (chunk.isLiquid(x, y, z + 1) && !chunk.isLiquid(x, y, z)))
            createSingle(x, y, z, voxelShape, DirectionEnum.NORTH, false, 0.7f, verticesList, textureCoordsList, indicesList, brightnessList, world, chunk);
        if (!chunk.containsVoxelAtOffset(x, y, z - 1) || (chunk.isLiquid(x, y, z - 1) && !chunk.isLiquid(x, y, z)))
            createSingle(x, y, z, voxelShape, DirectionEnum.SOUTH, false, 0.7f, verticesList, textureCoordsList, indicesList, brightnessList, world, chunk);
        voxelShape = null;
    }

    public void createSingle(int x, int y, int z, VoxelShape voxelShape, DirectionEnum directionEnum, boolean edge, float lightVal, ArrayList<Float> verticesList, ArrayList<Float> textureCoordsList, ArrayList<Integer> indicesList, ArrayList<Float> brightnessList, World world, Chunk chunk)
    {
        float[] vertices = new float[0];
        int[] indices = new int[0];
        switch(directionEnum)
        {
            case UP: vertices = voxelShape.getVerticesUp(); indices = voxelShape.getIndicesUp(); break;
            case DOWN: vertices = voxelShape.getVerticesDown(); indices = voxelShape.getIndicesDown(); break;
            case NORTH: vertices = voxelShape.getVerticesNorth(); indices = voxelShape.getIndicesNorth(); break;
            case SOUTH: vertices = voxelShape.getVerticesSouth(); indices = voxelShape.getIndicesSouth(); break;
            case EAST: vertices = voxelShape.getVerticesEast(); indices = voxelShape.getIndicesEast(); break;
            case WEST: vertices = voxelShape.getVerticesWest(); indices = voxelShape.getIndicesWest(); break;
        }
        createVertex(x, y, z, vertices, verticesList);
        createTextureCoord(textureCoordsList, chunk.getVoxelIDAtOffset(x, y, z));
        createIndex(indices, indicesList);
        createLightValue(world, chunk, lightVal, brightnessList, x, y, z, edge, directionEnum);
    }

    public void createSliceEdge(ArrayList<Float> verticesList, ArrayList<Float> textureCoordsList, ArrayList<Integer> indicesList, ArrayList<Float> brightnessList, World world, Chunk chunk, int x, int y, int z)
    {
        Voxel voxel = chunk.getVoxelAtOffset(x, y, z);
        if(voxel == null) return;
        VoxelShape voxelShape = voxel.getVoxelShape();
        voxel = null;

        if (!chunk.containsVoxelAtOffset(x + 1, y, z) || (chunk.isLiquid(x + 1, y, z) && !chunk.isLiquid(x, y, z)))
            if(x != World.CHUNK_SIZE - 1)
                createSingle(x, y, z, voxelShape, DirectionEnum.WEST, true, 0.9f, verticesList, textureCoordsList, indicesList, brightnessList, world, chunk);
            else
            {
                Chunk newChunk = world.getChunk(new Vector3f(chunk.chunkLoc.x + 1, chunk.chunkLoc.y, chunk.chunkLoc.z));
                if(newChunk != null && newChunk.hasLoaded)
                    if(newChunk.getVoxelIDAtOffset(0, y, z) == 0 || (newChunk.isLiquid(0, y, z) && !chunk.isLiquid(x, y, z)) || !chunk.isSolid(x, y, z))
                        createSingle(x, y, z, voxelShape, DirectionEnum.WEST, true, 0.9f, verticesList, textureCoordsList, indicesList, brightnessList, world, chunk);
            }

        if (!chunk.containsVoxelAtOffset(x - 1, y, z) || (chunk.isLiquid(x - 1, y, z) && !chunk.isLiquid(x, y, z)))
            if(x != 0)
                createSingle(x, y, z, voxelShape, DirectionEnum.EAST, true, 0.8f, verticesList, textureCoordsList, indicesList, brightnessList, world, chunk);
            else
            {
                Chunk newChunk = world.getChunk(new Vector3f(chunk.chunkLoc.x - 1, chunk.chunkLoc.y, chunk.chunkLoc.z));
                if(newChunk != null && newChunk.hasLoaded)
                    if(newChunk.getVoxelIDAtOffset(World.CHUNK_SIZE - 1, y, z) == 0 || (newChunk.isLiquid(World.CHUNK_SIZE - 1, y, z) && !chunk.isLiquid(x, y, z)) || !chunk.isSolid(x, y, z))
                        createSingle(x, y, z, voxelShape, DirectionEnum.EAST, true, 0.8f, verticesList, textureCoordsList, indicesList, brightnessList, world, chunk);
            }

        if (!chunk.containsVoxelAtOffset(x, y + 1, z) || (chunk.isLiquid(x, y + 1, z) && !chunk.isLiquid(x, y, z)))
            if(y != World.CHUNK_SIZE - 1)
                createSingle(x, y, z, voxelShape, DirectionEnum.UP, true, 1f, verticesList, textureCoordsList, indicesList, brightnessList, world, chunk);
            else
            {
                Chunk newChunk = world.getChunk(new Vector3f(chunk.chunkLoc.x, chunk.chunkLoc.y + 1, chunk.chunkLoc.z));
                if(newChunk != null && newChunk.hasLoaded)
                    if(newChunk.getVoxelIDAtOffset(x, 0, z) == 0 || (newChunk.isLiquid(x, 0, z) && !chunk.isLiquid(x, y, z)) || !chunk.isSolid(x, y, z))
                        createSingle(x, y, z, voxelShape, DirectionEnum.UP, true, 1f, verticesList, textureCoordsList, indicesList, brightnessList, world, chunk);
            }

        if (!chunk.containsVoxelAtOffset(x, y - 1, z) || (chunk.isLiquid(x, y - 1, z) && !chunk.isLiquid(x, y, z)))
            if(y != 0)
                createSingle(x, y, z, voxelShape, DirectionEnum.DOWN, true, 0.7f, verticesList, textureCoordsList, indicesList, brightnessList, world, chunk);
            else
            {
                Chunk newChunk = world.getChunk(new Vector3f(chunk.chunkLoc.x, chunk.chunkLoc.y - 1, chunk.chunkLoc.z));
                if(newChunk != null && chunk.hasLoaded)
                    if(newChunk.getVoxelIDAtOffset(x, World.CHUNK_SIZE - 1, z) == 0 || (newChunk.isLiquid(x, World.CHUNK_SIZE - 1, z) && !chunk.isLiquid(x, y, z)) || !chunk.isSolid(x, y, z))
                        createSingle(x, y, z, voxelShape, DirectionEnum.DOWN, true, 0.7f, verticesList, textureCoordsList, indicesList, brightnessList, world, chunk);
            }

        if (!chunk.containsVoxelAtOffset(x, y, z + 1) || (chunk.isLiquid(x, y, z + 1) && !chunk.isLiquid(x, y, z)))
            if(z != World.CHUNK_SIZE - 1)
                createSingle(x, y, z, voxelShape, DirectionEnum.NORTH, true, 0.7f, verticesList, textureCoordsList, indicesList, brightnessList, world, chunk);
            else
            {
                Chunk newChunk = world.getChunk(new Vector3f(chunk.chunkLoc.x, chunk.chunkLoc.y, chunk.chunkLoc.z + 1));
                if(newChunk != null && newChunk.hasLoaded)
                    if(newChunk.getVoxelIDAtOffset(x, y, 0) == 0 || (newChunk.isLiquid(x, y, 0) && !chunk.isLiquid(x, y, z)) || !chunk.isSolid(x, y, z))
                        createSingle(x, y, z, voxelShape, DirectionEnum.NORTH, true, 0.7f, verticesList, textureCoordsList, indicesList, brightnessList, world, chunk);
            }

        if (!chunk.containsVoxelAtOffset(x, y, z - 1) || (chunk.isLiquid(x, y, z - 1) && !chunk.isLiquid(x, y, z)))
            if(z != 0)
                createSingle(x, y, z, voxelShape, DirectionEnum.SOUTH, true, 0.7f, verticesList, textureCoordsList, indicesList, brightnessList, world, chunk);
            else
            {
                Chunk newChunk = world.getChunk(new Vector3f(chunk.chunkLoc.x, chunk.chunkLoc.y, chunk.chunkLoc.z - 1));
                if(newChunk != null && newChunk.hasLoaded)
                    if(newChunk.getVoxelIDAtOffset(x, y, World.CHUNK_SIZE - 1) == 0 || (newChunk.isLiquid(x, y, World.CHUNK_SIZE - 1) && !chunk.isLiquid(x, y, z)) || !chunk.isSolid(x, y, z))
                        createSingle(x, y, z, voxelShape, DirectionEnum.SOUTH, true, 0.7f, verticesList, textureCoordsList, indicesList, brightnessList, world, chunk);
            }
            voxelShape = null;
    }
}
