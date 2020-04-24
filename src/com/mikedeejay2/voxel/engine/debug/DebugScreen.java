package com.mikedeejay2.voxel.engine.debug;

import com.mikedeejay2.voxel.engine.core.Time;
import com.mikedeejay2.voxel.engine.graphics.font.TextMaster;
import com.mikedeejay2.voxel.engine.io.Window;
import com.mikedeejay2.voxel.engine.loaders.font.creator.FontType;
import com.mikedeejay2.voxel.engine.loaders.font.creator.GUIText;
import com.mikedeejay2.voxel.game.Main;
import org.joml.Vector2f;

import java.io.File;

public class DebugScreen
{
    boolean isEnabled;

    float fontSize;

    float fontColorR, fontColorG, fontColorB;

    FontType font;

    GUIText versionText;
    GUIText fpsText;
    GUIText locationText;

    Main main;

    public void init()
    {
        main = Main.getInstance();
        isEnabled = false;
        fontColorR = 1;
        fontColorG = 1;
        fontColorB = 1;
        fontSize = 0.8f;
        font = new FontType(Main.getLoader().loadTexture("fonts/ascii.png"), new File("res/fonts/ascii.fnt"));
    }

    public void update()
    {
        if (isEnabled)
        {
            TextMaster.editText(fpsText, Window.getFPS() + " fps");
            TextMaster.editText(locationText, "X: " + Math.round(main.getCamera().getPosition().x * 100) / 100f + ", Y: " + Math.round(main.getCamera().getPosition().y * 100) / 100f + ", Z: " + Math.round(main.getCamera().getPosition().z * 100) / 100f +
                    " (" + Math.round(main.getCamera().getYaw() * 100) / 100f + ", " + Math.round(main.getCamera().getPitch() * 100) / 100f + ")");
        }
    }

    public void toggle()
    {
        isEnabled = !isEnabled;
        if(isEnabled)
        {
            versionText = new GUIText("Mikedeejay2 Voxel Engine Alpha 1.0.0", fontSize, font, new Vector2f(0.003f, 0.0f), 1f, false);
            versionText.setcolor(1, 1, 1);
            fpsText = new GUIText(Window.getFPS() + " fps", fontSize, font, new Vector2f(0.003f, 0.03f), 1f, false);
            fpsText.setcolor(1, 1, 1);
            locationText = new GUIText("X: " + Math.round(main.getCamera().getPosition().x * 100) / 100f + ", Y: " + Math.round(main.getCamera().getPosition().y * 100) / 100f + ", Z: " + Math.round(main.getCamera().getPosition().z * 100) / 100f +
                    " (" + Math.round(main.getCamera().getYaw() * 100) / 100f + ", " + Math.round(main.getCamera().getPitch() * 100) / 100f + ")", fontSize, font, new Vector2f(0.003f, 0.06f), 1f, false);
            locationText.setcolor(1, 1, 1);
        }
        else
        {
            versionText.remove();
            fpsText.remove();
            locationText.remove();
        }
    }
}
