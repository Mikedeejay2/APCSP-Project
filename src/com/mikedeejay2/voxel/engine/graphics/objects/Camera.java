//package com.mikedeejay2.voxel.engine.graphics.objects;
//
//import org.joml.Matrix4f;
//import org.joml.Quaternionf;
//import org.joml.Vector3f;
//
//public class Camera
//{
//    Vector3f position;
//    Quaternionf rotation;
//    private FreeLook freeLook;
//    private FreeMove freeMove;
//
//    public Camera(float fov, float aspect, float zNear, float zFar, float sensitivity, float speed)
//    {
//        System.out.println("Camera");
//        this.freeLook = new FreeLook(this, sensitivity);
//        this.freeMove = new FreeMove(this, speed);
//        this.position = new Vector3f(0, 0,0);
//        this.rotation = new Quaternionf();
//    }
//
//    public void update(float delta)
//    {
//        freeLook.input(delta);
//        freeMove.input(delta);
//    }
//
//    public Vector3f getPosition()
//    {
//        return position;
//    }
//
//    public Quaternionf getRotation()
//    {
//        return rotation;
//    }
//}
