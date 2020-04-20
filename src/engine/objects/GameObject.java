package engine.objects;

import engine.graphics.Material;
import engine.graphics.Mesh;
import engine.maths.Quaternion;
import engine.maths.Transform;
import engine.maths.Vector3f;

public class GameObject
{
    private Transform transform;
    private Material material;
    private Mesh mesh;

    public GameObject(Vector3f position, Quaternion quaternion, Vector3f scale, Mesh mesh, Material material)
    {
        System.out.println("GameObject");
        transform = new Transform();
        transform.setPos(position);
        transform.setRot(quaternion);
        transform.setScale(scale);
        this.mesh = mesh;
        this.material = material;
    }

    public void create()
    {
        material.create();
    }

    public void destroy()
    {

    }

    public void update(float delta)
    {
        //transform.setRot(transform.getRot().addDegrees(0, 0, 1, 1));
        //transform.setPos(transform.getPos().add(0, 0, 0.01f));
    }

    public Vector3f getPosition()
    {
        return transform.getPos();
    }

    public Quaternion getRotation()
    {
        return transform.getRot();
    }

    public Vector3f getScale()
    {
        return transform.getScale();
    }

    public Mesh getMesh()
    {
        return mesh;
    }

    public Transform getTransform()
    {
        return transform;
    }

    public Material getMaterial()
    {
        return material;
    }
}
