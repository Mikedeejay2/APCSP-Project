package com.mikedeejay2.apcspfinal.graphics.hud;

import com.mikedeejay2.apcspfinal.Main;
import com.mikedeejay2.apcspfinal.graphics.font.TextMaster;
import com.mikedeejay2.apcspfinal.loaders.font.creator.FontType;
import com.mikedeejay2.apcspfinal.loaders.font.creator.GUIText;
import com.mikedeejay2.apcspfinal.player.Player;
import org.joml.Vector2f;

import java.io.File;

public class PlayerHUD
{
    Player player;

    float fontColor = 1f;
    float shadowColor = 0.5f;

    FontType font;

    GUIText selectedVoxel;

    String currentSelectedVoxel = "";

    Main main;

    public PlayerHUD(Player player)
    {
        main = Main.getInstance();
        this.player = player;
    }

    public void init()
    {
        font = new FontType(Main.getLoader().loadTexture("fonts/ascii.png"), new File("res/fonts/ascii.fnt"));
        selectedVoxel = new GUIText("", 1, font, new Vector2f(0.03f, 0.92f), 100, false);
        selectedVoxel.setColor(1, 1, 1);
        selectedVoxel.setShadowColor(0.3f, 0.3f, 0.3f);
        selectedVoxel.setHasShadow(true);
    }

    public void update()
    {
        if(!currentSelectedVoxel.equals(player.getCurrentVoxel()))
        {
            TextMaster.editText(selectedVoxel, player.getCurrentVoxel().substring(0, 1).toUpperCase() + player.getCurrentVoxel().substring(1));
        }
    }
}
