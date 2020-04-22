//package com.mikedeejay2.voxel.engine.graphics.objects;
//
//import com.mikedeejay2.voxel.engine.io.Input;
//import com.mikedeejay2.voxel.engine.io.Window;
//import org.joml.Vector2f;
//import org.joml.Vector3f;
//import org.lwjgl.glfw.GLFW;
//
//public class FreeLook
//{
//    Vector2f centerPosition = new Vector2f(Window.getWidth() / 2, Window.getHeight() / 2);
//
//    Camera camera;
//
//    boolean mouseLocked = false;
//    private float sensitivity;
//    private int unlockMouseKey;
//
//    public FreeLook(Camera camera, float sensitivity)
//    {
//        this(camera, sensitivity, GLFW.GLFW_KEY_ESCAPE);
//    }
//
//    public FreeLook(Camera camera, float sensitivity, int unlockMouseKey)
//    {
//        System.out.println("Freelook");
//        this.sensitivity = sensitivity;
//        this.unlockMouseKey = unlockMouseKey;
//        this.camera = camera;
//    }
//
//    public void input(float delta)
//    {
//        System.out.println("TO IMPUT OF FREELOOK OF CAMERA");
//        if (Input.getKey(unlockMouseKey))
//        {
//            Input.setCursor(true);
//            mouseLocked = false;
//        }
//        if (Input.getMouse(0))
//        {
//            Input.setMousePosition(centerPosition);
//            Input.setCursor(false);
//            mouseLocked = true;
//        }
//
//        if (mouseLocked)
//        {
//            Vector2f deltaPos = Input.getMousePosition().sub(centerPosition);
//
//            boolean rotY = deltaPos.x != 0;
//            boolean rotX = deltaPos.x != 0;
//            System.out.println("roty " + rotY + " rotX " + rotX);
//
//            if (rotY)
//            {
//                camera.getRotation().add(0, 1, 0, (float) Math.toRadians(deltaPos.x * sensitivity));
//            }
//            if (rotX)
//            {
//                camera.getRotation().add(1, 0, 0, (float) Math.toRadians(deltaPos.x * sensitivity));
//            }
//
//            if (rotY || rotX)
//                Input.setMousePosition(new Vector2f(Window.getWidth() / 2, Window.getHeight() / 2));
//        }
//    }
//}
