package engine.objects;

import engine.io.Input;
import engine.io.Window;
import engine.maths.Vector2f;
import engine.maths.Vector3f;
import org.lwjgl.glfw.GLFW;

public class FreeLook
{
    public static final Vector3f yAxis = new Vector3f(0, 1, 0);
    Vector2f centerPosition = new Vector2f(Window.getWidth() / 2, Window.getHeight() / 2);

    Camera camera;

    boolean mouseLocked = false;
    private float sensitivity;
    private int unlockMouseKey;

    public FreeLook(Camera camera, float sensitivity)
    {
        this(camera, sensitivity, GLFW.GLFW_KEY_ESCAPE);
    }

    public FreeLook(Camera camera, float sensitivity, int unlockMouseKey)
    {
        this.sensitivity = sensitivity;
        this.unlockMouseKey = unlockMouseKey;
        this.camera = camera;
    }

    public void input()
    {
        if (Input.isKeyDown(unlockMouseKey))
        {
            Input.setCursor(true);
            mouseLocked = false;
        }
        if (Input.isButtonDown(0))
        {
            Input.setMousePosition(centerPosition);
            Input.setCursor(false);
            mouseLocked = true;
        }

        if (mouseLocked)
        {
            Vector2f deltaPos = Input.getMousePosition().sub(centerPosition);

            boolean rotY = deltaPos.getX() != 0;
            boolean rotX = deltaPos.getY() != 0;

            if (rotY)
                camera.getTransform().rotate(yAxis, (float) Math.toRadians(deltaPos.getX() * sensitivity));
            if (rotX)
                camera.getTransform().rotate(camera.getTransform().getRot().getRight(), (float) Math.toRadians(deltaPos.getY() * sensitivity));

            if (rotY || rotX)
                Input.setMousePosition(new Vector2f(Window.getWidth() / 2, Window.getHeight() / 2));
        }
    }
}
