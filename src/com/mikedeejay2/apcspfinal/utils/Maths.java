package com.mikedeejay2.apcspfinal.utils;

import com.mikedeejay2.apcspfinal.graphics.objects.Camera;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/*
 * This class was based off of ThinMatrix's Youtube game engine
 * tutorials, but had to be completely programmed from scratch
 * because I'm using a newer version of LWJGL3.
 */
public class Maths
{
    public static Matrix4f createTransformationMatrix(Vector3f translation, float rx, float ry, float rz, float scale, Matrix4f matrix)
    {
        matrix.identity();
        matrix.translate(translation, matrix);
        matrix.rotate((float) Math.toRadians(rx), new Vector3f(1, 0, 0));
        matrix.rotate((float) Math.toRadians(ry), new Vector3f(0, 1, 0));
        matrix.rotate((float) Math.toRadians(rz), new Vector3f(0, 0, 1));
        matrix.scale(new Vector3f(scale, scale, scale));
        return matrix;
    }

    public static Matrix4f createViewMatrix(Camera camera, Matrix4f matrix)
    {
        matrix.identity();
        matrix.rotate((float) Math.toRadians(camera.getPitch()), 1, 0, 0);
        matrix.rotate((float) Math.toRadians(camera.getYaw()), 0, 1, 0);
        matrix.translate((float)-camera.getPosition().x, (float)-camera.getPosition().y, (float)-camera.getPosition().z);
        return matrix;
    }

    public static <T> int getLength(T[][][] arr){
        ArrayList l = new ArrayList(Arrays.asList(arr));
        l.removeAll(Collections.singleton(null));
        return l.size();
    }

    public static float lerp(float a, float b, float f)
    {
        return a + f * (b - a);
    }
}
