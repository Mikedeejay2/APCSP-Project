package engine.objects;

import engine.maths.Matrix4f;
import engine.maths.Transform;
import engine.maths.Vector3f;

public class Camera
{
    Transform transform;
    private Matrix4f projection;
    private FreeLook freeLook;
    private FreeMove freeMove;

    public Camera(float fov, float aspect, float zNear, float zFar, float sensitivity, float speed)
    {
        transform = new Transform();
        this.projection = new Matrix4f().initPerspective(fov, aspect, zNear, zFar);
        this.freeLook = new FreeLook(this, sensitivity);
        this.freeMove = new FreeMove(this, speed);
    }

    public void update()
    {
        freeLook.input();
        freeMove.input();
    }

    public Matrix4f getViewProjection()
    {
        Matrix4f cameraRotation = transform.getTransformedRot().conjugate().toRotationMatrix();
        Vector3f cameraPos = transform.getTransformedPos().mul(-1);

        Matrix4f cameraTranslation = new Matrix4f().initTranslation(cameraPos.getX(), cameraPos.getY(), cameraPos.getZ());

        return projection.mul(cameraRotation.mul(cameraTranslation));
    }

    public Transform getTransform()
    {
        return transform;
    }
}
