package com.mikedeejay2.apcspfinal.debug;

import com.mikedeejay2.apcspfinal.graphics.font.TextMaster;
import com.mikedeejay2.apcspfinal.io.Window;
import com.mikedeejay2.apcspfinal.loaders.font.creator.FontType;
import com.mikedeejay2.apcspfinal.loaders.font.creator.GUIText;
import com.mikedeejay2.apcspfinal.Main;
import org.joml.Vector2f;

import java.io.File;

public class DebugScreen
{
    boolean isEnabled;

    float fontSize;

    float fontColorR, fontColorG, fontColorB;

    float fontColor = 1f;
    float shadowColor = 0.5f;

    FontType font;

    GUIText versionText;
    GUIText fpsText;
    GUIText locationText;
    GUIText chunkLocationText;

    Main main;

    public DebugScreen(Main main)
    {
        this.main = main;
    }

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
            TextMaster.editText(fpsText, Window.getFPS() + " fps, " + main.getWorld().getChunkUpdates() + " chunk updates, " + main.getWorld().getAllChunksSize() + " chunks loaded");
            TextMaster.editText(locationText,
                    "X: " + Math.round(main.getCamera().getWorldPos().x * 100) / 100f +
                            ", Y: " + Math.round(main.getCamera().getWorldPos().y * 100) / 100f +
                            ", Z: " + Math.round(main.getCamera().getWorldPos().z * 100) / 100f +
                    " (" + Math.round(main.getCamera().getYaw() * 100) / 100f + ", " +
                            Math.round(main.getCamera().getPitch() * 100) / 100f + ")" + getDirection());
            TextMaster.editText(chunkLocationText, "Chunk Location " +
                    main.getWorld().getPlayerChunk().x + ", " +
                    main.getWorld().getPlayerChunk().y + ", " +
                    main.getWorld().getPlayerChunk().z);
        }
    }

    public void toggle()
    {
        isEnabled = !isEnabled;
        if(isEnabled)
        {
            versionText = new GUIText("Mikedeejay2 Voxel Engine Alpha 1.0.0", fontSize, font, new Vector2f(0.003f, 0.0f), 1f, false);

            fpsText = new GUIText("", fontSize, font, new Vector2f(0.003f, 0.03f), 1f, false);
            locationText = new GUIText("", fontSize, font, new Vector2f(0.003f, 0.06f), 1f, false);
            chunkLocationText = new GUIText("", fontSize, font, new Vector2f(0.003f, 0.09f), 1f, false);

            versionText.setColor(fontColor, fontColor, fontColor);
            fpsText.setColor(fontColor, fontColor, fontColor);
            locationText.setColor(fontColor, fontColor, fontColor);
            chunkLocationText.setColor(fontColor, fontColor, fontColor);

//            versionText.setShadowColor(shadowColor, shadowColor, shadowColor);
//            fpsText.setShadowColor(shadowColor, shadowColor, shadowColor);
//            locationText.setShadowColor(shadowColor, shadowColor, shadowColor);
//            chunkLocationText.setShadowColor(shadowColor, shadowColor, shadowColor);
//
//            versionText.setHasShadow(true);
//            fpsText.setHasShadow(true);
//            locationText.setHasShadow(true);
//            chunkLocationText.setHasShadow(true);
        }
        else
        {
            versionText.remove();
            fpsText.remove();
            locationText.remove();
            chunkLocationText.remove();
        }
    }

    private String getDirection()
    {
        if(main.getCamera().getYaw() >= 315 || (main.getCamera().getYaw() >= 0 && main.getCamera().getYaw() <= 45)) return " Facing: South";
        if((main.getCamera().getYaw() >= 45 && main.getCamera().getYaw() <= 135)) return " Facing: West";
        if((main.getCamera().getYaw() >= 135 && main.getCamera().getYaw() <= 225)) return " Facing: North";
        if((main.getCamera().getYaw() >= 225 && main.getCamera().getYaw() <= 315)) return " Facing: East";
        else return " Facing: Unknown";
    }
}
