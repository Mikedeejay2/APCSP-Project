package com.mikedeejay2.apcspfinal.utils;

import com.mikedeejay2.apcspfinal.graphics.objects.Camera;
import com.mikedeejay2.apcspfinal.io.Input;
import com.mikedeejay2.apcspfinal.io.Window;
import com.mikedeejay2.apcspfinal.Main;
import com.mikedeejay2.apcspfinal.world.World;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class Raycast
{

    private static final int ACCURACY = 100;
    private static final float RAY_RANGE = 7;

    private Vector3f currentRay;
    private Vector3f currentPoint;

    private Matrix4f projectionMatrix;
    private Matrix4f viewMatrix;
    private Camera camera;

    World instanceWorld;

    public Raycast(Camera cam, Matrix4f projectionMatrix)
    {
        this.camera = cam;
        this.projectionMatrix = projectionMatrix;
        this.viewMatrix = new Matrix4f();
        Maths.createViewMatrix(camera, viewMatrix);
        instanceWorld = Main.getInstance().getWorld();
    }

    public Vector3f getCurrentRay()
    {
        return currentRay;
    }

    public void update()
    {
        Maths.createViewMatrix(camera, viewMatrix);
        currentRay = calculateMouseRay();
        currentPoint = step(currentRay, true);
        if(currentPoint != null) currentPoint.add((float)camera.getWorldPos().x, (float)camera.getWorldPos().y, (float)camera.getWorldPos().z);
    }

    private Vector3f calculateMouseRay()
    {
        float mouseX = (float) Input.getMousePositionX();
        float mouseY = (float) Input.getMousePositionY();
        Vector2f normalizedCoords = getNormalizedDeviceCoords(mouseX, mouseY);
        Vector4f clipCoords = new Vector4f(normalizedCoords.x, normalizedCoords.y, -1f, 1f);
        Vector4f eyeCoords = toEyeCoords(clipCoords);
        Vector3f worldRay = toWorldCoords(eyeCoords);
        return worldRay;
    }

    private Vector3f toWorldCoords(Vector4f eyeCoords)
    {
        Matrix4f invertedView = viewMatrix.invert();
        Vector4f rayWorld = invertedView.transform(eyeCoords);
        Vector3f mouseRay = new Vector3f(rayWorld.x, rayWorld.y, rayWorld.z);
        mouseRay.normalize();
        return mouseRay;
    }

    private Vector4f toEyeCoords(Vector4f clipCoords)
    {
        Matrix4f invertedProjection = projectionMatrix.invert(new Matrix4f());
        Vector4f eyeCoords = invertedProjection.transform(clipCoords);

        return new Vector4f(eyeCoords.x/eyeCoords.w, eyeCoords.y/eyeCoords.w, eyeCoords.z/eyeCoords.w, 0.0f);
    }
    private Vector2f getNormalizedDeviceCoords(float mouseX, float mouseY)
    {
        float x = (2f*mouseX) / Window.getWindowWidth() - 1;
        float y = (2f*mouseY) / Window.getWindowHeight() - 1;
        return new Vector2f(x, y);
    }

    public Matrix4f getProjectionMatrix()
    {
        return projectionMatrix;
    }

    private Vector3f getCoordinatesOnRay(Vector3f ray, float distance) {
        Vector3f start = new Vector3f(camera.getPosition().x, camera.getPosition().y, camera.getPosition().z);
        Vector3f scaledRay = new Vector3f(ray.x * distance, ray.y * distance, ray.z * distance);
        return start.add(scaledRay);
    }

    private Vector3f step(Vector3f ray, boolean ignoreLiquids)
    {
        for(int i = 0; i < RAY_RANGE*ACCURACY; i++)
        {
            Vector3f coords = getCoordinatesOnRay(ray, (float)i/ACCURACY);

            if(instanceWorld.isVoxelAtCoordinate(
                    (int) Math.round((coords.x + camera.getWorldPos().x)),
                    (int) Math.round((coords.y + camera.getWorldPos().y)),
                    (int) Math.round((coords.z + camera.getWorldPos().z)),
                    ignoreLiquids))
                return coords;
        }
        return null;
    }

    public Vector3f getCurrentPoint()
    {
        return currentPoint;
    }
}
