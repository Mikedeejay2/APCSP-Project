//package com.mikedeejay2.voxel.engine.graphics.objects;
//
//import com.mikedeejay2.voxel.engine.io.Input;
//import com.mikedeejay2.voxel.engine.graphics.objects.Camera;
//import org.joml.Vector3f;
//import org.lwjgl.glfw.GLFW;
//
//public class FreeMove
//{
//    private float speed;
//    private int forwardKey;
//    private int backKey;
//    private int leftKey;
//    private int rightKey;
//
//    Camera camera;
//
//    public FreeMove(Camera camera, float speed)
//    {
//        this(camera, speed, GLFW.GLFW_KEY_W, GLFW.GLFW_KEY_S, GLFW.GLFW_KEY_A, GLFW.GLFW_KEY_D);
//    }
//
//    public FreeMove(Camera camera, float speed, int forwardKey, int backKey, int leftKey, int rightKey)
//    {
//        System.out.println("FreeMove");
//        this.speed = speed;
//        this.forwardKey = forwardKey;
//        this.backKey = backKey;
//        this.leftKey = leftKey;
//        this.rightKey = rightKey;
//        this.camera = camera;
//    }
//
//    public void input(float delta)
//    {
//        float movAmt = speed * delta;
//
//        if(Input.getKey(GLFW.GLFW_KEY_ESCAPE))
//        {
//            Input.setCursor(true);
//        }
//
//        if(Input.getKey(forwardKey))
//            move(camera.getRotation(), movAmt);
//        if(Input.getKey(backKey))
//            move(camera.getTransform().getRot().getForward(), -movAmt);
//        if(Input.getKey(leftKey))
//            move(camera.getTransform().getRot().getLeft(), movAmt);
//        if(Input.getKey(rightKey))
//            move(camera.getTransform().getRot().getRight(), movAmt);
//    }
//
//    public void move(Vector3f dir, float amt)
//    {
//        camera.getTransform().setPos(camera.getTransform().getPos().add(dir.mul(amt)));
//    }
//}
